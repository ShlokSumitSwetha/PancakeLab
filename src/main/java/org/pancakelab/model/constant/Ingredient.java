package org.pancakelab.model.constant;

/**
 * The {@code Ingredient} enum defines available ingredients for decorating pancakes.
 * Each ingredient has a display name, a price, and a validity flag indicating whether it is
 * currently allowed for use.
 * <p>
 * This enum is used throughout the application to refer to toppings such as chocolate and cream
 * in a consistent and type-safe manner.
 * </p>
 *
 * Example:
 * <pre>
 * Ingredient chocolate = Ingredient.MILK_CHOCOLATE;
 * double cost = chocolate.getPrice();
 * </pre>
 *
 * @author
 */
public enum Ingredient {

	/**
	 * Milk chocolate topping - priced at 3.0 units.
	 */
	MILK_CHOCOLATE("Milk Chocolate", 3, true),

	/**
	 * Dark chocolate topping - priced at 3.0 units.
	 */
	DARK_CHOCOLATE("Dark Chocolate", 3, true),

	/**
	 * Whipped cream topping - priced at 2.0 units.
	 */
	WHIPPED_CREAM("Whipped Cream", 2, true),

	/**
	 * Hazelnut topping - priced at 2.0 units.
	 */
	HAZELNUT("Hazelnut", 2, true);

	private final String name;
	private final boolean isValid;
	private final double price;

	/**
	 * Constructs an {@code Ingredient} with the given name, price, and validity status.
	 *
	 * @param name the display name of the ingredient
	 * @param price the price of the ingredient
	 * @param isValid whether the ingredient is currently allowed for use
	 */
	Ingredient(String name, double price, boolean isValid) {
		this.name = name;
		this.isValid = isValid;
		this.price = price;
	}

	/**
	 * Returns the display name of the ingredient.
	 *
	 * @return the name of the ingredient
	 */
	public String getName() {
		return name;
	}

	/**
	 * Indicates whether the ingredient is currently valid for use.
	 *
	 * @return {@code true} if the ingredient is valid; {@code false} otherwise
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * Returns the price of the ingredient.
	 *
	 * @return the ingredient's price
	 */
	public double getPrice() {
		return price;
	}
}