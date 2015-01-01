package de.markusrother.pned.core.listeners;

import java.io.IOException;
import java.util.EventListener;

import de.markusrother.pned.core.events.PetriNetIOCommand;

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

	/**
	 * <p>
	 * setCurrentDirectory.
	 * </p>
	 *
	 * @param cmd
	 *            a {@link de.markusrother.pned.core.events.PetriNetIOCommand}
	 *            object.
	 */
	void setCurrentDirectory(PetriNetIOCommand cmd);

	/**
	 * <p>
	 * importPnml.
	 * </p>
	 *
	 * @param cmd
	 *            a {@link de.markusrother.pned.core.events.PetriNetIOCommand}
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
	 *            a {@link de.markusrother.pned.core.events.PetriNetIOCommand}
	 *            object.
	 * @throws java.io.IOException
	 *             if any.
	 */
	void exportPnml(PetriNetIOCommand cmd) throws IOException;

}
