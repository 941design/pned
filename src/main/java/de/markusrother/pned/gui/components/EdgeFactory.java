package de.markusrother.pned.gui.components;

import de.markusrother.pned.gui.core.model.EdgeStyleModel;

/**
 * <p>EdgeFactory interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface EdgeFactory {

	/**
	 * <p>newEdge.</p>
	 *
	 * @param edgeId a {@link java.lang.String} object.
	 * @param sourceId a {@link java.lang.String} object.
	 * @param targetId a {@link java.lang.String} object.
	 * @return a {@link de.markusrother.pned.gui.components.EdgeComponent} object.
	 */
	EdgeComponent newEdge(String edgeId, String sourceId, String targetId);

	/**
	 * <p>getEdgeStyle.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.EdgeStyleModel} object.
	 */
	EdgeStyleModel getEdgeStyle();

}
