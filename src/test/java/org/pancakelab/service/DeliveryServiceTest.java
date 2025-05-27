package org.pancakelab.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pancakelab.logging.OrderLog;
import org.pancakelab.model.constant.OrderStatus;
import org.pancakelab.model.order.Order;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryServiceTest {

	private PancakeService pancakeService;
	private OrderService orderService;
	private DeliveryService deliveryService;

	@BeforeEach
	void setUp() {
		pancakeService = mock(PancakeService.class);
		orderService = mock(OrderService.class);
		deliveryService = new DeliveryService(pancakeService, orderService);
	}

	@Test
	void testDeliverOrderWhenPrepared() {
		UUID orderId = UUID.randomUUID();
		Order order = new Order(1, 101);  // Creating a dummy order
		//order.setId(orderId);
		order.setStatus(OrderStatus.PREPARED);

		List<String> pancakeDescriptions = Arrays.asList("Pancake with syrup", "Pancake with butter");

		when(orderService.findOrderById(orderId)).thenReturn(order);
		when(pancakeService.viewOrder(orderId)).thenReturn(pancakeDescriptions);

		// Mocking OrderLog to prevent actual logging calls during tests
		OrderLog mockOrderLog = mock(OrderLog.class);
		try {
			// The actual delivery method should now be tested
			Object[] result = deliveryService.deliverOrder(orderId);

			assertNotNull(result);
			assertEquals(OrderStatus.DELIVERED, order.getStatus());
			assertEquals(order, result[0]);
			assertEquals(pancakeDescriptions, result[1]);

			verify(pancakeService, times(1)).viewOrder(orderId);
			verify(pancakeService, times(1)).removeAllForOrder(orderId);
			verify(orderService, times(1)).deleteOrder(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testDeliverOrderWhenNotPrepared() {
		UUID orderId = UUID.randomUUID();
		Order order = new Order(1, 101);
		//order.setId(orderId);
		order.setStatus(OrderStatus.CREATED);  // Not prepared yet

		when(orderService.findOrderById(orderId)).thenReturn(order);

		// Attempt to deliver an order that isn't prepared
		Object[] result = deliveryService.deliverOrder(orderId);

		// The result should be null since the order isn't in the "PREPARED" state
		assertNull(result);

		verify(pancakeService, never()).viewOrder(orderId);
		verify(pancakeService, never()).removeAllForOrder(orderId);
		verify(orderService, never()).deleteOrder(orderId);
	}

	@Test
	void testDeliverOrderUpdatesStatusToDelivered() {
		UUID orderId = UUID.randomUUID();
		Order order = new Order(1, 101);
		//order.setId(orderId);
		order.setStatus(OrderStatus.PREPARED);

		List<String> pancakeDescriptions = Arrays.asList("Pancake with syrup");

		when(orderService.findOrderById(orderId)).thenReturn(order);
		when(pancakeService.viewOrder(orderId)).thenReturn(pancakeDescriptions);

		Object[] result = deliveryService.deliverOrder(orderId);

		// Assert that the order status is changed to DELIVERED after delivery
		assertEquals(OrderStatus.DELIVERED, order.getStatus());
	}

	@Test
	void testDeliverOrderCleansUpResources() {
		UUID orderId = UUID.randomUUID();
		Order order = new Order(1, 101);
		//order.setId(orderId);
		order.setStatus(OrderStatus.PREPARED);

		List<String> pancakeDescriptions = Arrays.asList("Pancake with syrup");

		when(orderService.findOrderById(orderId)).thenReturn(order);
		when(pancakeService.viewOrder(orderId)).thenReturn(pancakeDescriptions);

		Object[] result = deliveryService.deliverOrder(orderId);

		// Verify that pancakes were removed and order deleted
		verify(pancakeService, times(1)).removeAllForOrder(orderId);
		verify(orderService, times(1)).deleteOrder(orderId);
	}
}