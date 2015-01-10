package de.markusrother.pned.gui.core;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Abstract superclass for style model implementations that support change
 * listeners.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class AbstractStyle
	implements
		ChangeEventSource {

	/** The registered listeners. */
	protected final EventListenerList listeners;

	/**
	 * <p>
	 * Constructor for AbstractStyle.
	 * </p>
	 */
	public AbstractStyle() {
		listeners = new EventListenerList();
	}

	/** {@inheritDoc} */
	@Override
	public void addChangeListener(final ChangeListener l) {
		listeners.add(ChangeListener.class, l);
	}

	/** {@inheritDoc} */
	@Override
	public void removeChangeListener(final ChangeListener l) {
		listeners.remove(ChangeListener.class, l);
	}

	/**
	 * <p>
	 * Informs registered listeners of a change in style.
	 * </p>
	 */
	protected void fireChangeEvent() {
		final ChangeEvent evt = new ChangeEvent(this);
		for (final ChangeListener l : listeners.getListeners(ChangeListener.class)) {
			l.stateChanged(evt);
		}
	}

}
