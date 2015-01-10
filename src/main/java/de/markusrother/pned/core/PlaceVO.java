package de.markusrother.pned.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Currently unused!
 *
 */
@XmlType
public class PlaceVO {

	private final Integer marking;

	private @SuppressWarnings("unused") PlaceVO() {
		this.marking = null;
	}

	public PlaceVO(final DefaultPlace place) {
		this.marking = place.getMarking();
	}

	@XmlElement(name = "initialMarking")
	public Integer getMarking() {
		return marking;
	}

}
