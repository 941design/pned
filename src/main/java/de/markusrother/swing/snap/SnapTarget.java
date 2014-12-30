package de.markusrother.swing.snap;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JLayeredPane;

import de.markusrother.swing.DragDropAdapter;
import de.markusrother.swing.DragDropListener;

/**
 * <p>SnapTarget class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class SnapTarget extends JLayeredPane {

	/** Constant <code>NOT_VISIBLE=false</code> */
	private static final boolean NOT_VISIBLE = false;

	// Maybe OBSOLETE - Probably, all we have to do is to add the component
	// first, and then add snap points. We could then use our own LayoutManager,
	// and may not have to worry about layers. However, a different problem: to
	// manage this pane's bounds, we always need to query all points for their
	// preferred bounds.
	// Also, we need a single LayoutManager at the same time to position this
	// pane (and the snap Point layer) as well as all points, because for
	// positioning this, we need the current location of the targetComponent,
	// which most not change absolutely, but will change relatively when
	// enlarging this. setLocation() sets the position in the parent container.
	private final SnapPointLayer snapPointLayer;

	private final Component targetComponent;

	private Point dragStart;

	/**
	 * <p>Constructor for SnapTarget.</p>
	 *
	 * @param targetComponent a {@link java.awt.Component} object.
	 */
	public SnapTarget(final Component targetComponent) {

		this.targetComponent = targetComponent;

		// TODO - Adapt bounds lazily when adding snapPoints.

		final Dimension componentDimension = targetComponent.getPreferredSize();
		final Dimension paddedDimension = new Dimension(componentDimension.width + 10, componentDimension.height + 10);
		final Point origin = new Point(0, 0);
		final Point componentOrigin = new Point(5, 5);

		// Adding component layer:
		targetComponent.setBounds(new Rectangle(componentOrigin, componentDimension));
		// TODO - decouple listeners from this! And Create a draggable subclass!
		final DragDropListener<Component> l = new DragDropAdapter() {

			@Override
			public void onDrag(final Component component, final int deltaX, final int deltaY) {
				final Rectangle r = getBounds();
				r.translate(deltaX, deltaY);
				setBounds(r);
				fireComponentMovedEvent(deltaX, deltaY);
			}

		};
		targetComponent.addMouseListener(l);
		targetComponent.addMouseMotionListener(l);
		add(targetComponent, new Integer(0));

		// Adding snap point layer:
		snapPointLayer = new SnapPointLayer(this);
		snapPointLayer.setBounds(new Rectangle(origin, paddedDimension));
		add(snapPointLayer, new Integer(1));

		// OBSOLETE, TEST - Use to check whether the actual bounds enlarge when
		// adding snap points.
		// snapPointLayer.setBorder(new LineBorder(Color.GREEN));
		setOpaque(false);
	}

	/**
	 * <p>fireComponentMovedEvent.</p>
	 *
	 * @param deltaX a int.
	 * @param deltaY a int.
	 */
	void fireComponentMovedEvent(final int deltaX, final int deltaY) {
		for (final SnapPointComponent spc : getSnapPointComponents()) {
			// TODO - we could as well look up the listeners, here!
			spc.fireComponentMovedEvent(deltaX, deltaY);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return snapPointLayer.getPreferredSize();
	}

	/**
	 * <p>createSnapPoint.</p>
	 *
	 * @param constraint a {@link java.lang.String} object.
	 * @return a {@link java.awt.Component} object.
	 */
	public Component createSnapPoint(final String constraint) {
		return createSnapPoint(constraint, NOT_VISIBLE);
	}

	/**
	 * <p>createSnapPoint.</p>
	 *
	 * @param constraint a {@link java.lang.String} object.
	 * @param visible a boolean.
	 * @return a {@link java.awt.Component} object.
	 */
	public Component createSnapPoint(final String constraint, final boolean visible) {
		// FIXME - Create snapPointComponent here, query it for its
		// preferredBounds, then resize layers, if necessary!
		return snapPointLayer.createSnapPoint(constraint, visible);
	}

	/**
	 * <p>showSnapPoints.</p>
	 */
	public void showSnapPoints() {
		snapPointLayer.showSnapPoints();
	}

	/**
	 * <p>getSnapPoints.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<SnapPoint> getSnapPoints() {
		return snapPointLayer.getSnapPoints();
	}

	/**
	 * <p>getSnapPointComponents.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	private List<SnapPointComponent> getSnapPointComponents() {
		return snapPointLayer.getSnapPointComponents();
	}

	/**
	 * <p>Getter for the field <code>targetComponent</code>.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	public Component getTargetComponent() {
		return targetComponent;
	}

	/**
	 * <p>createSnapPointComponent.</p>
	 *
	 * @param constraint a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.swing.snap.SnapPointComponent} object.
	 */
	public SnapPointComponent createSnapPointComponent(final String constraint) {
		return new SnapPointComponent();
		// snapPointComponent.addMouseListener(l);
		// snapPointComponent.addMouseMotionListener(l);
		// return snapPointComponent;
	}

}
