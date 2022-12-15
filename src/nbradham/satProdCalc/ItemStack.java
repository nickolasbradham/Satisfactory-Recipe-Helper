package nbradham.satProdCalc;

/**
 * Holds information on a single item stack.
 * 
 * @author Nickolas Bradham
 *
 */
record ItemStack(String name, double count) {

	@Override
	public final boolean equals(Object other) {
		ItemStack o = (ItemStack) other;
		return o.name.equals(name) && o.count == count;
	}

	/**
	 * Parses a String into a new ItemStack instance.
	 * 
	 * @param str The String to parse.
	 * @return The new ItemStack instance.
	 */
	final static ItemStack parseItemStack(String str) {
		String[] indiv = str.split(":");
		return new ItemStack(indiv[1], Float.parseFloat(indiv[0]));
	}
}