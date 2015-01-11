package de.markusrother.pned.io;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
/**
 * <p>IntegerValueVO class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class IntegerValueVO {

	private final Integer value;

	/**
	 * <p>Constructor for IntegerValueVO.</p>
	 */
	private @SuppressWarnings("unused") IntegerValueVO() {
		this.value = null;
	}

	/**
	 * <p>Constructor for IntegerValueVO.</p>
	 *
	 * @param value a {@link java.lang.Integer} object.
	 */
	public IntegerValueVO(final Integer value) {
		this.value = value;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	@XmlElement(name = "value")
	public Integer getValue() {
		return value;
	}

}
