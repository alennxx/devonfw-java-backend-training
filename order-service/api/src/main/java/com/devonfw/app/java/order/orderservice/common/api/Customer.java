package com.devonfw.app.java.order.orderservice.common.api;

import com.devonfw.app.java.order.general.common.api.ApplicationEntity;

public interface Customer extends ApplicationEntity {

  /**
   * @return firstNameId
   */

  public String getFirstName();

  /**
   * @param firstName setter for firstName attribute
   */

  public void setFirstName(String firstName);

  /**
   * @return lastNameId
   */

  public String getLastName();

  /**
   * @param lastName setter for lastName attribute
   */

  public void setLastName(String lastName);

}
