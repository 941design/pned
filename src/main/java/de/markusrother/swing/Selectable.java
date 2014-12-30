package de.markusrother.swing;

import java.awt.Rectangle;

/**
 * <p>Selectable interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface Selectable {

	/**
	 * <p>isContained.</p>
	 *
	 * @param r a {@link java.awt.Rectangle} object.
	 * @return a boolean.
	 */
	boolean isContained(Rectangle r);

}
