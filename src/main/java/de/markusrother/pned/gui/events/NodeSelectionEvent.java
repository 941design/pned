package de.markusrother.pned.gui.events;

import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;

import de.markusrother.pned.gui.components.AbstractNode;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * NodeSelectionEvent class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeSelectionEvent extends EventObject
	implements
		JsonSerializable {

	public enum Type {
		// TODO - START,
		SELECT,
		DESELECT,
		FINISH,
		CANCEL;
	}

	/** Constant <code>NO_NODES</code> */
	private static final Collection<AbstractNode> NO_NODES = Collections.emptyList();

	private final Collection<AbstractNode> nodes;
	private final Type type;

	/**
	 * <p>
	 * Constructor for NodeSelectionEvent.
	 * </p>
	 *
	 * @param type
	 *            a
	 *            {@link de.markusrother.pned.gui.events.NodeSelectionEvent.Type}
	 *            object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 */
	public NodeSelectionEvent(final Type type, final Object source) {
		this(type, source, NO_NODES);
	}

	/**
	 * <p>
	 * Constructor for NodeSelectionEvent.
	 * </p>
	 *
	 * @param type
	 *            a
	 *            {@link de.markusrother.pned.gui.events.NodeSelectionEvent.Type}
	 *            object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodes
	 *            a {@link java.util.Collection} object.
	 */
	public NodeSelectionEvent(final Type type, final Object source, final Collection<AbstractNode> nodes) {
		super(source);
		this.type = type;
		this.nodes = nodes;
	}

	/**
	 * <p>
	 * Getter for the field <code>type</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.events.NodeSelectionEvent.Type}
	 *         object.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * <p>
	 * Getter for the field <code>nodes</code>.
	 * </p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public Collection<AbstractNode> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		return toJson();
	}

	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("type", type.name()) //
				.appendList("nodes", nodes.toArray(new AbstractNode[nodes.size()])) //
				.toString();
	}

}
