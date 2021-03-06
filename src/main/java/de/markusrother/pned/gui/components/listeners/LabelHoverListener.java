package de.markusrother.pned.gui.components.listeners;

import java.awt.Component;

import de.markusrother.pned.gui.components.ComponentState;
import de.markusrother.pned.gui.components.LabelComponent;
import de.markusrother.swing.HoverAdapter;

/**
 * <p>LabelHoverListener class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LabelHoverListener extends HoverAdapter {

    /** {@inheritDoc} */
    @Override
    protected void startHover(final Component component) {
        final LabelComponent nodeLabel = (LabelComponent) component;
        nodeLabel.setState(ComponentState.HOVER);
    }

    /** {@inheritDoc} */
    @Override
    protected void endHover(final Component component) {
        final LabelComponent nodeLabel = (LabelComponent) component;
        nodeLabel.setState(ComponentState.DEFAULT);
    }

}
