package de.markusrother.pned.gui.listeners;

import de.markusrother.pned.core.control.CommandTarget;
import de.markusrother.pned.gui.layout.listeners.LayoutCommandTarget;

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

}
