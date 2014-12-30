package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.markusrother.pned.io.LabelMarshaller;
import de.markusrother.pned.io.PositionMarshaller;
import de.markusrother.util.JsonBuilder;

/**
 * <p>Abstract NodeImpl class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class NodeImpl
	implements
		NodeModel {

	private final String id;
	private String label;
	private Point position;

	/**
	 * <p>Constructor for NodeImpl.</p>
	 */
	protected NodeImpl() {
		// IGNORE - Only needed by XmlMarshaller!
		this.id = null;
	}

	/**
	 * <p>Constructor for NodeImpl.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 * @param point a {@link java.awt.Point} object.
	 */
	protected NodeImpl(final String nodeId, final Point point) {
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
	@XmlJavaTypeAdapter(PositionMarshaller.class)
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
	 * <p>writeAttributesAsString.</p>
	 *
	 * @param jb a {@link de.markusrother.util.JsonBuilder} object.
	 */
	protected void writeAttributesAsString(final JsonBuilder jb) {
		jb.append("id", id) //
				.append("label", label) //
				.append("x", position.x) //
				.append("y", position.y);
	}
}
