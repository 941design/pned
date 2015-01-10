package de.markusrother.pned.gui.control.events;

import java.util.EventListener;

/**
 * <p>
 * EdgeEditListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeEditListener
	extends
		EventListener {

	/**
	 * <p>
	 * targetComponentEntered.
	 * </p>
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.EdgeEditEvent}
	 *            object.
	 */
	void componentEntered(EdgeEditEvent e);

	/**
	 * <p>
	 * targetComponentExited.
	 * </p>
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.EdgeEditEvent}
	 *            object.
	 */
	void componentExited(EdgeEditEvent e);

	/**
	 * <p>
	 * edgeCancelled.
	 * </p>
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.EdgeEditEvent}
	 *            object.
	 */
	void edgeCancelled(EdgeEditEvent e);

	/**
	 * <p>
	 * edgeFinished.
	 * </p>
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.EdgeEditEvent}
	 *            object.
	 */
	void edgeFinished(EdgeEditEvent e);

	/**
	 * <p>
	 * edgeStarted.
	 * </p>
	 *
	 * @param e
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.EdgeEditEvent}
	 *            object.
	 */
	void edgeStarted(EdgeEditEvent e);

}
