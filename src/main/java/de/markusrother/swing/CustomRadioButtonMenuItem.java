package de.markusrother.swing;

import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JRadioButtonMenuItem;

/**
 * <p>
 * CustomRadioButtonMenuItem class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class CustomRadioButtonMenuItem extends JRadioButtonMenuItem {

    /**
     * <p>
     * Constructor for CustomRadioButtonMenuItem.
     * </p>
     *
     * @param action
     *            a {@link javax.swing.AbstractAction} object.
     */
    public CustomRadioButtonMenuItem(final AbstractAction action) {
        super(action);
        setUI(new CustomRadioButtonMenuItemUI(this));
        addMouseListener((MouseListener) ui);
    }

}
