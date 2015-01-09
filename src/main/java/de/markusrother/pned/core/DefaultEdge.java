package de.markusrother.pned.core;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import de.markusrother.pned.core.model.EdgeModel;
import de.markusrother.util.JsonSerializable;
import de.markusrother.util.JsonBuilder;

/**
 * <p>
 * Default implementation of {@link de.markusrother.pned.core.model.EdgeModel}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
@XmlRootElement(name = "arc")
public class DefaultEdge
	implements
		EdgeModel,
		JsonSerializable {

	/** This edge's immutable unique identifier */
	private final String id;

	/** The source node's unique identifier */
	private String sourceId;
	/** The target node's unique identifier */
	private String targetId;

	/**
	 * <p>
	 * Default constructor needed by XmlMarshaller!
	 * </p>
	 */
	private @SuppressWarnings("unused") DefaultEdge() {
		// IGNORE
		this.id = null;
	}

	/**
	 * <p>
	 * Constructor for EdgeImpl.
	 * </p>
	 *
	 * @param edgeId
	 *            a {@link java.lang.String} - this edge's unique id.
	 * @param sourceId
	 *            a {@link java.lang.String} - the source node's unique id.
	 * @param targetId
	 *            a {@link java.lang.String} - the target node's unique id.
	 */
	public DefaultEdge(final String edgeId, final String sourceId, final String targetId) {
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
