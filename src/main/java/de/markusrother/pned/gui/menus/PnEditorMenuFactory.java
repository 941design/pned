package de.markusrother.pned.gui.menus;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;
import static de.markusrother.pned.gui.NodeCreationMode.TRANSITION;

import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.actions.CreatePlaceAction;
import de.markusrother.pned.gui.actions.CreateTransitionAction;
import de.markusrother.pned.gui.actions.LocationProvider;
import de.markusrother.pned.gui.actions.RemoveSelectedNodesAction;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.dialogs.FileDialogFactory;
import de.markusrother.pned.gui.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;

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
public class PnEditorMenuFactory
	implements
		NodeSelectionListener {

	/** Constant <code>label="Edit"</code> */
	private static final String editMenulabel = "Edit";
	/** Constant <code>mnemonic=KeyEvent.VK_E</code> */
	private static final int editMenuMnemonic = KeyEvent.VK_E;
	/** Constant <code>defaultNodeLocation</code> at x=100, y=100. */
	private static final Point defaultNodeLocation = new Point(100, 100);

	private boolean areNodesSelected;
	private final NodeCreationMode nodeCreationMode;
	private GuiEventBus eventBus;
	private final FileDialogFactory fileDialogFactory;

	/**
	 * <p>
	 * Constructor for PnEditorMenuFactory.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	public PnEditorMenuFactory(final GuiEventBus eventBus) {
		this.eventBus = eventBus;
		this.areNodesSelected = false;
		this.nodeCreationMode = NodeCreationMode.defaultCreationMode;
		this.fileDialogFactory = new FileDialogFactory(eventBus);

		eventBus.addListener(NodeSelectionListener.class, this);
	}

	/**
	 * <p>
	 * newEditMenu.
	 * </p>
	 *
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
		final JMenu jMenu = new JMenu(editMenulabel);
		jMenu.setMnemonic(editMenuMnemonic);
		populateEditMenu(jMenu, DefaultNodeLocationProvider.INSTANCE);
		return jMenu;
	}

	private void populateEditMenu(final JComponent jMenu, final LocationProvider locationProvider) {
		final Object eventSource = jMenu;
		final ButtonGroup buttonGroup = new ButtonGroup();

		final JRadioButtonMenuItem placeItem = CreatePlaceAction.newMenuItem( //
				eventBus, //
				eventSource, //
				locationProvider);
		buttonGroup.add(placeItem);
		jMenu.add(placeItem);
		placeItem.setSelected(nodeCreationMode == PLACE);

		final JRadioButtonMenuItem transitionItem = CreateTransitionAction.newMenuItem( //
				eventBus, //
				eventSource, //
				locationProvider);
		buttonGroup.add(transitionItem);
		jMenu.add(transitionItem);
		transitionItem.setSelected(nodeCreationMode == TRANSITION);

		jMenu.add(RemoveSelectedNodesAction.newMenuItem(eventBus, eventSource, areNodesSelected));
	}

	/**
	 * <p>
	 * newFileMenu.
	 * </p>
	 *
	 * @return a {@link javax.swing.JMenu} object.
	 */
	public JMenu newFileMenu() {
		return new PnedFileMenu(eventBus, fileDialogFactory);
	}

	/**
	 * <p>
	 * newPreferencesMenu.
	 * </p>
	 *
	 * @return a {@link javax.swing.JMenu} object.
	 */
	public JMenu newPreferencesMenu() {
		return new PnedPreferencesMenu(eventBus);
	}

	/**
	 * <p>
	 * newPopupMenu.
	 * </p>
	 *
	 */
	public JPopupMenu newPopupMenu(final Point poin) {
		final JPopupMenu popup = new JPopupMenu();
		final LocationProvider locationProvider = new LocationProvider() {
			@Override
			public Point getLocation() {
				return poin;
			}
		};
		populateEditMenu(popup, locationProvider);
		return popup;
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeMultiSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeMultiSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeMultiSelectionEvent event) {
		areNodesSelected = !event.getNodes().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeMultiSelectionEvent event) {
		areNodesSelected = false;
	}

	/**
	 * <p>
	 * Setter for the field <code>eventMulticaster</code>.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.control.GuiEventBus} object.
	 */
	public void setEventBus(final GuiEventBus eventBus) {
		if (this.eventBus != null) {
			this.eventBus.removeListener(NodeSelectionListener.class, this);
		}
		this.eventBus = eventBus;
		// FIXME - dispose, remove upon close!
		this.eventBus.addListener(NodeSelectionListener.class, this);
		this.fileDialogFactory.setEventBus(eventBus);
	}

}
