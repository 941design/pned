package de.markusrother.pned.gui.components;

/**
 * <p>
 * Enum of states a component can have.
 * </p>
 * 
 * TODO
 * <ul>
 * <li>Should be used as EnumSet!</li>
 * <li>UNSELECTABLE/SELECTABLE, MULTISELECTED, DRAGGED, HIDDEN, etc.</li>
 * <li>Is it possible to define an arithmetic on states? It would be nice to
 * have EnumPredicates to test against enum sets, such that or(DEFAULT,
 * HOVER).and(SELECTED)</li>
 * </ul>
 *
 * @author Markus Rother
 * @version 1.0
 */
public enum ComponentState {

	DEFAULT,
	HOVER,
	SINGLE_SELECTED,
	MULTI_SELECTED,
	VALID,
	INVALID;

}
