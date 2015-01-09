package de.markusrother.pned.gui.menus;

import java.awt.Point;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import de.markusrother.pned.gui.actions.EditMenuFactory;
import de.markusrother.pned.gui.core.GuiState;
import de.markusrother.pned.gui.dialogs.FileDialogFactory;

/**
 * TODO - maintain state and keep track of number of selected nodes! Depending
 * on number of nodes, the initial menu is enabled or disabled.
 *
 * Factory for edit menus that are sensitive to state changes.
 *
 * TODO - As the menus themselves are singleton, all this class does is
 * translate events to property changes. This is not entirely necessary, because
 * e.g. the selection removal action needs a reference to the event bus anyways.
 * the singleton might as well listen to the events itself. We could factor out
 * an event translator, whose only responsibility is to translate events. It
 * would listen to the bus for given events, and simply add the resulting events
 * to the bus again.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnMenuFactory {

	private final FileDialogFactory fileDialogFactory;
	private final EditMenuFactory editMenuFactory;

	private final GuiState state;

	/**
	 * <p>
	 * Constructor for PnEditorMenuFactory.
	 * </p>
	 *
	 * @param state a {@link de.markusrother.pned.gui.core.GuiState} object.
	 */
	public PnMenuFactory(final GuiState state) {
		this.state = state;
		this.fileDialogFactory = new FileDialogFactory(state);
		this.editMenuFactory = new EditMenuFactory(state);
	}

	/**
	 * <p>
	 * newEditMenu.
	 * </p>
	 *
	 * @return a {@link javax.swing.JMenu} object.
	 */
	public JMenu newEditMenu() {
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
		return editMenuFactory.newMenu();
	}

	/**
	 * <p>
	 * newFileMenu.
	 * </p>
	 *
	 * @return a {@link javax.swing.JMenu} object.
	 */
	public JMenu newFileMenu() {
		return new PnFileMenu(state.getEventBus(), fileDialogFactory);
	}

	/**
	 * <p>
	 * newPreferencesMenu.
	 * </p>
	 *
	 * @return a {@link javax.swing.JMenu} object.
	 */
	public JMenu newPreferencesMenu() {
		return new PnPreferencesMenu(state.getEventBus());
	}

	/**
	 * <p>
	 * newPopupMenu.
	 * </p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 * @return a {@link javax.swing.JPopupMenu} object.
	 */
	public JPopupMenu newPopupMenu(final Point point) {
		return editMenuFactory.newPopupMenu(point);
	}

}
