package de.markusrother.pned.gui.model;

import java.awt.Color;

import de.markusrother.swing.ChangeEventSource;

public interface ColorModel
	extends
		ChangeEventSource {

	Color getColor();

	void setColor(Color color);

}
