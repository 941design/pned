package de.markusrother.pned.core;

import java.awt.Point;

import de.markusrother.util.JsonBuildable;

public interface NodeModel
	extends
		JsonBuildable {

	String getId();

	boolean hasId(String id);

	String getLabel();

	Point getPosition();

	void setPosition(Point origin);

	void move(int deltaX, int deltaY);

}
