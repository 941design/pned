package de.markusrother.pned.gui.styles;

import java.awt.Component;

/**
 * <p>Style interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface Style<T extends Component> {

	/**
	 * <p>apply.</p>
	 *
	 * @param t a T object.
	 */
	void apply(T t);
}
