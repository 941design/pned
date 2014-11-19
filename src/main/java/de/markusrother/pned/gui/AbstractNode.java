package de.markusrother.pned.gui;

import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import de.markusrother.swing.DragListener;
import de.markusrother.swing.HoverListener;

public abstract class AbstractNode extends JPanel implements NodeSelectionListener {

	public enum State {
		DEFAULT, //
		HOVER, //
		SELECTED, //
	}

	private static final LayoutManager NO_LAYOUT_MANAGER = null;

	private final DragListener dragListener;

	private State state;

	public AbstractNode(final LayoutManager layoutManager) {
		super(layoutManager);
		this.state = State.DEFAULT;
		this.dragListener = new DragListener() {

			@Override
			public void startDrag(final Component component, final Point point) {
				// IGNORE
			}

			@Override
			public void onDrag(final Component component, final int deltaX, final int deltaY) {
				final Rectangle r = getBounds();
				r.translate(deltaX, deltaY);
				setBounds(r);
			}

			@Override
			public void endDrag(final Component component, final Point point) {
				// IGNORE
			}

		};
		DragListener.addToComponent(this, dragListener);
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
		DragListener.removeFromComponent(this, dragListener);
	}

	protected void resumeDragListener() {
		DragListener.addToComponent(this, dragListener);
	}

	protected void suspendHoverListener() {
		HoverListener.removeFromComponent(this, NodeHoverListener.INSTANCE);
	}

	protected void resumeHoverListener() {
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
	}

}
