package de.markusrother.pned.gui;

import java.awt.LayoutManager;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public abstract class AbstractNode extends JPanel {

	public AbstractNode(final LayoutManager layoutManager) {
		super(layoutManager);
	}

	public AbstractNode() {
		super();
	}

	/**
	 * @param theta
	 * @return A Point on the boundary of this.getShape().
	 */
	public abstract Point2D getIntersectionWithBounds(final double theta);

	abstract Shape getShape();

}
