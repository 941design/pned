package de.markusrother.pned.commands.listeners;

import java.io.IOException;
import java.util.EventListener;

import de.markusrother.pned.commands.PetriNetIOCommand;

public interface PetriNetIOListener
	extends
		EventListener {

	void importPnml(PetriNetIOCommand cmd) throws IOException;

	void exportPnml(PetriNetIOCommand cmd) throws IOException;

}
