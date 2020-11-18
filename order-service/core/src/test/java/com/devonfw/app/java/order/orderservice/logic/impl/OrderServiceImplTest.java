package com.devonfw.app.java.order.orderservice.logic.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.common.base.CustomerTestData;
import com.devonfw.app.java.order.orderservice.common.base.ItemTestData;
import com.devonfw.app.java.order.orderservice.common.base.OrderTestData;
import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.module.test.common.base.ComponentTest;

/**
 * @author NGASIORO
 *
 */
@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class OrderServiceImplTest extends ComponentTest {

  @Inject
  private Orderservice mSUT;

  @Test
  public void shouldIncreasePriceForItem() {

    // given
    prepareItems();
    String name = ItemTestData.SPAGHETTI_BOLOGNESE_NAME;
    Double amount = 12.5;
    Double priceBefore = this.mSUT.findItemByName(name).getPrice();

    // when
    boolean updated = this.mSUT.increaseItemPriceByName(name, amount);

    // then
    Double priceAfter = this.mSUT.findItemByName(name).getPrice();
    assertThat(updated).isTrue();
    assertThat(priceAfter).isGreaterThan(priceBefore);
    assertThat(priceAfter).isEqualTo(priceBefore + amount);
  }

  @Test
  public void shouldNotIncreasePriceForNotExistingItem() {

    // given
    prepareItems();
    String name = "Not existing item";
    Double amount = 12.5;

    // when
    boolean updated = this.mSUT.increaseItemPriceByName(name, amount);

    // then
    assertThat(updated).isFalse();
  }

  @Test
  public void shouldCreateOrderWithTwoPositionsAndOwner() {

    // given
    OrderEto order = OrderTestData.ORDER_PAID.createNew();
    CustomerEto owner = CustomerTestData.STEVE_JOBS.createNew();
    Set<ItemEto> positions = new HashSet<>(Arrays.asList(//
        ItemTestData.BOLOGNESE.createNew(), //
        ItemTestData.CARBONARA.createNew()));

    OrderCto orderCto = new OrderCto();
    orderCto.setOrder(order);
    orderCto.setOrderPositions(positions);
    orderCto.setOwner(owner);

    // when
    OrderCto result = this.mSUT.saveOrder(orderCto);

    // then
    assertThat(result).isNotNull();

    OrderEto resultOrder = result.getOrder();
    assertThat(resultOrder).isNotNull();
    assertThat(resultOrder.getId()).isNotNull();
    assertThat(resultOrder.getCreationDate()).isEqualTo(order.getCreationDate());
    assertThat(resultOrder.getOwnerId()).isNotNull();
    assertThat(resultOrder.getPrice()).isEqualTo(order.getPrice());
    assertThat(resultOrder.getStatus()).isEqualTo(order.getStatus());

    Set<ItemEto> resultPositions = result.getOrderPositions();
    assertThat(resultPositions).isNotNull();
    assertThat(resultPositions).hasSize(positions.size());
    assertThat(resultPositions).extracting("name").contains(//
        ItemTestData.SPAGHETTI_BOLOGNESE_NAME, ItemTestData.SPAGHETTI_CARBONARA_NAME);

    CustomerEto resultOwner = result.getOwner();
    assertThat(resultOwner).isNotNull();
    assertThat(resultOwner.getFirstName()).isEqualTo(owner.getFirstName());
    assertThat(resultOwner.getLastName()).isEqualTo(owner.getLastName());
  }

  @Test
  public void shouldFindOrderByCreationDateAndStatus() {

    // given
    LocalDate creationDate = LocalDate.of(2010, 10, 10);
    OrderStatus status = OrderStatus.NEW;
    OrderEto order = OrderTestData.ORDER_NEW//
        .creationDate(creationDate)//
        .ownerId(prepareCustomer().getId())//
        .createNew();
    order = this.mSUT.saveOrder(OrderTestData.ORDER_NEW.creationDate(creationDate).createNew());

    // when
    List<OrderEto> result = this.mSUT.findOrdersByCreationDateAndStatus(creationDate, status);

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualToComparingFieldByField(order);
  }

  @Test
  public void shouldFindItemsWithNameLikeOrdered() {

    // given
    prepareItems();
    String name = "Spaghetti";

    // when
    Page<ItemEto> result = this.mSUT.findItemsWithNameLikeOrdered(name);

    // then
    assertThat(result).hasSize(2);
    assertThat(result.getContent()).extracting("name")//
        .containsExactly(ItemTestData.SPAGHETTI_BOLOGNESE_NAME, ItemTestData.SPAGHETTI_CARBONARA_NAME);
  }

  @Test
  public void shouldNotFindItemsWithNameLikeCaseSensitive() {

    // given
    prepareItems();
    String name = "spaghetti";

    // when
    Page<ItemEto> result = this.mSUT.findItemsWithNameLikeOrdered(name);

    // then
    assertThat(result).isEmpty();
  }

  private void prepareItems() {

    this.mSUT.saveItem(ItemTestData.BOLOGNESE.createNew());
    this.mSUT.saveItem(ItemTestData.CARBONARA.createNew());
  }

  private CustomerEto prepareCustomer() {

    return this.mSUT.saveCustomer(CustomerTestData.STEVE_JOBS.createNew());
  }

}
