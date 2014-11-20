package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.Rectangle;

import javax.swing.JLabel;

import de.markusrother.swing.DefaultDragDropListener;
import de.markusrother.swing.DragDropListener;

public class NodeLabel extends JLabel {

	private final NodeMotionListener nodeMotionListener;
	private final DragDropListener dragDropListener;

	public NodeLabel(final String nodeId) {
		super(nodeId);
		// TODO - this needs to go to label, and be removed from eventBus upon
		// component removal!
		this.dragDropListener = new DefaultDragDropListener(this);
		DragDropListener.addToComponent(this, dragDropListener);
		this.nodeMotionListener = new NodeMotionListener() {

			@Override
			public void nodeMoved(final NodeMovedEvent event) {
				for (final AbstractNode node : event.getNodes()) {
					if (node.getId() == nodeId) {
						final Rectangle r = getBounds();
						r.translate(event.getDeltaX(), event.getDeltaY());
						setBounds(r);
						repaint();
					}
				}
			}

		};
		eventBus.addNodeMotionListener(nodeMotionListener);
	}

}
