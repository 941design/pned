package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import de.markusrother.swing.DefaultDragDropListener;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

public class NodeLabel extends JLabel {

	public enum State {
		DEFAULT, //
		HOVER, //
	}

	private final NodeMotionListener nodeMotionListener;
	private final DragDropListener dragDropListener;

	public NodeLabel(final String nodeId) {
		super(nodeId);
		// TODO - this needs to go to label, and be removed from eventBus upon
		// component removal!
		this.dragDropListener = new DefaultDragDropListener(this);
		DragDropListener.addToComponent(this, dragDropListener);
		HoverListener.addToComponent(this, new LabelHoverListener());
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
		setBorder(null);
	}

	public void setState(final State state) {
		switch (state) {
		case DEFAULT:
			setBorder(null);
			break;
		case HOVER:
			setBorder(new LineBorder(Color.GREEN));
			break;
		default:
			// TODO
			throw new RuntimeException("TODO");
		}
		revalidate();
		repaint();
	}

}
