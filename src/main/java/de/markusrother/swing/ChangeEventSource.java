package de.markusrother.swing;

import javax.swing.event.ChangeListener;

public interface ChangeEventSource {

	/**
	 * <p>
	 * addChangeListener.
	 * </p>
	 *
	 * @param listener
	 *            a {@link javax.swing.event.ChangeListener} object.
	 */
	void addChangeListener(ChangeListener listener);

	/**
	 * <p>
	 * removeChangeListener.
	 * </p>
	 *
	 * @param listener
	 *            a {@link javax.swing.event.ChangeListener} object.
	 */
	void removeChangeListener(ChangeListener listener);

}
