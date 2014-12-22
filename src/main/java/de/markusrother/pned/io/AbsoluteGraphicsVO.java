package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;

public class AbsoluteGraphicsVO extends AbstractGraphicsVO {

	private @SuppressWarnings("unused") AbsoluteGraphicsVO() {
		// IGNORE - Only needed by XmlMarshaller!
	}

	public AbsoluteGraphicsVO(final Point point) {
		super(point);
	}

	@Override
	@XmlElement(name = "position")
	public LocationVO getLocation() {
		return location;
	}

}
