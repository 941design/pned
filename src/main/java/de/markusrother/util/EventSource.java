package de.markusrother.util;

import java.util.EventListener;

/**
 * <p>
 * A producer of events.
 * </p>
 *
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EventSource {

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
