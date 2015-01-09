package de.markusrother.pned.gui.model;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Aggregate interface
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
