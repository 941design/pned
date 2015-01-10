package de.markusrother.pned.gui.components;


/**
 * <p>NodeFactory interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeComponentFactory {

	/**
	 * <p>newPlace.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.gui.components.PlaceComponent} object.
	 */
	PlaceComponent newPlace(String id);

	/**
	 * <p>newTransition.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.gui.components.TransitionComponent} object.
	 */
	TransitionComponent newTransition(String id);

}
