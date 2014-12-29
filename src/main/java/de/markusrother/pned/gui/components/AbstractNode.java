package de.markusrother.pned.gui.components;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JPanel;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.DefinitelyBounded;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.listeners.EdgeCreationListener;
import de.markusrother.pned.gui.listeners.EdgeCreator;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeHoverListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
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
		NodeMotionListener,
		NodeRemovalListener,
		NodeSelectionListener,
		EdgeEditListener,
		EdgeCreationListener,
		Selectable,
		Disposable,
		DefinitelyBounded {

	private static final LayoutManager NO_LAYOUT_MANAGER = null;

	// Listeners:
	private DragDropListener<AbstractNode> dragDropListener; // selectionHandler
	private EdgeCreator edgeCreationListener;
	private SingleNodeSelector singleNodeSelector;

	protected ComponentState state;
	// TODO - This could be substituted with a Model.
	private String id;

	protected final EventBus eventBus;

	public AbstractNode(final EventBus eventBus, final LayoutManager layoutManager) {
		super(layoutManager);
		this.eventBus = eventBus;
		this.state = ComponentState.DEFAULT; // TODO - empty EnumSet
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
		// TODO - In prospect to JDK8, I do not use Adapters. Default
		// implementations in adapters allow us to remove the ignored methods.
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeSelectionListener.class, this);
		eventBus.addListener(EdgeEditListener.class, this);
		eventBus.addListener(EdgeCreationListener.class, this);
	}

	public AbstractNode(final EventBus eventBus) {
		this(eventBus, NO_LAYOUT_MANAGER);
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
		case VALID:
		case INVALID:
		default:
			throw new IllegalStateException();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(final String nodeId) {
		this.id = nodeId;
	}

	public void setState(final ComponentState state) {
		this.state = state;
		repaint();
	}

	public boolean isSelected() {
		return state == ComponentState.SINGLE_SELECTED //
				|| state == ComponentState.MULTI_SELECTED;
	}

	public boolean isPartOfMultiselection() {
		return state == ComponentState.MULTI_SELECTED;
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

	void setEdgeCreationListener(final EdgeCreator listener) {
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
		EdgeCreator.removeFromComponent(this, edgeCreationListener);
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
		EdgeCreator.addToComponent(this, edgeCreationListener);
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
	public void nodeMoved(final NodeMovedEvent e) {
		for (final String nodeId : e.getNodeIds()) {
			if (getId().equals(nodeId)) {
				final Rectangle r = getBounds();
				r.translate(e.getDeltaX(), e.getDeltaY());
				setBounds(r);
				repaint();
				break;
			}
		}
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
				setState(ComponentState.SINGLE_SELECTED);
			} else {
				setState(ComponentState.MULTI_SELECTED);
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
		setState(ComponentState.DEFAULT); // TODO - in multiselection
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		final String myId = getId();
		if (myId.equals(e.getNodeId())) {
			dispose();
		}
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		if (isSelected()) {
			eventBus.nodeRemoved(new NodeRemovalEvent(this, getId()));
		}
	}

	@Override
	public void dispose() {
		eventBus.removeListener(NodeRemovalListener.class, this);
		eventBus.removeListener(NodeSelectionListener.class, this);
		eventBus.removeListener(EdgeEditListener.class, this);
		getParent().remove(this);
	}

	@Override
	public void targetComponentEntered(final EdgeEditEvent e) {
		// IGNORE
	}

	@Override
	public void targetComponentExited(final EdgeEditEvent e) {
		// IGNORE
	}

	@Override
	public void edgeMoved(final EdgeEditEvent e) {
		// IGNORE
	}

	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		// TODO - This could be wrapped in a StateToggleCommand
		// TODO - Use state set instead!
		resumeHoverListener();
		// resumeEdgeCreationListener(); // TODO - remove?
		resumeSelectionListener();
		resumeDragDropListener();
	}

	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		// TODO - This could be wrapped in a StateToggleCommand
		// TODO - Use state set instead!
		resumeHoverListener();
		// resumeEdgeCreationListener();
		resumeSelectionListener();
		resumeDragDropListener();
	}

	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		// TODO - This could be wrapped in a StateToggleCommand
		// TODO - Use state set instead!
		suspendHoverListener();
		// suspendEdgeCreationListener();
		suspendSelectionListener();
		suspendDragDropListener();
	}

	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		if (getId().equals(cmd.getSourceId())) {
			cmd.fulfillSourceNodePromise(this);
		} else if (getId().equals(cmd.getTargetId())) {
			cmd.fulfillTargetNodePromise(this);
		}
	}

}
