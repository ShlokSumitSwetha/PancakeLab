package org.pancakelab.service;

import org.pancakelab.logging.OrderLog;
import org.pancakelab.model.constant.OrderStatus;
import org.pancakelab.model.order.Order;

import java.util.*;

/**
 * Handles the delivery of prepared orders.
 * Verifies order readiness, performs delivery, logs the operation,
 * and cleans up associated resources.
 */
public class DeliveryService {

	private final PancakeService pancakeService;
	private final OrderService orderService;

	/**
	 * Constructs a new DeliveryService with dependencies.
	 *
	 * @param pancakeService the service managing pancake operations
	 * @param orderService   the service managing order lifecycles
	 */
	public DeliveryService(PancakeService pancakeService, OrderService orderService) {
		this.pancakeService = pancakeService;
		this.orderService = orderService;
	}

	/**
	 * Delivers a prepared order, logs the delivery, removes its pancakes, and deletes the order.
	 * Also updates the order's status to DELIVERED after the delivery process.
	 *
	 * @param orderId the ID of the order to deliver
	 * @return an Object array containing the {@link Order} and list of pancake descriptions,
	 *         or {@code null} if the order is not in a PREPARED state
	 */
	public Object[] deliverOrder(UUID orderId) {
		// Ensure the order is in the PREPARED state
		Order order = orderService.findOrderById(orderId);
		if (order.getStatus() != OrderStatus.PREPARED) {
			// If the order is not prepared, return null
			return null;
		}

		// Proceed with delivery
		List<String> pancakesToDeliver = pancakeService.viewOrder(orderId);

		// Log the delivery operation
		OrderLog.logDeliverOrder(order, pancakeService.getAllPancakes());

		// Clean up by removing pancakes and deleting the order
		pancakeService.removeAllForOrder(orderId);
		orderService.deleteOrder(orderId);

		// Update the order status to DELIVERED
		order.setStatus(OrderStatus.DELIVERED);

		// Return order and the list of pancakes
		return new Object[]{order, pancakesToDeliver};
	}
}