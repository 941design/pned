package de.markusrother.pned.gui.components;

import static de.markusrother.swing.SwingUtils.getCenter;
import static de.markusrother.util.MathUtils.getRadiansOfDelta;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Line2D;

import javax.swing.event.ChangeEvent;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.EdgeCreationCommand;
import de.markusrother.pned.control.commands.EdgeCreationListener;
import de.markusrother.pned.control.commands.NodeMotionCommand;
import de.markusrother.pned.control.commands.NodeMotionListener;
import de.markusrother.pned.control.commands.NodeRemovalCommand;
import de.markusrother.pned.gui.components.listeners.EdgeHoverListener;
import de.markusrother.pned.gui.components.listeners.NodeRemovalListener;
import de.markusrother.pned.gui.control.commands.EdgeRemoveCommand;
import de.markusrother.pned.gui.control.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.control.commands.PlaceLayoutListener;
import de.markusrother.pned.gui.control.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.control.commands.TransitionLayoutListener;
import de.markusrother.pned.gui.control.events.EdgeEditEvent;
import de.markusrother.pned.gui.control.events.EdgeEditListener;
import de.markusrother.pned.gui.control.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.core.Stylable;
import de.markusrother.pned.gui.core.model.EdgeStyleModel;
import de.markusrother.swing.HoverListener;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * TODO
 * <ul>
 * <li>It would be OK and better to couple the EdgeComponent with the
 * EdgeCreator instead of listening to the EventBus (EdgeEditListener)!</li>
 * <li>Create a segment which is invisible, but connected, to the source
 * components center, avoiding flickering.</li>
 * <li>create subclass for unfinished EdgeComponent</li>
 * </ul>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeComponent extends AbstractEdgeComponent<AbstractNodeComponent, AbstractNodeComponent>
	implements
		NodeRemovalListener,
		NodeMotionListener,
		EdgeCreationListener,
		EdgeEditListener,
		PlaceLayoutListener,
		TransitionLayoutListener,
		Stylable<EdgeStyleModel>,
		JsonSerializable,
		Disposable,
		ComponentListener {

	/** Constant <code>NO_TARGET_COMPONENT</code> */
	private static final AbstractNodeComponent NO_TARGET_COMPONENT = null;
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
	 * @param style
	 *            a {@link de.markusrother.pned.gui.core.model.EdgeStyleModel}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param source
	 *            a {@link java.awt.Point} object.
	 * @param target
	 *            a {@link java.awt.Point} object.
	 */
	public EdgeComponent(final EventBus eventBus, final EdgeStyleModel style,
			final AbstractNodeComponent sourceComponent, final Point source, final Point target) {
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
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param targetComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param id
	 *            a {@link java.lang.String} object.
	 * @param style
	 *            a {@link de.markusrother.pned.gui.core.model.EdgeStyleModel}
	 *            object.
	 */
	public EdgeComponent(final EventBus eventBus, final String id, final EdgeStyleModel style,
			final AbstractNodeComponent sourceComponent, final AbstractNodeComponent targetComponent) {
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
	 * @param style
	 *            a {@link de.markusrother.pned.gui.core.model.EdgeStyleModel}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param targetComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param targetComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param sourceComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param source
	 *            a {@link java.awt.Point} object.
	 * @param targetComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param targetComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param targetComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param targetComponent
	 *            a
	 *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
	 *            object.
	 * @param target
	 *            a {@link java.awt.Point} object.
	 * @param id
	 *            a {@link java.lang.String} object.
	 */
	private EdgeComponent(final EventBus eventBus, final String id, final EdgeStyleModel style,
			final AbstractNodeComponent sourceComponent, final AbstractNodeComponent targetComponent,
			final Point source, final Point target) {
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
		return component instanceof AbstractNodeComponent //
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
			setTargetComponent((AbstractNodeComponent) e.getComponent());
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
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		if (!acceptsTarget(e.getComponent())) {
			throw new IllegalArgumentException();
		}

		setTargetComponent((AbstractNodeComponent) e.getComponent());

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
			reconnectToSource();
			reconnectToTarget();
			repaint();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * TODO - The problem is the order of events! We must respond after the node
	 * responded to the resizing, otherwise it stays without effect! In practice
	 * this may not be a problem, because nodes are always created before edges,
	 * and hence precede in the listeners list, but that is coincidence and
	 * fragile.
	 */
	@Override
	public void setSize(final TransitionLayoutCommand cmd) {
		reconnect();
	}

	/**
	 * {@inheritDoc}
	 *
	 * TODO - The problem is the order of events! We must respond after the node
	 * responded to the resizing, otherwise it stays without effect! In practice
	 * this may not be a problem, because nodes are always created before edges,
	 * and hence precede in the listeners list, but that is coincidence and
	 * fragile.
	 */
	@Override
	public void setSize(final PlaceLayoutCommand cmd) {
		reconnect();
	}

	/**
	 * <p>
	 * reconnect.
	 * </p>
	 */
	private void reconnect() {
		reconnectToSource();
		reconnectToTarget();
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

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder builder = new JsonBuilder();
		return builder.append("id", id) //
				.append("sourceId", getSourceId()) //
				.append("targetId", getTargetId()) //
				.toString();
	}

	/** {@inheritDoc} */
	@Override
	public void componentResized(final ComponentEvent e) {
		final Component component = e.getComponent();
		final Container parent = getParent();
		if (component == parent) {
			// We must adjust our bounds, otherwise the edge component does not
			// show when source or target are dragged out of bounds
			setBounds(new Rectangle(new Point(0, 0), parent.getPreferredSize()));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void componentMoved(final ComponentEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void componentShown(final ComponentEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void componentHidden(final ComponentEvent e) {
		// IGNORE
	}
}
