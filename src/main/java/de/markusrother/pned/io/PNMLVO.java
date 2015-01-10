package de.markusrother.pned.io;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.markusrother.pned.core.DefaultPetriNet;

@XmlRootElement(name = "pnml")
public class PNMLVO {

	private final DefaultPetriNet net;

	public PNMLVO() {
		this.net = null;
	}

	public PNMLVO(final DefaultPetriNet net) {
		this.net = net;
	}

	@XmlElement(name = "net")
	public DefaultPetriNet getNet() {
		return net;
	}

}
