package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public abstract class NodeImpl
	implements
		NodeModel {

	private final String id;
	private String label;
	private Point point;

	protected NodeImpl() {
		// IGNORE - Only needed by XmlMarshaller!
		this.id = null;
	}

	protected NodeImpl(final String nodeId, final Point point) {
		this.id = nodeId;
		this.point = point;
	}

	@Override
	@XmlAttribute
	public String getId() {
		return id;
	}

	@Override
	public boolean hasId(final String nodeId) {
		return this.id != null && this.id.equals(nodeId);
	}

	@Override
	@XmlTransient
	// TODO - use LabelMarshaller
	public String getLabel() {
		// TODO
		throw new RuntimeException("TODO");
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	@Override
	@XmlElement(name = "graphics")
	@XmlJavaTypeAdapter(PointMarshaller.class)
	public Point getOrigin() {
		return point.getLocation();
	}

	@Override
	public void setOrigin(final Point point) {
		this.point = point;
	}

	@Override
	public void move(final int deltaX, final int deltaY) {
		point.translate(deltaX, deltaY);
	}

	@Override
	public String toString() {
		return toJson();
	}

	@Override
	public String toJson() {
		final JsonBuilder jb = new JsonBuilder();
		writeAttributesAsString(jb);
		return jb.toString();
	}

	protected void writeAttributesAsString(final JsonBuilder jb) {
		jb.append("id", id) //
				.append("label", label) //
				.append("x", point.x) //
				.append("y", point.y);
	}
}
