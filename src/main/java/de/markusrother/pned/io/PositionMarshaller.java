package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PositionMarshaller extends XmlAdapter<AbsoluteGraphicsVO, Point> {

	@Override
	public Point unmarshal(final AbsoluteGraphicsVO v) throws Exception {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public AbsoluteGraphicsVO marshal(final Point point) {
		return new AbsoluteGraphicsVO(point);
	}
}
