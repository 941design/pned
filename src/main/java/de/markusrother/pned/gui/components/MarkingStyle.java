package de.markusrother.pned.gui.components;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class MarkingStyle {

	public static final MarkingStyle DEFAULT;
	static {
		DEFAULT = new MarkingStyle();
		DEFAULT.setColor(Color.BLACK);
		DEFAULT.setShape(new Ellipse2D.Double(0, 0, 10, 10));
	}

	private Shape shape;
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public void setShape(final Shape shape) {
		this.shape = shape;
	}

	public Shape getShape() {
		return shape;
	}

}
