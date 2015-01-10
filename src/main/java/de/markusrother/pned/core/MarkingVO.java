package de.markusrother.pned.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class MarkingVO {

	private final IntegerValueVO marking;

	private @SuppressWarnings("unused") MarkingVO() {
		this.marking = null;
	}

	public MarkingVO(final Integer marking) {
		this.marking = new IntegerValueVO(marking);
	}

	@XmlElement(name = "token")
	public IntegerValueVO getMarking() {
		return marking;
	}

}
