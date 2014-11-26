package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

public class DefaultDragDropListener<T extends Component> extends DragDropListener<T> {

	private final T responsiveComponent;

	public DefaultDragDropListener(final Class<T> type, final T responsiveComponent) {
		super(type);
		this.responsiveComponent = responsiveComponent;
	}

	@Override
	public void startDrag(final T component, final Point dragStart) {
		// IGNORE
	}

	@Override
	public void onDrag(final T component, final int deltaX, final int deltaY) {
		final Rectangle r = responsiveComponent.getBounds();
		r.translate(deltaX, deltaY);
		responsiveComponent.setBounds(r);
	}

	@Override
	public void endDrag(final T component, final Point dragEnd) {
		// IGNORE
	}

}
