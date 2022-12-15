package nbradham.satProdCalc;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 * Handles all GUI related operations.
 * 
 * @author Nickolas Bradham
 *
 */
final class GUI {

	private static final String ALT_START = "Alternate: ";

	private final JCheckBox[] altBoxes;
	private final Main main;

	/**
	 * Constructs a new GUI instance.
	 * 
	 * @param setMain Pass in the Main instance.
	 */
	public GUI(Main setMain) {
		main = setMain;
		ArrayList<JCheckBox> altBoxList = new ArrayList<>();
		String name;
		ActionListener altChkListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAltRecipeForButton((JCheckBox) e.getSource());
			}
		};
		for (Recipe r : RecipeManager.getRecipes())
			if ((name = r.getName()).startsWith(ALT_START)) {
				JCheckBox t = new JCheckBox(name.substring(11), true);
				t.addActionListener(altChkListener);
				altBoxList.add(t);
			}
		Arrays.sort(altBoxes = altBoxList.toArray(new JCheckBox[0]), (a, b) -> {
			return a.getText().compareTo(b.getText());
		});
	}

	/**
	 * Creates and shows the {@link JFrame}.
	 */
	final void initAndShow() {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Satisfactory Production Calculator");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new GridBagLayout());

			GridBagConstraints gbc = new GridBagConstraints();

			JPanel altPane = new JPanel(new GridBagLayout()), outPane = new JPanel(), resPane = new JPanel();
			altPane.setBorder(new TitledBorder("Available Alternative Recipes"));

			JButton chkAlts = new JButton("Check All"), unchkAlts = new JButton("Uncheck All"),
					runCalcs = new JButton("Run Calculations");

			unchkAlts.addActionListener(new ActionListener() {

				@Override
				public final void actionPerformed(ActionEvent e) {
					setAltBoxes(false);
				}
			});

			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = .5;
			altPane.add(unchkAlts, gbc);

			chkAlts.addActionListener(new ActionListener() {

				@Override
				public final void actionPerformed(ActionEvent e) {
					setAltBoxes(true);
				}
			});

			gbc.gridx = 1;
			altPane.add(chkAlts, gbc);

			gbc.gridy = 1;
			altPane.add(Box.createVerticalStrut(4), gbc);

			Box altsBox = Box.createVerticalBox();
			for (JCheckBox jcb : altBoxes)
				altsBox.add(jcb);

			JScrollPane scroll = new JScrollPane(altsBox, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setPreferredSize(new Dimension(250, 650));
			scroll.getVerticalScrollBar().setUnitIncrement(8);

			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 2;
			altPane.add(scroll, gbc);

			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.gridheight = 3;
			frame.add(altPane, gbc);
			outPane.setBorder(new TitledBorder("Desired Output"));

			String[] items = main.getItems();
			Arrays.sort(items);
			JComboBox<String> outSel = new JComboBox<>(items);
			outPane.add(outSel);

			JSpinner outRate = new JSpinner(new SpinnerNumberModel(1f, 0, Double.MAX_VALUE, 1));
			outRate.setPreferredSize(new Dimension(50, 20));
			outPane.add(new JLabel("at"));
			outPane.add(outRate);
			outPane.add(new JLabel("item(s) / m."));

			gbc.gridx = 1;
			gbc.gridheight = 1;
			frame.add(outPane, gbc);

			runCalcs.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ProductionLine
							.calculate(new ItemStack((String) outSel.getSelectedItem(), (double) outRate.getValue()));
					// TODO Post results.
				}
			});
			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			frame.add(runCalcs, gbc);

			JTextArea resultArea = new JTextArea("Click \"Run Calculations\" or press Enter to calculate results.", 35,
					30);
			resultArea.setEditable(false);

			resPane.setBorder(new TitledBorder("Results"));
			resPane.add(resultArea);

			gbc.gridx = 1;
			gbc.gridy = 2;
			gbc.gridwidth = 3;
			gbc.anchor = GridBagConstraints.NORTH;
			frame.add(resPane, gbc);

			frame.getRootPane().setDefaultButton(runCalcs);
			frame.pack();
			frame.setVisible(true);
		});
	}

	/**
	 * Sets the selected state of all alternate recipe {@link JCheckBox}s and
	 * updates the recipes.
	 * 
	 * @param state The new state.
	 */
	private void setAltBoxes(boolean state) {
		SwingUtilities.invokeLater(() -> {
			for (JCheckBox jcb : altBoxes) {
				jcb.setSelected(state);
				updateAltRecipeForButton(jcb);
			}
		});
	}

	/**
	 * Updates the enable state of the recipe linked to {@code jcb}.
	 * 
	 * @param jcb The JCheckBox to update the recipe of.
	 */
	private void updateAltRecipeForButton(JCheckBox jcb) {
		RecipeManager.setRecipeDisabled(ALT_START + jcb.getText(), !jcb.isSelected());
	}
}