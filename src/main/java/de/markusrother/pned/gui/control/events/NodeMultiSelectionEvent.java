package de.markusrother.pned.gui.control.events;

import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;

import de.markusrother.pned.gui.components.AbstractNodeComponent;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * NodeSelectionEvent class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.events.NodeSelectionListener
 */
public class NodeMultiSelectionEvent extends EventObject
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
	private static final Collection<AbstractNodeComponent> NO_NODES = Collections.emptyList();

	private final Collection<AbstractNodeComponent> nodes;
	private final Type type;

	/**
	 * <p>
	 * Constructor for NodeSelectionEvent.
	 * </p>
	 *
	 * @param type
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type}
	 *            object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 */
	public NodeMultiSelectionEvent(final Type type, final Object source) {
		this(type, source, NO_NODES);
	}

	/**
	 * <p>
	 * Constructor for NodeSelectionEvent.
	 * </p>
	 *
	 * @param type
	 *            a
	 *            {@link de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type}
	 *            object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodes
	 *            a {@link java.util.Collection} object.
	 */
	public NodeMultiSelectionEvent(final Type type, final Object source, final Collection<AbstractNodeComponent> nodes) {
		super(source);
		this.type = type;
		this.nodes = nodes;
	}

	/**
	 * <p>
	 * Getter for the field <code>type</code>.
	 * </p>
	 *
	 * @return a
	 *         {@link de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent.Type}
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
	public Collection<AbstractNodeComponent> getNodes() {
		return nodes;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ':' + toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("type", type.name()) //
				.appendList("nodes", nodes.toArray(new AbstractNodeComponent[nodes.size()])) //
				.toString();
	}

}
