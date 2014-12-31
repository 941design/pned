package de.markusrother.pned.gui;

import de.markusrother.pned.commands.listeners.EdgeLayoutListener;
import de.markusrother.pned.commands.listeners.MarkingLayoutListener;
import de.markusrother.pned.commands.listeners.PlaceLayoutListener;
import de.markusrother.pned.commands.listeners.TransitionLayoutListener;

public interface GuiEventTarget
	extends
		EventTarget,
		PlaceLayoutListener,
		TransitionLayoutListener,
		EdgeLayoutListener,
		MarkingLayoutListener {

}
