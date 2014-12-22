package de.markusrother.pned.core;

import de.markusrother.util.JsonBuildable;

public interface EdgeModel
	extends
		JsonBuildable {

	String getId();

	boolean hasId(String edgeId);

	String getSourceId();

	void setSourceId(String sourceId);

	String getTargetId();

	void setTargetId(String targetId);

}
