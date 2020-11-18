package com.devonfw.app.java.order.orderservice.common.base;

import com.devonfw.app.java.order.common.builders.ItemEtoBuilder;

/**
 * @author NGASIORO
 *
 */
public interface ItemTestData {

  static final String FROM_ITALY = "From Italy";

  String SPAGHETTI_BOLOGNESE_NAME = "Spaghetti Bolognese";

  String SPAGHETTI_CARBONARA_NAME = "Spaghetti Carbonara";

  ItemEtoBuilder BOLOGNESE = new ItemEtoBuilder()//
      .name(SPAGHETTI_BOLOGNESE_NAME)//
      .description(FROM_ITALY)//
      .price(220.5);

  ItemEtoBuilder CARBONARA = new ItemEtoBuilder()//
      .name(SPAGHETTI_CARBONARA_NAME)//
      .description(FROM_ITALY)//
      .price(215.9);

}
