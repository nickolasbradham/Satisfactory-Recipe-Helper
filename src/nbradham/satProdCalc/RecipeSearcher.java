package nbradham.satProdCalc;

import java.util.HashMap;

/**
 * Performs a search for the best production line .
 * 
 * @author Nickolas Bradham
 *
 */
final class RecipeSearcher {

	private static String optimizeFor = GUI.OP_LEAST_IN;

	private final ResourceTracker tracker;
	private final HashMap<String, Recipe> usedRecipes = null;
	private final RecipeSearcher prev = null;

	/**
	 * Constructs a new RecipeSearcher with target item and rate.
	 * 
	 * @param toMake The item and rate to achieve.
	 */
	RecipeSearcher(ItemStack toMake) {
		tracker = new ResourceTracker(toMake);
	}

	/**
	 * Tries to find the best production based on desired optimization.
	 * 
	 * @return The results of the search.
	 */
	CalcResults findBest() {
		//TODO New Version
		return null;
	}

	/**
	 * Sets the desired optimization of the search.
	 * 
	 * @param str The optimization.
	 */
	static final void setOptimizeFor(String str) {
		optimizeFor = str;
	}
}