package de.markusrother.pned.core;

import java.awt.Point;

public interface NodeModel
	extends
		JsonBuildable {

	String getId();

	boolean hasId(String id);

	String getLabel();

	Point getOrigin();

	void setOrigin(Point origin);

	void move(int deltaX, int deltaY);

}
