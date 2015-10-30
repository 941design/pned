package de.markusrother.pned.gui.actions;

import de.markusrother.pned.control.commands.TransitionsExecutionCommand;
import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;


public class FireTransitionsAction
        extends AbstractStatefulAction {

    private static final String name = "Fire selected transitions";

    public FireTransitionsAction(final PnState state, final PnCommandTarget commandTarget) {
        super(state, commandTarget, name);
        setEnabled(state.areTargetNodesSelected());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Collection<String> transitionIds = state.getSelectedTransitionIds();
        commandTarget.fireTransitions(new TransitionsExecutionCommand(this, transitionIds));
    }

    public static Component newMenuItem(PnState state, PnCommandTarget commandTarget) {
        return new JMenuItem(new FireTransitionsAction(state, commandTarget));
    }

}
