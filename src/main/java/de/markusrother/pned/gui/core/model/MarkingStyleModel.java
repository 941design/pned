package de.markusrother.pned.gui.core.model;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Aggregate interface, combining other models to a model describing a place
 * marking's visual state.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface MarkingStyleModel
	extends
		ChangeEventSource,
		FontModel,
		ColorModel,
		SizeModel,
		ShapeModel {

	// NOTHING

}
