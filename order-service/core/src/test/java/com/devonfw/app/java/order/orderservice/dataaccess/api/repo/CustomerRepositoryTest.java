package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerSearchCriteriaTo;
import com.devonfw.module.test.common.base.ComponentTest;

/**
 * @author NGASIORO
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CustomerRepositoryTest extends ComponentTest {

  private static final String CUSTOMER_FIRST_NAME = "Steve";

  private static final String CUSTOMER_LAST_NAME = "Jobs";

  @Inject
  private CustomerRepository mSUT;

  @Override
  protected void doTearDown() {

    super.doTearDown();
    this.mSUT.deleteAll();
  }

  @Test
  public void shouldFindCustomerByFirstNameCriterion() {

    // given
    addCustomerToRepository(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME);
    CustomerSearchCriteriaTo criteria = new CustomerSearchCriteriaTo();
    criteria.setFirstName(CUSTOMER_FIRST_NAME);

    // when
    List<CustomerEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getFirstName()).isEqualTo(criteria.getFirstName());
  }

  @Test
  public void shouldFindCustomerByLastNameCriterion() {

    // given
    addCustomerToRepository(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME);
    CustomerSearchCriteriaTo criteria = new CustomerSearchCriteriaTo();
    criteria.setLastName(CUSTOMER_LAST_NAME);

    // when
    List<CustomerEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getLastName()).isEqualTo(criteria.getLastName());
  }

  @Test
  public void shouldFindCustomerByCriteria() {

    // given
    addCustomerToRepository(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME);
    CustomerSearchCriteriaTo criteria = new CustomerSearchCriteriaTo();
    criteria.setFirstName(CUSTOMER_FIRST_NAME);
    criteria.setLastName(CUSTOMER_LAST_NAME);

    // when
    List<CustomerEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(1);

    CustomerEntity customer = result.get(0);
    assertThat(customer.getFirstName()).isEqualTo(criteria.getFirstName());
    assertThat(customer.getLastName()).isEqualTo(criteria.getLastName());
  }

  @Test
  public void shouldFindAndSortCustomersByFirstName() {

    // given
    addCustomerToRepository(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME);
    addCustomerToRepository("Bill", CUSTOMER_LAST_NAME);

    Pageable pageable = PageRequest.of(0, 20, Sort.by("firstName").descending());
    CustomerSearchCriteriaTo criteria = new CustomerSearchCriteriaTo();
    criteria.setLastName(CUSTOMER_LAST_NAME);
    criteria.setPageable(pageable);

    // when
    List<CustomerEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    String firstCustomerFN = result.get(0).getFirstName();
    String secondCustomerFN = result.get(1).getFirstName();
    assertThat(firstCustomerFN.compareTo(secondCustomerFN)).isGreaterThan(0);
  }

  @Test
  public void shouldFindAndSortCustomersByLastName() {

    // given
    addCustomerToRepository(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME);
    addCustomerToRepository(CUSTOMER_FIRST_NAME, "Gates");

    Pageable pageable = PageRequest.of(0, 20, Sort.by("lastName").descending());
    CustomerSearchCriteriaTo criteria = new CustomerSearchCriteriaTo();
    criteria.setFirstName(CUSTOMER_FIRST_NAME);
    criteria.setPageable(pageable);

    // when
    List<CustomerEntity> result = this.mSUT.findByCriteria(criteria).getContent();

    // then
    assertThat(result).hasSize(2);

    String firstCustomerLN = result.get(0).getLastName();
    String secondCustomerLN = result.get(1).getLastName();
    assertThat(firstCustomerLN.compareTo(secondCustomerLN)).isGreaterThan(0);
  }

  @Test
  public void shouldRemoveCustomerById() {

    // given
    long id = addCustomerToRepository(CUSTOMER_FIRST_NAME, CUSTOMER_LAST_NAME).getId();
    long before = this.mSUT.count();

    // when
    this.mSUT.deleteById(id);

    // then
    long after = this.mSUT.count();
    assertThat(after).isLessThan(before);
    assertThat(after).isEqualTo(before - 1L);

    assertThat(this.mSUT.findAll()).extracting("id").doesNotContain(id);
  }

  private CustomerEntity addCustomerToRepository(String firstName, String lastName) {

    CustomerEntity customer = new CustomerEntity();
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    return this.mSUT.save(customer);
  }

}
