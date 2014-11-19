package de.markusrother.pned.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import de.markusrother.pned.gui.AbstractNode.State;
import de.markusrother.swing.DragListener;
import de.markusrother.swing.snap.SnapGridComponent;
import de.markusrother.swing.snap.SnapTarget;

/**
 * TODO - make generic of selected component type
 *
 */
public class NodeSelectionListener extends DragListener {

	private JPanel selectionPanel;

	public NodeSelectionListener() {
	}

	@Override
	public void startDrag(final Component component, final Point point) {
		final SnapGridComponent sgc = (SnapGridComponent) component;
		selectionPanel = new JPanel();
		selectionPanel.setBorder(new LineBorder(Color.CYAN, 2));
		selectionPanel.setBounds(new Rectangle(point, new Dimension()));
		selectionPanel.setOpaque(false);
		// TODO - make sure panel is on top of nodes! selection layer?
		sgc.add(selectionPanel);
		sgc.repaint();
	}

	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		final Rectangle r = selectionPanel.getBounds();
		selectionPanel.setBounds(new Rectangle(r.x, r.y, r.width + deltaX, r.height + deltaY));
	}

	@Override
	public void endDrag(final Component component, final Point point) {
		final SnapGridComponent sgc = (SnapGridComponent) component;
		final Rectangle r = selectionPanel.getBounds();
		final List<AbstractNode> nodes = collectSelectedNodes(sgc, r);
		// FIXME - Create SelectionListener(nodes), which should fire the
		// same drag event for each node in the selection as the one used
		// for single drag! Currently, the Abstract nodes are still wrapped
		// inside snap target components.
		// TODO - The entire grid needs to be selection aware, because we
		// may want to click anywhere in the non-selected area to release
		// the selection. Maybe that should go to the model.
		// FIXME - read this about layers before proceeding!
		// https://docs.oracle.com/javase/tutorial/uiswing/misc/jlayer.html#events

		// I could also create a MouseAdapter for nodes which manages state
		// and does not forward events unless state criteria is met! I LIKE
		// THIS. override addMouseListener, and andMouseMotionListener in
		// AbstractNode to intercept or create custom methods. Switch case
		// would make state handling concentrated, reducing coupling, and
		// generally quite nice. Just add listeners like crazy and let the
		// responsible instance take care of it.

		// TODO - SelectionDragListener
		final DragListener listener = new DragListener() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				super.mouseClicked(e);
				e.consume();
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				super.mouseEntered(e);
				e.consume();
			}

			@Override
			public void mouseExited(final MouseEvent e) {
				super.mouseExited(e);
				e.consume();
			}

			@Override
			public void startDrag(final Component component, final Point point) {
				// TODO
				throw new RuntimeException("TODO");
			}

			@Override
			public void onDrag(final Component component, final int deltaX, final int deltaY) {
				// TODO
				throw new RuntimeException("TODO");
			}

			@Override
			public void endDrag(final Component component, final Point point) {
				// TODO
				throw new RuntimeException("TODO");
			}
		};
		for (final AbstractNode node : nodes) {
			node.setState(State.SELECTED);
			DragListener.addToComponent(selectionPanel, listener);
		}
		sgc.remove(selectionPanel);
		// sgc.revalidate();
		sgc.repaint();
		selectionPanel = null;
	}

	private List<AbstractNode> collectSelectedNodes(final SnapGridComponent sgc, final Rectangle r) {
		// TODO - get rid of SnapTarget!
		final List<AbstractNode> selection = new LinkedList<>();
		for (final Component c : sgc.getComponents()) {
			if (c instanceof SnapTarget && r.contains(c.getLocation())) {
				final SnapTarget st = (SnapTarget) c;
				final AbstractNode node = (AbstractNode) st.getTargetComponent();
				selection.add(node);
			}
		}
		return selection;
	}
}
