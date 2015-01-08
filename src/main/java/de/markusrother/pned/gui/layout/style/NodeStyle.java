package de.markusrother.pned.gui.layout.style;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import de.markusrother.pned.gui.components.NodeStyleModel;

/**
 * <p>
 * NodeStyle class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeStyle
	implements
		NodeStyleModel {

	/** Constant <code>DEFAULT</code> */
	public static final NodeStyle DEFAULT = newDefault();

	public static NodeStyle newDefault() {
		final NodeStyle style = new NodeStyle();
		style.setSize(40);
		style.setDefaultColor(new Color(120, 120, 120, 120));
		style.setHoverColor(new Color(160, 220, 180, 120));
		style.setSelectionColor(new Color(160, 160, 160, 120));
		style.setDefaultBorder(null);
		style.setSelectionBorder(new LineBorder(Color.MAGENTA, 2));
		style.setHoverBorder(new LineBorder(Color.GREEN, 2));
		return style;
	}

	private int size;
	private Color defaultColor;
	private Color selectionColor;
	private Color hoverColor;
	private Border defaultBorder;
	private Border selectionBorder;
	private Border hoverBorder;

	private final EventListenerList listeners;

	public NodeStyle() {
		listeners = new EventListenerList();
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public void setSize(final int size) {
		this.size = size;
		fireChangeEvent();
	}

	private void fireChangeEvent() {
		final ChangeEvent evt = new ChangeEvent(this);
		for (final ChangeListener l : listeners.getListeners(ChangeListener.class)) {
			l.stateChanged(evt);
		}
	}

	/**
	 * <p>
	 * Getter for the field <code>defaultColor</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	@Override
	public Color getDefaultColor() {
		return defaultColor;
	}

	/**
	 * <p>
	 * Setter for the field <code>defaultColor</code>.
	 * </p>
	 *
	 * @param defaultColor
	 *            a {@link java.awt.Color} object.
	 */
	public void setDefaultColor(final Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	/**
	 * <p>
	 * Getter for the field <code>selectionColor</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	@Override
	public Color getSelectionColor() {
		return selectionColor;
	}

	/**
	 * <p>
	 * Setter for the field <code>selectionColor</code>.
	 * </p>
	 *
	 * @param selectionColor
	 *            a {@link java.awt.Color} object.
	 */
	public void setSelectionColor(final Color selectionColor) {
		this.selectionColor = selectionColor;
	}

	/**
	 * <p>
	 * Getter for the field <code>hoverColor</code>.
	 * </p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	@Override
	public Color getHoverColor() {
		return hoverColor;
	}

	/**
	 * <p>
	 * Setter for the field <code>hoverColor</code>.
	 * </p>
	 *
	 * @param hoverColor
	 *            a {@link java.awt.Color} object.
	 */
	public void setHoverColor(final Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	/**
	 * <p>
	 * Getter for the field <code>defaultBorder</code>.
	 * </p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	@Override
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
	 * Getter for the field <code>selectionBorder</code>.
	 * </p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	@Override
	public Border getSelectionBorder() {
		return selectionBorder;
	}

	/**
	 * <p>
	 * Setter for the field <code>selectionBorder</code>.
	 * </p>
	 *
	 * @param selectionBorder
	 *            a {@link javax.swing.border.Border} object.
	 */
	public void setSelectionBorder(final Border selectionBorder) {
		this.selectionBorder = selectionBorder;
	}

	/**
	 * <p>
	 * Getter for the field <code>hoverBorder</code>.
	 * </p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	@Override
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

	@Override
	public void addChangeListener(final ChangeListener l) {
		listeners.add(ChangeListener.class, l);
	}

	@Override
	public void removeChangeListener(final ChangeListener l) {
		listeners.remove(ChangeListener.class, l);
	}

}
