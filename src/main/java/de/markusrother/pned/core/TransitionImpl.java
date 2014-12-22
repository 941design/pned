package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transition")
public class TransitionImpl extends NodeImpl
	implements
		TransitionModel {

	private @SuppressWarnings("unused") TransitionImpl() {
		// IGNORE - Only needed by XmlMarshaller!
	}

	public TransitionImpl(final String nodeId, final Point point) {
		super(nodeId, point);
	}

}
