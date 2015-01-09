package de.markusrother.pned.gui.core;

/**
 * <p>NodeCreationMode class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public enum NodeCreationMode {

	PLACE,
	TRANSITION;

	/** Constant <code>defaultCreationMode</code> */
	public static final NodeCreationMode defaultCreationMode = PLACE;

	/**
	 * <p>inverse.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.NodeCreationMode} object.
	 */
	public NodeCreationMode inverse() {
		switch (this) {
		case PLACE:
			return TRANSITION;
		case TRANSITION:
			return PLACE;
		default:
			throw new IllegalStateException();
		}
	}
}
