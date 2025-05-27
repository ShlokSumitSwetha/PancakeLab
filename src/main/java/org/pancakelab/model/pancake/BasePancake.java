package org.pancakelab.model.pancake;

import org.pancakelab.model.constant.Ingredient;

import java.util.*;

/**
 * Represents a basic pancake with no added ingredients.
 *
 * This is the base implementation of the {@link Pancake} interface
 * and serves as the starting point for decorated pancakes.
 * @author [Sumith Ksheerasagar]
 */
public class BasePancake implements Pancake {

	/**
	 * The ID of the order this pancake belongs to.
	 */
	private UUID orderId;

	/**
	 * The price of the pancake.
	 */
	private double price;

	/**
	 * Returns the order ID associated with this pancake.
	 *
	 * @return the order UUID
	 */
	@Override
	public UUID getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order ID for this pancake.
	 *
	 * @param orderId the order UUID to associate
	 */
	@Override
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	/**
	 * Returns the list of ingredients in this pancake.
	 * For the base pancake, this is an empty list.
	 *
	 * @return empty list of ingredients
	 */
	@Override
	public List<Ingredient> ingredients() {
		return new ArrayList<>();
	}

	/**
	 * Returns the name of the pancake.
	 *
	 * @return "Basic Pancake"
	 */
	@Override
	public String getName() {
		return "Base Pancake";
	}

	/**
	 * Returns the description of the pancake.
	 * For a basic pancake, this is an empty string.
	 *
	 * @return empty description
	 */
	@Override
	public String getDescription() {
		return "";
	}

	/**
	 * Returns the price of the pancake.
	 *
	 * @return the price value
	 */
	@Override
	public double getPrice() {
		return this.price;
	}

	/**
	 * Sets the price of the pancake.
	 *
	 * @param price the price to be set
	 */
	@Override
	public void setPrice(double price) {
		this.price = price;
	}
}