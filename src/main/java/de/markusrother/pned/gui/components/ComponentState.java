package de.markusrother.pned.gui.components;

// TODO - Should be used as EnumSet!
// Is it possible to define an arithmetic on states? It would be nice to
// have EnumPredicates to test against enum sets, such that or(DEFAULT,
// HOVER).and(SELECTED)
//
// .allOf(), .anyOf(), .oneOf(), .twoOf(),
// .atLeast(oneOf(x,y,z)).or(q).and(a,b,c)
/**
 * <p>ComponentState class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public enum ComponentState {
	DEFAULT,
	HOVER, // HOVERED
	SINGLE_SELECTED, // SELECTED("UNSELECTED") for inverse operations?
	MULTI_SELECTED,
	VALID,
	INVALID;
	// TODO - UNSELECTABLE/SELECTABLE, MULTISELECTED, DRAGGED
}
