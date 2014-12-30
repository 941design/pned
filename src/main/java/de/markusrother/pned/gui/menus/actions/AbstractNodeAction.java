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
 */
public abstract class AbstractNodeAction extends AbstractAction
	implements
		NodeListener,
		ItemListener {

	protected Object source;
	protected final LocationProvider locationProvider;
	protected final EventBus eventBus;

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

	@Override
	public void itemStateChanged(final ItemEvent e) {
		final boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
		setSelected(isSelected);
		if (isSelected) {
			selected();
		}
	}

	protected abstract void selected();

	public abstract void setSelected(final boolean enabled);

	protected String requestNodeId() {
		final IdRequest req = new IdRequest(this);
		eventBus.requestId(req);
		return req.get();
	}

}
