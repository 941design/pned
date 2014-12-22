package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "place")
public class PlaceImpl extends NodeImpl
	implements
		PlaceModel {

	private int marking;

	private @SuppressWarnings("unused") PlaceImpl() {
		// IGNORE - Only needed by XmlMarshaller!
	}

	public PlaceImpl(final String nodeId, final Point point) {
		super(nodeId, point);
	}

	@Override
	@XmlElement(name = "initialMarking")
	public int getMarking() {
		return marking;
	}

	@Override
	public void setMarking(final int marking) {
		this.marking = marking;
	}

	@Override
	protected void writeAttributesAsString(final JsonBuilder jb) {
		super.writeAttributesAsString(jb);
		jb.append("marking", marking);
	}

}
