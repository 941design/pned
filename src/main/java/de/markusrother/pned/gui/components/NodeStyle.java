package de.markusrother.pned.gui.components;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class NodeStyle {

	public static final NodeStyle DEFAULT;
	static {
		DEFAULT = new NodeStyle();
		DEFAULT.setDefaultColor(new Color(120, 120, 120, 120));
		DEFAULT.setHoverColor(new Color(160, 220, 180, 120));
		DEFAULT.setSelectionColor(new Color(160, 160, 160, 120));
		DEFAULT.setDefaultBorder(null);
		DEFAULT.setSelectionBorder(new LineBorder(Color.MAGENTA));
		DEFAULT.setHoverBorder(new LineBorder(Color.GREEN));
	}

	private Color defaultColor;
	private Color selectionColor;
	private Color hoverColor;
	private Border defaultBorder;
	private Border selectionBorder;
	private Border hoverBorder;

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(final Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public Color getSelectionColor() {
		return selectionColor;
	}

	public void setSelectionColor(final Color selectionColor) {
		this.selectionColor = selectionColor;
	}

	public Color getHoverColor() {
		return hoverColor;
	}

	public void setHoverColor(final Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	public Border getDefaultBorder() {
		return defaultBorder;
	}

	public void setDefaultBorder(final Border defaultBorder) {
		this.defaultBorder = defaultBorder;
	}

	public Border getSelectionBorder() {
		return selectionBorder;
	}

	public void setSelectionBorder(final Border selectionBorder) {
		this.selectionBorder = selectionBorder;
	}

	public Border getHoverBorder() {
		return hoverBorder;
	}

	public void setHoverBorder(final Border hoverBorder) {
		this.hoverBorder = hoverBorder;
	}

}
