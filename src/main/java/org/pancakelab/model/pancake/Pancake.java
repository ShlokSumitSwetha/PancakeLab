package org.pancakelab.model.pancake;

import org.pancakelab.model.constant.Ingredient;

import java.util.List;
import java.util.UUID;

/**
 * Represents a pancake in the Pancake Lab ordering system.
 *
 * This interface defines the basic structure and behavior of a pancake,
 * including name, description, price, and its association with an order.
 */
public interface Pancake {

	/**
	 * Returns the name of the pancake.
	 *
	 * @return the name of the pancake
	 */
	String getName();

	/**
	 * Returns a detailed description of the pancake,
	 * typically including its ingredients.
	 *
	 * @return the description of the pancake
	 */
	String getDescription();

	/**
	 * Returns the price of the pancake.
	 *
	 * @return the price in dollars
	 */
	double getPrice();

	/**
	 * Sets the price of the pancake.
	 *
	 * @param price the price to be set
	 */
	void setPrice(double price);

	/**
	 * Gets the ID of the order this pancake is associated with.
	 *
	 * @return the UUID of the order
	 */
	UUID getOrderId();

	/**
	 * Sets the order ID for this pancake.
	 *
	 * @param orderId the UUID of the order
	 */
	void setOrderId(UUID orderId);

	/**
	 * Returns a list of ingredients that make up the pancake.
	 *
	 * @return a list of {@link Ingredient} items
	 */
	List<Ingredient> ingredients();
}