package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p>PositionMarshaller class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PositionMarshaller extends XmlAdapter<AbsoluteGraphicsVO, Point> {

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
