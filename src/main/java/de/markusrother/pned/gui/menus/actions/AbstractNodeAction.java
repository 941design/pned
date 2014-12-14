package de.markusrother.pned.gui.menus.actions;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.markusrother.pned.gui.events.NodeCreationEvent;
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

	public AbstractNodeAction(final Object source, final LocationProvider locationProvider, final int mnemonic,
			final String name) {
		super(name);
		this.source = source;
		this.locationProvider = locationProvider;
		putValue(Action.MNEMONIC_KEY, mnemonic);
		// FIXME - dispose!
		eventBus.addListener(NodeListener.class, this);
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// IGNORE
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

}
