package de.markusrother.pned.core.listeners;

import java.util.EventListener;

import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.TransitionCreationCommand;

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
	 * @param cmd a {@link de.markusrother.pned.core.commands.PlaceCreationCommand} object.
	 */
	void createPlace(PlaceCreationCommand cmd);

	/**
	 * <p>createTransition.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.core.commands.TransitionCreationCommand} object.
	 */
	void createTransition(TransitionCreationCommand cmd);

}
