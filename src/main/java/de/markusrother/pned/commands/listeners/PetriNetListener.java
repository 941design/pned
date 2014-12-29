package de.markusrother.pned.commands.listeners;

import java.util.EventListener;

import de.markusrother.pned.commands.PetriNetEditCommand;

public interface PetriNetListener
	extends
		EventListener {

	void disposePetriNet(PetriNetEditCommand cmd);

}
