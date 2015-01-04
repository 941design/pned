package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.JMenuItem;

import de.markusrother.pned.gui.control.GuiEventBus;

public class RemoveIncomingEdgesAction extends AbstractGuiAction {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove incoming edges";

	private final Set<String> edgeIds;

	public RemoveIncomingEdgesAction(final GuiEventBus eventBus, final Object source, final Set<String> edgeIds) {
		super(label, source, eventBus);

		this.edgeIds = edgeIds;
		setEnabled(!edgeIds.isEmpty());

		installListeners();
	}

	private void installListeners() {
		// eventBus.addListener(EdgeCreationListener.class, this);
	}

	private void removeListeners() {
		// TODO
	}

	public static JMenuItem newMenuItem(final GuiEventBus eventBus, final Object source, final Set<String> edgeIds) {
		return new JMenuItem(new RemoveIncomingEdgesAction(eventBus, source, edgeIds));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO
		throw new RuntimeException("TODO");
	}

}
