package de.markusrother.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.swing.JTextField;

public class CheckedTextField extends JTextField
	implements
		ActionListener {

	private static final Color validTextColor = Color.BLACK;
	private static final Color invalidTextColor = Color.RED;

	private static final String NO_TEXT = null;

	private final Collection<TextListener> listeners;
	private final Pattern pattern;

	public CheckedTextField(final Pattern pattern, final int jTextFieldSize) {
		this(NO_TEXT, pattern, jTextFieldSize);
	}

	public CheckedTextField(final String text, final Pattern pattern, final int jTextFieldSize) {
		super(text, jTextFieldSize);
		this.listeners = new LinkedList<>();
		this.pattern = pattern;
		addActionListener(this);
	}

	private void fireTextEnteredEvent(final ActionEvent e) {
		e.setSource(this);
		for (final TextListener l : listeners) {
			l.textEntered(e);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String text = getText();
		if (pattern.matcher(text).matches()) {
			setForeground(validTextColor);
			fireTextEnteredEvent(e);
		} else {
			setForeground(invalidTextColor);
		}
	}

	public void addTextListener(final TextListener listener) {
		listeners.add(listener);
	}

	public void removeTextListener(final TextListener listener) {
		listeners.remove(listener);
	}

}
