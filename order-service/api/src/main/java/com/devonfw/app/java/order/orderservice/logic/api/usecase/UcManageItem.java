package com.devonfw.app.java.order.orderservice.logic.api.usecase;

import com.devonfw.app.java.order.orderservice.common.api.Item;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;

/**
 * @author NGASIORO
 *
 */
public interface UcManageItem {

  /**
   * Deletes an item from the database by its id 'itemId'
   *
   * @param itemId ID of the item to delete
   * @return boolean <code>true</code> if the item can be deleted, <code>false</code> otherwise
   */
  boolean deleteItem(long itemId);

  /**
   * Saves an item and stores it in the database
   *
   * @param item the {@link ItemEto} to create
   * @return the new {@link ItemEto} that has been saved with Id and Version
   */
  ItemEto saveItem(ItemEto item);

  /**
   * Increases price of an item with given 'name' by given 'amount'
   *
   * @param name name of the {@link Item} to update
   * @param amount amount by which price of {@link Item} should be increased
   * @return boolean <code>true</code> if the item was updated, <code>false</code> otherwise
   */
  boolean increaseItemPriceByName(String name, Double amount);

}
