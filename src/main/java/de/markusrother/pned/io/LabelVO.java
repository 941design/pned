package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "label", "graphics" })
public class LabelVO {

	private final String label;
	private final RelativeGraphicsVO graphics;

	private @SuppressWarnings("unused") LabelVO() {
		// IGNORE - Only needed by XmlMarshaller!
		this.label = null;
		this.graphics = null;
	}

	public LabelVO(final String label) {
		this.label = label;
		this.graphics = new RelativeGraphicsVO(new Point()); // TODO
	}

	@XmlElement(name = "text")
	public String getLabel() {
		return label;
	}

	@XmlElement(name = "graphics")
	public RelativeGraphicsVO getGraphics() {
		return graphics;
	}
}
