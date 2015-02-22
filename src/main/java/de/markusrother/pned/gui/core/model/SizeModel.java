package de.markusrother.pned.gui.core.model;

import de.markusrother.swing.ChangeEventSource;

/**
 * <p>
 * Model for resizable objects, where size is an implementor-defined measure.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface SizeModel
    extends
        ChangeEventSource {

    /**
     * <p>
     * Returns current size.
     * </p>
     *
     * @return a int - an implementor defined measure.
     */
    int getSize();

    /**
     * <p>
     * Sets new size.
     * </p>
     *
     * @param size
     *            a int - an implementor defined measure.
     */
    void setSize(int size);

}
