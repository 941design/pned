package de.markusrother.pned.control.listeners;

import java.util.EventListener;

import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;

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
	 * @param cmd a {@link de.markusrother.pned.control.commands.PlaceCreationCommand} object.
	 */
	void createPlace(PlaceCreationCommand cmd);

	/**
	 * <p>createTransition.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.control.commands.TransitionCreationCommand} object.
	 */
	void createTransition(TransitionCreationCommand cmd);

}
