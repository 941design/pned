package de.markusrother.pned.core;

import java.util.EventListener;

import javax.swing.event.EventListenerList;

import de.markusrother.pned.core.events.PetriNetCommandSource;
import de.markusrother.pned.core.events.TransitionActivationEvent;
import de.markusrother.pned.core.listeners.TransitionActivationListener;

public class CommandSourceMock
	implements
		PetriNetCommandSource,
		TransitionActivationListener {

	private final EventListenerList listeners = new EventListenerList();

	public CommandSourceMock() {
	}

	@Override
	public <T extends EventListener> T[] getListeners(final Class<T> clazz) {
		return listeners.getListeners(clazz);
	}

	@Override
	public <T extends EventListener> void addListener(final Class<T> clazz, final T l) {
		listeners.add(clazz, l);
	}

	@Override
	public <T extends EventListener> void removeListener(final Class<T> clazz, final T l) {
		listeners.remove(clazz, l);
	}

	public int getListenerCount() {
		return listeners.getListenerCount();
	}

	@Override
	public void transitionActivated(final TransitionActivationEvent e) {
		// IGNORE
	}

	@Override
	public void transitionDeactivated(final TransitionActivationEvent e) {
		// IGNORE
	}

}
