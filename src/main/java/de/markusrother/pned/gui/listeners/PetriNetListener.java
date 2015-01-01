package de.markusrother.pned.gui.listeners;

import java.util.EventListener;

import de.markusrother.pned.gui.events.PetriNetEditCommand;

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
	 * @param cmd a {@link de.markusrother.pned.gui.events.PetriNetEditCommand} object.
	 */
	void createPetriNet(PetriNetEditCommand cmd);

}
