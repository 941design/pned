package de.markusrother.pned.gui.control.commands;

import java.util.EventObject;

import de.markusrother.pned.gui.control.PnState.NewNodeType;
import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * SetNodeTypeCommand class.
 * </p>
 *
 * TODO - merge into PetriNetCommand
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.commands.NodeListener
 */
public class SetNodeTypeCommand extends EventObject
    implements
        JsonSerializable {

    private final NewNodeType mode;

    /**
     * <p>
     * Constructor for SetNodeTypeCommand.
     * </p>
     *
     * @param source
     *            a {@link java.lang.Object} object.
     * @param mode
     *            a
     *            {@link de.markusrother.pned.gui.control.PnState.NewNodeType}
     *            object.
     */
    public SetNodeTypeCommand(final Object source, final NewNodeType mode) {
        super(source);
        this.mode = mode;
    }

    /**
     * <p>
     * Getter for the field <code>mode</code>.
     * </p>
     *
     * @return a
     *         {@link de.markusrother.pned.gui.control.PnState.NewNodeType}
     *         object.
     */
    public NewNodeType getMode() {
        return mode;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getClass().getSimpleName() + ':' + toJson();
    }

    /** {@inheritDoc} */
    @Override
    public String toJson() {
        final JsonBuilder builder = new JsonBuilder();
        return builder.append("type", mode.name()) //
                .toString();
    }

}
