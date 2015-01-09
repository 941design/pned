package de.markusrother.pned.gui.layout.style;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import de.markusrother.swing.ChangeEventSource;

public class AbstractStyle
	implements
		ChangeEventSource {

	private final EventListenerList listeners;

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

	protected void fireChangeEvent() {
		final ChangeEvent evt = new ChangeEvent(this);
		for (final ChangeListener l : listeners.getListeners(ChangeListener.class)) {
			l.stateChanged(evt);
		}
	}

}
