package org.pancakelab.model.pancake;

import org.pancakelab.model.constant.Ingredient;

import java.util.*;

/**
 * Concrete decorator class that adds Dark Chocolate as a topping to a {@link Pancake}.
 *
 * <p>This class extends the {@link PancakeDecorator} and modifies the
 * pancake's description and ingredients to include dark chocolate.</p>
 *
 * <p>It is part of the Decorator Pattern implementation that allows dynamic
 * composition of pancakes with various ingredients.</p>
 */
public class DarkChocolate extends PancakeDecorator {

	/**
	 * Constructs a DarkChocolate decorator for the given Pancake.
	 *
	 * @param pancake the pancake to decorate
	 */
	public DarkChocolate(Pancake pancake) {
		super(pancake);
	}

	/**
	 * Returns the updated description including dark chocolate.
	 *
	 * @return description with dark chocolate added
	 */
	@Override
	public String getDescription() {
		return pancake.getDescription() + " " + Ingredient.DARK_CHOCOLATE.getName();
	}

	/**
	 * Returns a list of ingredients including only dark chocolate.
	 *
	 * <p><b>Note:</b> If you want to include existing ingredients from the base pancake,
	 * you should combine them with this one using:
	 * {@code List.copyOf(pancake.ingredients()).add(Ingredient.DARK_CHOCOLATE)}</p>
	 *
	 * @return list containing dark chocolate as the sole ingredient
	 */
	@Override
	public List<Ingredient> ingredients() {
		return List.of(Ingredient.DARK_CHOCOLATE);
	}
}