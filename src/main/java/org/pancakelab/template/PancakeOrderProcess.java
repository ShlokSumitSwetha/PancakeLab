package org.pancakelab.template;

import org.pancakelab.service.DeliveryService;
import org.pancakelab.service.OrderService;
import org.pancakelab.service.PancakeService;

import java.util.*;

public class PancakeOrderProcess extends OrderProcessTemplate {

	private final PancakeService pancakeService;
	private final OrderService orderService;
	private final DeliveryService deliveryService;
	public PancakeOrderProcess(PancakeService pancakeService, OrderService orderService, DeliveryService deliveryService) {

		this.pancakeService = pancakeService;
		this.orderService = orderService;
		this.deliveryService = deliveryService;

	}

	@Override
	protected void prepare(UUID orderId) {
		orderService.prepareOrder(orderId);
	}

	@Override
	protected void deliver(UUID orderId) {
		deliveryService.deliverOrder(orderId);
	}

	@Override
	protected void complete(UUID orderId) {
		orderService.completeOrder(orderId);
	}
}
