package de.markusrother.pned.gui.components;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * <p>NodeStyle class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeStyle {

	/** Constant <code>DEFAULT</code> */
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

	/**
	 * <p>Getter for the field <code>defaultColor</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getDefaultColor() {
		return defaultColor;
	}

	/**
	 * <p>Setter for the field <code>defaultColor</code>.</p>
	 *
	 * @param defaultColor a {@link java.awt.Color} object.
	 */
	public void setDefaultColor(final Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	/**
	 * <p>Getter for the field <code>selectionColor</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getSelectionColor() {
		return selectionColor;
	}

	/**
	 * <p>Setter for the field <code>selectionColor</code>.</p>
	 *
	 * @param selectionColor a {@link java.awt.Color} object.
	 */
	public void setSelectionColor(final Color selectionColor) {
		this.selectionColor = selectionColor;
	}

	/**
	 * <p>Getter for the field <code>hoverColor</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getHoverColor() {
		return hoverColor;
	}

	/**
	 * <p>Setter for the field <code>hoverColor</code>.</p>
	 *
	 * @param hoverColor a {@link java.awt.Color} object.
	 */
	public void setHoverColor(final Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	/**
	 * <p>Getter for the field <code>defaultBorder</code>.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getDefaultBorder() {
		return defaultBorder;
	}

	/**
	 * <p>Setter for the field <code>defaultBorder</code>.</p>
	 *
	 * @param defaultBorder a {@link javax.swing.border.Border} object.
	 */
	public void setDefaultBorder(final Border defaultBorder) {
		this.defaultBorder = defaultBorder;
	}

	/**
	 * <p>Getter for the field <code>selectionBorder</code>.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getSelectionBorder() {
		return selectionBorder;
	}

	/**
	 * <p>Setter for the field <code>selectionBorder</code>.</p>
	 *
	 * @param selectionBorder a {@link javax.swing.border.Border} object.
	 */
	public void setSelectionBorder(final Border selectionBorder) {
		this.selectionBorder = selectionBorder;
	}

	/**
	 * <p>Getter for the field <code>hoverBorder</code>.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getHoverBorder() {
		return hoverBorder;
	}

	/**
	 * <p>Setter for the field <code>hoverBorder</code>.</p>
	 *
	 * @param hoverBorder a {@link javax.swing.border.Border} object.
	 */
	public void setHoverBorder(final Border hoverBorder) {
		this.hoverBorder = hoverBorder;
	}

}
