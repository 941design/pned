package de.markusrother.pned.gui;

import java.awt.LayoutManager;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import de.markusrother.swing.DefaultDragDropListener;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

public abstract class AbstractNode extends JPanel implements NodeSelectionListener {

	// TODO - Should be used as EnumSet!
	public enum State {
		DEFAULT, //
		HOVER, // HOVERED
		SELECTED, //
		// TODO - UNSELECTABLE/SELECTABLE, MULTISELECTED, DRAGGED
	}

	private static final LayoutManager NO_LAYOUT_MANAGER = null;

	private final DragDropListener dragDropListener;

	private State state;

	public AbstractNode(final LayoutManager layoutManager) {
		super(layoutManager);
		this.state = State.DEFAULT; // TODO - empty EnumSet
		this.dragDropListener = new DefaultDragDropListener(this);
		DragDropListener.addToComponent(this, dragDropListener);
	}

	public AbstractNode() {
		this(NO_LAYOUT_MANAGER);
	}

	/**
	 * @param theta
	 *            growing clockwise
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
		DragDropListener.removeFromComponent(this, dragDropListener);
	}

	protected void resumeDragListener() {
		DragDropListener.addToComponent(this, dragDropListener);
	}

	protected void suspendHoverListener() {
		HoverListener.removeFromComponent(this, NodeHoverListener.INSTANCE);
	}

	protected void resumeHoverListener() {
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
	}

}
