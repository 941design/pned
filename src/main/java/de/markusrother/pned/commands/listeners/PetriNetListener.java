package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.PetriNetEditCommand;

/**
 * <p>PetriNetListener interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PetriNetListener
	extends
		EventListener {

	/**
	 * <p>disposePetriNet.</p>
	 *
	 * @param cmd a {@link de.markusrother.pned.commands.PetriNetEditCommand} object.
	 */
	void disposePetriNet(PetriNetEditCommand cmd);

}
