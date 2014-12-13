package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.JPanel;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.DefinitelyBounded;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationRequest;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeHoverListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.SelectionDragDropListener;
import de.markusrother.pned.gui.listeners.SingleNodeSelector;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;
import de.markusrother.swing.Selectable;

/**
 * TODO - We can get rid entirely of the single component drag listener, if we
 * always use multi-selections. Then the click event could simply also activate
 * a selection!
 *
 */
public abstract class AbstractNode extends JPanel
	implements
		NodeListener,
		NodeSelectionListener,
		EdgeEditListener,
		Selectable,
		Disposable,
		DefinitelyBounded {

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
		SINGLE_SELECTED, // SELECTED("UNSELECTED") for inverse operations?
		MULTI_SELECTED;
		// TODO - UNSELECTABLE/SELECTABLE, MULTISELECTED, DRAGGED
	}

	private static final LayoutManager NO_LAYOUT_MANAGER = null;

	// Listeners:
	private DragDropListener<AbstractNode> dragDropListener; // selectionHandler
	private EdgeCreationListener edgeCreationListener;
	private SingleNodeSelector singleNodeSelector;

	protected State state;
	// TODO - This could be substituted with a Model.
	private Future<String> id;

	public AbstractNode(final LayoutManager layoutManager) {
		super(layoutManager);
		this.state = State.DEFAULT; // TODO - empty EnumSet
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
		// TODO - In prospect to JDK8, I do not use Adapters. Default
		// implementations in adapters allow us to remove the ignored methods.
		eventBus.addNodeListener(this);
		eventBus.addNodeSelectionListener(this);
		eventBus.addEdgeEditListener(this);
	}

	public AbstractNode() {
		this(NO_LAYOUT_MANAGER);
	}

	protected abstract Shape getShape();

	protected abstract NodeStyle getStyle();

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final NodeStyle style = getStyle();
		switch (state) {
		case MULTI_SELECTED:
		case SINGLE_SELECTED:
			setForeground(style.getSelectionColor());
			setBorder(style.getSelectionBorder());
			break;
		case HOVER:
			setForeground(style.getHoverColor());
			setBorder(style.getHoverBorder());
			break;
		case DEFAULT:
			setForeground(style.getDefaultColor());
			setBorder(style.getDefaultBorder());
			break;
		default:
			throw new IllegalStateException();
		}
	}

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

	public void setState(final State state) {
		this.state = state;
		repaint();
	}

	public boolean isSelected() {
		return state == State.SINGLE_SELECTED //
				|| state == State.MULTI_SELECTED;
	}

	public boolean isPartOfMultiselection() {
		return state == State.MULTI_SELECTED;
	}

	void setSingleNodeSelector(final SingleNodeSelector listener) {
		if (singleNodeSelector != null) {
			suspendSelectionListener();
		}
		singleNodeSelector = listener;
		resumeSelectionListener();
	}

	void setDragDropListener(final SelectionDragDropListener listener) {
		if (dragDropListener != null) {
			suspendDragDropListener();
		}
		dragDropListener = listener;
		resumeDragDropListener();
	}

	void setEdgeCreationListener(final EdgeCreationListener listener) {
		if (edgeCreationListener != null) {
			suspendEdgeCreationListener();
		}
		edgeCreationListener = listener;
		resumeEdgeCreationListener();
	}

	public void removeDragListener(final DragDropListener<AbstractNode> listener) {
		DragDropListener.removeFromComponent(this, listener);
	}

	private void suspendHoverListener() {
		HoverListener.removeFromComponent(this, NodeHoverListener.INSTANCE);
	}

	private void suspendDragDropListener() {
		DragDropListener.removeFromComponent(this, dragDropListener);
	}

	private void suspendSelectionListener() {
		DragDropListener.removeFromComponent(this, singleNodeSelector);
	}

	private void suspendEdgeCreationListener() {
		EdgeCreationListener.removeFromComponent(this, edgeCreationListener);
	}

	private void resumeHoverListener() {
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
	}

	private void resumeDragDropListener() {
		DragDropListener.addToComponent(this, dragDropListener);
	}

	private void resumeSelectionListener() {
		DragDropListener.addToComponent(this, singleNodeSelector);
	}

	private void resumeEdgeCreationListener() {
		EdgeCreationListener.addToComponent(this, edgeCreationListener);
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
			// TODO - nicer!
			if (event.getSource() instanceof SingleNodeSelector) {
				setState(State.SINGLE_SELECTED);
			} else {
				setState(State.MULTI_SELECTED);
			}
		}
	}

	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		if (event.getNodes().contains(this)) {
			deselect();
		}
	}

	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent event) {
		if (event.getNodes().contains(this)) {
			// TODO - suspend selection listener?
		}
	}

	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent event) {
		if (isSelected()) {
			deselect();
		}
	}

	private void deselect() {
		// resumeDragListener();
		resumeHoverListener();
		setState(State.DEFAULT); // TODO - in multiselection
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		// IGNORE, OBSOLETE - will go to different listener!
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// IGNORE, OBSOLETE - will go to different listener!
	}

	@Override
	public void createPlace(final PlaceCreationRequest e) {
		// IGNORE, OBSOLETE - will go to different listener!
	}

	@Override
	public void createTransition(final TransitionCreationRequest e) {
		// IGNORE, OBSOLETE - will go to different listener!
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		if (e.getNode() == this) {
			dispose();
		}
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		if (isSelected()) {
			eventBus.nodeRemoved(new NodeRemovalEvent(this, this));
		}
	}

	@Override
	public void dispose() {
		eventBus.removeNodeListener(this);
		eventBus.removeNodeSelectionListener(this);
		getParent().remove(this);
	}

	@Override
	public void targetComponentEntered(final EdgeEditEvent e) {
	}

	@Override
	public void targetComponentExited(final EdgeEditEvent e) {
	}

	@Override
	public void edgeMoved(final EdgeEditEvent e) {
	}

	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		// TODO - Use state set instead!
		resumeHoverListener();
		// resumeEdgeCreationListener(); // TODO - remove?
		resumeSelectionListener();
		resumeDragDropListener();
	}

	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		// TODO - Use state set instead!
		resumeHoverListener();
		// resumeEdgeCreationListener();
		resumeSelectionListener();
		resumeDragDropListener();
	}

	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		// TODO - Use state set instead!
		suspendHoverListener();
		// suspendEdgeCreationListener();
		suspendSelectionListener();
		suspendDragDropListener();
	}

}
