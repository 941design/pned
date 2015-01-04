package de.markusrother.swing;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollPaneUI;

/**
 * TODO - Maybe it is necessary to create one UI each for all LAFs!?
 *
 * TODO - Create a proxy : 1. retrieva all LAF classes at runtime 2. proxy all
 * ScrollPaneUI classes with this change listener stuff.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class CustomScrollPaneUI extends BasicScrollPaneUI
	implements
		ChangeListener {

	private final Collection<ChangeListener> horizontalListeners;
	private final Collection<ChangeListener> verticalListeners;

	/**
	 * <p>Constructor for CustomScrollPaneUI.</p>
	 */
	public CustomScrollPaneUI() {
		super();
		this.verticalListeners = new LinkedList<>();
		this.horizontalListeners = new LinkedList<>();
	}

	/** {@inheritDoc} */
	@Override
	public void installListeners(final JScrollPane c) {
		super.installListeners(c);
		scrollpane.getVerticalScrollBar().getModel().addChangeListener(this);
		scrollpane.getHorizontalScrollBar().getModel().addChangeListener(this);
	}

	/** {@inheritDoc} */
	@Override
	public void stateChanged(final ChangeEvent e) {
		final Object source = e.getSource();
		if (source == scrollpane.getVerticalScrollBar().getModel()) {
			for (final ChangeListener listener : verticalListeners) {
				listener.stateChanged(e);
			}
		} else if (source == scrollpane.getHorizontalScrollBar().getModel()) {
			for (final ChangeListener listener : horizontalListeners) {
				listener.stateChanged(e);
			}
		}
	}

	/**
	 * <p>addVerticalChangeListener.</p>
	 *
	 * @param l a {@link javax.swing.event.ChangeListener} object.
	 */
	public void addVerticalChangeListener(final ChangeListener l) {
		this.verticalListeners.add(l);
	}

	/**
	 * <p>removeVerticalChangeListener.</p>
	 *
	 * @param l a {@link javax.swing.event.ChangeListener} object.
	 */
	public void removeVerticalChangeListener(final ChangeListener l) {
		this.verticalListeners.remove(l);
	}

	/**
	 * <p>addHorizontalChangeListener.</p>
	 *
	 * @param l a {@link javax.swing.event.ChangeListener} object.
	 */
	public void addHorizontalChangeListener(final ChangeListener l) {
		this.horizontalListeners.add(l);
	}

	/**
	 * <p>removeHorizontalChangeListener.</p>
	 *
	 * @param l a {@link javax.swing.event.ChangeListener} object.
	 */
	public void removeHorizontalChangeListener(final ChangeListener l) {
		this.horizontalListeners.remove(l);
	}

}
