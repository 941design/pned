package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;

import de.markusrother.pned.core.commands.EdgeCreationCommand;
import de.markusrother.pned.core.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.control.GuiEventBus;

public class RemoveIncomingEdgesAction extends AbstractGuiAction
	implements
		EdgeCreationListener {

	/** Constant <code>label="Remove selected nodes"</code> */
	private static final String label = "Remove incoming edges";

	private final Map<String, String> incomingEdgesMap;

	public RemoveIncomingEdgesAction(final GuiEventBus eventBus, final Object source,
			final Map<String, String> incomingEdgesMap) {
		super(label, source, eventBus);

		this.incomingEdgesMap = new HashMap<>(incomingEdgesMap);

		setEnabled(!incomingEdgesMap.isEmpty());

		// FIXME - dispose!
		eventBus.addListener(EdgeCreationListener.class, this);
	}

	public static JMenuItem newMenuItem(final GuiEventBus eventBus, final Object source,
			final Map<String, String> incomingEdgesMap) {
		return new JMenuItem(new RemoveIncomingEdgesAction(eventBus, source, incomingEdgesMap));
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		final String targetId = cmd.getTargetId();
		incomingEdgesMap.put(edgeId, targetId);
	}

}
