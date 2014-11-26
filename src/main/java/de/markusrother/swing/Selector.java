package de.markusrother.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Class used to mark multiple components a.k.a select them for future processing.
 * 
 * This listener must not be registered at the components themselves, but for
 * the container that contains the components to be selected. Therefore, this
 * class does NOT handle selection by single click on one of those components!
 */
public abstract class Selector<T extends Selectable> extends DragDropListener<Container> {

	private final Class<T> type;

	private JPanel selectionPanel;
	private Point dragOrigin;
	private Collection<T> currentSelection;

	public Selector(final Class<T> type) {
		super(Container.class);
		this.type = type;
	}

	private Collection<T> getCurrentSelection() {
		// OBSOLETE - Currently not needed, maybe nice to have.
		return currentSelection;
	}

	private void setCurrentSelection(final Collection<T> currentSelection) {
		// OBSOLETE - Currently not needed, maybe nice to have.
		this.currentSelection = currentSelection;
	}

	private JPanel createSelectionPanel(final Point origin) {
		// TODO - either make abstract or add layout manager!
		final JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.CYAN, 2));
		panel.setBounds(new Rectangle(origin, new Dimension()));
		panel.setOpaque(false);
		return panel;
	}

	private Collection<T> collectSelectedItems(final Container container, final Rectangle r) {
		final Collection<T> selection = new LinkedList<>();
		for (final Component c : container.getComponents()) {
			if (type.isInstance(c)) {
				final T item = type.cast(c);
				if (item.isContained(r)) {
					selection.add(item);
				}
			}
		}
		return selection;
	}

	@Override
	public void startDrag(final Container container, final Point origin) {
		this.dragOrigin = origin;
		this.currentSelection = Collections.emptyList();
		this.selectionPanel = createSelectionPanel(origin);
		container.add(selectionPanel);
		container.repaint();
		startedSelection();
	}

	@Override
	public void onDrag(final Container container, final int deltaX, final int deltaY) {
		final Rectangle oldBounds = selectionPanel.getBounds();
		final Rectangle newBounds = getNewSelectionBounds(oldBounds, deltaX, deltaY);
		selectionPanel.setBounds(newBounds);
		adjustSelectedItems(container, newBounds);
	}

	Rectangle getNewSelectionBounds(final Rectangle r, final int deltaX, final int deltaY) {
		// TODO, TEST - make this testable, test transitivity and commutativity!

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
		return new Rectangle(x, y, w, h);
	}

	private void adjustSelectedItems(final Container container, final Rectangle selection) {

		final Collection<T> items = collectSelectedItems(container, selection);

		// Nodes that were not selected before but are now selected:
		final Collection<T> selectedItems = new ArrayList<>(items);
		selectedItems.removeAll(currentSelection);
		if (selectedItems.size() > 0) {
			addedToSelection(selectedItems);
		}

		// Nodes that were but are no longer selected:
		final Collection<T> unselectedItems = new ArrayList<>(currentSelection);
		unselectedItems.removeAll(items);
		if (unselectedItems.size() > 0) {
			removedFromSelection(unselectedItems);
		}

		currentSelection = items;
	}

	public abstract void startedSelection();

	public abstract void addedToSelection(Collection<T> items);

	public abstract void removedFromSelection(Collection<T> items);

	public abstract void finishedSelection(Collection<T> items);

	public static Rectangle resizeDragPanelBounds(final Rectangle r, final int deltaX, final int deltaY) {
		// OBSOLETE
		final int x = -deltaX > r.width ? r.x + r.width + deltaX : r.x;
		final int y = -deltaY > r.height ? r.y + r.height + deltaY : r.y;
		final int w = Math.abs(r.width + deltaX);
		final int h = Math.abs(r.height + deltaY);
		return new Rectangle(x, y, w, h);
	}

	@Override
	public void endDrag(final Container container, final Point point) {
		container.remove(selectionPanel);
		container.repaint();
		finishedSelection(currentSelection);
		this.selectionPanel = null;
		this.dragOrigin = null;
		this.currentSelection = null;
	}

}
