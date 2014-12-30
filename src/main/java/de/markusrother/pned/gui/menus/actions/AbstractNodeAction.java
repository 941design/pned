package de.markusrother.pned.gui.menus.actions;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.events.IdRequest;
import de.markusrother.pned.gui.listeners.NodeListener;

/**
 * TODO - resize menu on toggle or create with enough space to the right!
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractNodeAction extends AbstractAction
	implements
		NodeListener,
		ItemListener {

	protected Object source;
	protected final LocationProvider locationProvider;
	protected final EventBus eventBus;

	/**
	 * <p>Constructor for AbstractNodeAction.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param locationProvider a {@link de.markusrother.pned.gui.menus.actions.LocationProvider} object.
	 * @param mnemonic a int.
	 * @param name a {@link java.lang.String} object.
	 */
	public AbstractNodeAction(final EventBus eventBus, final Object source, final LocationProvider locationProvider,
			final int mnemonic, final String name) {
		super(name);
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
		final boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
		setSelected(isSelected);
		if (isSelected) {
			selected();
		}
	}

	/**
	 * <p>selected.</p>
	 */
	protected abstract void selected();

	/**
	 * <p>setSelected.</p>
	 *
	 * @param enabled a boolean.
	 */
	public abstract void setSelected(final boolean enabled);

	/**
	 * <p>requestNodeId.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	protected String requestNodeId() {
		final IdRequest req = new IdRequest(this);
		eventBus.requestId(req);
		return req.get();
	}

}
