package de.markusrother.pned.control.commands;

import java.awt.Point;

/**
 * <p>
 * PlaceCreationCommand class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PlaceCreationCommand extends AbstractNodeCreationCommand {

	/**
	 * <p>
	 * Constructor for PlaceCreationCommand.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 * @param point
	 *            a {@link java.awt.Point} object.
	 */
	public PlaceCreationCommand(final Object source, final String nodeId, final Point point) {
		super(source, nodeId, point);
	}

}
