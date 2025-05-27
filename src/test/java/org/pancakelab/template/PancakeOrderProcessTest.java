package org.pancakelab.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pancakelab.service.DeliveryService;
import org.pancakelab.service.OrderService;
import org.pancakelab.service.PancakeService;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class PancakeOrderProcessTest {

	private PancakeService pancakeService;
	private OrderService orderService;
	private DeliveryService deliveryService;
	private PancakeOrderProcess pancakeOrderProcess;

	private UUID orderId;

	@BeforeEach
	public void setUp() {
		// Mocking the dependencies
		pancakeService = mock(PancakeService.class);
		orderService = mock(OrderService.class);
		deliveryService = mock(DeliveryService.class);

		// Create an instance of PancakeOrderProcess with the mocked services
		pancakeOrderProcess = new PancakeOrderProcess(pancakeService, orderService, deliveryService);

		// Creating a random orderId for testing
		orderId = UUID.randomUUID();
	}

	@Test
	public void testProcessOrder() {
		// Calling the processOrder method
		pancakeOrderProcess.processOrder(orderId);

		// Verify that the methods are called in the correct order
		verify(orderService).prepareOrder(orderId);  // prepare
		verify(deliveryService).deliverOrder(orderId);  // deliver
		verify(orderService).completeOrder(orderId);  // complete
	}

	@Test
	public void testPrepareOrder() {
		// Call the prepare method directly and verify
		pancakeOrderProcess.prepare(orderId);
		verify(orderService).prepareOrder(orderId);
	}

	@Test
	public void testDeliverOrder() {
		// Call the deliver method directly and verify
		pancakeOrderProcess.deliver(orderId);
		verify(deliveryService).deliverOrder(orderId);
	}

	@Test
	public void testCompleteOrder() {
		// Call the complete method directly and verify
		pancakeOrderProcess.complete(orderId);
		verify(orderService).completeOrder(orderId);
	}
}