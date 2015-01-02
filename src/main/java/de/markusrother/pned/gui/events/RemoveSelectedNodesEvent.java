package de.markusrother.pned.gui.events;

import java.util.EventObject;

/**
 * <p>
 * RemoveSelectedNodesEvent class.
 * </p>
 *
 * FIXME - Should become single RemoveNodeEvent!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class RemoveSelectedNodesEvent extends EventObject {

	/**
	 * <p>
	 * Constructor for RemoveSelectedNodesEvent.
	 * </p>
	 *
	 * @param source
	 *            a {@link java.lang.Object} object.
	 */
	public RemoveSelectedNodesEvent(final Object source) {
		super(source);
	}

}
