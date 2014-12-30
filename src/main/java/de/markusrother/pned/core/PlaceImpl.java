package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.markusrother.util.JsonBuilder;

/**
 * <p>PlaceImpl class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "place")
public class PlaceImpl extends NodeImpl
	implements
		PlaceModel {

	private int marking;

	/**
	 * <p>Constructor for PlaceImpl.</p>
	 */
	private @SuppressWarnings("unused") PlaceImpl() {
		// IGNORE - Only needed by XmlMarshaller!
	}

	/**
	 * <p>Constructor for PlaceImpl.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 * @param point a {@link java.awt.Point} object.
	 */
	public PlaceImpl(final String nodeId, final Point point) {
		super(nodeId, point);
	}

	/** {@inheritDoc} */
	@Override
	@XmlElement(name = "initialMarking")
	public int getMarking() {
		return marking;
	}

	/** {@inheritDoc} */
	@Override
	public void setMarking(final int marking) {
		this.marking = marking;
	}

	/** {@inheritDoc} */
	@Override
	protected void writeAttributesAsString(final JsonBuilder jb) {
		super.writeAttributesAsString(jb);
		jb.append("marking", marking);
	}

}
