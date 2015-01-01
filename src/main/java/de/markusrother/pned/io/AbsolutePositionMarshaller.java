package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p>
 * A class transforming the
 * {@link de.markusrother.pned.core.AbstractDefaultNode#getPosition} to an
 * {@link de.markusrother.pned.io.AbsoluteGraphicsVO} during conversion to pnml (xml).
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see PetriNetMarshaller
 */
public class AbsolutePositionMarshaller extends XmlAdapter<AbsoluteGraphicsVO, Point> {

	/** {@inheritDoc} */
	@Override
	public Point unmarshal(final AbsoluteGraphicsVO v) throws Exception {
		throw new UnsupportedOperationException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public AbsoluteGraphicsVO marshal(final Point point) {
		return new AbsoluteGraphicsVO(point);
	}
}
