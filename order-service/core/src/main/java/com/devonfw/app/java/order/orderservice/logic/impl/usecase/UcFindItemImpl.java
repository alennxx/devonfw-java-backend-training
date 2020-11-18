package com.devonfw.app.java.order.orderservice.logic.impl.usecase;

import java.util.Objects;
import java.util.Optional;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcFindItem;
import com.devonfw.app.java.order.orderservice.logic.base.usecase.AbstractItemUc;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.devonfw.module.basic.common.api.query.StringSearchOperator;

/**
 * @author NGASIORO
 *
 */
@Named
@Validated
@Transactional
public class UcFindItemImpl extends AbstractItemUc implements UcFindItem {

  private static final Logger LOG = LoggerFactory.getLogger(UcFindItemImpl.class);

  @Override
  public ItemEto findItem(long id) {

    Optional<ItemEntity> itemOpt = getItemRepository().findById(id);
    if (itemOpt.isPresent()) {
      LOG.debug("Found item with id {}", id);
      ItemEntity item = itemOpt.get();
      return getBeanMapper().map(item, ItemEto.class);
    }
    LOG.debug("Item with id {} not found", id);
    return null;
  }

  @Override
  public ItemEto findItemByName(String name) {

    Objects.requireNonNull(name);

    Optional<ItemEntity> itemOpt = getItemRepository().findByName(name);
    if (itemOpt.isPresent()) {
      LOG.debug("Found item with name {}", name);
      ItemEntity item = itemOpt.get();
      return getBeanMapper().map(item, ItemEto.class);
    }
    LOG.debug("Item with name {} not found", name);
    return null;
  }

  @Override
  public Page<ItemEto> findItems(ItemSearchCriteriaTo criteria) {

    Page<ItemEntity> items = getItemRepository().findByCriteria(criteria);
    LOG.debug("Found {} items matching criteria", items.getTotalElements());
    return mapPaginatedEntityList(items, ItemEto.class);
  }

  @Override
  public Page<ItemEto> findItemsWithNameLikeOrdered(String name) {

    Objects.requireNonNull(name);

    return findItems(createSearchCriteriaForLikeName(name));
  }

  private ItemSearchCriteriaTo createSearchCriteriaForLikeName(String name) {

    Pageable pageable = PageRequest.of(0, 20, Sort.by("name"));
    StringSearchOperator syntax = StringSearchOperator.LIKE;
    StringSearchConfigTo nameOption = StringSearchConfigTo.of(syntax);
    nameOption.setIgnoreCase(false);
    nameOption.setMatchSubstring(true);

    ItemSearchCriteriaTo criteria = new ItemSearchCriteriaTo();
    criteria.setName(name);
    criteria.setNameOption(nameOption);
    criteria.setPageable(pageable);

    return criteria;
  }

}
