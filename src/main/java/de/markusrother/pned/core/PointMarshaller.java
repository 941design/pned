package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PointMarshaller extends XmlAdapter<GraphicsVO, Point> {

	@Override
	public Point unmarshal(final GraphicsVO v) throws Exception {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public GraphicsVO marshal(final Point point) throws Exception {
		return new GraphicsVO(point);
	}
}
