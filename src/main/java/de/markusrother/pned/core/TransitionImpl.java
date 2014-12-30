package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>TransitionImpl class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "transition")
public class TransitionImpl extends NodeImpl
	implements
		TransitionModel {

	/**
	 * <p>Constructor for TransitionImpl.</p>
	 */
	private @SuppressWarnings("unused") TransitionImpl() {
		// IGNORE - Only needed by XmlMarshaller!
	}

	/**
	 * <p>Constructor for TransitionImpl.</p>
	 *
	 * @param nodeId a {@link java.lang.String} object.
	 * @param point a {@link java.awt.Point} object.
	 */
	public TransitionImpl(final String nodeId, final Point point) {
		super(nodeId, point);
	}

}
