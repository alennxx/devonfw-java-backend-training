package com.devonfw.app.java.order.general.service.impl.rest;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.devonfw.app.java.order.SpringBootApp;
import com.devonfw.app.java.order.general.base.AbstractRestServiceTest;
import com.devonfw.app.java.order.general.common.base.test.DbTestHelper;
import com.devonfw.app.java.order.orderservice.common.base.ItemTestData;
import com.devonfw.app.java.order.orderservice.dataaccess.api.repo.CustomerRepository;
import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.service.api.rest.OrderserviceRestService;

/**
 * @author NGASIORO
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringBootApp.class, OrderServiceRestTestConfig.class })
public class OrderServiceRestTest extends AbstractRestServiceTest {

  @Inject
  private OrderServiceRestTestHelper helper;

  @Inject
  private OrderserviceRestService service;

  @Inject
  private DbTestHelper dbTestHelper;

  @Inject
  private Orderservice orderService;

  @Inject
  private CustomerRepository customerRepository;

  @Override
  protected void doSetUp() {

    super.doSetUp();
    this.dbTestHelper.resetDatabase();
  }

  @Override
  protected void doTearDown() {

    this.service = null;
    super.doTearDown();
  }

  @Test
  public void shouldFindItemByName() {

    // given
    OrderCto order = this.helper.createDummyOrderCTO();
    this.service.saveOrder(order);
    // when
    ItemEto item = this.service.findItemByName(ItemTestData.SPAGHETTI_BOLOGNESE_NAME);
    // then
    assertThat(item).isNotNull();
    assertThat(item.getName()).isEqualTo(ItemTestData.SPAGHETTI_BOLOGNESE_NAME);
  }

}
