package org.pancakelab.model.pancake;

import org.pancakelab.model.constant.Ingredient;

import java.util.*;

/**
 * Abstract decorator class for Pancakes.
 *
 * <p>This class implements the {@link Pancake} interface and serves as a base for concrete
 * decorators (e.g., {@code ChocolatePancake}, {@code WhippedCreamPancake}).
 * It allows additional ingredients or behavior to be added to a basic pancake dynamically.</p>
 *
 * <p>It delegates all method calls to the wrapped Pancake instance.</p>
 */
public class PancakeDecorator implements Pancake {

	/**
	 * The pancake being decorated (wrapped).
	 */
	protected final Pancake pancake;

	/**
	 * Constructs a decorator with the specified Pancake to wrap.
	 *
	 * @param pancake the pancake to decorate
	 */
	public PancakeDecorator(Pancake pancake) {
		this.pancake = pancake;
	}

	/**
	 * Returns the list of ingredients of the wrapped pancake.
	 *
	 * @return list of ingredients
	 */
	@Override
	public List<Ingredient> ingredients() {
		return pancake.ingredients();
	}

	/**
	 * Returns the name of the wrapped pancake.
	 *
	 * @return pancake name
	 */
	@Override
	public String getName() {
		return pancake.getName();
	}

	/**
	 * Returns the description of the wrapped pancake.
	 *
	 * @return pancake description
	 */
	@Override
	public String getDescription() {
		return pancake.getDescription();
	}

	/**
	 * Returns the price of the wrapped pancake.
	 *
	 * @return pancake price
	 */
	@Override
	public double getPrice() {
		return pancake.getPrice();
	}

	/**
	 * Sets the price of the wrapped pancake.
	 *
	 * @param price the new price
	 */
	@Override
	public void setPrice(double price) {
		pancake.setPrice(price);
	}

	/**
	 * Returns the order ID of the wrapped pancake.
	 *
	 * @return order ID
	 */
	@Override
	public UUID getOrderId() {
		return pancake.getOrderId();
	}

	/**
	 * Sets the order ID for the wrapped pancake.
	 *
	 * @param orderId the order ID
	 */
	@Override
	public void setOrderId(UUID orderId) {
		pancake.setOrderId(orderId);
	}
}