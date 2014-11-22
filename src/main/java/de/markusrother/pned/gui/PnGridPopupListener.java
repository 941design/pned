package de.markusrother.pned.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.markusrother.pned.gui.PnGridPanel.State;
import de.markusrother.swing.PopupListener;

public class PnGridPopupListener extends PopupListener {

	final PnGridPanel pnGridPanel;
	Point popupPoint;

	PnGridPopupListener(final PnGridPanel pnGridPanel) {
		this.pnGridPanel = pnGridPanel;
	}

	@Override
	public void popup(final MouseEvent e) {
		popupPoint = e.getPoint();
		final JPopupMenu popup = new JPopupMenu();
		if (pnGridPanel.hasState(State.MULTISELECTION)) {
			addNodeSelectionRemovalItem(popup);
		}
		addPlaceCreationItem(popup);
		addTransitionCreationItem(popup);
		addNodeCreationToggleItem(popup);
		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	private void addNodeCreationToggleItem(final JPopupMenu popup) {
		// TODO - use config
		final String nodeType = pnGridPanel.hasState(State.TRANSITION_CREATION) ? "place" : "transition";
		final JMenuItem item = new JMenuItem("Set default node type to " + nodeType);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				pnGridPanel.toggleNodeCreationMode();
			}
		});
		popup.add(item);
	}

	private void addPlaceCreationItem(final JPopupMenu popup) {
		final JMenuItem item = new JMenuItem("Create place");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				pnGridPanel.createPlace(popupPoint);
				// TODO - use event bus!
				// eventBus.nodeCreated(new NodeCreationEvent(this, node,
				// nodeIdPromise));
			}
		});
		popup.add(item);
	}

	private void addTransitionCreationItem(final JPopupMenu popup) {
		final JMenuItem item = new JMenuItem("Create transition");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				pnGridPanel.createTransition(popupPoint);
				// TODO - use event bus!
			}
		});
		popup.add(item);
	}

	private void addNodeSelectionRemovalItem(final JPopupMenu popup) {
		final JMenuItem item = new JMenuItem("Remove selected nodes");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				pnGridPanel.removeSelectedNodes();
				// TODO - use event bus!
			}
		});
		popup.add(item);
	}

}
