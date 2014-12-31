package de.markusrother.pned.gui.menus;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;
import static de.markusrother.pned.gui.NodeCreationMode.TRANSITION;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.menus.actions.CreatePlaceAction;
import de.markusrother.pned.gui.menus.actions.CreateTransitionAction;
import de.markusrother.pned.gui.menus.actions.RemoveSelectedNodesAction;

/**
 * <p>PnedEditMenu class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnedEditMenu extends JMenu {

	/** Constant <code>label="Edit"</code> */
	private static final String label = "Edit";
	/** Constant <code>mnemonic=KeyEvent.VK_E</code> */
	private static final int mnemonic = KeyEvent.VK_E;

	/**
	 * <p>Constructor for PnedEditMenu.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param areNodesSelected a boolean.
	 * @param mode a {@link de.markusrother.pned.gui.NodeCreationMode} object.
	 */
	public PnedEditMenu(final EventBus eventMulticaster, final boolean areNodesSelected, final NodeCreationMode mode) {
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
		final ButtonGroup buttonGroup = new ButtonGroup();

		final JRadioButtonMenuItem placeItem = CreatePlaceAction.newMenuItem( //
				eventMulticaster, //
				eventSource, //
				DefaultNodeLocationProvider.INSTANCE);
		buttonGroup.add(placeItem);
		add(placeItem);
		placeItem.setSelected(mode == PLACE);

		final JRadioButtonMenuItem transitionItem = CreateTransitionAction.newMenuItem( //
				eventMulticaster, //
				eventSource, //
				DefaultNodeLocationProvider.INSTANCE);
		buttonGroup.add(transitionItem);
		add(transitionItem);
		transitionItem.setSelected(mode == TRANSITION);

		add(RemoveSelectedNodesAction.newMenuItem(eventMulticaster, eventSource, areNodesSelected));
	}

}
