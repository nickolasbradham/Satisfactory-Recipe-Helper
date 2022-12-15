package nbradham.satProdCalc;

import java.util.Arrays;

/**
 * Holds information about the current state of resources in the search.
 * 
 * @author Nickolas Bradham
 *
 */
record ResourceTracker(ItemStack[] requirements, ItemStack[] byproducts) {

	/**
	 * Creates a new ResourceTracker with only {@code toMake} as a requirement.
	 * 
	 * @param toMake The item and rate being required.
	 */
	ResourceTracker(ItemStack toMake) {
		this(new ItemStack[] { toMake }, new ItemStack[0]);
	}

	@Override
	public final boolean equals(Object other) {
		ResourceTracker o = (ResourceTracker) other;
		return Arrays.equals(requirements, o.requirements) && Arrays.equals(byproducts, o.byproducts);
	}
}