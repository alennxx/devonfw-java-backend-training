package com.devonfw.app.java.order.orderservice.logic.impl.usecase;

import java.util.Objects;
import java.util.Optional;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageItem;
import com.devonfw.app.java.order.orderservice.logic.base.usecase.AbstractItemUc;

/**
 * @author NGASIORO
 *
 */
@Named
@Validated
@Transactional
public class UcManageItemImpl extends AbstractItemUc implements UcManageItem {

  private static final Logger LOG = LoggerFactory.getLogger(UcManageItemImpl.class);

  @Override
  public boolean deleteItem(long itemId) {

    Optional<ItemEntity> item = getItemRepository().findById(itemId);
    if (item.isPresent()) {
      getItemRepository().delete(item.get());
      LOG.debug("Deleted Item with id {}.", itemId);
      return true;
    }
    LOG.debug("Item with id {} not found for deletion", itemId);
    return false;
  }

  @Override
  public ItemEto saveItem(ItemEto item) {

    Objects.requireNonNull(item, "item");

    ItemEntity itemEntity = getBeanMapper().map(item, ItemEntity.class);
    itemEntity = getItemRepository().save(itemEntity);
    LOG.debug("Saved item with id {} in db", itemEntity.getId());
    return getBeanMapper().map(itemEntity, ItemEto.class);
  }

  @Override
  public boolean increaseItemPriceByName(String name, Double amount) {

    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(amount, "amount");

    Optional<ItemEntity> foundItem = getItemRepository().findByName(name);
    if (foundItem.isPresent()) {
      ItemEntity item = foundItem.get();
      item.setPrice(item.getPrice() + amount);
      getItemRepository().save(item);
      return true;
    }
    LOG.debug("Item with name {} not found for price adjustment", name);
    return false;
  }

}
