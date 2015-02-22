package de.markusrother.pned.gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.SetNodeTypeCommand;
import de.markusrother.swing.CustomRadioButtonMenuItem;

/**
 * <p>
 * Class for compound actions: Setting the default node type to transition,
 * <b>and</b> creating a transition. This action combines an
 * {@link java.awt.event.ActionListener} with an
 * {@link java.awt.event.ItemListener} for selectable items. It can be used for
 * e.g. {@link javax.swing.JRadioButtonMenuItem}s where toggle and selection
 * (click) trigger separate {@link java.awt.event.ActionEvent}s, such as in
 * {@link #newMenuItem(PnEventBus, LocationProvider)}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see CustomRadioButtonMenuItem
 * @see de.markusrother.pned.gui.control.PnEventBus
 */
public class CreateTransitionAction extends AbstractCreateNodeAction {

    /** Constant <code>label="Create transition"</code> */
    private static final String name = "Create transition";
    /** Constant <code>mnemonic=KeyEvent.VK_T</code> */
    private static final int mnemonic = KeyEvent.VK_T;

    /**
     * <p>
     * Creates and returns a {@link javax.swing.JRadioButtonMenuItem} where
     * toggle and click on label trigger separate actions as described here
     * {@link de.markusrother.pned.gui.actions.CreateTransitionAction}.
     * </p>
     *
     * @param eventBus
     *            an {@link de.markusrother.pned.gui.control.PnEventBus} to be
     *            posted to.
     * @param locationProvider
     *            a {@link de.markusrother.pned.gui.actions.LocationProvider} to
     *            provide coordinates for newly created nodes.
     * @return a {@link javax.swing.JRadioButtonMenuItem} with this action
     *         bound.
     * @see CustomRadioButtonMenuItem
     */
    public static JRadioButtonMenuItem newMenuItem(final PnEventBus eventBus, final LocationProvider locationProvider) {
        final CreateTransitionAction action = new CreateTransitionAction(eventBus, locationProvider);
        final CustomRadioButtonMenuItem menuItem = new CustomRadioButtonMenuItem(action);
        menuItem.addItemListener(action);
        return menuItem;
    }

    /**
     * <p>
     * Constructor for CreateTransitionAction.
     * </p>
     *
     * @param eventBus
     *            an {@link de.markusrother.pned.gui.control.PnEventBus} to be
     *            posted to.
     * @param locationProvider
     *            a {@link de.markusrother.pned.gui.actions.LocationProvider} to
     *            provide coordinates for newly created nodes.
     */
    private CreateTransitionAction(final PnEventBus eventBus, final LocationProvider locationProvider) {
        super(eventBus, locationProvider, mnemonic, name);
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final String nodeId = eventBus.requestId();
        eventBus.createTransition(new TransitionCreationCommand(this, nodeId, locationProvider.getLocation()));
    }

    /** {@inheritDoc} */
    @Override
    public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
        setSelected(cmd.getMode() == PnState.NewNodeType.TRANSITION);
    }

    /** {@inheritDoc} */
    @Override
    public void setSelected(final boolean selected) {
        putValue(Action.NAME, name + (selected ? " (default)" : ""));
    }

    /** {@inheritDoc} */
    @Override
    protected void fireSetNodeTypeCommand() {
        eventBus.setCurrentNodeType(new SetNodeTypeCommand(this, PnState.NewNodeType.TRANSITION));
    }

}
