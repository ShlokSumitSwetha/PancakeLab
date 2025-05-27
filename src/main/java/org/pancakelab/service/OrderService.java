package org.pancakelab.service;

import org.pancakelab.logging.OrderLog;
import org.pancakelab.model.constant.OrderStatus;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancake.Pancake;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing the lifecycle of pancake orders.
 * Handles creation, cancellation, preparation, and completion of orders.
 */
public class OrderService {

	private final List<Order> orders = new ArrayList<>();
	private final Map<UUID, OrderStatus> orderStatusMap = new HashMap<>();

	/**
	 * Creates a new {@link Order} and registers it in the system.
	 */
	public Order createOrder(int building, int room) {
		Order order = new Order(building, room);
		orders.add(order);
		orderStatusMap.put(order.getId(), OrderStatus.CREATED);
		return order;
	}

	/**
	 * Cancels the order and updates its status to CANCELLED.
	 * Also logs the operation.
	 */
	public void cancelOrder(UUID orderId, List<Pancake> allPancakes) {
		Order order = findOrderById(orderId);

		// Only update status if it’s not already cancelled
		OrderStatus currentStatus = getOrderStatus(orderId);
		if (currentStatus == OrderStatus.CANCELLED) {
			throw new IllegalStateException("Order is already cancelled.");
		}

		orderStatusMap.put(orderId, OrderStatus.CANCELLED);
		OrderLog.logCancelOrder(order, allPancakes);
	}

	/**
	 * Marks the order as completed.
	 */
	public void completeOrder(UUID orderId) {
		validateOrderExists(orderId);
		orderStatusMap.put(orderId, OrderStatus.COMPLETED);
	}

	/**
	 * Marks the order as prepared.
	 */
	public void prepareOrder(UUID orderId) {
		validateOrderExists(orderId);
		orderStatusMap.put(orderId, OrderStatus.PREPARED);
	}

	/**
	 * Lists all order IDs currently in the COMPLETED state.
	 */
	public Set<UUID> listCompletedOrders() {
		return listOrdersByStatus(OrderStatus.COMPLETED);
	}

	/**
	 * Lists all order IDs currently in the PREPARED state.
	 */
	public Set<UUID> listPreparedOrders() {
		return listOrdersByStatus(OrderStatus.PREPARED);
	}

	/**
	 * Lists all order IDs currently in the CANCELLED state.
	 */
	public Set<UUID> listCancelledOrders() {
		return listOrdersByStatus(OrderStatus.CANCELLED);
	}

	/**
	 * Returns a filtered set of order IDs by status.
	 */
	private Set<UUID> listOrdersByStatus(OrderStatus status) {
		return orderStatusMap.entrySet().stream()
				.filter(entry -> entry.getValue() == status)
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}

	/**
	 * Retrieves all orders in the system.
	 */
	public List<Order> getAllOrders() {
		return new ArrayList<>(orders);
	}

	/**
	 * Finds an order by ID.
	 */
	public Order findOrderById(UUID orderId) {
		return orders.stream()
				.filter(order -> order.getId().equals(orderId))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
	}

	/**
	 * Validates that an order exists.
	 */
	private void validateOrderExists(UUID orderId) {
		if (!orderStatusMap.containsKey(orderId)) {
			throw new IllegalArgumentException("Order not found: " + orderId);
		}
	}

	/**
	 * Returns the current status of the order.
	 */
	public OrderStatus getOrderStatus(UUID orderId) {
		validateOrderExists(orderId);
		return orderStatusMap.get(orderId);
	}

	/**
	 * Deletes the order completely from the system.
	 */
	public void deleteOrder(UUID orderId) {
		orders.removeIf(order -> order.getId().equals(orderId));
		orderStatusMap.remove(orderId);
	}
}