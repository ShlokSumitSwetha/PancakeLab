package org.pancakelab.service;

import org.junit.jupiter.api.*;
import org.pancakelab.model.constant.OrderStatus;
import org.pancakelab.model.order.Order;
import org.pancakelab.model.pancake.BasePancake;
import org.pancakelab.model.pancake.Pancake;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // Annotation for test method ordering
public class PancakeServiceTest {

	private final PancakeService pancakeService = new PancakeService();
	private final List<Order> orders = new ArrayList<>();
	private Order order;

	@BeforeEach
	public void setup() {
		order = new Order(1, 101);
		orders.add(order);
	}

	@AfterEach
	public void tearDown() {
		orders.clear();
		pancakeService.removeAllForOrder(order.getId());
	}

	@Test
	public void WhenRemovingPancakes_ThenCorrectCountIsRemoved() {
		Pancake pancake = new BasePancake();
		pancakeService.addPancakes(order, pancake, 5);

		pancakeService.removePancakes(order.getId(), 2, orders);
		List<String> result = pancakeService.viewOrder(order.getId());
		assertEquals(3, result.size());
	}

	@Test
	public void WhenRemovingTooManyPancakes_ThenOnlyExistingAreRemoved() {
		Pancake pancake = new BasePancake();
		pancakeService.addPancakes(order, pancake, 2);

		pancakeService.removePancakes(order.getId(), 5, orders);
		assertEquals(0, pancakeService.viewOrder(order.getId()).size());
	}

	@Test
	public void WhenRemovingFromInvalidOrder_ThenExceptionThrown() {
		UUID unknownOrderId = UUID.randomUUID();
		assertThrows(IllegalArgumentException.class, () -> pancakeService.removePancakes(unknownOrderId, 2, orders));
	}

	@Test
	public void WhenRemovingWithInvalidCount_ThenExceptionThrown() {
		Pancake pancake = new BasePancake();
		pancakeService.addPancakes(order, pancake, 2);
		assertThrows(IllegalArgumentException.class, () -> pancakeService.removePancakes(order.getId(), 0, orders));
	}

	@Test
	public void WhenViewingOrderWithNoPancakes_ThenEmptyListReturned() {
		assertTrue(pancakeService.viewOrder(order.getId()).isEmpty());
	}

	@Test
	public void WhenCreatingPancakeWithNonNumericInput_ThenHandleGracefully() {
		List<String> badInput = List.of("a", "2");
		Pancake pancake = pancakeService.createPancake(badInput, order);
		assertNull(pancake);
	}

	@Test
	public void WhenCreatingPancakeWithNullIngredients_ThenExceptionThrown() {
		assertThrows(IllegalArgumentException.class, () -> pancakeService.createPancake(null, order));
	}

	@Test
	public void WhenCreatingPancakeOnNonCreatedOrder_ThenExceptionThrown() {
		order.setStatus(OrderStatus.PREPARED);
		List<String> ingredients = List.of("1", "2");
		assertThrows(IllegalStateException.class, () -> pancakeService.createPancake(ingredients, order));
	}

	@Test
	public void WhenAddingPancakesToNonCreatedOrder_ThenExceptionThrown() {
		order.setStatus(OrderStatus.PREPARED);
		assertThrows(IllegalStateException.class, () -> pancakeService.addPancakes(order, new BasePancake(), 1));
	}

	@Test
	public void WhenRemovingAllPancakes_ThenOrderIsEmpty() {
		Pancake pancake = new BasePancake();
		pancakeService.addPancakes(order, pancake, 4);

		pancakeService.removeAllForOrder(order.getId());

		assertTrue(pancakeService.viewOrder(order.getId()).isEmpty());
	}
}