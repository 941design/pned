package de.markusrother.pned.gui;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import de.markusrother.swing.DragDropAdapter;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

public abstract class AbstractNode extends JPanel implements NodeSelectionListener {

	public enum State {
		DEFAULT, //
		HOVER, //
		SELECTED, //
	}

	private static final LayoutManager NO_LAYOUT_MANAGER = null;

	private final DragDropListener dragListener;

	private State state;

	public AbstractNode(final LayoutManager layoutManager) {
		super(layoutManager);
		this.state = State.DEFAULT;
		this.dragListener = new DragDropAdapter() {

			@Override
			public void onDrag(final Component component, final int deltaX, final int deltaY) {
				final Rectangle r = getBounds();
				r.translate(deltaX, deltaY);
				setBounds(r);
			}

		};
		DragDropListener.addToComponent(this, dragListener);
	}

	public AbstractNode() {
		this(NO_LAYOUT_MANAGER);
	}

	/**
	 * @param theta
	 * @return A Point on the boundary of this.getShape().
	 */
	public abstract Point2D getIntersectionWithBounds(final double theta);

	abstract Shape getShape();

	public void setState(final State state) {
		this.state = state;
		setLayout(state);
	}

	abstract void setLayout(State state);

	protected void suspendSingleDragListener() {
		DragDropListener.removeFromComponent(this, dragListener);
	}

	protected void resumeDragListener() {
		DragDropListener.addToComponent(this, dragListener);
	}

	protected void suspendHoverListener() {
		HoverListener.removeFromComponent(this, NodeHoverListener.INSTANCE);
	}

	protected void resumeHoverListener() {
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
	}

}
