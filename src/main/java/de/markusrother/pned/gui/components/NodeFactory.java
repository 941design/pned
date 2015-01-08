package de.markusrother.pned.gui.components;


public interface NodeFactory {

	Place newPlace(String id);

	Transition newTransition(String id);

}
