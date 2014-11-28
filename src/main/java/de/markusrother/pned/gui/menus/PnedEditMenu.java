package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JSeparator;

import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.menus.actions.CreatePlaceAction;
import de.markusrother.pned.gui.menus.actions.CreateTransitionAction;
import de.markusrother.pned.gui.menus.actions.DefaultNodeLocationProvider;
import de.markusrother.pned.gui.menus.actions.NodeCreationToggleAction;
import de.markusrother.pned.gui.menus.actions.RemoveSelectedNodesAction;

public class PnedEditMenu extends JMenu {

	private static final String label = "Edit";
	private static final int mnemonic = KeyEvent.VK_E;

	public PnedEditMenu(final boolean areNodesSelected, final NodeCreationMode nodeCreationMode) {
		// TODO - Why not receive the grid, because Actions are grid dependent
		// rather than headless model dependent. That is exactly the choice I
		// have to make, here: Should menus be headless (via eventBus) or not.
		// If so, we need a stateful MenuFactory that listens on the bus. That
		// is however not sufficient, because, the menubar is instantiated once.
		// The created instances would hence also have to listen to the bus
		// themselves (or to property changes for that matter).

		// TODO - we have to make sure that upon loading files or other actions
		// that indirectly deselect nodes the removal action is also disabled.
		// An idea would be to have implicit actions.
		super(label);
		setMnemonic(mnemonic);

		final Object eventSource = this;
		add(CreatePlaceAction.newMenuItem(eventSource, DefaultNodeLocationProvider.INSTANCE));
		add(CreateTransitionAction.newMenuItem(eventSource, DefaultNodeLocationProvider.INSTANCE));
		add(RemoveSelectedNodesAction.newMenuItem(eventSource, areNodesSelected));
		add(new JSeparator());
		add(NodeCreationToggleAction.newMenuItem(eventSource, nodeCreationMode));
	}

}
