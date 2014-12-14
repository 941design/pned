package de.markusrother.pned.gui;

import de.markusrother.pned.gui.listeners.NodeCreationListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.PlaceEditListener;

public interface EventTarget
	extends
		NodeCreationListener,
		NodeMotionListener,
		PlaceEditListener {

}
