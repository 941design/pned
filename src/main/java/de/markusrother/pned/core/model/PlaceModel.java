package de.markusrother.pned.core.model;

/**
 * <p>
 * Mutable model for Petri net places. In addition to {@link de.markusrother.pned.core.model.NodeModel}s places
 * consist of a mutable marking. The marking is <b>not</b> typed, but is rather
 * a simple weight count.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PlaceModel
	extends
		NodeModel {

	/**
	 * <p>
	 * Returns this place's current marking.
	 * </p>
	 *
	 * @return a int - the marking.
	 */
	int getMarking();

	/**
	 * <p>
	 * Sets this place's marking.
	 * </p>
	 *
	 * @param marking
	 *            a int - the new marking.
	 */
	void setMarking(int marking);

}
