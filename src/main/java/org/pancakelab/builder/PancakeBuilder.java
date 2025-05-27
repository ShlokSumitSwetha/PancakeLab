package org.pancakelab.builder;

import org.pancakelab.model.constant.Ingredient;
import org.pancakelab.model.pancake.*;

/**
 * The `PancakeBuilder` class is responsible for building customized pancakes with various ingredients.
 * This builder follows the Builder design pattern to allow flexible and easy construction of pancakes.
 * It starts with a base pancake and allows you to add ingredients to it, creating a final customized pancake.
 *
 * The builder allows chaining of method calls to add ingredients and finally build the fully constructed pancake.
 * </pre>
 */
public class PancakeBuilder {
    private Pancake pancake;

    /**
     * Constructs a new `PancakeBuilder` and initializes it with a base pancake.
     * This is the starting point for building a customized pancake.
     */
    public PancakeBuilder() {
        this.pancake = new BasePancake();
    }

    /**
     * Adds an ingredient to the pancake.
     * Depending on the ingredient passed, the pancake will be decorated with the corresponding ingredient.
     *
     * The valid ingredient names are:
     * - Dark Chocolate
     * - Milk Chocolate
     * - Hazelnut
     * - Whipped Cream
     *
     * The ingredient name is converted to uppercase, spaces are replaced with underscores,
     * and then it is matched with the corresponding `Ingredient` enum constant.
     *
     * If an invalid ingredient is passed, an `IllegalArgumentException` will be thrown.
     *
     * @param ingredient The name of the ingredient to be added to the pancake.
     * @return The current `PancakeBuilder` instance to allow for method chaining.
     * @throws IllegalArgumentException If an invalid ingredient is provided.
     */
    public PancakeBuilder addIngredient(String ingredient) {
        switch (Ingredient.valueOf(ingredient.trim().replace(" ", "_").toUpperCase())) {
            case DARK_CHOCOLATE:
                pancake = new DarkChocolate(pancake);
                break;
            case MILK_CHOCOLATE:
                pancake = new MilkChocolate(pancake);
                break;
            case HAZELNUT:
                pancake = new Hazelnut(pancake);
                break;
            case WHIPPED_CREAM:
                pancake = new WhippedCream(pancake);
                break;
            default:
                throw new IllegalArgumentException("Invalid ingredient: " + ingredient);
        }
        return this;
    }

    /**
     * Builds and returns the final pancake with all the added ingredients.
     *
     * @return The constructed `Pancake` object.
     */
    public Pancake build() {
        return pancake;
    }
}