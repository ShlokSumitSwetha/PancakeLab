package org.pancakelab.model.pancake;

import org.pancakelab.model.constant.Ingredient;

import java.util.*;

/**
 * The {@code MilkChocolate} class is a concrete decorator in the Pancake Decorator design pattern.
 * It adds milk chocolate as an ingredient to a base {@link Pancake}.
 * <p>
 * This class extends {@link PancakeDecorator}, allowing dynamic addition of behavior
 * (in this case, the milk chocolate ingredient and its description) to {@link Pancake} instances.
 * </p>
 *
 * @author
 */
public class MilkChocolate extends PancakeDecorator {

	/**
	 * Constructs a {@code MilkChocolate} decorator that wraps the specified pancake.
	 *
	 * @param pancake the base {@link Pancake} to decorate with milk chocolate
	 */
	public MilkChocolate(Pancake pancake) {
		super(pancake);
	}

	/**
	 * Returns the description of the pancake including the milk chocolate.
	 *
	 * @return the decorated pancake description
	 */
	@Override
	public String getDescription() {
		return pancake.getDescription() + " " + Ingredient.MILK_CHOCOLATE.getName();
	}

	/**
	 * Returns a list containing only the milk chocolate ingredient.
	 * Note: This only includes the ingredient added by this decorator.
	 * To get a full ingredient list, delegate calls should be chained through the base pancake.
	 *
	 * @return a list containing {@link Ingredient#MILK_CHOCOLATE}
	 */
	@Override
	public List<Ingredient> ingredients() {
		return List.of(Ingredient.MILK_CHOCOLATE);
	}
}