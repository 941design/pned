package de.markusrother.pned.gui.components;

import java.awt.Color;

public class NodeStyle {

	private static final NodeStyle defaultNodeStyle;
	static {
		defaultNodeStyle = new NodeStyle();
		defaultNodeStyle.setDefaultColor(new Color(120, 120, 120, 120));
		defaultNodeStyle.setHoverColor(new Color(160, 220, 180, 120));
		defaultNodeStyle.setSelectionColor(new Color(160, 160, 160, 120));
	}

	public static NodeStyle getDefaultNodeStyle() {
		return defaultNodeStyle;
	}

	private Color defaultColor;
	private Color selectionColor;
	private Color hoverColor;

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
}
