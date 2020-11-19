package com.devonfw.app.java.order.orderservice.logic.impl;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.general.common.api.security.ApplicationAccessControlConfig;
import com.devonfw.app.java.order.general.logic.base.AbstractComponentFacade;
import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderSearchCriteriaTo;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcFindCustomer;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcFindItem;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcFindOrder;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageCustomer;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageItem;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageOrder;

/**
 * @author NGASIORO
 */
@Named
public class OrderserviceImpl extends AbstractComponentFacade implements Orderservice {

  @Inject
  UcFindItem ucFindItem;

  @Inject
  UcManageItem ucManageItem;

  @Inject
  private UcFindCustomer ucFindCustomer;

  @Inject
  private UcManageCustomer ucManageCustomer;

  @Inject
  private UcFindOrder ucFindOrder;

  @Inject
  private UcManageOrder ucManageOrder;

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_ITEM)
  public ItemEto findItem(long id) {

    return this.ucFindItem.findItem(id);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_ITEM)
  public ItemEto findItemByName(String name) {

    return this.ucFindItem.findItemByName(name);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_ITEM)
  public Page<ItemEto> findItems(ItemSearchCriteriaTo criteria) {

    return this.ucFindItem.findItems(criteria);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_DELETE_ITEM)
  public boolean deleteItem(long itemId) {

    return this.ucManageItem.deleteItem(itemId);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_SAVE_ITEM)
  public ItemEto saveItem(ItemEto item) {

    return this.ucManageItem.saveItem(item);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_CUSTOMER)
  public CustomerEto findCustomer(long id) {

    return this.ucFindCustomer.findCustomer(id);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_CUSTOMER)
  public Page<CustomerEto> findCustomers(CustomerSearchCriteriaTo criteria) {

    return this.ucFindCustomer.findCustomers(criteria);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_SAVE_CUSTOMER)
  public CustomerEto saveCustomer(CustomerEto customer) {

    return this.ucManageCustomer.saveCustomer(customer);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_DELETE_CUSTOMER)
  public boolean deleteCustomer(long id) {

    return this.ucManageCustomer.deleteCustomer(id);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_ORDER)
  public OrderEto findOrder(long id) {

    return this.ucFindOrder.findOrder(id);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_ORDER)
  public Page<OrderEto> findOrders(OrderSearchCriteriaTo criteria) {

    return this.ucFindOrder.findOrders(criteria);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_SAVE_ORDER)
  public OrderEto saveOrder(OrderEto order) {

    return this.ucManageOrder.saveOrder(order);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_DELETE_ORDER)
  public boolean deleteOrder(long id) {

    return this.ucManageOrder.deleteOrder(id);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_SAVE_ITEM)
  public boolean increaseItemPriceByName(String name, Double amount) {

    return this.ucManageItem.increaseItemPriceByName(name, amount);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_SAVE_ORDER)
  public OrderCto saveOrder(OrderCto order) {

    return this.ucManageOrder.saveOrder(order);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_ORDER)
  public List<OrderEto> findOrdersByCreationDateAndStatus(LocalDate creationDate, OrderStatus status) {

    return this.ucFindOrder.findOrdersByCreationDateAndStatus(creationDate, status);
  }

  @Override
  @RolesAllowed(ApplicationAccessControlConfig.PERMISSION_FIND_ITEM)
  public Page<ItemEto> findItemsWithNameLikeOrdered(String name) {

    return this.ucFindItem.findItemsWithNameLikeOrdered(name);
  }

}
