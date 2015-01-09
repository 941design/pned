package de.markusrother.pned.gui.control.commands;

import java.awt.Color;

/**
 * <p>EdgeLayoutCommand class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeLayoutCommand extends LayoutCommand {

	/**
	 * <p>Constructor for EdgeLayoutCommand.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param type a ChangeType object.
	 * @param color a {@link java.awt.Color} object.
	 */
	public EdgeLayoutCommand(final Object source, final ChangeType type, final Color color) {
		this(source, type, NO_SIZE, color);
	}

	/**
	 * <p>Constructor for EdgeLayoutCommand.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param type a ChangeType object.
	 * @param size a int.
	 */
	public EdgeLayoutCommand(final Object source, final ChangeType type, final int size) {
		this(source, type, size, NO_COLOR);
	}

	/**
	 * <p>Constructor for EdgeLayoutCommand.</p>
	 *
	 * @param source a {@link java.lang.Object} object.
	 * @param type a ChangeType object.
	 * @param size a int.
	 * @param color a {@link java.awt.Color} object.
	 */
	public EdgeLayoutCommand(final Object source, final ChangeType type, final int size, final Color color) {
		super(source, type, size, color);
	}

}
