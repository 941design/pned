package de.markusrother.pned.gui.components;

import java.awt.Component;

import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>Abstract ComponentResizer class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
abstract class ComponentResizer
	implements
		ChangeListener {

	/** Constant <code>enlargementFactor=1.001</code> */
	protected static final double enlargementFactor = 1.001;
	/** Constant <code>thresholdRatio=0.9</code> */
	protected static final double thresholdRatio = 0.9;

	protected final Component component;

	/**
	 * <p>Constructor for ComponentResizer.</p>
	 *
	 * @param component a {@link java.awt.Component} object.
	 */
	ComponentResizer(final Component component) {
		this.component = component;
	}

	/** {@inheritDoc} */
	@Override
	public void stateChanged(final ChangeEvent e) {
		if (e.getSource() instanceof BoundedRangeModel) {
			stateChanged((BoundedRangeModel) e.getSource());
		} else {
			throw new IllegalArgumentException("Can only listen to state changes of "
					+ BoundedRangeModel.class.getSimpleName());
		}
	}

	/**
	 * <p>stateChanged.</p>
	 *
	 * @param source a {@link javax.swing.BoundedRangeModel} object.
	 */
	protected abstract void stateChanged(BoundedRangeModel source);

}
