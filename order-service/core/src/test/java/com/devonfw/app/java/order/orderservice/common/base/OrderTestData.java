package com.devonfw.app.java.order.orderservice.common.base;

import java.time.LocalDate;

import com.devonfw.app.java.order.common.builders.OrderEtoBuilder;
import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;

/**
 * @author NGASIORO
 *
 */
public interface OrderTestData {

  OrderEtoBuilder ORDER_NEW = new OrderEtoBuilder()//
      .creationDate(LocalDate.now())//
      .price(120.3)//
      .status(OrderStatus.NEW);

  OrderEtoBuilder ORDER_PAID = new OrderEtoBuilder()//
      .creationDate(LocalDate.now())//
      .price(300.5)//
      .status(OrderStatus.PAID);

}
