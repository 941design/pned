package de.markusrother.pned.gui.components;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.event.ChangeListener;

public interface NodeStyleModel {

	void addChangeListener(ChangeListener listener);

	void removeChangeListener(ChangeListener listener);

	int getSize();

	void setSize(int size);

	Color getDefaultColor();

	Color getSelectionColor();

	Color getHoverColor();

	Border getDefaultBorder();

	Border getSelectionBorder();

	Border getHoverBorder();

}
