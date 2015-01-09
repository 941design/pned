package de.markusrother.pned.gui.model;

import java.awt.Color;

import javax.swing.border.Border;

import de.markusrother.pned.gui.components.NodeLabel;

/**
 * <p>
 * NodeLabelStyle class.
 * </p>
 *
 * FIXME OBSOLETE by NodeStyleModel merge apply into label
 *
 * @author Markus Rother
 * @version 1.0
 */
@Deprecated
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

	/**
	 * <p>
	 * Getter for the field <code>defaultFg</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getDefaultFg() {
		return defaultFg;
	}

	/**
	 * <p>
	 * Setter for the field <code>defaultFg</code>.
	 * </p>
	 *
	 * @param defaultFg
	 *            a {@link java.awt.Color} object.
	 */
	public void setDefaultFg(final Color defaultFg) {
		this.defaultFg = defaultFg;
	}

	/**
	 * <p>
	 * Getter for the field <code>defaultBg</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getDefaultBg() {
		return defaultBg;
	}

	/**
	 * <p>
	 * Setter for the field <code>defaultBg</code>.
	 * </p>
	 *
	 * @param defaultBg
	 *            a {@link java.awt.Color} object.
	 */
	public void setDefaultBg(final Color defaultBg) {
		this.defaultBg = defaultBg;
	}

	/**
	 * <p>
	 * Getter for the field <code>defaultBorder</code>.
	 * </p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getDefaultBorder() {
		return defaultBorder;
	}

	/**
	 * <p>
	 * Setter for the field <code>defaultBorder</code>.
	 * </p>
	 *
	 * @param defaultBorder
	 *            a {@link javax.swing.border.Border} object.
	 */
	public void setDefaultBorder(final Border defaultBorder) {
		this.defaultBorder = defaultBorder;
	}

	/**
	 * <p>
	 * isDefaultOpacity.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean isDefaultOpacity() {
		return defaultOpacity;
	}

	/**
	 * <p>
	 * Setter for the field <code>defaultOpacity</code>.
	 * </p>
	 *
	 * @param defaultOpacity
	 *            a boolean.
	 */
	public void setDefaultOpacity(final boolean defaultOpacity) {
		this.defaultOpacity = defaultOpacity;
	}

	/**
	 * <p>
	 * Getter for the field <code>hoverFg</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getHoverFg() {
		return hoverFg;
	}

	/**
	 * <p>
	 * Setter for the field <code>hoverFg</code>.
	 * </p>
	 *
	 * @param hoverFg
	 *            a {@link java.awt.Color} object.
	 */
	public void setHoverFg(final Color hoverFg) {
		this.hoverFg = hoverFg;
	}

	/**
	 * <p>
	 * Getter for the field <code>hoverBg</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getHoverBg() {
		return hoverBg;
	}

	/**
	 * <p>
	 * Setter for the field <code>hoverBg</code>.
	 * </p>
	 *
	 * @param hoverBg
	 *            a {@link java.awt.Color} object.
	 */
	public void setHoverBg(final Color hoverBg) {
		this.hoverBg = hoverBg;
	}

	/**
	 * <p>
	 * Getter for the field <code>hoverBorder</code>.
	 * </p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public Border getHoverBorder() {
		return hoverBorder;
	}

	/**
	 * <p>
	 * Setter for the field <code>hoverBorder</code>.
	 * </p>
	 *
	 * @param hoverBorder
	 *            a {@link javax.swing.border.Border} object.
	 */
	public void setHoverBorder(final Border hoverBorder) {
		this.hoverBorder = hoverBorder;
	}

	/**
	 * <p>
	 * isHoverOpacity.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean isHoverOpacity() {
		return hoverOpacity;
	}

	/**
	 * <p>
	 * Setter for the field <code>hoverOpacity</code>.
	 * </p>
	 *
	 * @param hoverOpacity
	 *            a boolean.
	 */
	public void setHoverOpacity(final boolean hoverOpacity) {
		this.hoverOpacity = hoverOpacity;
	}

	/** {@inheritDoc} */
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
		case INVALID:
		case MULTI_SELECTED:
		case SINGLE_SELECTED:
		case VALID:
		default:
			nodeLabel.setForeground(defaultFg);
			nodeLabel.setBackground(defaultBg);
			nodeLabel.setOpaque(defaultOpacity);
			nodeLabel.setBorder(defaultBorder);
			break;
		}
	}

}
