package de.markusrother.pned.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.gui.layout.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.layout.listeners.MarkingLayoutListener;
import de.markusrother.pned.gui.layout.style.MarkingStyle;

/**
 * <p>Marking class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class Marking extends JPanel
	implements
		MarkingLayoutListener {

	private final MarkingStyle style;
	private int value;
	private final JLabel label;

	/**
	 * <p>Constructor for Marking.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	Marking(final EventBus eventBus) {
		this.style = MarkingStyle.DEFAULT;
		this.label = new JLabel();
		add(label);
		setOpaque(false);
		// FIXME - remove on dispose!
		eventBus.addListener(MarkingLayoutListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(final Graphics g) {
		// TODO - final int value = model.getValue();
		label.setVisible(value > 1);
		g.setColor(style.getColor());
		super.paintComponent(g);
		if (value == 1) {
			final Graphics2D g2 = (Graphics2D) g;
			g2.fill(style.getShape());
		}
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a int.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a int.
	 */
	public void setValue(final int value) {
		if (this.value != value) {
			this.value = value;
			label.setText(String.valueOf(value));
			repaint();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(final MarkingLayoutCommand cmd) {
		final int extent = cmd.getSize();
		style.setSize(extent);
		label.setFont(style.getFont());
		setPreferredSize(new Dimension(extent, extent));
		// TODO - There may be a problem concering the order of queued events.
		// During sliding change events are fired constantly. Maybe(?) that is
		// why the parent is not redrawn.
		invalidate();
		repaint();
	}

}
