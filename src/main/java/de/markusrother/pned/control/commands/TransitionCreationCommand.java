package de.markusrother.pned.control.commands;

import java.awt.Point;

/**
 * <p>
 * TransitionCreationCommand class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class TransitionCreationCommand extends AbstractNodeCreationCommand {

	/**
	 * <p>
	 * Constructor for TransitionCreationCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 * @param point
	 *            a {@link java.awt.Point} object.
	 */
	public TransitionCreationCommand(final Object source, final String nodeId, final Point point) {
		super(source, nodeId, point);
	}

}
