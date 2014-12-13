package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;
import static de.markusrother.util.TrigUtils.getRadiansOfDelta;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

import de.markusrother.pned.events.RemoveSelectedNodesEvent;
import de.markusrother.pned.gui.Disposable;
import de.markusrother.pned.gui.events.EdgeEditEvent;
import de.markusrother.pned.gui.events.NodeCreationEvent;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.NodeRemovalEvent;
import de.markusrother.pned.gui.events.PlaceCreationRequest;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationRequest;
import de.markusrother.pned.gui.listeners.EdgeEditListener;
import de.markusrother.pned.gui.listeners.NodeListener;
import de.markusrother.pned.gui.listeners.NodeMotionListener;
import de.markusrother.swing.HoverListener;

/**
 * TODO - manage the contact-points of source component, too. Create a segment
 * which is invisible, but connected, to the source components center, avoiding
 * flickering.
 */
public class EdgeComponent extends AbstractEdgeComponent<AbstractNode, AbstractNode>
	implements
		NodeListener,
		NodeMotionListener,
		EdgeEditListener,
		Disposable {

	public EdgeStyle getStyle() {
		return style;
	}

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

	public ComponentState getState() {
		return state;
	}

	public void setState(final ComponentState state) {
		this.state = state;
	}

	private final EdgeStyle style;

	public EdgeComponent(final AbstractNode sourceComponent, final Point source, final Point target) {
		super(sourceComponent, source, target);
		this.style = EdgeStyle.DEFAULT;
		setState(ComponentState.DEFAULT);
		eventBus.addNodeListener(this);
		eventBus.addNodeMotionListener(this);
		eventBus.addEdgeEditListener(this);
	}

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

	public boolean acceptsTarget(final Component component) {
		return component instanceof AbstractNode //
				&& sourceComponent.getClass() != component.getClass();
	}

	boolean edgeContains(final Point point) {
		// WTF !? line.contains(point) returns false, with the explanation:
		// "lines never contain AREAS" WTF! A point is not an area...
		// TODO - line thickness is variable!
		return line != null && (line.ptSegDistSq(point) < 5 || tip.contains(point));
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		// IGNORE
	}

	@Override
	public void nodeCreated(final NodeCreationEvent e) {
		// IGNORE
	}

	@Override
	public void createPlace(final PlaceCreationRequest e) {
		// IGNORE
	}

	@Override
	public void createTransition(final TransitionCreationRequest e) {
		// IGNORE
	}

	@Override
	public void nodeRemoved(final NodeRemovalEvent e) {
		final AbstractNode node = e.getNode();
		if (node.equals(sourceComponent) || node.equals(targetComponent)) {
			dispose();
		}
	}

	@Override
	public void removeSelectedNodes(final RemoveSelectedNodesEvent e) {
		// IGNORE
	}

	@Override
	public void targetComponentEntered(final EdgeEditEvent e) {
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

	@Override
	public void targetComponentExited(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		removeTargetComponent();
		setState(ComponentState.DEFAULT);
	}

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

	@Override
	public void edgeCancelled(final EdgeEditEvent e) {
		if (e.getEdge() != this) {
			// IGNORE - Not interested in other edges events.
			return;
		}
		dispose();
	}

	@Override
	public void edgeFinished(final EdgeEditEvent e) {
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

	@Override
	public void edgeStarted(final EdgeEditEvent e) {
		// IGNORE - If we weren't started we would not exist...
	}

	@Override
	public void dispose() {
		// TODO - create generic removeAllListeners()
		eventBus.removeNodeListener(this);
		eventBus.removeNodeMotionListener(this);
		eventBus.removeEdgeEditListener(this);
		// TODO - may require synchronization, if two removal events are fired
		// before this is properly removed from eventBus.
		getParent().remove(this);
	}

	@Override
	public void nodeMoved(final NodeMovedEvent event) {
		if (event.getNodes().contains(sourceComponent) //
				|| event.getNodes().contains(targetComponent)) {
			connectToSource();
			connectToTarget();
			repaint();
		}
	}

}
