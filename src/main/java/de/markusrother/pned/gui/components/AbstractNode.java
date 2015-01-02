package de.markusrother.pned.gui.components;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JPanel;

import de.markusrother.pned.core.commands.NodeMotionCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.gui.DefinitelyBounded;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.layout.style.NodeStyle;
import de.markusrother.pned.gui.listeners.EdgeCreator;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeHoverListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.listeners.NodeRequestListener;
import de.markusrother.pned.gui.listeners.NodeSelectionListener;
import de.markusrother.pned.gui.listeners.SelectionDragDropListener;
import de.markusrother.pned.gui.listeners.SingleNodeSelector;
import de.markusrother.pned.gui.requests.NodeRequest;
import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.HoverListener;
import de.markusrother.swing.Selectable;

/**
 * TODO - We can get rid entirely of the single component drag listener, if we
 * always use multi-selections. Then the click event could simply also activate
 * a selection!
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractNode extends JPanel
	implements
		NodeRequestListener,
		NodeMotionListener,
		NodeRemovalListener,
		NodeSelectionListener,
		EdgeEditListener,
		Selectable,
		Disposable,
		DefinitelyBounded {

	/** Constant <code>NO_LAYOUT_MANAGER</code> */
	private static final LayoutManager NO_LAYOUT_MANAGER = null;

	// Listeners:
	private DragDropListener<AbstractNode> dragDropListener; // selectionHandler
	private EdgeCreator edgeCreationListener;
	private SingleNodeSelector singleNodeSelector;

	protected ComponentState state;
	// TODO - This could be substituted with a Model.
	private String id;

	protected final EventBus eventBus;

	/**
	 * <p>
	 * Constructor for AbstractNode.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param layoutManager
	 *            a {@link java.awt.LayoutManager} object.
	 */
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
		eventBus.addListener(NodeRequestListener.class, this);
	}

	/**
	 * <p>
	 * Constructor for AbstractNode.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	public AbstractNode(final EventBus eventBus) {
		this(eventBus, NO_LAYOUT_MANAGER);
	}

	/**
	 * <p>
	 * getShape.
	 * </p>
	 *
	 * @return a {@link java.awt.Shape} object.
	 */
	protected abstract Shape getShape();

	/**
	 * <p>
	 * getStyle.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.layout.style.NodeStyle} object.
	 */
	protected abstract NodeStyle getStyle();

	/** {@inheritDoc} */
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

	/**
	 * <p>
	 * Getter for the field <code>id</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>
	 * Setter for the field <code>id</code>.
	 * </p>
	 *
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 */
	public void setId(final String nodeId) {
		this.id = nodeId;
	}

	/**
	 * <p>
	 * Setter for the field <code>state</code>.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.components.ComponentState}
	 *            object.
	 */
	public void setState(final ComponentState state) {
		this.state = state;
		repaint();
	}

	/**
	 * <p>
	 * isSelected.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean isSelected() {
		return state == ComponentState.SINGLE_SELECTED //
				|| state == ComponentState.MULTI_SELECTED;
	}

	/**
	 * <p>
	 * isPartOfMultiselection.
	 * </p>
	 *
	 * @return a boolean.
	 */
	public boolean isPartOfMultiselection() {
		return state == ComponentState.MULTI_SELECTED;
	}

	/**
	 * <p>
	 * Setter for the field <code>singleNodeSelector</code>.
	 * </p>
	 *
	 * @param listener
	 *            a
	 *            {@link de.markusrother.pned.gui.listeners.SingleNodeSelector}
	 *            object.
	 */
	void setSingleNodeSelector(final SingleNodeSelector listener) {
		if (singleNodeSelector != null) {
			suspendSelectionListener();
		}
		singleNodeSelector = listener;
		resumeSelectionListener();
	}

	/**
	 * <p>
	 * Setter for the field <code>dragDropListener</code>.
	 * </p>
	 *
	 * @param listener
	 *            a
	 *            {@link de.markusrother.pned.gui.listeners.SelectionDragDropListener}
	 *            object.
	 */
	void setDragDropListener(final SelectionDragDropListener listener) {
		if (dragDropListener != null) {
			suspendDragDropListener();
		}
		dragDropListener = listener;
		resumeDragDropListener();
	}

	/**
	 * <p>
	 * Setter for the field <code>edgeCreationListener</code>.
	 * </p>
	 *
	 * @param listener
	 *            a {@link de.markusrother.pned.gui.listeners.EdgeCreator}
	 *            object.
	 */
	void setEdgeCreationListener(final EdgeCreator listener) {
		if (edgeCreationListener != null) {
			suspendEdgeCreationListener();
		}
		edgeCreationListener = listener;
		resumeEdgeCreationListener();
	}

	/**
	 * <p>
	 * removeDragListener.
	 * </p>
	 *
	 * @param listener
	 *            a {@link de.markusrother.swing.DragDropListener} object.
	 */
	public void removeDragListener(final DragDropListener<AbstractNode> listener) {
		DragDropListener.removeFromComponent(this, listener);
	}

	/**
	 * <p>
	 * suspendHoverListener.
	 * </p>
	 */
	private void suspendHoverListener() {
		HoverListener.removeFromComponent(this, NodeHoverListener.INSTANCE);
	}

	/**
	 * <p>
	 * suspendDragDropListener.
	 * </p>
	 */
	private void suspendDragDropListener() {
		DragDropListener.removeFromComponent(this, dragDropListener);
	}

	/**
	 * <p>
	 * suspendSelectionListener.
	 * </p>
	 */
	private void suspendSelectionListener() {
		DragDropListener.removeFromComponent(this, singleNodeSelector);
	}

	/**
	 * <p>
	 * suspendEdgeCreationListener.
	 * </p>
	 */
	private void suspendEdgeCreationListener() {
		EdgeCreator.removeFromComponent(this, edgeCreationListener);
	}

	/**
	 * <p>
	 * resumeHoverListener.
	 * </p>
	 */
	private void resumeHoverListener() {
		HoverListener.addToComponent(this, NodeHoverListener.INSTANCE);
	}

	/**
	 * <p>
	 * resumeDragDropListener.
	 * </p>
	 */
	private void resumeDragDropListener() {
		DragDropListener.addToComponent(this, dragDropListener);
	}

	/**
	 * <p>
	 * resumeSelectionListener.
	 * </p>
	 */
	private void resumeSelectionListener() {
		DragDropListener.addToComponent(this, singleNodeSelector);
	}

	/**
	 * <p>
	 * resumeEdgeCreationListener.
	 * </p>
	 */
	private void resumeEdgeCreationListener() {
		EdgeCreator.addToComponent(this, edgeCreationListener);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isContained(final Rectangle r) {
		// TODO - Code duplication with PnGrid (Create a static util class)
		final Point point = getLocation();
		point.translate( //
				(int) Math.floor((getWidth() + 0.5) / 2.0), //
				(int) Math.floor((getHeight() + 0.5) / 2.0));
		return r.contains(point);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMotionCommand e) {
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

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		if (event.getNodes().contains(this)) {
			deselect();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent event) {
		if (event.getNodes().contains(this)) {
			// TODO - suspend selection listener?
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent event) {
		if (isSelected()) {
			deselect();
		}
	}

	/**
	 * <p>
	 * deselect.
	 * </p>
	 */
	private void deselect() {
		// resumeDragListener();
		resumeHoverListener();
		setState(ComponentState.DEFAULT); // TODO - in multiselection
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand e) {
		final String myId = getId();
		if (myId.equals(e.getNodeId())) {
			dispose();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		if (isSelected()) {
			eventBus.nodeRemoved(new NodeRemovalCommand(this, getId()));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		eventBus.removeListener(NodeRemovalListener.class, this);
		eventBus.removeListener(NodeSelectionListener.class, this);
		eventBus.removeListener(EdgeEditListener.class, this);
		getParent().remove(this);
	}

	/** {@inheritDoc} */
	@Override
	public void targetComponentEntered(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void targetComponentExited(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void edgeMoved(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		// TODO - This could be wrapped in a StateToggleCommand
		// TODO - Use state set instead!
		resumeHoverListener();
		// resumeEdgeCreationListener(); // TODO - remove?
		resumeSelectionListener();
		resumeDragDropListener();
	}

	/** {@inheritDoc} */
	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		// TODO - This could be wrapped in a StateToggleCommand
		// TODO - Use state set instead!
		resumeHoverListener();
		// resumeEdgeCreationListener();
		resumeSelectionListener();
		resumeDragDropListener();
	}

	/** {@inheritDoc} */
	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		// TODO - This could be wrapped in a StateToggleCommand
		// TODO - Use state set instead!
		suspendHoverListener();
		// suspendEdgeCreationListener();
		suspendSelectionListener();
		suspendDragDropListener();
	}

	/** {@inheritDoc} */
	@Override
	public void requestNode(final NodeRequest req) {
		final String myId = getId();
		final String requestedId = req.getNodeId();
		if (myId.equals(requestedId)) {
			req.set(this);
		}
	}

}
