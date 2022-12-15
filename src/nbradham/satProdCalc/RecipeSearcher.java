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
		HashMap<String, ItemStack> recipeOuts;
		HashMap<String, Double> reqsBuild = new HashMap<>(), byBuild = new HashMap<>();
		ItemStack[] requirements = tracker.requirements();
		String reqName, outName;
		double multiplier = 0;

		for (ItemStack requirement : requirements) {
			for (Recipe recipe : RecipeManager.getRecipesByOutput(requirement.name())) {
				if (recipe.isDisabled())
					continue;

				reqsBuild.clear();
				byBuild.clear();

				// Forward all requirements accept the one being processed.
				for (ItemStack chk : requirements)
					if (chk != requirement)
						reqsBuild.put(chk.name(), chk.count());

				// Forward all byproducts.
				for (ItemStack add : tracker.byproducts())
					byBuild.put(add.name(), add.count());

				// Calculate recipe IO ratio at required rate.
				multiplier = requirement.count()
						/ (recipeOuts = recipe.getOutputs()).get(reqName = requirement.name()).count();

				// TODO Check byproducts for reuse.

				for (ItemStack out : recipeOuts.values())
					if (!reqName.equals(outName = out.name()))
						byBuild.put(outName, byBuild.getOrDefault(outName, 0d) + multiplier * out.count());

				// TODO Process already computed recipes. Check if this resource pool is already
				// found. Build and execute next RecipeSearcher. Check if results are better
				// than current best. Etc.
			}
		}
		return null;
	}

	/**
	 * Checks if {@code chk} is a resource pool already found in this chain of
	 * recipes.
	 * 
	 * @param chk The ResourceTracker to compare to previous trackers.
	 * @return True if this RecipeSearcher or previous ones have the same resource
	 *         pool.
	 */
	private boolean isPoolAlreadyFound(ResourceTracker chk) {
		return chk.equals(tracker) || prev.isPoolAlreadyFound(chk);
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