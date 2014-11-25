package de.markusrother.pned.gui.menu;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;
import static de.markusrother.pned.gui.menu.EditMenuFactory.ARE_NODES_SELECTED;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.TransitionCreationRequest;

public class PnedEditMenu extends JMenu
	implements
		PropertyChangeListener {

	private static final String EDIT = "Edit";
	private static final String CREATE_PLACE = "Create place";
	private static final String CREATE_TRANSITION = "Create transition";
	private static final String DELETE_NODES = "Delete selected nodes";

	private static final CreatePlaceAction createPlaceAction = new CreatePlaceAction();
	private static final CreateTransitionAction createTransitionAction = new CreateTransitionAction();
	private static final DeleteNodesAction deleteNodesAction = new DeleteNodesAction();

	static class CreatePlaceAction extends AbstractAction {

		CreatePlaceAction() {
			super(CREATE_PLACE);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			eventBus.createPlace(new PlaceCreationRequest(this));
			// TODO - alternatively grid.createPlace();
		}
	}

	static class CreateTransitionAction extends AbstractAction {

		CreateTransitionAction() {
			super(CREATE_TRANSITION);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			eventBus.createTransition(new TransitionCreationRequest(this));
			// TODO - alternatively grid.createTransition();
		}
	}

	static class DeleteNodesAction extends AbstractAction {

		DeleteNodesAction() {
			super(DELETE_NODES);
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
			setEnabled(false); // Initially no nodes are selected
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			eventBus.removeSelectedNodes(new RemoveSelectedNodesEvent(this));
		}
	}

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
		add(new JMenuItem(createPlaceAction));
		add(new JMenuItem(createTransitionAction));
		add(new JMenuItem(deleteNodesAction));
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		// TODO - we have to make sure that upon loading files or other actions
		// that indirectly deselect nodes the removal action is also disabled.
		// An idea would be to have implicit actions.
		if (ARE_NODES_SELECTED.equals(evt.getPropertyName())) {
			deleteNodesAction.setEnabled((boolean) evt.getNewValue());
		} else {
			throw new IllegalArgumentException();
		}
	}

}
