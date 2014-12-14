package de.markusrother.swing;

import static de.markusrother.util.Patterns.intPattern;

import java.awt.event.ActionEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

	public ScaleGroup(final String label, final Orientation orientation) {
		this(label, orientation, new DefaultBoundedRangeModel());
	}

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

	}

	private void fireValueChangedEvent() {
		final ChangeEvent e = new ChangeEvent(this);
		for (final ChangeListener listener : listenerList.getListeners(ChangeListener.class)) {
			listener.stateChanged(e);
		}
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

	@Override
	public void stateChanged(final ChangeEvent e) {
		final int value = jSlider.getValue();
		textField.setText(String.valueOf(value));
		fireValueChangedEvent();
	}

	@Override
	public void textEntered(final ActionEvent e) {
		final int value = Integer.valueOf(textField.getText());
		jSlider.setValue(value);
		fireValueChangedEvent();
	}

	public String getLabel() {
		return jLabel.getText();
	}

	public void setLabel(final String label) {
		jLabel.setText(label);
	}

	public int getValue() {
		return jSlider.getValue();
	}

	public void setValue(final int value) {
		jSlider.setValue(value);
	}

	public BoundedRangeModel getModel() {
		return jSlider.getModel();
	}

	public void setModel(final BoundedRangeModel model) {
		jSlider.setModel(model);
	}
}
