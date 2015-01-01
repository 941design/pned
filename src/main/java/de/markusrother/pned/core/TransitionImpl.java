package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * Default implementation of {@link TransitionModel}.
 * </p>
 * 
 * FIXME - rename to DefaultTransition
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "transition")
public class TransitionImpl extends NodeImpl
	implements
		TransitionModel {

	/**
	 * <p>
	 * Default constructor needed by XmlMarshaller!
	 * </p>
	 */
	private @SuppressWarnings("unused") TransitionImpl() {
		// IGNORE
	}

	/**
	 * <p>
	 * Constructor for TransitionImpl.
	 * </p>
	 *
	 * @param nodeId
	 *            a {@link java.lang.String} - this transition's unique id.
	 * @param point
	 *            a {@link java.awt.Point} - this transition's coordinates.
	 */
	public TransitionImpl(final String nodeId, final Point point) {
		super(nodeId, point);
	}

}
