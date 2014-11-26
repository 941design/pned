package de.markusrother.pned.gui;

public enum NodeCreationMode {

	PLACE,
	TRANSITION;

	public static final NodeCreationMode defaultCreationMode = PLACE;

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
