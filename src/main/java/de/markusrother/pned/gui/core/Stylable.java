package de.markusrother.pned.gui.core;

import javax.swing.event.ChangeListener;

import de.markusrother.swing.ChangeEventSource;

/**
 * Components that can be styled and listen to changes in that style model.
 *
 * @param <T>
 *            the Style
 * @author Markus Rother
 * @version 1.0
 */
public interface Stylable<T extends ChangeEventSource>
	extends
		ChangeListener {

	/**
	 * <p>
	 * setStyle.
	 * </p>
	 *
	 * @param style
	 *            a T object.
	 */
	void setStyle(T style);

}
