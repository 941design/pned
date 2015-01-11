package de.markusrother.pned.io;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.markusrother.pned.core.DefaultPetriNet;

/**
 * <p>PNMLVO class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "pnml")
public class PNMLVO {

	private final DefaultPetriNet net;

	/**
	 * <p>Constructor for PNMLVO.</p>
	 */
	private @SuppressWarnings("unused") PNMLVO() {
		this.net = null;
	}

	/**
	 * <p>Constructor for PNMLVO.</p>
	 *
	 * @param net a {@link de.markusrother.pned.core.DefaultPetriNet} object.
	 */
	public PNMLVO(final DefaultPetriNet net) {
		this.net = net;
	}

	/**
	 * <p>Getter for the field <code>net</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.core.DefaultPetriNet} object.
	 */
	@XmlElement(name = "net")
	public DefaultPetriNet getNet() {
		return net;
	}

}
