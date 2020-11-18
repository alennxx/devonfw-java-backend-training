package com.devonfw.app.java.order.orderservice.common.base;

import com.devonfw.app.java.order.common.builders.CustomerEtoBuilder;

/**
 * @author NGASIORO
 *
 */
public interface CustomerTestData {

  CustomerEtoBuilder STEVE_JOBS = new CustomerEtoBuilder()//
      .firstName("Steve")//
      .lastName("Jobs");

}
