package de.markusrother.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.swing.JTextField;

/**
 * <p>CheckedTextField class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class CheckedTextField extends JTextField
	implements
		ActionListener {

	/** Constant <code>validTextColor</code> */
	private static final Color validTextColor = Color.BLACK;
	/** Constant <code>invalidTextColor</code> */
	private static final Color invalidTextColor = Color.RED;

	/** Constant <code>NO_TEXT="null"</code> */
	private static final String NO_TEXT = null;

	private final Collection<TextListener> listeners;
	private final Pattern pattern;

	/**
	 * <p>Constructor for CheckedTextField.</p>
	 *
	 * @param pattern a {@link java.util.regex.Pattern} object.
	 * @param jTextFieldSize a int.
	 */
	public CheckedTextField(final Pattern pattern, final int jTextFieldSize) {
		this(NO_TEXT, pattern, jTextFieldSize);
	}

	/**
	 * <p>Constructor for CheckedTextField.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @param pattern a {@link java.util.regex.Pattern} object.
	 * @param jTextFieldSize a int.
	 */
	public CheckedTextField(final String text, final Pattern pattern, final int jTextFieldSize) {
		super(text, jTextFieldSize);
		this.listeners = new LinkedList<>();
		this.pattern = pattern;
		addActionListener(this);
	}

	/**
	 * <p>fireTextEnteredEvent.</p>
	 *
	 * @param e a {@link java.awt.event.ActionEvent} object.
	 */
	private void fireTextEnteredEvent(final ActionEvent e) {
		e.setSource(this);
		for (final TextListener l : listeners) {
			l.textEntered(e);
		}
	}

	/** {@inheritDoc} */
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

	/**
	 * <p>addTextListener.</p>
	 *
	 * @param listener a {@link de.markusrother.swing.TextListener} object.
	 */
	public void addTextListener(final TextListener listener) {
		listeners.add(listener);
	}

	/**
	 * <p>removeTextListener.</p>
	 *
	 * @param listener a {@link de.markusrother.swing.TextListener} object.
	 */
	public void removeTextListener(final TextListener listener) {
		listeners.remove(listener);
	}

}
