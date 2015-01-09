package de.markusrother.pned.gui.control.commands;

import de.markusrother.pned.control.commands.CommandTarget;

/**
 * <p>
 * GuiCommandTarget interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PnCommandTarget
	extends
		CommandTarget,
		LayoutCommandTarget,
		PetriNetListener,
		NodeListener {

	// NOTHING

}
