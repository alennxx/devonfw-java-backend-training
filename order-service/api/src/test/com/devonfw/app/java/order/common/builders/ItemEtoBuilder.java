package com.devonfw.app.java.order.common.builders;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;

/**
 * Test data builder for ItemEto generated with cobigen.
 */
public class ItemEtoBuilder {

	private List<Consumer<ItemEto>> parameterToBeApplied;

	/**
	 * The constructor.
	 */
	public ItemEtoBuilder() {
    this.parameterToBeApplied = new LinkedList<>();
    fillMandatoryFields();
    fillMandatoryFields_custom();
  }

	/**
	 * @param description the description to add.
	 * @return the builder for fluent population of fields.
	 */
	public ItemEtoBuilder description(final String description) {
        this.parameterToBeApplied.add(target -> target.setDescription(description));

        return this;
    }

	/**
	 * @param name the name to add.
	 * @return the builder for fluent population of fields.
	 */
	public ItemEtoBuilder name(final String name) {
        this.parameterToBeApplied.add(target -> target.setName(name));

        return this;
    }

	/**
	 * @param price the price to add.
	 * @return the builder for fluent population of fields.
	 */
	public ItemEtoBuilder price(final Double price) {
        this.parameterToBeApplied.add(target -> target.setPrice(price));

        return this;
    }

	/**
	 * @return the populated ItemEto.
	 */
	public ItemEto createNew() {
        ItemEto itemeto = new ItemEto();
        for (Consumer<ItemEto> parameter : parameterToBeApplied) {
            parameter.accept(itemeto);
        }
        return itemeto;
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
