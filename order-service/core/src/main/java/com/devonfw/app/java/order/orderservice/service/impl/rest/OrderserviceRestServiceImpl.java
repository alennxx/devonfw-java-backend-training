package com.devonfw.app.java.order.orderservice.service.impl.rest;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.app.java.order.orderservice.service.api.rest.OrderserviceRestService;

/**
 * @author NGASIORO
 *
 */
@Named("OrderserviceRestService")
public class OrderserviceRestServiceImpl implements OrderserviceRestService {

  @Inject
  private Orderservice orderService;

  @Override
  public ItemEto findItemByName(String name) {

    return this.orderService.findItemByName(name);
  }

  @Override
  public Page<ItemEto> findItemsWithNameLikeOrdered(String name) {

    return this.orderService.findItemsWithNameLikeOrdered(name);
  }

  @Override
  public ItemEto saveItem(ItemEto item) {

    return this.orderService.saveItem(item);
  }

  @Override
  public void increaseItemPriceByName(String name, Double amount) {

    this.orderService.increaseItemPriceByName(name, amount);
  }

  @Override
  public void deleteItem(long id) {

    this.orderService.deleteItem(id);
  }

  @Override
  public CustomerEto saveCustomer(CustomerEto customer) {

    return this.orderService.saveCustomer(customer);
  }

  @Override
  public void deleteCustomer(long id) {

    this.orderService.deleteCustomer(id);
  }

  @Override
  public List<OrderEto> findOrdersByCreationDateAndStatus(LocalDate creationDate, OrderStatus status) {

    return findOrdersByCreationDateAndStatus(creationDate, status);
  }

  @Override
  public OrderCto saveOrder(OrderCto order) {

    return this.orderService.saveOrder(order);
  }

  @Override
  public void deleteOrder(long id) {

    this.orderService.deleteOrder(id);
  }

}
