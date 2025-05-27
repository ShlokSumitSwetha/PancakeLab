package org.pancakelab.model.pancake;

import org.pancakelab.model.constant.Ingredient;

import java.util.*;

/**
 * The {@code WhippedCream} class is a concrete decorator in the Pancake Decorator design pattern.
 * It adds whipped cream as an ingredient to a base {@link Pancake}.
 * <p>
 * This class extends {@link PancakeDecorator}, allowing dynamic enhancement of a {@link Pancake}
 * object by appending whipped cream to its description and ingredients list.
 * </p>
 *
 * Example usage:
 * <pre>
 * Pancake base = new BasePancake();
 * Pancake withWhippedCream = new WhippedCream(base);
 * </pre>
 *
 * @author
 */
public class WhippedCream extends PancakeDecorator {

	/**
	 * Constructs a {@code WhippedCream} decorator that wraps the specified pancake.
	 *
	 * @param pancake the base {@link Pancake} to decorate with whipped cream
	 */
	public WhippedCream(Pancake pancake) {
		super(pancake);
	}

	/**
	 * Returns the description of the pancake including the whipped cream.
	 *
	 * @return the decorated pancake description
	 */
	@Override
	public String getDescription() {
		return pancake.getDescription() + " " + Ingredient.WHIPPED_CREAM.getName();
	}

	/**
	 * Returns a list containing only the whipped cream ingredient.
	 * Note: This list includes only the ingredient added by this decorator.
	 * For the full ingredient list, you should combine it with the base pancake's ingredients.
	 *
	 * @return a list containing {@link Ingredient#WHIPPED_CREAM}
	 */
	@Override
	public List<Ingredient> ingredients() {
		return List.of(Ingredient.WHIPPED_CREAM);
	}
}