package de.markusrother.swing.snap;

import java.util.EventListener;

/**
 * <p>SnapPointListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface SnapPointListener extends EventListener {

	/**
	 * <p>snapPointMoved.</p>
	 *
	 * @param deltaX a int.
	 * @param deltaY a int.
	 */
	void snapPointMoved(int deltaX, int deltaY);

}
