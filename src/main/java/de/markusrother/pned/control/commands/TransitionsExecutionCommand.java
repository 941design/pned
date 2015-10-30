package de.markusrother.pned.control.commands;

import java.util.Collection;
import java.util.EventObject;

/**
 * <p>
 * Instances of this class trigger execution of multiple active
 * {@link de.markusrother.pned.core.model.TransitionModel}s.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 * @see de.markusrother.pned.control.commands.TransitionListener
 */
public class TransitionsExecutionCommand
        extends EventObject {

    private final Collection<String> transitionIds;

    /**
     * <p>
     * Constructor for TransitionsExecutionCommand.
     * </p>
     *
     * @param source        a {@link java.lang.Object} - this event's source.
     * @param transitionIds a collection of {@link java.lang.String}s - the unique identifiers of the
     *                      transitions to be fired.
     */
    public TransitionsExecutionCommand(final Object source, final Collection<String> transitionIds) {
        super(source);
        this.transitionIds = transitionIds;
    }

    /**
     * <p>
     * Getter for the field <code>transitionIds</code>.
     * </p>
     *
     * @return a collection of {@link java.lang.String}s - the unique identifiers of the
     * transitions to be fired.
     */
    public Collection<String> getTransitionIds() {
        return transitionIds;
    }

}
