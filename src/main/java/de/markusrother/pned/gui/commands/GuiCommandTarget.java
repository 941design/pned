package de.markusrother.pned.gui.commands;

import de.markusrother.pned.control.commands.CommandTarget;

/**
 * <p>
 * GuiCommandTarget interface.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface GuiCommandTarget
	extends
		CommandTarget,
		LayoutCommandTarget,
		PetriNetListener,
		NodeListener {

	// NOTHING

}
