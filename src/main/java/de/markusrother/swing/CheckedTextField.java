package de.markusrother.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckedTextField extends JPanel
	implements
		ActionListener {

	private static final int defaultTextFieldSize = 6;
	private static final Color validTextColor = Color.BLACK;
	private static final Color invalidTextColor = Color.RED;

	private final Collection<TextListener> listeners;
	private final Pattern pattern;

	private final JLabel jLabel;
	private final JTextField jTextField;

	public CheckedTextField(final String label, final Pattern pattern) {
		this(label, pattern, defaultTextFieldSize);
	}

	public CheckedTextField(final String label, final Pattern pattern, final int jTextFieldSize) {
		this.pattern = pattern;
		this.listeners = new LinkedList<>();
		this.jLabel = new JLabel(label);
		this.jTextField = new JTextField(jTextFieldSize);
		jTextField.addActionListener(this);
		add(jLabel);
		add(jTextField);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String text = jTextField.getText();
		if (pattern.matcher(text).matches()) {
			jTextField.setForeground(validTextColor);
			fireTextEnteredEvent(e);
		} else {
			jTextField.setForeground(invalidTextColor);
		}
	}

	private void fireTextEnteredEvent(final ActionEvent e) {
		e.setSource(this);
		for (final TextListener l : listeners) {
			l.textEntered(e);
		}
	}

	public void addTextListener(final TextListener listener) {
		listeners.add(listener);
	}

	public void removeTextListener(final TextListener listener) {
		listeners.remove(listener);
	}

	public String getText() {
		return jTextField.getText();
	}

	public void setText(final String text) {
		jTextField.setText(text);
	}

}
