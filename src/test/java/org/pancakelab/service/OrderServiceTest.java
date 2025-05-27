package org.pancakelab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pancakelab.model.constant.OrderStatus;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancake.Pancake;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

	private OrderService orderService;

	@BeforeEach
	void setUp() {
		orderService = new OrderService();
	}

	@Test
	void testCreateOrder() {
		Order order = orderService.createOrder(1, 101);
		assertNotNull(order);
		assertEquals(OrderStatus.CREATED, orderService.getOrderStatus(order.getId()));
	}

	@Test
	void testCancelOrder() {
		Order order = orderService.createOrder(2, 202);
		List<Pancake> pancakes = new ArrayList<>(); // Empty list for now
		orderService.cancelOrder(order.getId(), pancakes);

		assertEquals(OrderStatus.CANCELLED, orderService.getOrderStatus(order.getId()));
	}

	@Test
	void testCancelAlreadyCancelledOrderThrowsException() {
		Order order = orderService.createOrder(3, 303);
		orderService.cancelOrder(order.getId(), new ArrayList<>());

		IllegalStateException exception = assertThrows(
				IllegalStateException.class,
				() -> orderService.cancelOrder(order.getId(), new ArrayList<>())
		);
		assertEquals("Order is already cancelled.", exception.getMessage());
	}

	@Test
	void testCompleteOrder() {
		Order order = orderService.createOrder(4, 404);
		orderService.completeOrder(order.getId());

		assertEquals(OrderStatus.COMPLETED, orderService.getOrderStatus(order.getId()));
	}

	@Test
	void testPrepareOrder() {
		Order order = orderService.createOrder(5, 505);
		orderService.prepareOrder(order.getId());

		assertEquals(OrderStatus.PREPARED, orderService.getOrderStatus(order.getId()));
	}

	@Test
	void testListOrdersByStatus() {
		Order order1 = orderService.createOrder(6, 606);
		Order order2 = orderService.createOrder(7, 707);
		Order order3 = orderService.createOrder(8, 808);

		orderService.prepareOrder(order1.getId());
		orderService.completeOrder(order2.getId());
		orderService.cancelOrder(order3.getId(), new ArrayList<>());

		assertTrue(orderService.listPreparedOrders().contains(order1.getId()));
		assertTrue(orderService.listCompletedOrders().contains(order2.getId()));
		assertTrue(orderService.listCancelledOrders().contains(order3.getId()));
	}

	@Test
	void testDeleteOrder() {
		Order order = orderService.createOrder(9, 909);
		UUID orderId = order.getId();

		orderService.deleteOrder(orderId);

		assertThrows(IllegalArgumentException.class, () -> orderService.getOrderStatus(orderId));
	}

	@Test
	void testFindOrderById() {
		Order order = orderService.createOrder(10, 1001);
		UUID orderId = order.getId();

		Order found = orderService.findOrderById(orderId);
		assertEquals(orderId, found.getId());
	}

	@Test
	void testFindOrderByIdThrowsIfNotFound() {
		UUID invalidId = UUID.randomUUID();
		assertThrows(IllegalArgumentException.class, () -> orderService.findOrderById(invalidId));
	}

	@Test
	void testGetAllOrders() {
		orderService.createOrder(1, 101);
		orderService.createOrder(2, 102);

		List<Order> allOrders = orderService.getAllOrders();
		assertEquals(2, allOrders.size());
	}
}