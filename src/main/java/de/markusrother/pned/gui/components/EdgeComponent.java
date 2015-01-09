package de.markusrother.pned.gui.components;

import static de.markusrother.swing.SwingUtils.getCenter;
import static de.markusrother.util.MathUtils.getRadiansOfDelta;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

import javax.swing.event.ChangeEvent;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.EdgeCreationListener;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeMotionListener;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.commands.PlaceLayoutListener;
import de.markusrother.pned.gui.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.commands.TransitionLayoutListener;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.EdgeEditListener;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.listeners.EdgeHoverListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.model.EdgeStyleModel;
import de.markusrother.pned.gui.style.Stylable;
import de.markusrother.swing.HoverListener;

/**
 * TODO - manage the contact-points of source component, too. Create a segment
 * which is invisible, but connected, to the source components center, avoiding
 * flickering.
 *
 * FIXME - create subclass for unfinished EdgeComponent
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeComponent extends AbstractEdgeComponent<AbstractNode, AbstractNode>
	implements
		NodeRemovalListener,
		NodeMotionListener,
		EdgeCreationListener,
		EdgeEditListener,
		PlaceLayoutListener,
		TransitionLayoutListener,
		Stylable<EdgeStyleModel>,
		Disposable {

	/** Constant <code>NO_TARGET_COMPONENT</code> */
	private static final AbstractNode NO_TARGET_COMPONENT = null;
	/** Constant <code>NO_ID=""</code> */
	private static final String NO_ID = "";

	/**
	 * <p>
	 * getSourceId.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSourceId() {
		return sourceComponent.getId();
	}

	/**
	 * <p>
	 * getTargetId.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTargetId() {
		return targetComponent == null ? null : targetComponent.getId();
	}

	private Shape tip;
	private Line2D line;
	private ComponentState state;

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
	 * Setter for the field <code>state</code>.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.components.ComponentState}
	 *            object.
	 */
	public void setState(final ComponentState state) {
		this.state = state;
	}

	private final EventBus eventBus;
	private EdgeStyleModel style;
	private final String id;

	/**
	 * <p>
	 * Constructor for EdgeComponent.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @param style a {@link de.markusrother.pned.gui.model.EdgeStyleModel} object.
	 * @param sourceComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param sourceComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param source
	 *            a {@link java.awt.Point} object.
	 * @param target
	 *            a {@link java.awt.Point} object.
	 */
	public EdgeComponent(final EventBus eventBus, final EdgeStyleModel style, final AbstractNode sourceComponent,
			final Point source, final Point target) {
		this(eventBus, NO_ID, style, sourceComponent, NO_TARGET_COMPONENT, source, target);
	}

	/**
	 * <p>
	 * Constructor for EdgeComponent.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @param sourceComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param targetComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param id
	 *            a {@link java.lang.String} object.
	 * @param style a {@link de.markusrother.pned.gui.model.EdgeStyleModel} object.
	 */
	public EdgeComponent(final EventBus eventBus, final String id, final EdgeStyleModel style,
			final AbstractNode sourceComponent, final AbstractNode targetComponent) {
		this(eventBus, id, style, sourceComponent, targetComponent, getCenter(sourceComponent),
				getCenter(targetComponent));
		reconnect();
	}

	/**
	 * <p>
	 * Constructor for EdgeComponent.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.control.EventBus} object.
	 * @param style a {@link de.markusrother.pned.gui.model.EdgeStyleModel} object.
	 * @param sourceComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param targetComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param sourceComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param source
	 *            a {@link java.awt.Point} object.
	 * @param targetComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param target
	 *            a {@link java.awt.Point} object.
	 * @param id
	 *            a {@link java.lang.String} object.
	 */
	private EdgeComponent(final EventBus eventBus, final String id, final EdgeStyleModel style,
			final AbstractNode sourceComponent, final AbstractNode targetComponent, final Point source,
			final Point target) {
		super(sourceComponent, targetComponent, source, target);

		this.eventBus = eventBus;
		this.id = id;

		setStyle(style);
		setState(ComponentState.DEFAULT);

		eventBus.addListener(EdgeCreationListener.class, this);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(PlaceLayoutListener.class, this);
		eventBus.addListener(TransitionLayoutListener.class, this);
		eventBus.addListener(EdgeEditListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		switch (state) {
		case HOVER:
			g.setColor(style.getHoverColor());
			break;
		case INVALID:
			g.setColor(style.getInvalidColor());
			break;
		case VALID:
			g.setColor(style.getValidColor());
			break;
		case MULTI_SELECTED:
		case SINGLE_SELECTED:
		case DEFAULT:
			g.setColor(style.getDefaultColor());
			break;
		default:
			throw new IllegalStateException();
		}

		final Graphics2D g2 = (Graphics2D) g;

		// Draw line:
		g2.setStroke(style.getStroke());
		line = new Line2D.Double(source, target);
		g2.draw(line);

		// Draw tip:
		g2.translate(target.x, target.y);
		g2.rotate(getRadiansOfDelta(source, target));
		g2.fill(style.getShape());

		// TODO - Currently the edge component is as large as the entire grid!
		// We could optimize a little, here.
	}

	/**
	 * <p>
	 * acceptsTarget.
	 * </p>
	 *
	 * @param component
	 *            a {@link java.awt.Component} object.
	 * @return a boolean.
	 */
	public boolean acceptsTarget(final Component component) {
		return component instanceof AbstractNode //
				&& sourceComponent.getClass() != component.getClass();
	}

	/**
	 * <p>
	 * edgeContains.
	 * </p>
	 *
	 * @param point
	 *            a {@link java.awt.Point} object.
	 * @return a boolean.
	 */
	public boolean edgeContains(final Point point) {
		// WTF !? line.contains(point) returns false, with the explanation:
		// "lines never contain AREAS" WTF! A point is not an area...
		// TODO - line thickness is variable!
		return line != null && (line.ptSegDistSq(point) < 5 || tip.contains(point));
	}

	/** {@inheritDoc} */
	@Override
	public void nodeRemoved(final NodeRemovalCommand e) {
		final String sourceId = getSourceId();
		final String targetId = getTargetId();
		final String nodeId = e.getNodeId();
		if (nodeId.equals(sourceId) || nodeId.equals(targetId)) {
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
	public void componentEntered(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		if (acceptsTarget(e.getComponent())) {
			setTargetComponent((AbstractNode) e.getComponent());
			setState(ComponentState.VALID);
		} else {
			setState(ComponentState.INVALID);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void componentExited(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		removeTargetComponent();
		setState(ComponentState.DEFAULT);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeMoved(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		if (sourceComponent.equals(e.getComponent())) {
			// IGNORE - Just moving around on source component.
			return;
		}
		if (hasTargetComponent() && targetComponent.equals(e.getComponent())) {
			// IGNORE - Just moving around on target component.
			return;
		}
		// e.component() may or may not be a valid target. We are not interested
		// on that. This method is not responsible for connecting to targets!
		// That is done when entering potential target components. Also, we rely
		// on target removal on exiting components. Therefore, we can safely
		// connect to the point, without being bound to any target component.
		setUnboundTarget(e.getLocation());
		repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		dispose();
	}

	/** {@inheritDoc} */
	@Override
	public void edgeFinished(final EdgeEditEvent e) {
		// FIXME - Why dose this receive its own creation event?
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		if (!acceptsTarget(e.getComponent())) {
			throw new IllegalArgumentException();
		}

		setTargetComponent((AbstractNode) e.getComponent());

		final HoverListener hoverListener = EdgeHoverListener.INSTANCE;
		// TODO - setHoverListener() -> to field.
		HoverListener.addToComponent(this, hoverListener);

		setState(ComponentState.DEFAULT);
	}

	/** {@inheritDoc} */
	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		// IGNORE - If we weren't started we would not exist...
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		// TODO - create generic removeAllListeners()
		eventBus.removeListener(EdgeCreationListener.class, this);
		eventBus.removeListener(NodeRemovalListener.class, this);
		eventBus.removeListener(NodeMotionListener.class, this);
		eventBus.removeListener(PlaceLayoutListener.class, this);
		eventBus.removeListener(TransitionLayoutListener.class, this);
		eventBus.removeListener(EdgeEditListener.class, this);
		// TODO - may require synchronization, if two removal events are fired
		// before this is properly removed from eventBus.
		final Container parent = getParent();
		parent.remove(this);
		parent.revalidate();
		parent.repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMotionCommand event) {
		final String movedNodeId = event.getNodeId();
		final String sourceId = getSourceId();
		final String targetId = getTargetId();
		if (movedNodeId.equals(sourceId) || movedNodeId.equals(targetId)) {
			// Must always reconnect to both nodes, because moving any changes
			// the edges angle.
			connectToSource();
			connectToTarget();
			repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		// TODO - use revalidate();
		// FIXME - The problem is the order of events! We must NOT
		// respond to a command, but to an event after the fact! Hence,
		// we must to create the event in addition to the command!
		reconnect();
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
		// TODO - use revalidate();
		// FIXME - The problem is the order of events! We must NOT
		// respond to a command, but to an event after the fact! Hence,
		// we must to create the event in addition to the command!
		reconnect();
	}

	/**
	 * <p>
	 * reconnect.
	 * </p>
	 */
	private void reconnect() {
		connectToSource();
		connectToTarget();
		repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void createEdge(final EdgeCreationCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void removeEdge(final EdgeRemoveCommand cmd) {
		final String edgeId = cmd.getEdgeId();
		if (this.id.equals(edgeId)) {
			dispose();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void stateChanged(final ChangeEvent e) {
		if (e.getSource() == this.style) {
			invalidate();
			repaint();
		} else {
			throw new RuntimeException("Unexpected event source " + e.getSource());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setStyle(final EdgeStyleModel style) {
		if (this.style != null) {
			this.style.removeChangeListener(this);
		}
		this.style = style;
		this.style.addChangeListener(this);
	}

}
