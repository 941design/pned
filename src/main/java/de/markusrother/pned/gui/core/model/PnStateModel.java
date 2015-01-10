package de.markusrother.pned.gui.core.model;

import java.io.File;
import java.util.Collection;
import java.util.Set;

import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.core.NodeCreationMode;

/**
 * <p>PnStateModel interface.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public interface PnStateModel {

	/**
	 * <p>getEventBus.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.control.PnEventBus} object.
	 */
	PnEventBus getEventBus();

	/**
	 * <p>getCurrentDirectory.</p>
	 *
	 * @return a {@link java.io.File} object.
	 */
	File getCurrentDirectory();

	/**
	 * <p>getNodeCreationMode.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.NodeCreationMode} object.
	 */
	NodeCreationMode getNodeCreationMode();

	/**
	 * <p>areNodesSelected.</p>
	 *
	 * @return a boolean.
	 */
	boolean areNodesSelected();

	/**
	 * <p>areSourceNodesSelected.</p>
	 *
	 * @return a boolean.
	 */
	boolean areSourceNodesSelected();

	/**
	 * <p>areTargetNodesSelected.</p>
	 *
	 * @return a boolean.
	 */
	boolean areTargetNodesSelected();

	/**
	 * <p>getSelectedNodeIds.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<String> getSelectedNodeIds();

	/**
	 * <p>getSelectedSourceNodeIds.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<String> getSelectedSourceNodeIds();

	/**
	 * <p>getSelectedTargetNodeIds.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<String> getSelectedTargetNodeIds();

	/**
	 * <p>getSelectedIncomingEdgeIds.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<String> getSelectedIncomingEdgeIds();

	/**
	 * <p>getSelectedOutgoingEdgeIds.</p>
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<String> getSelectedOutgoingEdgeIds();

	/**
	 * <p>getPlaceStyle.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.NodeStyleModel} object.
	 */
	NodeStyleModel getPlaceStyle();

	/**
	 * <p>getTransitionStyle.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.NodeStyleModel} object.
	 */
	NodeStyleModel getTransitionStyle();

	/**
	 * <p>getEdgeStyle.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.EdgeStyleModel} object.
	 */
	EdgeStyleModel getEdgeStyle();

	/**
	 * <p>getMarkingStyle.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.MarkingStyleModel} object.
	 */
	MarkingStyleModel getMarkingStyle();

}
