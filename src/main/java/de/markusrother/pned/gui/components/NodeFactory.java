package de.markusrother.pned.gui.components;


/**
 * <p>NodeFactory interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface NodeFactory {

	/**
	 * <p>newPlace.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.gui.components.Place} object.
	 */
	Place newPlace(String id);

	/**
	 * <p>newTransition.</p>
	 *
	 * @param id a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.gui.components.Transition} object.
	 */
	Transition newTransition(String id);

}
