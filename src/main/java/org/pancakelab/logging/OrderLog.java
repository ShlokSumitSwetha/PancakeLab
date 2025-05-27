package org.pancakelab.logging;

import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancake.Pancake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OrderLog {

	private static final Logger logger = LoggerFactory.getLogger(OrderLog.class);

	public static void logAddPancake(Order order, String description, List<Pancake> pancakes) {
		long count = countPancakesForOrder(order, pancakes);
		logger.debug("🧇 Added pancake '{}' to order {} ({} pancakes) [{}]",
				description, order.getId(), count, location(order));
	}

	public static void logRemovePancakes(Order order, int countRemoved, List<Pancake> pancakes) {
		long count = countPancakesForOrder(order, pancakes);
		logger.info("🗑️ Removed {} pancake(s) from order {} ({} pancakes left) [{}]",
				countRemoved, order.getId(), count, location(order));
	}

	public static void logCancelOrder(Order order, List<Pancake> pancakes) {
		long count = countPancakesForOrder(order, pancakes);
		logger.debug("❌ Cancelled order {} with {} pancakes [{}]",
				order.getId(), count, location(order));
	}

	public static void logDeliverOrder(Order order, List<Pancake> pancakes) {
		long count = countPancakesForOrder(order, pancakes);
		logger.info("🚚 Delivered order {} with {} pancakes [{}]",
				order.getId(), count, location(order));
	}

	// --- Private utilities ---

	private static long countPancakesForOrder(Order order, List<Pancake> pancakes) {
		return pancakes.stream()
				.filter(p -> p.getOrderId().equals(order.getId()))
				.count();
	}

	private static String location(Order order) {
		return "Building %d, Room %d".formatted(order.getBuilding(), order.getRoom());
	}
}