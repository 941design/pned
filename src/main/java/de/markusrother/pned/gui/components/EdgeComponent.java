package de.markusrother.pned.gui.components;

import static de.markusrother.util.TrigUtils.getRadiansOfDelta;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

import de.markusrother.pned.core.commands.NodeMotionCommand;
import de.markusrother.pned.core.commands.NodeRemovalCommand;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.listeners.NodeMotionListener;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.layout.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.layout.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.layout.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.layout.listeners.EdgeLayoutListener;
import de.markusrother.pned.gui.layout.listeners.PlaceLayoutListener;
import de.markusrother.pned.gui.layout.listeners.TransitionLayoutListener;
import de.markusrother.pned.gui.layout.style.EdgeStyle;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeRemovalListener;
import de.markusrother.swing.HoverListener;

/**
 * TODO - manage the contact-points of source component, too. Create a segment
 * which is invisible, but connected, to the source components center, avoiding
 * flickering.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EdgeComponent extends AbstractEdgeComponent<AbstractNode, AbstractNode>
	implements
		NodeRemovalListener,
		NodeMotionListener,
		EdgeEditListener,
		EdgeLayoutListener,
		PlaceLayoutListener,
		TransitionLayoutListener,
		Disposable {

	/** Constant <code>NO_TARGET_COMPONENT</code> */
	private static final AbstractNode NO_TARGET_COMPONENT = null;
	/** Constant <code>NO_SOURCE</code> */
	private static final Point NO_SOURCE = new Point();
	/** Constant <code>NO_TARGET</code> */
	private static final Point NO_TARGET = new Point();

	/**
	 * <p>
	 * Getter for the field <code>style</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.layout.style.EdgeStyle} object.
	 */
	public EdgeStyle getStyle() {
		return style;
	}

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

	/**
	 * <p>
	 * createHoverListener.
	 * </p>
	 *
	 * @param edge
	 *            a {@link de.markusrother.pned.gui.components.EdgeComponent}
	 *            object.
	 * @return a {@link de.markusrother.swing.HoverListener} object.
	 */
	private static HoverListener createHoverListener(final EdgeComponent edge) {

		// TODO - extract class!

		return new HoverListener() {

			@Override
			protected boolean inHoverArea(final Point p) {
				return edge.edgeContains(p);
			}

			@Override
			protected void startHover(final Component component) {
				edge.setState(ComponentState.HOVER);
			}

			@Override
			protected void endHover(final Component component) {
				edge.setState(ComponentState.DEFAULT);
			}
		};
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
	private final EdgeStyle style;

	/**
	 * <p>
	 * Constructor for EdgeComponent.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
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
	public EdgeComponent(final EventBus eventBus, final AbstractNode sourceComponent, final Point source,
			final Point target) {
		this(eventBus, sourceComponent, NO_TARGET_COMPONENT, source, target);
	}

	/**
	 * <p>
	 * Constructor for EdgeComponent.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param sourceComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 * @param targetComponent
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 */
	public EdgeComponent(final EventBus eventBus, final AbstractNode sourceComponent, final AbstractNode targetComponent) {
		this(eventBus, sourceComponent, targetComponent, NO_SOURCE, NO_TARGET);
		reconnect();
	}

	/**
	 * <p>
	 * Constructor for EdgeComponent.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
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
	 */
	public EdgeComponent(final EventBus eventBus, final AbstractNode sourceComponent,
			final AbstractNode targetComponent, final Point source, final Point target) {
		super(sourceComponent, targetComponent, source, target);
		this.eventBus = eventBus;
		this.style = EdgeStyle.DEFAULT;
		setState(ComponentState.DEFAULT);
		eventBus.addListener(NodeRemovalListener.class, this);
		eventBus.addListener(NodeMotionListener.class, this);
		eventBus.addListener(EdgeLayoutListener.class, this);
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
		g2.setStroke(style.getLineStroke());
		line = new Line2D.Double(source, target);
		g2.draw(line);

		// Draw tip:
		g2.translate(target.x, target.y);
		g2.rotate(getRadiansOfDelta(source, target));
		g2.fill(style.getTip());

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
	boolean edgeContains(final Point point) {
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

		final HoverListener hoverListener = createHoverListener(this);
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
		eventBus.removeListener(NodeRemovalListener.class, this);
		eventBus.removeListener(NodeMotionListener.class, this);
		eventBus.removeListener(EdgeLayoutListener.class, this);
		eventBus.removeListener(PlaceLayoutListener.class, this);
		eventBus.removeListener(TransitionLayoutListener.class, this);
		eventBus.removeListener(EdgeEditListener.class, this);
		// TODO - may require synchronization, if two removal events are fired
		// before this is properly removed from eventBus.
		getParent().remove(this);
	}

	/** {@inheritDoc} */
	@Override
	public void nodeMoved(final NodeMotionCommand event) {
		boolean repaint = false;
		final String sourceId = getSourceId();
		final String targetId = getTargetId();
		if (event.getNodeId().contains(sourceId)) {
			connectToSource();
			repaint = true;
		}
		if (event.getNodeId().contains(targetId)) {
			connectToTarget();
			repaint = true;
		}
		if (repaint) {
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
	public void setSize(final EdgeLayoutCommand cmd) {
		final int extent = cmd.getSize();
		style.setTipSize(extent);
		repaint();
	}

}
