package de.markusrother.pned.gui.components;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.markusrother.pned.commands.MarkingLayoutCommand;
import de.markusrother.pned.commands.listeners.MarkingLayoutListener;

public class Marking extends JPanel
	implements
		MarkingLayoutListener {

	private final MarkingStyle style;
	private int value;
	private final JLabel label;

	Marking() {
		this.style = MarkingStyle.DEFAULT;
		this.label = new JLabel();
		add(label);
		setOpaque(false);
		// FIXME - remove on dispose!
		eventBus.addListener(MarkingLayoutListener.class, this);
	}

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

	public int getValue() {
		return value;
	}

	public void setValue(final int value) {
		if (this.value != value) {
			this.value = value;
			label.setText(String.valueOf(value));
			repaint();
		}
	}

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
