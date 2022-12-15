package nbradham.satProdCalc;

import java.util.HashMap;

/**
 * Holds information on a single machine.
 * 
 * @author Nickolas Bradham
 *
 */
record Machine(String name, HashMap<String, ItemStack> cost, short power) {

	@Override
	public final String toString() {
		return String.format("%s[name=%s, cost=%s, power=%d]", Machine.class.getSimpleName(), name, cost, power);
	}
}