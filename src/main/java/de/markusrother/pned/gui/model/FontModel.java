package de.markusrother.pned.gui.model;

import java.awt.Font;

import de.markusrother.swing.ChangeEventSource;

public interface FontModel
	extends
		ChangeEventSource {

	void setFontStyle(int fontStyle);

	int getFontStyle();

	void setFontName(String fontName);

	String getFontName();

	Font getFont();

}
