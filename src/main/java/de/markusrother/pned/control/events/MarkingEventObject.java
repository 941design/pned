package de.markusrother.pned.control.events;

import java.util.EventObject;

import de.markusrother.util.JsonBuilder;
import de.markusrother.util.JsonSerializable;

/**
 * <p>
 * Abstract superclass for occurrences that indicate a change in a
 * {@link de.markusrother.pned.core.model.PlaceModel}s marking.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.control.EventBus
 */
public abstract class MarkingEventObject extends EventObject
    implements
        JsonSerializable {

    /** The unique identifier of the place, whose marking is to be changed. */
    private final String placeId;
    /** The new marking. */
    private final int marking;

    /**
     * <p>
     * Constructor for PlaceEditEvent.
     * </p>
     *
     * @param source
     *            a {@link java.lang.Object} - this event's source.
     * @param placeId
     *            a {@link java.lang.String} - the unique identifier of the
     *            place, whose marking is to be changed.
     * @param marking
     *            a int - the new marking.
     */
    public MarkingEventObject(final Object source, final String placeId, final int marking) {
        super(source);
        this.placeId = placeId;
        this.marking = marking;
    }

    /**
     * <p>
     * Getter for the field <code>placeId</code>.
     * </p>
     *
     * @return a {@link java.lang.String} - the unique identifier of the place,
     *         whose marking is to be changed.
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * <p>
     * Getter for the field <code>marking</code>.
     * </p>
     *
     * @return a int - the new marking.
     */
    public int getMarking() {
        return marking;
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
        return builder.append("placeId", placeId) //
                .append("marking", marking) //
                .toString();
    }

}
