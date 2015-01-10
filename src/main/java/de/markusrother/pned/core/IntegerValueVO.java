package de.markusrother.pned.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class IntegerValueVO {

	private final Integer value;

	private @SuppressWarnings("unused") IntegerValueVO() {
		this.value = null;
	}

	public IntegerValueVO(final Integer value) {
		this.value = value;
	}

	@XmlElement(name = "value")
	public Integer getValue() {
		return value;
	}

}
