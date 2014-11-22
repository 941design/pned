package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.PnGridPanel.eventBus;

import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.JPanel;

import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;
import de.markusrother.swing.Selectable;

/**
 * TODO - We can get rid entirely of the single component drag listener, if we
 * always use multi-selections. Then the click event could simply also activate
 * a selection!
 *
 */
public abstract class AbstractNode extends JPanel implements NodeListener, NodeSelectionListener, Selectable,
		Disposable, DefinitelyBounded {

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

	private DragDropListener dragListener; // selectionHandler
	private EdgeCreationListener edgeCreationListener;
	private SingleNodeSelector singleNodeSelector;

	private State state;
	// TODO - This could be substituted with a Model.
	private Future<String> id;

	public AbstractNode(final LayoutManager layoutManager) {
		super(layoutManager);
		this.state = State.DEFAULT; // TODO - empty EnumSet
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
		eventBus.addNodeListener(this);
		eventBus.addNodeSelectionListener(this);
	}

	public AbstractNode() {
		this(NO_LAYOUT_MANAGER);
	}

	protected abstract Shape getShape();

	String getId() {
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

	void setId(final Future<String> future) {
		this.id = future;
	}

	void setState(final State state) {
		this.state = state;
		setLayout(state);
	}

	/**
	 * TODO - not a nice pattern in respect to visibility!
	 */
	protected abstract void setLayout(State state);

	void setSingleNodeSelector(final SingleNodeSelector listener) {
		if (singleNodeSelector != null) {
			DragDropListener.removeFromComponent(this, singleNodeSelector);
		}
		singleNodeSelector = listener;
		DragDropListener.addToComponent(this, singleNodeSelector);
	}

	void setDragListener(final SelectionDragDropListener listener) {
		if (dragListener != null) {
			DragDropListener.removeFromComponent(this, dragListener);
		}
		dragListener = listener;
		DragDropListener.addToComponent(this, dragListener);
	}

	void setEdgeCreationListener(final EdgeCreationListener listener) {
		if (edgeCreationListener != null) {
			EdgeCreationListener.removeFromComponent(this, edgeCreationListener);
		}
		edgeCreationListener = listener;
		EdgeCreationListener.addToComponent(this, edgeCreationListener);
	}

	void removeDragListener(final DragDropListener listener) {
		DragDropListener.removeFromComponent(this, listener);
	}

	private void suspendHoverListener() {
		HoverListener.removeFromComponent(this, NodeHoverListener.INSTANCE);
	}

	private void resumeHoverListener() {
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
	}

	@Override
	public boolean isContained(final Rectangle r) {
		// TODO - Code duplication with PnGrid (Create a static util class)
		final Point point = getLocation();
		point.translate( //
				(int) Math.floor((getWidth() + 0.5) / 2.0), //
				(int) Math.floor((getHeight() + 0.5) / 2.0));
		return r.contains(point);
	}

	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		// TODO - to improve performance iteration, the grid/container could
		// instead listen to this event!
		if (event.getNodes().contains(this)) {
			// suspendDragListener();
			suspendHoverListener();
			// TODO - just replace DEFAULT/UNSELECTED by SELECTED in state
			// EnumSet.
			setState(State.SELECTED); // TODO - in multiselection
		}
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		if (event.getNodes().contains(this)) {
			// resumeDragListener();
			resumeHoverListener();
			setState(State.DEFAULT); // TODO - in multiselection
		}
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// IGNORE
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		if (e.getNode() == this) {
			dispose();
		}
	}

	@Override
	public void dispose() {
		eventBus.removeNodeListener(this);
		eventBus.removeNodeSelectionListener(this);
		getParent().remove(this);
	}

}
