package de.markusrother.pned.gui.events;

import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;

import de.markusrother.pned.gui.components.AbstractNode;

public class NodeSelectionEvent extends EventObject {

	public enum Type {
		// TODO - START,
		SELECT,
		DESELECT,
		FINISH,
		CANCEL;
	}

	private static final Collection<AbstractNode> NO_NODES = Collections.emptyList();

	private final Collection<AbstractNode> nodes;
	private final Type type;

	public NodeSelectionEvent(final Type type, final Object source) {
		this(type, source, NO_NODES);
	}

	public NodeSelectionEvent(final Type type, final Object source, final Collection<AbstractNode> nodes) {
		super(source);
		this.type = type;
		this.nodes = nodes;
	}

	public Type getType() {
		return type;
	}

	public Collection<AbstractNode> getNodes() {
		return nodes;
	}

}
