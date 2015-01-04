package de.markusrother.pned.gui.components;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoundedRangeModel;

/**
 * <p>VerticalComponentResizer class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class VerticalComponentResizer extends ComponentResizer {

	VerticalComponentResizer(final Component component) {
		super(component);
	}

	/** {@inheritDoc} */
	@Override
	protected void stateChanged(final BoundedRangeModel model) {
		final double extent = model.getExtent();
		final double maximum = model.getMaximum();
		final double value = model.getValue();
		// Must use preferredSize, because dimensions are initialized
		// with zero.
		final Dimension preferredSize = component.getPreferredSize();
		final double height = preferredSize.getHeight();
		final double width = preferredSize.getWidth();
		final double ratio = (value + extent) / maximum;
		if (ratio > thresholdRatio) {
			// TODO - calculate biggest increment possible!
			component.setPreferredSize(new Dimension((int) width, (int) (height * enlargementFactor)));
		}
	}

}
