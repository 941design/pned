package de.markusrother.pned.gui.model;

import java.awt.Shape;

import de.markusrother.swing.ChangeEventSource;

public interface ShapeModel
	extends
		ChangeEventSource {

	Shape getShape();

	void setShape(Shape shape);

}
