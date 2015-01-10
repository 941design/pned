package de.markusrother.pned.gui.components;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.LabelEditListener;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeMotionListener;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.gui.components.listeners.LabelHoverListener;
import de.markusrother.pned.gui.components.listeners.NodeLabelEditor;
import de.markusrother.pned.gui.components.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.events.EdgeEditEvent;
import de.markusrother.pned.gui.control.events.EdgeEditListener;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.core.model.NodeLabelStyle;
import de.markusrother.swing.DefaultDragDropListener;
import de.markusrother.swing.DragDropListener;

/**
 * <p>
 * NodeLabel class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeLabel extends JLabel
	implements
		LabelEditListener,
		NodeMotionListener,
		NodeRemovalListener,
		EdgeEditListener,
		Disposable {

	/** Constant <code>style</code> */
	private static NodeLabelStyle style = new NodeLabelStyle();
	static {
		style.setDefaultFg(Color.BLACK);
		style.setDefaultBg(Color.WHITE);
		style.setDefaultBorder(new LineBorder(Color.GRAY));
		style.setDefaultOpacity(true);
		style.setHoverFg(Color.BLACK);
		style.setHoverBg(Color.LIGHT_GRAY);
		style.setHoverBorder(new LineBorder(Color.GREEN));
		style.setHoverOpacity(true);
	}

	private final DragDropListener<NodeLabel> dragDropListener;
	private final String nodeId;
	private final PnEventBus eventBus;
	private final NodeLabelEditor labelEditor;
	private final LabelHoverListener hoverListener;

	private ComponentState state;

	/**
	 * <p>
	 * Constructor for NodeLabel.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 * @param labelEditor
	 *            a
	 *            {@link de.markusrother.pned.gui.components.listeners.NodeLabelEditor}
	 *            object.
	 */
	public NodeLabel(final PnEventBus eventBus, final NodeLabelEditor labelEditor, final String nodeId) {
		this(eventBus, nodeId, labelEditor, nodeId);
	}

	/**
	 * <p>
	 * Constructor for NodeLabel.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @param nodeId
	 *            a {@link java.lang.String} object.
	 * @param nodeLabel
	 *            a {@link java.lang.String} object.
	 * @param labelEditor
	 *            a
	 *            {@link de.markusrother.pned.gui.components.listeners.NodeLabelEditor}
	 *            object.
	 */
	public NodeLabel(final PnEventBus eventBus, final String nodeId, final NodeLabelEditor labelEditor,
			final String nodeLabel) {
		// TODO - Use setters for NOdeLabelEditor and EventBus!
		super(nodeLabel);

		this.eventBus = eventBus;
		this.nodeId = nodeId;

		this.dragDropListener = new DefaultDragDropListener<>(NodeLabel.class, this);
		DragDropListener.addToComponent(this, dragDropListener);

		this.labelEditor = labelEditor;
		labelEditor.addToComponent(this);

		this.hoverListener = new LabelHoverListener();
		this.hoverListener.addToComponent(this);

		// TODO - dispose and assert removal of listeners!
		eventBus.addListener(LabelEditListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(EdgeEditListener.class, this);

		setState(ComponentState.DEFAULT);
	}

	/**
	 * <p>
	 * Getter for the field <code>nodeId</code>.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNodeId() {
		return nodeId;
	}

	/**
	 * <p>
	 * Getter for the field <code>state</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.components.ComponentState}
	 *         object.
	 */
	public ComponentState getState() {
		return state;
	}

	/**
	 * <p>
	 * Getter for the field <code>eventBus</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.control.EventBus} object.
	 */
	public EventBus getEventBus() {
		return eventBus;
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
		// TODO - create interface Stateful
		this.state = state;
		style.apply(this);
		revalidate();
		repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMotionCommand e) {
		if (this.nodeId.equals(e.getNodeId())) {
			final Rectangle r = getBounds();
			r.translate(e.getDeltaX(), e.getDeltaY());
			setBounds(r);
			repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand e) {
		final String removedNodeId = e.getNodeId();
		if (this.nodeId.equals(removedNodeId)) {
			dispose();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		eventBus.removeListener(NodeRemovalListener.class, this);
		eventBus.removeListener(NodeMotionListener.class, this);
		getParent().remove(this);
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(final LabelEditCommand e) {
		if (this.nodeId.equals(e.getElementId())) {
			setText(e.getLabel());
			setSize(getPreferredSize()); // TODO - padding!
		}
	}

	/** {@inheritDoc} */
	@Override
	public void componentEntered(final EdgeEditEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void componentExited(final EdgeEditEvent e) {
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
		installListeners();
	}

	/** {@inheritDoc} */
	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		installListeners();
	}

	/** {@inheritDoc} */
	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		suspendListeners();
	}

	/**
	 * <p>
	 * suspendListeners.
	 * </p>
	 */
	protected void suspendListeners() {
		DragDropListener.removeFromComponent(this, dragDropListener);
		labelEditor.removeFromComponent(this);
		hoverListener.removeFromComponent(this);
	}

	/**
	 * <p>
	 * installListeners.
	 * </p>
	 */
	protected void installListeners() {
		DragDropListener.addToComponent(this, dragDropListener);
		labelEditor.addToComponent(this);
		hoverListener.addToComponent(this);
	}

}
