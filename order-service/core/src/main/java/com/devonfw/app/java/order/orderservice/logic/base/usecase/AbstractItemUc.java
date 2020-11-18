package com.devonfw.app.java.order.orderservice.logic.base.usecase;

import javax.inject.Inject;

import com.devonfw.app.java.order.general.logic.base.AbstractUc;
import com.devonfw.app.java.order.orderservice.dataaccess.api.repo.ItemRepository;

/**
 * @author NGASIORO
 *
 */
public class AbstractItemUc extends AbstractUc {

  @Inject
  private ItemRepository itemRepository;

  public ItemRepository getItemRepository() {

    return this.itemRepository;
  }

}
