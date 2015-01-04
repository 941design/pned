package de.markusrother.pned.gui.actions;

import javax.swing.AbstractAction;

import de.markusrother.pned.gui.control.GuiEventBus;

public abstract class AbstractGuiAction extends AbstractAction {

	protected final Object source;
	protected final GuiEventBus eventBus;

	public AbstractGuiAction(final String label, final Object source, final GuiEventBus eventBus) {
		super(label);
		this.source = source;
		this.eventBus = eventBus;
	}

}
