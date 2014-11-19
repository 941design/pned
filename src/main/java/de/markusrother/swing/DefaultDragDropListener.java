package de.markusrother.swing;

import java.awt.Component;
import java.awt.Rectangle;

public class DefaultDragDropListener extends DragDropAdapter {

	private final Component responsiveComponent;

	public DefaultDragDropListener(final Component responsiveComponent) {
		this.responsiveComponent = responsiveComponent;
	}

	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		final Rectangle r = responsiveComponent.getBounds();
		r.translate(deltaX, deltaY);
		responsiveComponent.setBounds(r);
	}

}
