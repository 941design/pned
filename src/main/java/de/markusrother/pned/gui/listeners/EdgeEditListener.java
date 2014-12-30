package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.EdgeEditEvent;

/**
 * <p>EdgeEditListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeEditListener
	extends
		EventListener {

	/**
	 * <p>targetComponentEntered.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.EdgeEditEvent} object.
	 */
	void targetComponentEntered(EdgeEditEvent e);

	/**
	 * <p>targetComponentExited.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.EdgeEditEvent} object.
	 */
	void targetComponentExited(EdgeEditEvent e);

	/**
	 * <p>edgeMoved.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.EdgeEditEvent} object.
	 */
	void edgeMoved(EdgeEditEvent e);

	/**
	 * <p>edgeCancelled.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.EdgeEditEvent} object.
	 */
	void edgeCancelled(EdgeEditEvent e);

	/**
	 * <p>edgeFinished.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.EdgeEditEvent} object.
	 */
	void edgeFinished(EdgeEditEvent e);

	/**
	 * <p>edgeStarted.</p>
	 *
	 * @param e a {@link de.markusrother.pned.gui.events.EdgeEditEvent} object.
	 */
	void edgeStarted(EdgeEditEvent e);

}
