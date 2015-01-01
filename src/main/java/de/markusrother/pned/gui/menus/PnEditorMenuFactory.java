package de.markusrother.pned.gui.menus;

import javax.swing.JMenu;

import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.dialogs.FileDialogFactory;
import de.markusrother.pned.gui.events.GuiEventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
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
	}

	/**
	 * <p>
	 * newEditMenu.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.menus.PnedEditMenu} object.
	 */
	public PnedEditMenu newEditMenu() {
		return new PnedEditMenu(eventBus, areNodesSelected, nodeCreationMode);
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
	 * @return a {@link de.markusrother.pned.gui.menus.PnedEditMenu} object.
	 */
	public PnedEditMenu newPopupMenu() {
		// TODO
		throw new RuntimeException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent event) {
		areNodesSelected = !event.getNodes().isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent event) {
		areNodesSelected = false;
	}

	/**
	 * <p>
	 * Setter for the field <code>eventMulticaster</code>.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
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
