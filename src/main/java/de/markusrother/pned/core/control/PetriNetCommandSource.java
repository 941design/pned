package de.markusrother.pned.core.control;

import java.util.EventListener;

/**
 * <p>
 * PetriNetCommandSource interface.
 * </p>
 *
 * FIXME - rename to CommandSource with T super CommandTarget
 * 
 * @author Markus Rother
 * @version 1.0
 */
public interface PetriNetCommandSource {

	/**
	 * <p>
	 * getListeners.
	 * </p>
	 *
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @param <T>
	 *            a T object.
	 * @return an array of T objects.
	 */
	<T extends EventListener> T[] getListeners(Class<T> clazz);

	/**
	 * <p>
	 * addListener.
	 * </p>
	 *
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @param l
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 */
	<T extends EventListener> void addListener(Class<T> clazz, T l);

	/**
	 * <p>
	 * removeListener.
	 * </p>
	 *
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 * @param l
	 *            a T object.
	 * @param <T>
	 *            a T object.
	 */
	<T extends EventListener> void removeListener(Class<T> clazz, T l);

}
