package de.markusrother.pned.io;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
/**
 * <p>MarkingVO class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class MarkingVO {

	private final IntegerValueVO marking;

	/**
	 * <p>Constructor for MarkingVO.</p>
	 */
	private @SuppressWarnings("unused") MarkingVO() {
		this.marking = null;
	}

	/**
	 * <p>Constructor for MarkingVO.</p>
	 *
	 * @param marking a {@link java.lang.Integer} object.
	 */
	public MarkingVO(final Integer marking) {
		this.marking = new IntegerValueVO(marking);
	}

	/**
	 * <p>Getter for the field <code>marking</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.io.IntegerValueVO} object.
	 */
	@XmlElement(name = "token")
	public IntegerValueVO getMarking() {
		return marking;
	}

}
