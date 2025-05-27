package org.pancakelab;

import org.pancakelab.model.order.Order;
import org.pancakelab.model.constant.Ingredient;
import org.pancakelab.model.pancake.Pancake;
import org.pancakelab.template.OrderProcessTemplate;
import org.pancakelab.template.PancakeOrderProcess;
import org.pancakelab.service.DeliveryService;
import org.pancakelab.service.OrderService;
import org.pancakelab.service.PancakeService;

import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

/**
 * Main starter class for the Pancake Factory
 * This class serves as the entry point to the Pancake Factory application. It handles the user interface, allowing customers to:
 * - View the pancake ingredient menu.
 * - Place an order with selected ingredients.
 * - Confirm or cancel the order.
 */
public class Main {

	// Logger instance for logging messages
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	// Service instances for handling pancake creation, orders, and delivery
	private static PancakeService pancakeService = new PancakeService();
	private static OrderService orderService = new OrderService();
	private static DeliveryService deliveryService = new DeliveryService(pancakeService, orderService);

	// Flag to control the main loop
	static boolean running = true;

	// Static block to configure the logging format
	static {
		// Disable default handlers
		Logger rootLogger = Logger.getLogger("");
		for (Handler handler : rootLogger.getHandlers()) {
			rootLogger.removeHandler(handler);
		}
		// Add our custom console handler with a simple formatter
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatterNoTimestamp2());
		handler.setLevel(Level.ALL);

		logger.addHandler(handler);
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
	}

	/**
	 * Main method to start the Pancake Factory application.
	 * Provides a menu for users to choose options, such as showing the menu, placing an order, or exiting.
	 *
	 * @param args Command-line arguments (not used in this implementation).
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		logger.info("\n \u001B[34mWelcome to Pancake Factory!\u001B[0m");

		while (running) {
			// Main menu options for the user
			logger.info("\n \u001B[35mPlease select an option:");
			logger.info("1. Show Menu");
			logger.info("2. Place an Order");
			logger.info("3. Exit");

			int choice = getValidatedInt(scanner, "👉 Enter your choice (1-3):", 1, 3);

			switch (choice) {
				case 1:
					showMenu();  // Display the menu of ingredients
					break;
				case 2:
					takeOrder(scanner);  // Process the pancake order
					break;
				case 3:
					logger.info("\u001B[34mThank you for visiting Pancake Factory! Have a delicious day!");
					running = false;  // Exit the application
					break;
				default:
					logger.warning("❌ Invalid choice. Please select 1, 2, or 3.");
			}
		}

		scanner.close();
	}

	/**
	 * Displays the list of available ingredients along with their prices.
	 * This allows the user to choose from a variety of ingredients for their pancake.
	 */
	private static void showMenu() {
		Ingredient[] ingredients = Ingredient.values();
		logger.info("🥞 Build your pancake by choosing ingredients:");
		for (int i = 0; i < ingredients.length; i++) {
			logger.info(String.format("\n %d. %s ($%s)", i + 1, ingredients[i].getName(), ingredients[i].getPrice()));
		}
	}

	/**
	 * Takes the order from the user, including their name, delivery details, and pancake ingredient choices.
	 * Displays a summary of the order and asks for confirmation.
	 *
	 * @param scanner The scanner object used to get user input.
	 */
	private static void takeOrder(Scanner scanner) {
		logger.info("\n👉 Enter your name: ");
		String name = scanner.nextLine();

		int buildingNumber = getValidatedInt(scanner, "👉 Enter the building number (1-10):", 1, 10);
		int roomNumber = getValidatedInt(scanner, "👉 Enter the room number (1-100):", 1, 100);

		// Show the menu of ingredients to the user
		showMenu();

		// Get the selected ingredients for the pancake
		List<String> selectedIngredients = getValidIngredients(scanner);

		// Create a new order
		Order order = orderService.createOrder(buildingNumber, roomNumber);
		Pancake pancake = pancakeService.createPancake(selectedIngredients, order);

		// Ask for the number of pancakes to be ordered
		int quantity = getValidatedInt(scanner, "👉 How many pancakes would you like?", 1);

		// Display the order summary
		logger.info("\n\u001B[32m---******** Your Order Summary ******---\u001B[0m");
		logger.info("\u001B[32mName:" + name);
		logger.info("\u001B[32mPancake:" + pancake.getDescription());
		logger.info("\u001B[32mQuantity:" + quantity);
		logger.info("\u001B[32mTotal Price: $" + quantity * pancake.getPrice());
		logger.info("\n\u001B[32m------ ****************************** ---------\u001B[0m");

		// Add the ordered pancakes to the order
		pancakeService.addPancakes(order, pancake, quantity);

		// Ask the user to confirm or cancel the order
		int choice = getValidatedInt(scanner, "👉 Please select an option to confirm your order:\n1. Proceed with the order\n2. Cancel the order", 1, 2);

		switch (choice) {
			case 1:
				confirmedOrder(order.getId());
				break;
			case 2:
				cancelOrder(order.getId());
				break;
			default:
				logger.warning("❌ Invalid choice. Please select 1 or 2.");
		}
	}

	/**
	 * Prompts the user to input valid ingredient numbers, ensuring they are correct.
	 *
	 * @param scanner The scanner object used to get user input.
	 * @return A list of valid ingredient numbers.
	 */
	private static List<String> getValidIngredients(Scanner scanner) {
		List<String> selectedIngredients = null;
		while (selectedIngredients == null || selectedIngredients.isEmpty()) {
			logger.info("👉 Enter ingredient numbers (comma-separated):");
			String input = scanner.nextLine();
			selectedIngredients = validateIngredients(input);
			if (selectedIngredients == null || selectedIngredients.isEmpty()) {
				logger.warning("❌ Please enter valid ingredient numbers (e.g., 1, 2, 3).");
			}
		}
		return selectedIngredients;
	}

	/**
	 * Validates the ingredient numbers input by the user.
	 *
	 * @param input The comma-separated string of ingredient numbers.
	 * @return A list of valid ingredient numbers.
	 */
	private static List<String> validateIngredients(String input) {
		List<String> selectedIngredients = new ArrayList<>();
		String[] ingredients = input.split(",");
		for (String ingredient : ingredients) {
			try {
				int index = Integer.parseInt(ingredient.trim()) - 1;
				if (index >= 0 && index < Ingredient.values().length) {
					selectedIngredients.add(String.valueOf(index + 1));
				}
			} catch (NumberFormatException e) {
				// Ignore invalid numbers
			}
		}
		return selectedIngredients.isEmpty() ? null : selectedIngredients;
	}

	/**
	 * Confirms the order and processes it, starting the delivery process.
	 *
	 * @param orderId The UUID of the order to be confirmed.
	 */
	private static void confirmedOrder(UUID orderId) {
		OrderProcessTemplate orderProcessTemplate = new PancakeOrderProcess(pancakeService, orderService, deliveryService);
		orderProcessTemplate.processOrder(orderId);
		logger.info("\u001B[32mYour order is successful, and your pancake will be delivered in 15 minutes.\u001B[0m");
	}

	/**
	 * Cancels the order and removes any associated pancakes.
	 *
	 * @param orderId The UUID of the order to be canceled.
	 */
	private static void cancelOrder(UUID orderId) {
		orderService.cancelOrder(orderId, pancakeService.getAllPancakes());
		logger.info("\u001B[31mYour order is cancelled.\u001B[0m");
	}

	/**
	 * Prompts the user to enter a valid integer within the specified range.
	 *
	 * @param scanner The scanner object used to get user input.
	 * @param prompt The prompt message displayed to the user.
	 * @param min The minimum valid integer.
	 * @param max The maximum valid integer.
	 * @return The validated integer entered by the user.
	 */
	private static int getValidatedInt(Scanner scanner, String prompt, int min, int max) {
		int value = -1;
		boolean valid = false;
		while (!valid) {
			logger.info(prompt);
			if (scanner.hasNextInt()) {
				value = scanner.nextInt();
				scanner.nextLine(); // consume newline
				if (value >= min && value <= max) {
					valid = true;
				} else {
					logger.warning("❌ Please enter a valid number between " + min + " and " + max);
				}
			} else {
				logger.warning("❌ Invalid input. Please enter a number.");
				scanner.nextLine(); // discard invalid input
			}
		}
		return value;
	}

	/**
	 * Prompts the user to enter a valid integer with a minimum value (no maximum limit).
	 *
	 * @param scanner The scanner object used to get user input.
	 * @param prompt The prompt message displayed to the user.
	 * @param min The minimum valid integer.
	 * @return The validated integer entered by the user.
	 */
	private static int getValidatedInt(Scanner scanner, String prompt, int min) {
		return getValidatedInt(scanner, prompt, min, Integer.MAX_VALUE);
	}

}

/**
 * Custom formatter class for logging.
 * This formatter outputs only the log message without any timestamp or additional metadata.
 */
class SimpleFormatterNoTimestamp2 extends Formatter {
	@Override
	public String format(LogRecord record) {
		return record.getMessage() + System.lineSeparator();
	}
}