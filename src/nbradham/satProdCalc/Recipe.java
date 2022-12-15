package nbradham.satProdCalc;

import java.util.HashMap;

/**
 * Holds information on a single recipe.
 * 
 * @author Nickolas Bradham
 *
 */
final class Recipe {

	private final String name;
	private final Machine machine;
	private final HashMap<String, ItemStack> inputs, outputs;
	private final short time;
	private boolean disabled = false;

	/**
	 * Constructs a new Recipe.
	 * 
	 * @param setName    The name of the Recipe.
	 * @param setMachine The machine the Recipe is made in.
	 * @param setInputs  The input item counts of the Recipe.
	 * @param setTime    The base time the Recipe takes to craft.
	 * @param setOutputs The output item counts of the Recipe.
	 */
	Recipe(String setName, Machine setMachine, HashMap<String, ItemStack> setInputs, short setTime,
			HashMap<String, ItemStack> setOutputs) {
		name = setName;
		machine = setMachine;
		inputs = setInputs;
		outputs = setOutputs;
		time = setTime;
	}

	/**
	 * Sets the disable state of this Recipe.
	 * 
	 * @param setDisabled The new state.
	 */
	final void setDisabled(boolean setDisabled) {
		disabled = setDisabled;
	}

	/**
	 * Retrieves the name of the Recipe.
	 * 
	 * @return The name of the Recipe.
	 */
	final String getName() {
		return name;
	}

	/**
	 * Retrieves the outputs of this Recipe.
	 * 
	 * @return A ItemStack array containing all outputs.
	 */
	final HashMap<String, ItemStack> getOutputs() {
		return outputs;
	}

	/**
	 * Retrieves if this recipe is usable.
	 * 
	 * @return True if this recipe is disabled.
	 */
	final boolean isDisabled() {
		return disabled;
	}

	@Override
	public final String toString() {
		return String.format("%s[name=%s, machine=%s, inputs=%s, time=%d, outputs=%s]", Recipe.class.getSimpleName(),
				name, machine, inputs, time, outputs);
	}
}