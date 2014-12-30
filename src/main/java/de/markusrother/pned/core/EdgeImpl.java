package de.markusrother.pned.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.markusrother.util.JsonBuilder;

/**
 * <p>EdgeImpl class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "arc")
public class EdgeImpl
	implements
		EdgeModel {

	private final String id;

	private String sourceId;

	private String targetId;

	/**
	 * <p>Constructor for EdgeImpl.</p>
	 */
	private @SuppressWarnings("unused") EdgeImpl() {
		// IGNORE - Only needed by XmlMarshaller!
		this.id = null;
	}

	/**
	 * <p>Constructor for EdgeImpl.</p>
	 *
	 * @param edgeId a {@link java.lang.String} object.
	 * @param sourceId a {@link java.lang.String} object.
	 * @param targetId a {@link java.lang.String} object.
	 */
	public EdgeImpl(final String edgeId, final String sourceId, final String targetId) {
		this.id = edgeId;
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	/** {@inheritDoc} */
	@Override
	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasId(final String edgeId) {
		return this.id != null && this.id.equals(edgeId);
	}

	/** {@inheritDoc} */
	@Override
	@XmlAttribute(name = "source")
	public String getSourceId() {
		return sourceId;
	}

	/** {@inheritDoc} */
	@Override
	public void setSourceId(final String sourceId) {
		this.sourceId = sourceId;
	}

	/** {@inheritDoc} */
	@Override
	@XmlAttribute(name = "target")
	public String getTargetId() {
		return targetId;
	}

	/** {@inheritDoc} */
	@Override
	public void setTargetId(final String targetId) {
		this.targetId = targetId;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/** {@inheritDoc} */
	@Override
	public String toJson() {
		final JsonBuilder jb = new JsonBuilder();
		return jb.append("id", id) //
				.append("sourceId", sourceId) //
				.append("targetId", targetId) //
				.toString();
	}

}
