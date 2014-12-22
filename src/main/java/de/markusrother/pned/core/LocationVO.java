package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAttribute;

public class LocationVO {

	private final int x;
	private final int y;

	private @SuppressWarnings("unused") LocationVO() {
		// IGNORE - Only needed by XmlMarshaller!
		this.x = -1;
		this.y = -1;
	}

	public LocationVO(final Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	@XmlAttribute
	public int getX() {
		return x;
	}

	@XmlAttribute
	public int getY() {
		return y;
	}

}
