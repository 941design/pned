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

	/**
	 * <p>
	 * newDefault.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.layout.style.NodeStyle} object.
	 */
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

	/**
	 * <p>
	 * Constructor for NodeStyle.
	 * </p>
	 */
	public NodeStyle() {
		listeners = new EventListenerList();
	}

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		return size;
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final int size) {
		this.size = size;
		fireChangeEvent();
	}

	/**
	 * <p>
	 * fireChangeEvent.
	 * </p>
	 */
	private void fireChangeEvent() {
		final ChangeEvent evt = new ChangeEvent(this);
		for (final ChangeListener l : listeners.getListeners(ChangeListener.class)) {
			l.stateChanged(evt);
		}
	}

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public void addChangeListener(final ChangeListener l) {
		listeners.add(ChangeListener.class, l);
	}

	/** {@inheritDoc} */
	@Override
	public void removeChangeListener(final ChangeListener l) {
		listeners.remove(ChangeListener.class, l);
	}

}
