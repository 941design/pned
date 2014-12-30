package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.TransitionCreationCommand;

/**
 * <p>NodeCreationListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeCreationListener
	extends
		EventListener {

	/**
	 * <p>createPlace.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.gui.events.PlaceCreationCommand} object.
	 */
	void createPlace(PlaceCreationCommand cmd);

	/**
	 * <p>createTransition.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.gui.events.TransitionCreationCommand} object.
	 */
	void createTransition(TransitionCreationCommand cmd);

}
