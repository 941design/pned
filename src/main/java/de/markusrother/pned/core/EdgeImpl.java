package de.markusrother.pned.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.markusrother.util.JsonBuilder;

@XmlRootElement(name = "arc")
public class EdgeImpl
	implements
		EdgeModel {

	private final String id;

	private String sourceId;

	private String targetId;

	private @SuppressWarnings("unused") EdgeImpl() {
		// IGNORE - Only needed by XmlMarshaller!
		this.id = null;
	}

	public EdgeImpl(final String edgeId, final String sourceId, final String targetId) {
		this.id = edgeId;
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	@Override
	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	@Override
	public boolean hasId(final String edgeId) {
		return this.id != null && this.id.equals(edgeId);
	}

	@Override
	@XmlAttribute(name = "source")
	public String getSourceId() {
		return sourceId;
	}

	@Override
	public void setSourceId(final String sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	@XmlAttribute(name = "target")
	public String getTargetId() {
		return targetId;
	}

	@Override
	public void setTargetId(final String targetId) {
		this.targetId = targetId;
	}

	@Override
	public String toString() {
		return toJson();
	}

	@Override
	public String toJson() {
		final JsonBuilder jb = new JsonBuilder();
		return jb.append("id", id) //
				.append("sourceId", sourceId) //
				.append("targetId", targetId) //
				.toString();
	}

}
