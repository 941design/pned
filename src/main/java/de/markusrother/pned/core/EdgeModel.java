package de.markusrother.pned.core;

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
