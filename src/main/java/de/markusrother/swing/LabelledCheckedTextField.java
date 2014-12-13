package de.markusrother.swing;

import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelledCheckedTextField extends JPanel {

	private static final int defaultTextFieldSize = 6;

	private final JLabel jLabel;

	private final CheckedTextField checkedTextField;

	public LabelledCheckedTextField(final String label, final Pattern pattern) {
		this(label, pattern, defaultTextFieldSize);
	}

	public LabelledCheckedTextField(final String label, final Pattern pattern, final int jTextFieldSize) {
		this.jLabel = new JLabel(label);
		this.checkedTextField = new CheckedTextField(pattern, jTextFieldSize);
		add(jLabel);
		add(checkedTextField);
	}

	public void addTextListener(final TextListener listener) {
		checkedTextField.addTextListener(listener);
	}

	public void removeTextListener(final TextListener listener) {
		checkedTextField.removeTextListener(listener);
	}

	public String getText() {
		return checkedTextField.getText();
	}

	public void setText(final String text) {
		checkedTextField.setText(text);
	}

}
