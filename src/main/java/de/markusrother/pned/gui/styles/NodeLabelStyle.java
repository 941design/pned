package de.markusrother.pned.gui.styles;

import java.awt.Color;

import javax.swing.border.Border;

import de.markusrother.pned.gui.components.NodeLabel;

public class NodeLabelStyle
	implements
		Style<NodeLabel> {

	private Color defaultFg;
	private Color defaultBg;
	private Border defaultBorder;
	private boolean defaultOpacity;

	private Color hoverFg;
	private Color hoverBg;
	private Border hoverBorder;
	private boolean hoverOpacity;

	public Color getDefaultFg() {
		return defaultFg;
	}

	public void setDefaultFg(final Color defaultFg) {
		this.defaultFg = defaultFg;
	}

	public Color getDefaultBg() {
		return defaultBg;
	}

	public void setDefaultBg(final Color defaultBg) {
		this.defaultBg = defaultBg;
	}

	public Border getDefaultBorder() {
		return defaultBorder;
	}

	public void setDefaultBorder(final Border defaultBorder) {
		this.defaultBorder = defaultBorder;
	}

	public boolean isDefaultOpacity() {
		return defaultOpacity;
	}

	public void setDefaultOpacity(final boolean defaultOpacity) {
		this.defaultOpacity = defaultOpacity;
	}

	public Color getHoverFg() {
		return hoverFg;
	}

	public void setHoverFg(final Color hoverFg) {
		this.hoverFg = hoverFg;
	}

	public Color getHoverBg() {
		return hoverBg;
	}

	public void setHoverBg(final Color hoverBg) {
		this.hoverBg = hoverBg;
	}

	public Border getHoverBorder() {
		return hoverBorder;
	}

	public void setHoverBorder(final Border hoverBorder) {
		this.hoverBorder = hoverBorder;
	}

	public boolean isHoverOpacity() {
		return hoverOpacity;
	}

	public void setHoverOpacity(final boolean hoverOpacity) {
		this.hoverOpacity = hoverOpacity;
	}

	@Override
	public void apply(final NodeLabel nodeLabel) {
		// TODO - There must be a nicer mapping of styles to attributes!
		// nodeLabel.setForeground(state);
		switch (nodeLabel.getState()) {
		case HOVER:
			nodeLabel.setForeground(hoverFg);
			nodeLabel.setBackground(hoverBg);
			nodeLabel.setOpaque(hoverOpacity);
			nodeLabel.setBorder(hoverBorder);
			break;
		case DEFAULT:
		default:
			nodeLabel.setForeground(defaultFg);
			nodeLabel.setBackground(defaultBg);
			nodeLabel.setOpaque(defaultOpacity);
			nodeLabel.setBorder(defaultBorder);
			break;
		}
	}

}
