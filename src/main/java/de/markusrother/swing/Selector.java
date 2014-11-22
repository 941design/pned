package de.markusrother.swing;

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

/**
 * TODO - make generic of selected component type
 * 
 * Class used to mark nodes a.k.a select them for future processing.
 */
public abstract class Selector<T extends Selectable> extends DragDropListener {

	private final Class<T> type;

	public Selector(final Class<T> type) {
		this.type = type;
	}

	private List<T> getCurrentSelection() {
		return currentSelection;
	}

	private void setCurrentSelection(final List<T> currentSelection) {
		this.currentSelection = currentSelection;
	}

	private JPanel selectionPanel;
	private Point dragOrigin;
	private List<T> currentSelection;

	private Container expectContainer(final Component component) {
		try {
			return (Container) component;
		} catch (final ClassCastException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	private JPanel createSelectionPanel(final Point origin) {
		// TODO - either make abstract or add layout manager!
		final JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.CYAN, 2));
		panel.setBounds(new Rectangle(origin, new Dimension()));
		panel.setOpaque(false);
		return panel;
	}

	private List<T> collectSelectedItems(final Container container, final Rectangle r) {
		final List<T> selection = new LinkedList<>();
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
	public void startDrag(final Component component, final Point origin) {
		final Container container = expectContainer(component);
		this.dragOrigin = origin;
		this.currentSelection = Collections.emptyList();
		this.selectionPanel = createSelectionPanel(origin);
		container.add(selectionPanel);
		container.repaint();
	}

	@Override
	public void onDrag(final Component component, final int deltaX, final int deltaY) {
		final Rectangle oldBounds = selectionPanel.getBounds();
		final Rectangle newBounds = getNewSelectionBounds(oldBounds, deltaX, deltaY);
		selectionPanel.setBounds(newBounds);
		adjustSelectedItems(component, newBounds);
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

	private void adjustSelectedItems(final Component component, final Rectangle selection) {
		final Container container = expectContainer(component);

		final List<T> items = collectSelectedItems(container, selection);

		// Nodes that were not selected before but are now selected:
		final List<T> selectedItems = new ArrayList<>(items);
		selectedItems.removeAll(currentSelection);
		if (selectedItems.size() > 0) {
			addedToSelection(selectedItems);
		}

		// Nodes that were but are no longer selected:
		final List<T> unselectedItems = new ArrayList<>(currentSelection);
		unselectedItems.removeAll(items);
		if (unselectedItems.size() > 0) {
			removedFromSelection(unselectedItems);
		}

		currentSelection = items;
	}

	public abstract void addedToSelection(List<T> items);

	public abstract void removedFromSelection(List<T> items);

	public static Rectangle resizeDragPanelBounds(final Rectangle r, final int deltaX, final int deltaY) {
		// OBSOLETE
		final int x = -deltaX > r.width ? r.x + r.width + deltaX : r.x;
		final int y = -deltaY > r.height ? r.y + r.height + deltaY : r.y;
		final int w = Math.abs(r.width + deltaX);
		final int h = Math.abs(r.height + deltaY);
		return new Rectangle(x, y, w, h);
	}

	@Override
	public void endDrag(final Component component, final Point point) {
		final Container container = (Container) component;
		container.remove(selectionPanel);
		container.repaint();
		this.selectionPanel = null;
		this.dragOrigin = null;
		this.currentSelection = null;
	}
}
