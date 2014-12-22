package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;

public class RelativeGraphicsVO extends AbstractGraphicsVO {

	private @SuppressWarnings("unused") RelativeGraphicsVO() {
		// IGNORE - Only needed by XmlMarshaller!
	}

	public RelativeGraphicsVO(final Point point) {
		super(point);
	}

	@Override
	@XmlElement(name = "offset")
	public LocationVO getLocation() {
		return location;
	}

}
