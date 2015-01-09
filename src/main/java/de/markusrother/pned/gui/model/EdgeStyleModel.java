package de.markusrother.pned.gui.model;

import java.awt.Color;
import java.awt.Stroke;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Aggregate interface
 * </p>
 */
public interface EdgeStyleModel
	extends
		ChangeEventSource,
		ShapeModel,
		SizeModel {

	Color getDefaultColor();

	void setDefaultColor(Color black);

	Color getHoverColor();

	void setHoverColor(Color blue);

	Color getValidColor();

	void setValidColor(Color green);

	Color getInvalidColor();

	void setInvalidColor(Color red);

	Stroke getStroke();

	void setStroke(Stroke stroke);

}
