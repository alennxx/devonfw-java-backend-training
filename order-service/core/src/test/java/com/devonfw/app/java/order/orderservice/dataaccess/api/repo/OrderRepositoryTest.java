package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.OrderEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderSearchCriteriaTo;
import com.devonfw.module.test.common.base.ComponentTest;

/**
 * @author NGASIORO
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class OrderRepositoryTest extends ComponentTest {

  private static final LocalDate ORDER_CREATION_DATE = LocalDate.now();

  private static final Double ORDER_PRICE = new Double(200);

  private static final OrderStatus ORDER_STATUS = OrderStatus.NEW;

  @Inject
  private OrderRepository mSUT;

  @Inject
  private CustomerRepository customerRepository;

  @Inject
  private ItemRepository itemRepository;

  @Override
  protected void doTearDown() {

    super.doTearDown();
    this.mSUT.deleteAll();
    this.customerRepository.deleteAll();
    this.itemRepository.deleteAll();
  }

  @Test
  public void shouldCreateOrderWithTwoOrderPositionsAndOwner() {

    // given
    CustomerEntity owner = addCustomerToRepository();

    ItemEntity item1 = new ItemEntity();
    item1.setName("spaghetti bolognese");
    item1.setDescription("Italian pasta");
    item1.setPrice(120.5);
    this.itemRepository.save(item1);

    ItemEntity item2 = new ItemEntity();
    item2.setName("tagiatelle");
    item2.setDescription("more pasta");
    item2.setPrice(110.5);
    this.itemRepository.save(item2);

    Set<ItemEntity> positions = new HashSet<>(Arrays.asList(item1, item2));

    OrderEntity order = new OrderEntity();
    order.setCreationDate(ORDER_CREATION_DATE);
    order.setPrice(ORDER_PRICE);
    order.setOwnerId(owner.getId());
    order.setOwner(owner);
    order.setOrderPositions(positions);
    order.setStatus(ORDER_STATUS);

    long before = this.mSUT.count();

    // when
    OrderEntity result = this.mSUT.save(order);

    // then
    assertThat(result.getId()).isEqualTo(order.getId());
    assertThat(result.getCreationDate()).isEqualTo(order.getCreationDate());
    assertThat(result.getOwnerId()).isEqualTo(owner.getId());

    CustomerEntity resultOwner = result.getOwner();
    assertThat(resultOwner.getId()).isEqualTo(owner.getId());
    assertThat(resultOwner.getFirstName()).isEqualTo(owner.getFirstName());
    assertThat(resultOwner.getLastName()).isEqualTo(owner.getLastName());

    assertThat(result.getOrderPositions()).extracting("name").containsOnly(item1.getName(), item2.getName());

    long after = this.mSUT.count();
    assertThat(after).isGreaterThan(before);
  }

  @Test
  public void shouldFindOrderByCreationDateCriterion() {

    // given
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, addCustomerToRepository());
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setCreationDate(ORDER_CREATION_DATE);

    // when
    List<OrderEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getCreationDate()).isEqualTo(criteria.getCreationDate());
  }

  @Test
  public void shouldFindOrderByOwnerCriterion() {

    // given
    CustomerEntity owner = addCustomerToRepository();
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, owner);
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setOwnerId(owner.getId());

    // when
    List<OrderEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getOwnerId()).isEqualTo(criteria.getOwnerId());
  }

  @Test
  public void shouldFindOrderByPriceCriterion() {

    // given
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, addCustomerToRepository());
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setPrice(ORDER_PRICE);

    // when
    List<OrderEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getPrice()).isEqualTo(criteria.getPrice());
  }

  @Test
  public void shouldFindOrderByStatusCriterion() {

    // given
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, addCustomerToRepository());
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setStatus(ORDER_STATUS);

    // when
    List<OrderEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getStatus()).isEqualTo(criteria.getStatus());
  }

  @Test
  public void shouldFindOrderByCriteria() {

    // given
    CustomerEntity owner = addCustomerToRepository();
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, owner);
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setCreationDate(ORDER_CREATION_DATE);
    criteria.setOwnerId(owner.getId());
    criteria.setPrice(ORDER_PRICE);
    criteria.setStatus(ORDER_STATUS);

    // when
    List<OrderEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);

    OrderEntity order = result.get(0);
    assertThat(order.getCreationDate()).isEqualTo(criteria.getCreationDate());
    assertThat(order.getOwnerId()).isEqualTo(criteria.getOwnerId());
    assertThat(order.getPrice()).isEqualTo(criteria.getPrice());
    assertThat(order.getStatus()).isEqualTo(criteria.getStatus());
  }

  @Test
  public void shouldFindAndSortOrdersByCreationDate() {

    // given
    CustomerEntity owner = addCustomerToRepository();
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, owner);
    addOrderToRepository(LocalDate.of(2000, 1, 1), ORDER_PRICE, ORDER_STATUS, owner);

    Pageable pageable = PageRequest.of(0, 20, Sort.by("creationDate").descending());
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setOwnerId(owner.getId());
    criteria.setPageable(pageable);

    // when
    List<OrderEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    LocalDate firstOrderCD = result.get(0).getCreationDate();
    LocalDate secondOrderCD = result.get(1).getCreationDate();
    assertThat(firstOrderCD).isAfter(secondOrderCD);
  }

  @Test
  public void shouldFindAndSortOrdersByPrice() {

    // given
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, addCustomerToRepository());
    addOrderToRepository(ORDER_CREATION_DATE, new Double(99999), ORDER_STATUS, addCustomerToRepository());

    Pageable pageable = PageRequest.of(0, 20, Sort.by("price").descending());
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setCreationDate(ORDER_CREATION_DATE);
    criteria.setPageable(pageable);

    // when
    List<OrderEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    Double firstOrderPrice = result.get(0).getPrice();
    Double secondOrderPrice = result.get(1).getPrice();
    assertThat(firstOrderPrice).isGreaterThan(secondOrderPrice);
  }

  @Test
  public void shouldFindAndSortOrdersByStatus() {

    // given
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, addCustomerToRepository());
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, OrderStatus.SERVED, addCustomerToRepository());

    Pageable pageable = PageRequest.of(0, 20, Sort.by("price").descending());
    OrderSearchCriteriaTo criteria = new OrderSearchCriteriaTo();
    criteria.setCreationDate(ORDER_CREATION_DATE);
    criteria.setPageable(pageable);

    // when
    List<OrderEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    OrderStatus firstOrderStatus = result.get(0).getStatus();
    OrderStatus secondOrderStatus = result.get(1).getStatus();
    assertThat(firstOrderStatus).isGreaterThan(secondOrderStatus);
  }

  @Test
  public void shouldFindOrderByCreationDateAndStatus() {

    // given
    addOrderToRepository(ORDER_CREATION_DATE, ORDER_PRICE, ORDER_STATUS, addCustomerToRepository());

    // when
    List<OrderEntity> result = this.mSUT.findAllByCreationDateAndStatus(ORDER_CREATION_DATE, ORDER_STATUS);

    // then
    assertThat(result.size()).isEqualTo(1);

    OrderEntity order = result.get(0);
    assertThat(order.getCreationDate()).isEqualTo(ORDER_CREATION_DATE);
    assertThat(order.getStatus()).isEqualTo(ORDER_STATUS);
  }

  private CustomerEntity addCustomerToRepository() {

    CustomerEntity customer = new CustomerEntity();
    customer.setFirstName("Steve");
    customer.setLastName("Jobs");
    return this.customerRepository.save(customer);
  }

  private void addOrderToRepository(LocalDate creationDate, Double price, OrderStatus status, CustomerEntity owner) {

    OrderEntity order = new OrderEntity();
    order.setCreationDate(creationDate);
    order.setOwner(owner);
    order.setOwnerId(owner.getId());
    order.setPrice(price);
    order.setStatus(status);
    this.mSUT.save(order);
  }

}
