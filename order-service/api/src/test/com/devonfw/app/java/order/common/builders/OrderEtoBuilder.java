package com.devonfw.app.java.order.common.builders;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;

/**
 * Test data builder for OrderEto generated with cobigen.
 */
public class OrderEtoBuilder {

  private List<Consumer<OrderEto>> parameterToBeApplied;

  /**
   * The constructor.
   */
  public OrderEtoBuilder() {

    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

  /**
   * @param creationDate the creationDate to add.
   * @return the builder for fluent population of fields.
   */
  public OrderEtoBuilder creationDate(final LocalDate creationDate) {

    this.parameterToBeApplied.add(target -> target.setCreationDate(creationDate));

    return this;
  }

  /**
   * @param ownerId the ownerId to add.
   * @return the builder for fluent population of fields.
   */
  public OrderEtoBuilder ownerId(final Long ownerId) {

    this.parameterToBeApplied.add(target -> target.setOwnerId(ownerId));

    return this;
  }

  /**
   * @param price the price to add.
   * @return the builder for fluent population of fields.
   */
  public OrderEtoBuilder price(final Double price) {

    this.parameterToBeApplied.add(target -> target.setPrice(price));

    return this;
  }

  /**
   * @param status the status to add.
   * @return the builder for fluent population of fields.
   */
  public OrderEtoBuilder status(final OrderStatus status) {

    this.parameterToBeApplied.add(target -> target.setStatus(status));

    return this;
  }

  /**
   * @return the populated OrderEto.
   */
  public OrderEto createNew() {

    OrderEto ordereto = new OrderEto();
    for (Consumer<OrderEto> parameter : parameterToBeApplied) {
      parameter.accept(ordereto);
    }
    return ordereto;
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
