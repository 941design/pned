package de.markusrother.pned.gui.core.model;

import java.awt.Component;

/**
 * <p>
 * Style interface.
 * </p>
 *
 * FIXME OBSOLETE
 *
 * @author Markus Rother
 * @version 1.0
 */
@Deprecated
public interface Style<T extends Component> {

    /**
     * <p>
     * apply.
     * </p>
     *
     * @param t
     *            a T object.
     */
    void apply(T t);
}
