package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;

public abstract class DragDropAdapter extends DragDropListener<Component> {

	protected DragDropAdapter() {
		super(Component.class);
	}

	@Override
	public void startDrag(final Component component, final Point point) {
		// IGNORE
	}

	@Override
	public void endDrag(final Component component, final Point point) {
		// IGNORE
	}

	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		// IGNORE
	}

}
