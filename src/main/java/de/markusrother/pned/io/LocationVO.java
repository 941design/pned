package de.markusrother.pned.io;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * <p>
 * Value object representing a location's coordinates, e.g. of offsets. Used for
 * marshalling to pnml (xml).
 * </p>
 *
 * <pre>
 * ...
 * &lt;offset x="23" y="42"/&gt;
 * ...
 * </pre>
 *
 * @author Markus Rother
 * @version 1.0
 * @see PetriNetMarshaller
 */
public class LocationVO {

    private final int x;
    private final int y;

    /**
     * <p>
     * Default constructor needed by XmlMarshaller!
     * </p>
     */
    private @SuppressWarnings("unused") LocationVO() {
        // IGNORE
        this.x = -1;
        this.y = -1;
    }

    /**
     * <p>
     * Constructor for LocationVO.
     * </p>
     *
     * @param point
     *            a {@link java.awt.Point} - the locations coordinates.
     */
    public LocationVO(final Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    /**
     * <p>
     * Getter for the field <code>x</code>.
     * </p>
     *
     * @return the abscissa.
     */
    @XmlAttribute
    public int getX() {
        return x;
    }

    /**
     * <p>
     * Getter for the field <code>y</code>.
     * </p>
     *
     * @return the ordinate.
     */
    @XmlAttribute
    public int getY() {
        return y;
    }

}
