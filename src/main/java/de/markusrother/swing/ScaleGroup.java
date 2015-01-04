package de.markusrother.swing;

import static de.markusrother.util.Patterns.intPattern;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * ScaleGroup class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class ScaleGroup extends JPanel
	implements
		ChangeListener,
		TextListener {

	public static enum Orientation {
		HORIZONTAL(BoxLayout.X_AXIS),
		VERTICAL(BoxLayout.Y_AXIS);

		public final int boxLayoutConstant;

		private Orientation(final int boxLayoutConstant) {
			this.boxLayoutConstant = boxLayoutConstant;
		}
	}

	private final JLabel jLabel;
	private final JSlider jSlider;
	private final CheckedTextField textField;

	/**
	 * <p>
	 * Constructor for ScaleGroup.
	 * </p>
	 *
	 * @param label
	 *            a {@link java.lang.String} object.
	 * @param orientation
	 *            a {@link de.markusrother.swing.ScaleGroup.Orientation} object.
	 */
	public ScaleGroup(final String label, final Orientation orientation) {
		this(label, orientation, new DefaultBoundedRangeModel());
	}

	/**
	 * <p>
	 * Constructor for ScaleGroup.
	 * </p>
	 *
	 * @param label
	 *            a {@link java.lang.String} object.
	 * @param orientation
	 *            a {@link de.markusrother.swing.ScaleGroup.Orientation} object.
	 * @param model
	 *            a {@link javax.swing.BoundedRangeModel} object.
	 */
	public ScaleGroup(final String label, final Orientation orientation, final BoundedRangeModel model) {

		// TODO - Create LogarithmicRangeModel
		// TODO - Create own LayoutManager, try BorderLayout, or use
		// EmptyBorders for padding.

		setLayout(new BoxLayout(this, orientation.boxLayoutConstant));

		jLabel = new JLabel(label);
		add(jLabel);

		textField = new CheckedTextField(intPattern, 10);
		textField.addTextListener(this);
		add(textField);

		jSlider = new JSlider(model);
		jSlider.addChangeListener(this);
		add(jSlider);

		setMaximumSize(getPreferredSize()); // TODO - Create LayoutManager
	}

	/**
	 * <p>
	 * fireValueChangedEvent.
	 * </p>
	 */
	private void fireValueChangedEvent() {
		final ChangeEvent e = new ChangeEvent(this);
		for (final ChangeListener listener : listenerList.getListeners(ChangeListener.class)) {
			listener.stateChanged(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		final Dimension labelSize = jLabel.getPreferredSize();
		final Dimension textSize = textField.getPreferredSize();
		final Dimension sliderSize = jSlider.getPreferredSize();
		final int w = labelSize.width + textSize.width + sliderSize.width;
		final int h = labelSize.height + textSize.height + sliderSize.height;
		return new Dimension(w, h);
	}

	/**
	 * Adds a ChangeListener to the scale.
	 *
	 * @param l
	 *            the ChangeListener to add
	 */
	public void addChangeListener(final ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}

	/**
	 * Removes a ChangeListener from the scale.
	 *
	 * @param l
	 *            the ChangeListener to remove
	 */
	public void removeChangeListener(final ChangeListener l) {
		listenerList.remove(ChangeListener.class, l);
	}

	/** {@inheritDoc} */
	@Override
	public void stateChanged(final ChangeEvent e) {
		final int value = jSlider.getValue();
		textField.setText(String.valueOf(value));
		fireValueChangedEvent();
	}

	/** {@inheritDoc} */
	@Override
	public void textEntered(final ActionEvent e) {
		final int value = Integer.valueOf(textField.getText());
		jSlider.setValue(value);
		fireValueChangedEvent();
	}

	/**
	 * <p>
	 * getLabel.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLabel() {
		return jLabel.getText();
	}

	/**
	 * <p>
	 * setLabel.
	 * </p>
	 *
	 * @param label
	 *            a {@link java.lang.String} object.
	 */
	public void setLabel(final String label) {
		jLabel.setText(label);
	}

	/**
	 * <p>
	 * getValue.
	 * </p>
	 *
	 * @return a int.
	 */
	public int getValue() {
		return jSlider.getValue();
	}

	/**
	 * <p>
	 * setValue.
	 * </p>
	 *
	 * @param value
	 *            a int.
	 */
	public void setValue(final int value) {
		jSlider.setValue(value);
	}

	/**
	 * <p>
	 * getModel.
	 * </p>
	 *
	 * @return a {@link javax.swing.BoundedRangeModel} object.
	 */
	public BoundedRangeModel getModel() {
		return jSlider.getModel();
	}

	/**
	 * <p>
	 * setModel.
	 * </p>
	 *
	 * @param model
	 *            a {@link javax.swing.BoundedRangeModel} object.
	 */
	public void setModel(final BoundedRangeModel model) {
		jSlider.setModel(model);
	}

	/** {@inheritDoc} */
	@Override
	public void cancel(final AWTEvent e) {
		// TODO
		throw new RuntimeException("TODO");
	}

}
