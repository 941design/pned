package de.markusrother.pned.gui;

import java.util.EventListener;

public interface PetriNetCommandSource {

	<T extends EventListener> T[] getListeners(Class<T> clazz);

	<T extends EventListener> void addListener(Class<T> clazz, T l);

	<T extends EventListener> void removeListener(Class<T> clazz, T l);

}
