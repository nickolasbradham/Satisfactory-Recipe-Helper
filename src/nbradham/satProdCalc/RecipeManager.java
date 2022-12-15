package nbradham.satProdCalc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Handles storing and managing all {@link Recipe} instances.
 * 
 * @author Nickolas Bradham
 *
 */
final class RecipeManager {

	private static final HashMap<String, Recipe[]> RECIPES_BY_OUTPUT = new HashMap<>();
	private static final HashMap<String, Recipe> RECIPES_BY_NAME = new HashMap<>();

	/**
	 * Parses recipes data file.
	 */
	final static void loadRecipes() {
		Scanner scan = new Scanner(RecipeManager.class.getResourceAsStream("/recipes.tsv")).useDelimiter("\t|\r\n");
		scan.nextLine();

		String name;
		while (scan.hasNext()) {
			Recipe r = new Recipe(name = scan.next(), MachineManager.getMachine(scan.next()),
					Main.parseItemStacks(scan.next()), scan.nextShort(), Main.parseItemStacks(scan.next()));
			RECIPES_BY_NAME.put(name, r);
			r.getOutputs().values().forEach(is -> {
				String isName = is.name();
				Recipe[] rs = Arrays.copyOf(rs = RECIPES_BY_OUTPUT.getOrDefault(isName, new Recipe[0]), rs.length + 1);
				rs[rs.length - 1] = r;
				RECIPES_BY_OUTPUT.put(isName, rs);
			});
		}
	}

	/**
	 * Sets the enable state of {@link Recipe} {@code recipeName}.
	 * 
	 * @param recipeName The name of the {@link Recipe}.
	 * @param disabled    The new state.
	 */
	static void setRecipeDisabled(String recipeName, boolean disabled) {
		RECIPES_BY_NAME.get(recipeName).setDisabled(disabled);
	}

	/**
	 * Retrieves all Recipe instances.
	 * 
	 * @return A new Recipe array.
	 */
	final static Recipe[] getRecipes() {
		return RECIPES_BY_NAME.values().toArray(new Recipe[0]);
	}

	/**
	 * Retrieves all recipes with {@code name} as a result.
	 * 
	 * @param name The item to search by.
	 * @return A Recipe array containing all relevant recipes.
	 */
	final static Recipe[] getRecipesByOutput(String name) {
		return RECIPES_BY_OUTPUT.get(name);
	}
}