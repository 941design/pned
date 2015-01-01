package de.markusrother.pned.commands.listeners;

import java.io.IOException;
import java.util.EventListener;

import de.markusrother.pned.commands.PetriNetIOCommand;

/**
 * <p>
 * PetriNetIOListener interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PetriNetIOListener
	extends
		EventListener {

	void setCurrentPath(PetriNetIOCommand cmd);

	/**
	 * <p>
	 * importPnml.
	 * </p>
	 *
	 * @param cmd
	 *            a {@link de.markusrother.pned.commands.PetriNetIOCommand}
	 *            object.
	 * @throws java.io.IOException
	 *             if any.
	 */
	void importPnml(PetriNetIOCommand cmd) throws IOException;

	/**
	 * <p>
	 * exportPnml.
	 * </p>
	 *
	 * @param cmd
	 *            a {@link de.markusrother.pned.commands.PetriNetIOCommand}
	 *            object.
	 * @throws java.io.IOException
	 *             if any.
	 */
	void exportPnml(PetriNetIOCommand cmd) throws IOException;

}
