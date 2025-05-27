package org.pancakelab.model.pancake;

import org.pancakelab.model.constant.Ingredient;

import java.util.*;

/**
 * Concrete decorator that adds Hazelnut topping to a {@link Pancake}.
 *
 * <p>This class wraps an existing Pancake and extends its functionality
 * by adding hazelnut to its description and ingredients.</p>
 *
 * <p>It follows the Decorator Pattern to allow flexible and dynamic
 * composition of pancakes with different ingredients.</p>
 */
public class Hazelnut extends PancakeDecorator {

	/**
	 * Constructs a Hazelnut decorator for the given Pancake.
	 *
	 * @param pancake the pancake to decorate with hazelnut
	 */
	public Hazelnut(Pancake pancake) {
		super(pancake);
	}

	/**
	 * Returns the description of the pancake, including "Hazelnut".
	 *
	 * @return updated pancake description
	 */
	@Override
	public String getDescription() {
		return pancake.getDescription() + " " + Ingredient.HAZELNUT.getName();
	}

	/**
	 * Returns a list of ingredients for this decorator.
	 *
	 * <p>Currently returns only the hazelnut ingredient. To accumulate
	 * previous ingredients, you can extend this logic accordingly.</p>
	 *
	 * @return list containing hazelnut as the only ingredient
	 */
	@Override
	public List<Ingredient> ingredients() {
		return List.of(Ingredient.HAZELNUT);
	}
}