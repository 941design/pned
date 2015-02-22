package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import de.markusrother.pned.core.model.PlaceModel;
import de.markusrother.pned.io.MarkingMarshaller;
import de.markusrother.util.JsonBuilder;

/**
 * <p>
 * Default implementation of {@link de.markusrother.pned.core.model.PlaceModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "place")
public class DefaultPlace extends AbstractDefaultNode
    implements
        PlaceModel {

    /** The current marking. */
    private int marking;

    /**
     * <p>
     * Default constructor needed by XmlMarshaller!
     * </p>
     */
    private @SuppressWarnings("unused") DefaultPlace() {
        // IGNORE
    }

    /**
     * <p>
     * Instantiates a valid {@link de.markusrother.pned.core.model.PlaceModel}.
     * </p>
     *
     * @param nodeId
     *            a {@link java.lang.String} - this place's unique id.
     * @param point
     *            a {@link java.awt.Point} - this place's coordinates.
     */
    public DefaultPlace(final String nodeId, final Point point) {
        super(nodeId, point);
    }

    /** {@inheritDoc} */
    @Override
    @XmlElement(name = "initialMarking")
    @XmlJavaTypeAdapter(MarkingMarshaller.class)
    public Integer getMarking() {
        return marking;
    }

    /** {@inheritDoc} */
    @Override
    public void setMarking(final Integer marking) {
        this.marking = marking;
    }

    /** {@inheritDoc} */
    @Override
    protected void writeAttributesAsString(final JsonBuilder jb) {
        super.writeAttributesAsString(jb);
        jb.append("marking", marking);
    }

}
