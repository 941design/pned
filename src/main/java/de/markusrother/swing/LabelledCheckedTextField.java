package de.markusrother.swing;

import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>LabelledCheckedTextField class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LabelledCheckedTextField extends JPanel {

	/** Constant <code>defaultTextFieldSize=6</code> */
	private static final int defaultTextFieldSize = 6;

	private final JLabel jLabel;

	private final CheckedTextField checkedTextField;

	/**
	 * <p>Constructor for LabelledCheckedTextField.</p>
	 *
	 * @param label a {@link java.lang.String} object.
	 * @param pattern a {@link java.util.regex.Pattern} object.
	 */
	public LabelledCheckedTextField(final String label, final Pattern pattern) {
		this(label, pattern, defaultTextFieldSize);
	}

	/**
	 * <p>Constructor for LabelledCheckedTextField.</p>
	 *
	 * @param label a {@link java.lang.String} object.
	 * @param pattern a {@link java.util.regex.Pattern} object.
	 * @param jTextFieldSize a int.
	 */
	public LabelledCheckedTextField(final String label, final Pattern pattern, final int jTextFieldSize) {
		this.jLabel = new JLabel(label);
		this.checkedTextField = new CheckedTextField(pattern, jTextFieldSize);
		add(jLabel);
		add(checkedTextField);
	}

	/**
	 * <p>addTextListener.</p>
	 *
	 * @param listener a {@link de.markusrother.swing.TextListener} object.
	 */
	public void addTextListener(final TextListener listener) {
		checkedTextField.addTextListener(listener);
	}

	/**
	 * <p>removeTextListener.</p>
	 *
	 * @param listener a {@link de.markusrother.swing.TextListener} object.
	 */
	public void removeTextListener(final TextListener listener) {
		checkedTextField.removeTextListener(listener);
	}

	/**
	 * <p>getText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getText() {
		return checkedTextField.getText();
	}

	/**
	 * <p>setText.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public void setText(final String text) {
		checkedTextField.setText(text);
	}

}
