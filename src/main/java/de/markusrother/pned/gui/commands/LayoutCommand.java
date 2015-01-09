package de.markusrother.pned.gui.commands;

import java.awt.Color;
import java.util.EventObject;

/**
 * <p>Abstract LayoutCommand class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class LayoutCommand extends EventObject {

	public enum ChangeType {
		SIZE,
		DEFAULT_COLOR,
		DEFAULT_BORDER_COLOR,
		SELECTION_COLOR,
		SELECTION_BORDER_COLOR,
		HOVER_COLOR,
		HOVER_BORDER_COLOR;
	}

	/** Constant <code>NO_SIZE=0</code> */
	public static final int NO_SIZE = 0;
	/** Constant <code>NO_COLOR</code> */
	public static final Color NO_COLOR = null;

	private final ChangeType type;
	private final Color color;
	private final int size;

	// TODO - private final int width;
	// TODO - private final int height;
	// TODO - private final Ratio ratio;

	/**
	 * <p>Constructor for LayoutCommand.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param type a {@link de.markusrother.pned.gui.commands.LayoutCommand.ChangeType} object.
	 * @param size a int.
	 * @param color a {@link java.awt.Color} object.
	 */
	public LayoutCommand(final Object source, final ChangeType type, final int size, final Color color) {
		super(source);
		this.type = type;
		this.size = size;
		this.color = color;
	}

	/**
	 * <p>Getter for the field <code>type</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.commands.LayoutCommand.ChangeType} object.
	 */
	public ChangeType getType() {
		return type;
	}

	/**
	 * <p>Getter for the field <code>color</code>.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * <p>Getter for the field <code>size</code>.</p>
	 *
	 * @return a int.
	 */
	public int getSize() {
		return size;
	}

}
