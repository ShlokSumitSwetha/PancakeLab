package org.pancakelab.builder;

import org.junit.jupiter.api.Test;
import org.pancakelab.model.pancake.Pancake;

import static org.junit.jupiter.api.Assertions.*;

class PancakeBuilderTest {

	@Test
	void shouldAddMilkChocolate() {
		Pancake pancake = new PancakeBuilder()
				.addIngredient("Milk Chocolate")
				.build();

		assertTrue(pancake.getDescription().contains("Milk Chocolate"));
	}

	@Test
	void shouldAddMultipleIngredients() {
		Pancake pancake = new PancakeBuilder()
				.addIngredient("Hazelnut")
				.addIngredient("Whipped Cream")
				.build();

		String description = pancake.getDescription();
		assertTrue(description.contains("Hazelnut"));
		assertTrue(description.contains("Whipped Cream"));
	}

	@Test
	void shouldIgnoreCaseAndSpacesInIngredientName() {
		Pancake pancake = new PancakeBuilder()
				.addIngredient("  dark chocolate  ")
				.build();

		assertTrue(pancake.getDescription().contains("Dark Chocolate"));
	}

	@Test
	void shouldThrowExceptionForInvalidIngredient() {
		PancakeBuilder builder = new PancakeBuilder();
		assertThrows(IllegalArgumentException.class, () -> builder.addIngredient("Caramel"));
	}
	@Test
	void shouldBuildBasePancakeWithoutIngredients() {
		Pancake pancake = new PancakeBuilder().build();
		assertEquals("", pancake.getDescription());
	}
}
