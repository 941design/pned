package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.events.IdRequest;
import de.markusrother.pned.gui.listeners.NodeListener;

/**
 * <p>
 * Abstract superclass for compound node actions: default node type selection
 * <b>and</b> node creation. This action combines an
 * {@link java.awt.event.ActionListener} with an {@link ItemListener} for
 * selectable items. It can be used for e.g.
 * {@link javax.swing.JRadioButtonMenuItem}s where toggle and selection trigger
 * separate {@link java.awt.event.ActionEvent}s.
 * </p>
 * <p>
 * Setting the default node type posts a
 * {@link de.markusrother.pned.gui.events.SetNodeTypeCommand} on the provided
 * {@link EventBus}. This is done without performing other actions. The actual
 * {@link java.awt.event.ActionListener} is implemented by subclasses.
 * </p>
 * 
 * <p>
 * TODO - Take generic argument EventTarget and IdRequestListener
 * </p>
 * <p>
 * TODO - Resize menu on toggle or create with enough space to the right!
 * </p>
 * <p>
 * TODO - Currently, this violates SRP!
 * </p>
 * <p>
 * TODO - Create generic class for this double feature.
 * </p>
 * 
 * @author Markus Rother
 * @version 1.0
 */
abstract class AbstractCreateNodeAction extends AbstractAction
	implements
		ItemListener,
		NodeListener {

	/**
	 * The posted {@link java.util.EventObject}s' source.
	 */
	protected final Object source;

	/**
	 * A provider of coordinates for newly created nodes.
	 */
	protected final LocationProvider locationProvider;

	/**
	 * The event target to which events are posted to, and from which new node
	 * ids are requested from.
	 */
	protected final EventBus eventBus;

	/**
	 * <p>
	 * Constructor for AbstractNodeAction.
	 * </p>
	 *
	 * @param eventBus
	 *            an {@link de.markusrother.pned.gui.EventBus} to be posted to.
	 * @param source
	 *            an {@link java.lang.Object} - the posted
	 *            {@link java.util.EventObject}s' source.
	 * @param locationProvider
	 *            a
	 *            {@link de.markusrother.pned.gui.menus.actions.LocationProvider}
	 *            to provide coordinates for newly created nodes.
	 * @param mnemonic
	 *            an int.
	 * @param label
	 *            a {@link java.lang.String} - this action's textual
	 *            representation.
	 */
	protected AbstractCreateNodeAction(final EventBus eventBus, final Object source,
			final LocationProvider locationProvider, final int mnemonic, final String label) {
		super(label);
		this.eventBus = eventBus;
		this.source = source;
		this.locationProvider = locationProvider;
		putValue(Action.MNEMONIC_KEY, mnemonic);
		// FIXME - dispose!
		eventBus.addListener(NodeListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void itemStateChanged(final ItemEvent e) {
		final int stateChange = e.getStateChange();
		if (stateChange == ItemEvent.SELECTED) {
			setSelected(true);
			fireSetNodeTypeCommand();
		} else if (stateChange == ItemEvent.DESELECTED) {
			setSelected(false);
		} else {
			// IGNORE - Should not happen.
		}
	}

	/**
	 * <p>
	 * Posts {@link de.markusrother.pned.gui.events.SetNodeTypeCommand} on
	 * {@link de.markusrother.pned.gui.EventBus}.
	 * </p>
	 */
	protected abstract void fireSetNodeTypeCommand();

	/**
	 * <p>
	 * Toggles selection state of this action. E.g. to adapt visualization.
	 * </p>
	 *
	 * @param selected
	 *            a boolean.
	 */
	protected abstract void setSelected(final boolean selected);

	/**
	 * <p>
	 * Posts {@link de.markusrother.pned.gui.events.IdRequest} on {@link de.markusrother.pned.gui.EventBus} and returns requested id.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	protected String requestNodeId() {
		final IdRequest req = new IdRequest(this);
		eventBus.requestId(req);
		return req.get();
	}

}
