package de.markusrother.pned.commands;

import java.awt.Color;

public class TransitionLayoutCommand extends LayoutCommand {

	public TransitionLayoutCommand(final Object source, final ChangeType type, final Color color) {
		this(source, type, NO_SIZE, color);
	}

	public TransitionLayoutCommand(final Object source, final ChangeType type, final int size) {
		this(source, type, size, NO_COLOR);
	}

	public TransitionLayoutCommand(final Object source, final ChangeType type, final int size, final Color color) {
		super(source, type, size, color);
	}

}
