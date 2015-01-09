package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.markusrother.pned.core.model.NodeModel;
import de.markusrother.pned.io.AbsolutePositionMarshaller;
import de.markusrother.pned.io.LabelMarshaller;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Abstract default implementation of
 * {@link de.markusrother.pned.core.model.NodeModel}.
 * </p>
 * 
 * <h4>TODO</h4>
 * <ul>
 * <li>Move xml related annotations to an .xml configuration file to decouple
 * this class from the de.markusrother.core.io package</li>
 * </ul>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractDefaultNode
	implements
		NodeModel,
		JsonSerializable {

	private final String id;
	private String label;
	private Point position;

	/**
	 * <p>
	 * Default constructor needed by XmlMarshaller!
	 * </p>
	 */
	protected AbstractDefaultNode() {
		// IGNORE
		this.id = null;
	}

	/**
	 * <p>
	 * Constructor for NodeImpl.
	 * </p>
	 *
	 * @param nodeId
	 *            a {@link java.lang.String} - this node's unique id.
	 * @param point
	 *            a {@link java.awt.Point} - this node's coordinates.
	 */
	protected AbstractDefaultNode(final String nodeId, final Point point) {
		this.id = nodeId;
		this.position = point;
	}

	/** {@inheritDoc} */
	@Override
	@XmlAttribute
	public String getId() {
		return id;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasId(final String nodeId) {
		return this.id != null && this.id.equals(nodeId);
	}

	/** {@inheritDoc} */
	@Override
	@XmlElement(name = "name")
	@XmlJavaTypeAdapter(LabelMarshaller.class)
	public String getLabel() {
		return label;
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	/** {@inheritDoc} */
	@Override
	@XmlElement(name = "graphics")
	@XmlJavaTypeAdapter(AbsolutePositionMarshaller.class)
	public Point getPosition() {
		return position.getLocation();
	}

	/** {@inheritDoc} */
	@Override
	public void setPosition(final Point point) {
		this.position = point;
	}

	/** {@inheritDoc} */
	@Override
	public void move(final int deltaX, final int deltaY) {
		position.translate(deltaX, deltaY);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder jb = new JsonBuilder();
		writeAttributesAsString(jb);
		return jb.toString();
	}

	/**
	 * <p>
	 * Writes attributes to given json builder.
	 * </p>
	 *
	 * @param jb
	 *            a {@link de.markusrother.util.JsonBuilder} to be written to.
	 */
	protected void writeAttributesAsString(final JsonBuilder jb) {
		jb.append("id", id) //
				.append("label", label) //
				.append("x", position.x) //
				.append("y", position.y);
	}

}
