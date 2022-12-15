package nbradham.satProdCalc;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles core operations.
 * 
 * @author Nickolas Bradham
 *
 */
final class Main {

	static {
		MachineManager.loadMachines();
		RecipeManager.loadRecipes();
	}
	private final GUI gui = new GUI(this);

	/**
	 * Tells the GUI to initialize and show.
	 */
	private void start() {
		gui.initAndShow();
	}

	/**
	 * Performs the production line calculations.
	 * 
	 * @param itemStack The item and rate to produce.
	 * @return The results of the calculation.
	 */
	CalcResults calculate(ItemStack itemStack) {
		return new RecipeSearcher(itemStack).findBest();
	}

	/**
	 * Retrieves all craftable items from all recipes in the {@link RecipeManager}.
	 * 
	 * @return All available item names.
	 */
	final String[] getItems() {
		ArrayList<String> itemList = new ArrayList<>();
		for (Recipe r : RecipeManager.getRecipes())
			r.getOutputs().values().forEach(is -> {
				String name = is.name();
				if (!itemList.contains(name))
					itemList.add(name);
			});
		return itemList.toArray(new String[0]);
	}

	/**
	 * Parses a String into a array of ItemStack instances.
	 * 
	 * @param str The String to parse.
	 * @return A new ItemStack array containing all item stacks from the String.
	 */
	static HashMap<String, ItemStack> parseItemStacks(String str) {
		HashMap<String, ItemStack> iBuild = new HashMap<>();
		ItemStack is;
		for (String s : str.split(", "))
			iBuild.put((is = ItemStack.parseItemStack(s)).name(), is);
		return iBuild;
	}

	/**
	 * Constructs and starts a new Main instance.
	 * 
	 * @param args Ignored.
	 */
	public static final void main(String[] args) {
		new Main().start();
	}
}