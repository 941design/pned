package de.markusrother.pned.io;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.markusrother.pned.core.DefaultPlace;

/**
 * Currently unused!
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlType
public class PlaceVO {

    private final Integer marking;

    /**
     * <p>Constructor for PlaceVO.</p>
     */
    private @SuppressWarnings("unused") PlaceVO() {
        this.marking = null;
    }

    /**
     * <p>Constructor for PlaceVO.</p>
     *
     * @param place a {@link de.markusrother.pned.core.DefaultPlace} object.
     */
    public PlaceVO(final DefaultPlace place) {
        this.marking = place.getMarking();
    }

    /**
     * <p>Getter for the field <code>marking</code>.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    @XmlElement(name = "initialMarking")
    public Integer getMarking() {
        return marking;
    }

}
