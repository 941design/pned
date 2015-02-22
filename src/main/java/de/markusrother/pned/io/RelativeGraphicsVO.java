package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>
 * Value object representing the graphics pnml tag of relatively positionable
 * elements, e.g. labels. Used for marshalling to pnml (xml).
 * </p>
 *
 * <pre>
 * ...
 * &lt;graphics&gt;
 *     &lt;offset x="23" y="42"/&gt;
 * &lt;/graphics&gt;
 * ...
 * </pre>
 *
 * @author Markus Rother
 * @version 1.0
 * @see PetriNetMarshaller
 * @see AbsolutePositionMarshaller
 */
public class RelativeGraphicsVO extends AbstractGraphicsVO {

    /**
     * <p>
     * Default constructor needed by XmlMarshaller!
     * </p>
     */
    private @SuppressWarnings("unused") RelativeGraphicsVO() {
        // IGNORE
    }

    /**
     * <p>
     * Constructor for RelativeGraphicsVO.
     * </p>
     *
     * @param point
     *            a {@link java.awt.Point} representing the graphic's relative
     *            coordinates.
     */
    public RelativeGraphicsVO(final Point point) {
        super(point);
    }

    /** {@inheritDoc} */
    @Override
    @XmlElement(name = "offset")
    public LocationVO getLocation() {
        return location;
    }

}
