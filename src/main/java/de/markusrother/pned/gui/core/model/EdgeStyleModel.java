package de.markusrother.pned.gui.core.model;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Aggregate interface, combining other models to a model describing an edge's
 * visual state.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeStyleModel
	extends
		ChangeEventSource,
		ShapeModel,
		SizeModel,
		ColorsModel {

	// NOTHING

}
