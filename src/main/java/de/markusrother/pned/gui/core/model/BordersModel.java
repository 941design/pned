package de.markusrother.pned.gui.core.model;

import javax.swing.border.Border;

/**
 * <p>
 * Mutable model for bordered objects, providing different borders for different
 * states.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface BordersModel {

    /**
     * <p>getDefaultBorder.</p>
     *
     * @return a {@link javax.swing.border.Border} object.
     */
    Border getDefaultBorder();

    /**
     * <p>setDefaultBorder.</p>
     *
     * @param defaultBorder a {@link javax.swing.border.Border} object.
     */
    void setDefaultBorder(Border defaultBorder);

    /**
     * <p>getHoverBorder.</p>
     *
     * @return a {@link javax.swing.border.Border} object.
     */
    Border getHoverBorder();

    /**
     * <p>setHoverBorder.</p>
     *
     * @param hoverBorder a {@link javax.swing.border.Border} object.
     */
    void setHoverBorder(Border hoverBorder);

    /**
     * <p>getSelectionBorder.</p>
     *
     * @return a {@link javax.swing.border.Border} object.
     */
    Border getSelectionBorder();

    /**
     * <p>setSelectionBorder.</p>
     *
     * @param selectionBorder a {@link javax.swing.border.Border} object.
     */
    void setSelectionBorder(Border selectionBorder);

}
