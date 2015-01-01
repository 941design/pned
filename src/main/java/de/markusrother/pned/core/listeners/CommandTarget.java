package de.markusrother.pned.core.listeners;

/**
 * <p>
 * Super/Compound interface for command listeners.
 * </p>
 * 
 * FIXME - rename to CommandTarget
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface CommandTarget
	extends
		PetriNetIOListener,
		NodeCreationListener,
		EdgeCreationListener,
		NodeMotionListener,
		NodeRemovalListener,
		PlaceEditListener,
		LabelEditListener {

}
