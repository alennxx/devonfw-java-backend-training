package com.devonfw.app.java.order.orderservice.logic.api.to;

import com.devonfw.app.java.order.general.common.api.to.AbstractSearchCriteriaTo;
import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;

/**
 * {@link SearchCriteriaTo} to find instances of {@link com.devonfw.app.java.order.orderservice.common.api.Customer}s.
 */
public class CustomerSearchCriteriaTo extends AbstractSearchCriteriaTo {

  private static final long serialVersionUID = 1L;

  private String firstName;

  private String lastName;

  private StringSearchConfigTo firstNameOption;

  private StringSearchConfigTo lastNameOption;

  /**
   * @return firstNameId
   */
  public String getFirstName() {

    return this.firstName;
  }

  /**
   * @param firstName setter for firstName attribute
   */
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * @return lastNameId
   */
  public String getLastName() {

    return this.lastName;
  }

  /**
   * @param lastName setter for lastName attribute
   */
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getFirstName() firstName}.
   */
  public StringSearchConfigTo getFirstNameOption() {

    return this.firstNameOption;
  }

  /**
   * @param firstNameOption new value of {@link #getFirstNameOption()}.
   */
  public void setFirstNameOption(StringSearchConfigTo firstNameOption) {

    this.firstNameOption = firstNameOption;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getLastName() lastName}.
   */
  public StringSearchConfigTo getLastNameOption() {

    return this.lastNameOption;
  }

  /**
   * @param lastNameOption new value of {@link #getLastNameOption()}.
   */
  public void setLastNameOption(StringSearchConfigTo lastNameOption) {

    this.lastNameOption = lastNameOption;
  }

}
