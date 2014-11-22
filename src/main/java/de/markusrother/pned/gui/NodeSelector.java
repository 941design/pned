package de.markusrother.pned.gui;

import static de.markusrother.pned.gui.NodeSelectionEvent.Type.SELECTED;
import static de.markusrother.pned.gui.NodeSelectionEvent.Type.UNSELECTED;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import de.markusrother.swing.DragDropListener;
import de.markusrother.swing.snap.SnapGridComponent;

/**
 * TODO - make generic of selected component type
 * 
 * FIXME - selection only works from top left to bottom right!
 * 
 * Class used to mark nodes a.k.a select them for future processing.
 */
public class NodeSelector extends DragDropListener {

	public List<AbstractNode> getCurrentSelection() {
		return currentSelection;
	}

	public void setCurrentSelection(final List<AbstractNode> currentSelection) {
		this.currentSelection = currentSelection;
	}

	private JPanel selectionPanel;
	private Point dragOrigin;
	private List<AbstractNode> currentSelection;

	private JPanel createSelectionPanel(final Point origin) {
		final JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.CYAN, 2));
		panel.setBounds(new Rectangle(origin, new Dimension()));
		panel.setOpaque(false);
		return panel;
	}

	private List<AbstractNode> collectSelectedNodes(final Container container, final Rectangle r) {
		final List<AbstractNode> selection = new LinkedList<>();
		for (final Component c : container.getComponents()) {
			if (c instanceof AbstractNode && r.contains(((AbstractNode) c).getCenter())) {
				final AbstractNode node = (AbstractNode) c;
				selection.add(node);
			}
		}
		return selection;
	}

	@Override
	public void startDrag(final Component component, final Point origin) {
		this.dragOrigin = origin;
		this.currentSelection = Collections.emptyList();
		this.selectionPanel = createSelectionPanel(origin);
		final SnapGridComponent sgc = (SnapGridComponent) component;
		// TODO - make sure panel is on top of nodes! selection layer?
		sgc.add(selectionPanel);
		sgc.repaint();
	}

	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		// TODO, TEST - make this testable, test transitivity and commutativity!
		final Rectangle r = selectionPanel.getBounds();

		final boolean isDragRight = deltaX >= 0;
		final boolean isDragLeft = !isDragRight;
		final boolean isDragDown = deltaY >= 0;
		final boolean isDragUp = !isDragDown;
		final boolean isOriginX = r.x == dragOrigin.x;
		final boolean isOriginY = r.y == dragOrigin.y;
		final boolean isCrossingOriginX = isDragRight && !isOriginX && r.width < deltaX //
				|| isDragLeft && isOriginX && r.width < -deltaX;
		final boolean isCrossingOriginY = isDragDown && !isOriginY && r.height < deltaY //
				|| isDragUp && isOriginY && r.height < -deltaY;

		final int x, w;
		if (isDragRight && isOriginX) {
			x = dragOrigin.x;
			w = r.width + deltaX;
		} else if (isDragRight && isCrossingOriginX) {
			x = dragOrigin.x;
			w = -(r.width - deltaX);
		} else if (isDragRight && !isCrossingOriginX) {
			x = r.x + deltaX;
			w = r.width - deltaX;
		} else if (isDragLeft && isOriginX && !isCrossingOriginX) {
			x = dragOrigin.x;
			w = r.width + deltaX;
		} else if (isDragLeft && !isOriginX) {
			x = r.x + deltaX;
			w = r.width - deltaX;
		} else if (isDragLeft && isCrossingOriginX) {
			x = dragOrigin.x + r.width + deltaX;
			w = -(r.width + deltaX);
		} else {
			throw new IllegalStateException();
		}

		final int y, h;
		if (isDragDown && isOriginY) {
			y = dragOrigin.y;
			h = r.height + deltaY;
		} else if (isDragDown && isCrossingOriginY) {
			y = dragOrigin.y;
			h = -(r.height - deltaY);
		} else if (isDragDown && !isCrossingOriginY) {
			y = r.y + deltaY;
			h = r.height - deltaY;
		} else if (isDragUp && isOriginY && !isCrossingOriginY) {
			y = dragOrigin.y;
			h = r.height + deltaY;
		} else if (isDragUp && !isOriginY) {
			y = r.y + deltaY;
			h = r.height - deltaY;
		} else if (isDragUp && isCrossingOriginY) {
			y = dragOrigin.y + r.height + deltaY;
			h = -(r.height + deltaY);
		} else {
			throw new IllegalStateException();
		}
		final Rectangle selection = new Rectangle(x, y, w, h);
		selectNodes(component, selection);
		selectionPanel.setBounds(selection);
	}

	private void selectNodes(final Component component, final Rectangle selection) {
		final SnapGridComponent sgc = (SnapGridComponent) component;
		final List<AbstractNode> nodes = collectSelectedNodes(sgc, selection);

		// Nodes that were not selected before but are now selected:
		final List<AbstractNode> selectedNodes = new ArrayList<>(nodes);
		selectedNodes.removeAll(currentSelection);
		if (selectedNodes.size() > 0) {
			PnGridPanel.eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(SELECTED, sgc, selectedNodes));
		}

		// Nodes that were but are no longer selected:
		final List<AbstractNode> unselectedNodes = new ArrayList<>(currentSelection);
		unselectedNodes.removeAll(nodes);
		if (unselectedNodes.size() > 0) {
			PnGridPanel.eventBus.fireNodeSelectionEvent(new NodeSelectionEvent(UNSELECTED, sgc, unselectedNodes));
		}

		currentSelection = nodes;
	}

	static Rectangle resizeDragPanelBounds(final Rectangle r, final int deltaX, final int deltaY) {
		final int x = -deltaX > r.width ? r.x + r.width + deltaX : r.x;
		final int y = -deltaY > r.height ? r.y + r.height + deltaY : r.y;
		final int w = Math.abs(r.width + deltaX);
		final int h = Math.abs(r.height + deltaY);
		return new Rectangle(x, y, w, h);
	}

	@Override
	public void endDrag(final Component component, final Point point) {
		// Now we have a selection, and need to do something with it. Obtaining
		// the selection, altered global state. Many other actions have to
		// respect that we are now in selection mode, e.g. starting another, new
		// selection, cancelling selection by clicking somewhere. The latter
		// event is NOT likely to bubble back to this selection.
		// From this point of view, a number of unknown components, or even more
		// general objects exist, that may cancel the selection. The decision to
		// cancel a selection created here should also remain here. The question
		// then is, how to receive those possible cancellation events without
		// coupling all components with each other.

		// FIXME - Create SelectionListener(nodes), which should fire the
		// same drag event for each node in the selection as the one used
		// for single drag! Currently, the Abstract nodes are still wrapped
		// inside snap target components.
		// TODO - The entire grid needs to be selection aware, because we
		// may want to click anywhere in the non-selected area to release
		// the selection. Maybe that should go to the model.
		// @see EventListenerList

		// I could also create a MouseAdapter for nodes which manages state
		// and does not forward events unless state criteria is met! I LIKE
		// THIS. override addMouseListener, and andMouseMotionListener in
		// AbstractNode to intercept or create custom methods. Switch case
		// would make state handling concentrated, reducing coupling, and
		// generally quite nice. Just add listeners like crazy and let the
		// responsible instance take care of it. The event bus could be a
		// singleton.
		final Container container = (Container) component;
		container.remove(selectionPanel);
		container.repaint();
		this.selectionPanel = null;
		this.dragOrigin = null;
		this.currentSelection = null;
	}
}
