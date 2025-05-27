package org.pancakelab.model.order;

import org.pancakelab.model.constant.OrderStatus;

import java.util.*;

/**
 * The {@code Order} class represents a customer's pancake order.
 * Each order is uniquely identified by a UUID and is associated with a specific building and room number.
 * The order also tracks its current status throughout the lifecycle (e.g., CREATED, COMPLETED, DELIVERED).
 * <p>
 * This class is immutable after creation, and uses the UUID to define equality between order instances.
 * </p>
 *
 * Example usage:
 * <pre>
 * Order order = new Order(5, 203);
 * UUID orderId = order.getId();
 * </pre>
 *
 * @author [Sumith Ksheerasagar]
 */
public class Order {

	/** Unique identifier for the order */
	private final UUID id;

	/** Building number where the order is to be delivered */
	private final int building;

	/** Room number where the order is to be delivered */
	private final int room;

	/** Current status of the order (e.g., CREATED, COMPLETED, DELIVERED) */
	private OrderStatus status;

	/**
	 * Constructs a new {@code Order} with the specified building and room number.
	 * A unique UUID is automatically generated for the order, and the initial status is set to {@link OrderStatus#CREATED}.
	 *
	 * @param building the building number where the order is to be delivered
	 * @param room the room number where the order is to be delivered
	 */
	public Order(int building, int room) {
		this.id = UUID.randomUUID();
		this.building = building;
		this.room = room;
		this.status = OrderStatus.CREATED; // Default status is CREATED
	}

	/**
	 * Returns the unique identifier of the order.
	 * The UUID is used to uniquely identify the order.
	 *
	 * @return the UUID of the order
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Returns the building number associated with the order.
	 * This is the building where the order will be delivered.
	 *
	 * @return the building number
	 */
	public int getBuilding() {
		return building;
	}

	/**
	 * Returns the room number associated with the order.
	 * This is the room within the building where the order will be delivered.
	 *
	 * @return the room number
	 */
	public int getRoom() {
		return room;
	}

	/**
	 * Returns the current status of the order.
	 * The status can be one of the values in {@link OrderStatus}, such as CREATED, COMPLETED, or DELIVERED.
	 *
	 * @return the current order status
	 */
	public OrderStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status of the order.
	 * This method is used to change the order's status as it progresses through its lifecycle.
	 *
	 * @param status the new status to set for the order
	 */
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	/**
	 * Indicates whether this order is equal to another object.
	 * Two orders are considered equal if they have the same UUID.
	 *
	 * @param o the object to compare with
	 * @return {@code true} if the objects are equal (same UUID); {@code false} otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return Objects.equals(id, order.id);
	}

	/**
	 * Returns the hash code of the order, based on its UUID.
	 * This method is used to ensure that orders with the same UUID have the same hash code.
	 *
	 * @return the hash code of the order
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}