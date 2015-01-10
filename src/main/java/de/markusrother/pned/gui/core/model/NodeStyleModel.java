package de.markusrother.pned.gui.core.model;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Aggregate interface, combining other models to a model describing a node's
 * visual state.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeStyleModel
	extends
		ChangeEventSource,
		ColorsModel,
		SizeModel,
		BordersModel {

	// NOTHING

}
