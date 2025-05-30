package org.pancakelab.service;

import org.pancakelab.logging.OrderLog;
import org.pancakelab.model.constant.OrderStatus;
import org.pancakelab.model.order.Order;
import org.pancakelab.builder.PancakeBuilder;
import org.pancakelab.model.constant.Ingredient;
import org.pancakelab.model.pancake.Pancake;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service class responsible for managing pancake creation, addition, and removal from orders.
 */
public class PancakeService {

	/** List of pancakes in the system */
	private final List<Pancake> pancakes = new CopyOnWriteArrayList<>();

	/**
	 * Adds multiple pancakes to an order and logs the action.
	 *
	 * @param order    the order to add pancakes to
	 * @param pancake the pancake to add to the order
	 * @param count    the number of pancakes to add
	 */
	public void addPancakes(Order order, Pancake pancake, int count) {
		// Validate the order's status before performing any operation
		validateOrderStatus(order);

		if (order == null || pancake == null || count <= 0) {
			throw new IllegalArgumentException("Invalid order, pancake, or count");
		}

		// Add the specified number of pancakes to the order
		for (int i = 0; i < count; i++) {
			pancake.setOrderId(order.getId());
			pancakes.add(pancake);
			OrderLog.logAddPancake(order, pancake.getDescription(), pancakes);
		}
	}

	/**
	 * Removes pancakes from an order by count and logs the action.
	 *
	 * @param orderId the order ID to remove pancakes from
	 * @param count   the number of pancakes to remove
	 * @param orders  the list of orders in the system
	 * @throws IllegalArgumentException if the order is not found
	 */
	public void removePancakes(UUID orderId, int count, List<Order> orders) {
		// Validate that the order is in a valid state before removing pancakes
		Order order = orders.stream()
				.filter(o -> o.getId().equals(orderId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Order not found"));

		validateOrderStatus(order);

		if (count <= 0) {
			throw new IllegalArgumentException("Count must be greater than 0");
		}

		final AtomicInteger removedCount = new AtomicInteger(0);

		// Remove the pancakes from the order
		pancakes.removeIf(p -> p.getOrderId().equals(orderId) && removedCount.getAndIncrement() < count);

		OrderLog.logRemovePancakes(order, removedCount.get(), pancakes);
	}

	/**
	 * Retrieves the descriptions of pancakes associated with a specific order.
	 *
	 * @param orderId the ID of the order to retrieve pancakes for
	 * @return a list of pancake descriptions for the order
	 */
	public List<String> viewOrder(UUID orderId) {
		// Retrieve the descriptions of pancakes for the specified order
		return pancakes.stream()
				.filter(p -> p.getOrderId().equals(orderId))
				.map(Pancake::getDescription)
				.toList();
	}

	/**
	 * Retrieves all pancakes in the system.
	 *
	 * @return a list of all pancakes
	 */
	public List<Pancake> getAllPancakes() {
		return pancakes;
	}

	/**
	 * Removes all pancakes associated with a given order ID.
	 *
	 * @param orderId the ID of the order whose pancakes to remove
	 */
	public void removeAllForOrder(UUID orderId) {
		pancakes.removeIf(p -> p.getOrderId().equals(orderId));
	}

	/**
	 * Builds a pancake based on the selected ingredients and associates it with an order.
	 *
	 * @param selectedIngredients the list of selected ingredient indexes (as strings)
	 * @param order               the order to associate the pancake with
	 * @return the newly created pancake
	 */
	public Pancake createPancake(List<String> selectedIngredients, Order order) {
		// Validate the order's status before performing any operation
		validateOrderStatus(order);

		if (selectedIngredients == null || selectedIngredients.isEmpty()) {
			throw new IllegalArgumentException("No ingredients selected");
		}

		PancakeBuilder builder = new PancakeBuilder();
		Pancake pancake = null;
		double totalPrice = 0;
		Ingredient[] ingredients = Ingredient.values();

		try {
			// Loop through selected ingredients and build the pancake
			for (String ingredient : selectedIngredients) {
				int index = Integer.parseInt(ingredient.trim()) - 1;

				if (index < 0 || index >= ingredients.length) {
					System.out.println("❌ Invalid ingredient number: " + ingredient.trim());
					continue;
				}

				Ingredient selected = ingredients[index];
				totalPrice += selected.getPrice();
				builder.addIngredient(selected.name().toLowerCase().replace("_", " "));
			}

			pancake = builder.build();
			pancake.setOrderId(order.getId());
			pancake.setPrice(totalPrice);

			System.out.println("✅\u001B[32m Creating Pancake with ingredients: " + pancake.getDescription());

		} catch (NumberFormatException e) {
			System.out.println("❌ Please enter numbers only (e.g., 1, 2, 3)");
		} catch (IllegalArgumentException e) {
			System.out.println("❌ Error: " + e.getMessage());
		}

		return pancake;
	}

	/**
	 * Validates if an order is in the correct state before pancake creation or modification.
	 *
	 * @param order the order to validate
	 * @throws IllegalStateException if the order status is not valid for pancake modifications
	 */
	private void validateOrderStatus(Order order) {
		if (order == null || order.getStatus() != OrderStatus.CREATED) {
			throw new IllegalStateException("Order must be in CREATED state for pancake modification");
		}
	}
}