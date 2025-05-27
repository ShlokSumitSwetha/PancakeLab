package org.pancakelab.template;

import java.util.*;

public abstract class OrderProcessTemplate {

	public final void processOrder(UUID orderId) {
		prepare(orderId);
		deliver(orderId);
		complete(orderId);
	}

	protected abstract void prepare(UUID orderId);

	protected abstract void deliver(UUID orderId);

	protected abstract void complete(UUID orderId);
}