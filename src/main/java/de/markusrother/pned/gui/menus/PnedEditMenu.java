package de.markusrother.pned.gui.menus;

import static de.markusrother.pned.gui.menus.EditMenuFactory.ARE_NODES_SELECTED;
import static de.markusrother.pned.gui.menus.items.DeleteNodesMenuItem.deleteNodesAction;

import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;
import javax.swing.JSeparator;

import de.markusrother.pned.gui.menus.items.CreatePlaceMenuItem;
import de.markusrother.pned.gui.menus.items.CreateTransitionMenuItem;
import de.markusrother.pned.gui.menus.items.DeleteNodesMenuItem;
import de.markusrother.pned.gui.menus.items.NodeCreationToggleMenuItem;

public class PnedEditMenu extends JMenu
	implements
		PropertyChangeListener {

	private static final String EDIT = "Edit";

	public PnedEditMenu() {
		// TODO - Why not receive the grid, because Actions are grid dependent
		// rather than headless model dependent. That is exactly the choice I
		// have to make, here: Should menus be headless (via eventBus) or not.
		// If so, we need a stateful MenuFactory that listens on the bus. That
		// is however not sufficient, because, the menubar is instantiated once.
		// The created instances would hence also have to listen to the bus
		// themselves (or to property changes for that matter).
		super(EDIT);

		setMnemonic(KeyEvent.VK_E);
		add(new CreatePlaceMenuItem());
		add(new CreateTransitionMenuItem());
		add(new DeleteNodesMenuItem());
		add(new JSeparator());
		add(new NodeCreationToggleMenuItem());
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		// TODO - we have to make sure that upon loading files or other actions
		// that indirectly deselect nodes the removal action is also disabled.
		// An idea would be to have implicit actions.
		if (ARE_NODES_SELECTED.equals(evt.getPropertyName())) {
			// TODO - this is somewhat not nice. Do we really need stateful
			// actions, or should state be managed elsewhere?
			deleteNodesAction.setEnabled((boolean) evt.getNewValue());
		} else {
			throw new IllegalArgumentException();
		}
	}

}
