package de.markusrother.pned.gui.components.menus;

import java.awt.Point;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import de.markusrother.pned.gui.control.PnState;

/**
 * Factory for menus that are sensitive to state changes.
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnMenuFactory {

    private final EditMenuFactory editMenuFactory;
    private final PnState state;

    /**
     * <p>
     * Constructor for PnEditorMenuFactory.
     * </p>
     *
     * @param state
     *            a {@link de.markusrother.pned.gui.control.PnState} object.
     */
    public PnMenuFactory(final PnState state) {
        this.state = state;
        this.editMenuFactory = new EditMenuFactory(state);
    }

    /**
     * <p>
     * newEditMenu.
     * </p>
     *
     * @return a {@link javax.swing.JMenu} object.
     */
    public JMenu newEditMenu() {
        return editMenuFactory.newMenu();
    }

    /**
     * <p>
     * newFileMenu.
     * </p>
     *
     * @return a {@link javax.swing.JMenu} object.
     */
    public JMenu newFileMenu() {
        return new PnFileMenu(state, state.getEventBus());
    }

    /**
     * <p>
     * newPreferencesMenu.
     * </p>
     *
     * @return a {@link javax.swing.JMenu} object.
     */
    public JMenu newPreferencesMenu() {
        return new PnPreferencesMenu(state, state.getEventBus());
    }

    /**
     * <p>
     * newPopupMenu.
     * </p>
     *
     * @param point
     *            a {@link java.awt.Point} - at which to show the popup.
     * @return a {@link javax.swing.JPopupMenu} object.
     */
    public JPopupMenu newPopupMenu(final Point point) {
        return editMenuFactory.newPopupMenu(point);
    }

}
