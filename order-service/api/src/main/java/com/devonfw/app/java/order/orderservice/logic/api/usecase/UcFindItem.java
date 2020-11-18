package com.devonfw.app.java.order.orderservice.logic.api.usecase;

import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;

/**
 * @author NGASIORO
 *
 */
public interface UcFindItem {

  /**
   * Finds an Item by its 'id'
   *
   * @param id ID 'id' of the item
   * @return The {@link ItemEto} with 'id'
   */
  ItemEto findItem(long id);

  /**
   * Finds an Item by its 'name'
   *
   * @param name 'name' of the item
   * @return The {@link ItemEto} with 'name'
   */
  ItemEto findItemByName(String name);

  /**
   * Returns a paginated list of Items matching the search criteria
   *
   * @param criteria the {@link ItemSearchCriteriaTo}
   * @return the {@link Page} of matching {@link ItemEto}s
   */
  Page<ItemEto> findItems(ItemSearchCriteriaTo criteria);

  /**
   * Returns a paginated list of Items with name LIKE given 'name' ordered by name
   *
   * @param name
   * @return the {@link Page} of matching {@link ItemEto}s
   */
  Page<ItemEto> findItemsWithNameLikeOrdered(String name);

}
