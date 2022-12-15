package nbradham.satProdCalc;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Handles operations dealing with machine information.
 * 
 * @author Nickolas Bradham
 *
 */
final class MachineManager {

	private static final HashMap<String, Machine> MACHINES = new HashMap<>();

	/**
	 * Parses machines data file.
	 */
	final static void loadMachines() {
		Scanner scan = new Scanner(MachineManager.class.getResourceAsStream("/machines.tsv")).useDelimiter("\t|\r\n");
		scan.nextLine();

		String name;
		while (scan.hasNext())
			MACHINES.put(name = scan.next(), new Machine(name, Main.parseItemStacks(scan.next()), scan.nextShort()));
	}

	/**
	 * Retrieves the Machine instance with name {@code name}.
	 * 
	 * @param name The name of the machine to get.
	 * @return The Machine record holding the desired information.
	 */
	final static Machine getMachine(String name) {
		return MACHINES.get(name);
	}
}