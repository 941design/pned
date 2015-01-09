package de.markusrother.pned.gui.core;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>AbstractStyle class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class AbstractStyle
	implements
		ChangeEventSource {

	private final EventListenerList listeners;

	/**
	 * <p>Constructor for AbstractStyle.</p>
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
	 * <p>fireChangeEvent.</p>
	 */
	protected void fireChangeEvent() {
		final ChangeEvent evt = new ChangeEvent(this);
		for (final ChangeListener l : listeners.getListeners(ChangeListener.class)) {
			l.stateChanged(evt);
		}
	}

}
