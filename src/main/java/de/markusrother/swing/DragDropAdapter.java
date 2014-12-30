package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;

/**
 * <p>Abstract DragDropAdapter class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class DragDropAdapter extends DragDropListener<Component> {

	/**
	 * <p>Constructor for DragDropAdapter.</p>
	 */
	protected DragDropAdapter() {
		super(Component.class);
	}

	/** {@inheritDoc} */
	@Override
	public void startDrag(final Component component, final Point point) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void endDrag(final Component component, final Point point) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		// IGNORE
	}

}
