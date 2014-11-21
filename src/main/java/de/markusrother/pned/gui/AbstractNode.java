package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.JPanel;

import de.markusrother.swing.DefaultDragDropListener;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;

/**
 * TODO - We can get rid entirely of the single component drag listener, if we
 * always use multi-selections. Then the click event could simply also activate
 * a selection!
 *
 */
public abstract class AbstractNode extends JPanel implements NodeSelectionListener {

	// TODO - Should be used as EnumSet!
	// Is it possible to define an arithmetic on states? It would be nice to
	// have EnumPredicates to test against enum sets, such that or(DEFAULT,
	// HOVER).and(SELECTED)
	//
	// .allOf(), .anyOf(), .oneOf(), .twoOf(),
	// .atLeast(oneOf(x,y,z)).or(q).and(a,b,c)
	public enum State {
		DEFAULT, //
		HOVER, // HOVERED
		SELECTED, // SELECTED("UNSELECTED") for inverse operations?
		// TODO - UNSELECTABLE/SELECTABLE, MULTISELECTED, DRAGGED
	}

	private static final LayoutManager NO_LAYOUT_MANAGER = null;

	private final DragDropListener dragDropListener;

	private State state;
	// TODO - This could be substituted with a Model.
	private Future<String> id;

	public AbstractNode(final LayoutManager layoutManager) {
		super(layoutManager);
		this.state = State.DEFAULT; // TODO - empty EnumSet
		this.dragDropListener = new DefaultDragDropListener(this);
		DragDropListener.addToComponent(this, dragDropListener);
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
		eventBus.addNodeSelectionListener(this);
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

	public Point getCenter() {
		final Point point = getLocation();
		point.translate( //
				(int) Math.floor((getWidth() + 0.5) / 2.0), //
				(int) Math.floor((getHeight() + 0.5) / 2.0));
		return point;
	}

	public String getId() {
		try {
			// TODO - to constant:
			return id.get(500L, TimeUnit.MILLISECONDS);
		} catch (final InterruptedException e) {
			// TODO
			throw new RuntimeException("TODO");
		} catch (final ExecutionException e) {
			// TODO
			throw new RuntimeException("TODO");
		} catch (final TimeoutException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	public void setId(final Future<String> future) {
		this.id = future;
	}

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

	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		// TODO - to improve performance iteration, the grid/container could
		// instead listen to this event!
		if (event.getNodes().contains(this)) {
			suspendSingleDragListener();
			suspendHoverListener();
			// TODO - just replace DEFAULT/UNSELECTED by SELECTED in state
			// EnumSet.
			setState(State.SELECTED); // TODO - in multiselection
		}
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		if (event.getNodes().contains(this)) {
			resumeDragListener();
			resumeHoverListener();
			setState(State.DEFAULT); // TODO - in multiselection
		}
	}

}
