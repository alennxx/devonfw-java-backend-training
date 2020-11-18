package com.devonfw.app.java.order.general.service.impl.rest;

import java.util.Arrays;
import java.util.HashSet;

import com.devonfw.app.java.order.orderservice.common.base.CustomerTestData;
import com.devonfw.app.java.order.orderservice.common.base.ItemTestData;
import com.devonfw.app.java.order.orderservice.common.base.OrderTestData;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;

/**
 * @author NGASIORO
 *
 */
public class OrderServiceRestTestHelper {

  public OrderCto createDummyOrderCTO() {

    OrderCto orderCto = new OrderCto();
    orderCto.setOrder(OrderTestData.ORDER_NEW.createNew());
    orderCto.setOwner(CustomerTestData.STEVE_JOBS.createNew());
    orderCto.setOrderPositions(
        new HashSet<>(Arrays.asList(ItemTestData.BOLOGNESE.createNew(), ItemTestData.CARBONARA.createNew())));

    return orderCto;
  }

}
