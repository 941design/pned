package de.markusrother.pned.io;

import java.awt.Point;

public abstract class AbstractGraphicsVO {

	protected final LocationVO location;

	protected @SuppressWarnings("unused") AbstractGraphicsVO() {
		// IGNORE - Only needed by XmlMarshaller!
		this.location = null;
	}

	public AbstractGraphicsVO(final Point point) {
		this.location = new LocationVO(point);
	}

	abstract LocationVO getLocation();
}
