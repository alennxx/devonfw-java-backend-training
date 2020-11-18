package com.devonfw.app.java.order.common.builders;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.devonfw.app.java.order.orderservice.logic.api.to.CustomerEto;

/**
 * Test data builder for CustomerEto generated with cobigen.
 */
public class CustomerEtoBuilder {

  private List<Consumer<CustomerEto>> parameterToBeApplied;

  /**
   * The constructor.
   */
  public CustomerEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * @param firstName the firstName to add.
   * @return the builder for fluent population of fields.
   */
  public CustomerEtoBuilder firstName(final String firstName) {

    this.parameterToBeApplied.add(target -> target.setFirstName(firstName));

    return this;
  }

  /**
   * @param lastName the lastName to add.
   * @return the builder for fluent population of fields.
   */
  public CustomerEtoBuilder lastName(final String lastName) {

    this.parameterToBeApplied.add(target -> target.setLastName(lastName));

    return this;
  }

  /**
   * @return the populated CustomerEto.
   */
  public CustomerEto createNew() {

    CustomerEto customereto = new CustomerEto();
    for (Consumer<CustomerEto> parameter : parameterToBeApplied) {
      parameter.accept(customereto);
    }
    return customereto;
  }

  /**
   * Might be enriched to users needs (will not be overwritten)
   */
  private void fillMandatoryFields_custom() {

  }

  /**
   * Fills all mandatory fields by default. (will be overwritten on re-generation)
   */
  private void fillMandatoryFields() {

  }

}
