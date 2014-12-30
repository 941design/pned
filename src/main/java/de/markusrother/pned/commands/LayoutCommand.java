package de.markusrother.pned.commands;

import java.awt.Color;
import java.util.EventObject;

public abstract class LayoutCommand extends EventObject {

	public enum ChangeType {
		SIZE,
		DEFAULT_COLOR,
		DEFAULT_BORDER_COLOR,
		SELECTION_COLOR,
		SELECTION_BORDER_COLOR,
		HOVER_COLOR,
		HOVER_BORDER_COLOR;
	}

	public static final int NO_SIZE = 0;
	public static final Color NO_COLOR = null;

	private final ChangeType type;
	private final Color color;
	private final int size;

	// TODO - private final int width;
	// TODO - private final int height;
	// TODO - private final Ratio ratio;

	public LayoutCommand(final Object source, final ChangeType type, final int size, final Color color) {
		super(source);
		this.type = type;
		this.size = size;
		this.color = color;
	}

	public ChangeType getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}

}
