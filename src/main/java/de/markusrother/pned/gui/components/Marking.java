package de.markusrother.pned.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.gui.layout.style.Stylable;
import de.markusrother.pned.gui.model.MarkingStyleModel;

/**
 * <p>
 * Marking class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Marking extends JPanel
	implements
		Stylable<MarkingStyleModel> {

	private final JLabel label;

	protected MarkingStyleModel style;
	protected int value;

	/**
	 * <p>
	 * Constructor for Marking.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param style a {@link de.markusrother.pned.gui.model.MarkingStyleModel} object.
	 */
	Marking(final EventBus eventBus, final MarkingStyleModel style) {
		this.label = new JLabel();
		label.setFont(style.getFont());

		add(label);
		setOpaque(false);

		setStyle(style);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(final Graphics g) {
		label.setVisible(value > 1);
		g.setColor(style.getColor());
		super.paintComponent(g);
		if (value == 1) {
			final Graphics2D g2 = (Graphics2D) g;
			g2.fill(style.getShape());
		}
	}

	/**
	 * <p>
	 * Getter for the field <code>value</code>.
	 * </p>
	 *
	 * @return a int.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <p>
	 * Setter for the field <code>value</code>.
	 * </p>
	 *
	 * @param value
	 *            a int.
	 */
	public void setValue(final int value) {
		if (this.value != value) {
			this.value = value;
			label.setText(String.valueOf(value));
			doLayout();
			repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setStyle(final MarkingStyleModel style) {
		if (this.style != null) {
			this.style.removeChangeListener(this);
		}
		this.style = style;
		this.style.addChangeListener(this);
	}

	/** {@inheritDoc} */
	@Override
	public void stateChanged(final ChangeEvent e) {
		if (e.getSource() == this.style) {
			final int extent = style.getSize();
			label.setFont(style.getFont());
			setPreferredSize(new Dimension(extent, extent));
			invalidate();
			repaint();
		} else {
			throw new RuntimeException("Unexpected event source " + e.getSource());
		}
	}

}
