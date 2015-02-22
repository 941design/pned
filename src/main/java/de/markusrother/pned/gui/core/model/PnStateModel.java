package de.markusrother.pned.gui.core.model;

import java.io.File;
import java.util.Set;

import de.markusrother.pned.gui.control.PnState.NewNodeType;

/**
 * <p>
 * Model describing a Petri net's current visualization's state.
 * </p>
 * <p>
 * It is expected that this models state is updated by GUI interactions, hence
 * there are no setters.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.pned.gui.control.PnEventBus
 */
public interface PnStateModel {

	/**
	 * <p>
	 * Gets the current directory.
	 * </p>
	 *
	 * @return a {@link java.io.File} object.
	 */
	File getCurrentDirectory();

	/**
	 * <p>
	 * Gets current node creation mode, either
	 * {@link de.markusrother.pned.gui.control.PnState.NewNodeType#PLACE} or
	 * {@link de.markusrother.pned.gui.control.PnState.NewNodeType#TRANSITION} .
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.control.PnState.NewNodeType}
	 *         object.
	 */
	NewNodeType getNewNodeType();

	/**
	 * <p>
	 * Returns true if any nodes are currently selected.
	 * </p>
	 *
	 * @return a boolean.
	 */
	boolean areNodesSelected();

	/**
	 * <p>
	 * Returns true if any currently selected nodes are input nodes.
	 * </p>
	 *
	 * @return a boolean.
	 */
	boolean areSourceNodesSelected();

	/**
	 * <p>
	 * Returns true if any currently selected nodes are output nodes.
	 * </p>
	 *
	 * @return a boolean.
	 */
	boolean areTargetNodesSelected();

	/**
	 * <p>
	 * Returns unique identifiers of all currently selected nodes.
	 * </p>
	 *
	 * @return a {@link java.util.Set} of {@link java.lang.String}s.
	 */
	Set<String> getSelectedNodeIds();

	/**
	 * <p>
	 * Returns unique identifiers of all currently selected nodes that are input
	 * nodes.
	 * </p>
	 *
	 * @return a {@link java.util.Set} of {@link java.lang.String}s.
	 */
	Set<String> getSelectedSourceNodeIds();

	/**
	 * <p>
	 * Returns unique identifiers of all currently selected nodes that are
	 * output nodes.
	 * 
	 * </p>
	 *
	 * @return a {@link java.util.Set} of {@link java.lang.String}s.
	 */
	Set<String> getSelectedTargetNodeIds();

	/**
	 * <p>
	 * Returns unique identifiers of all selected nodes incoming edges if any.
	 * </p>
	 *
	 * @return a {@link java.util.Set} of {@link java.lang.String}s.
	 */
	Set<String> getSelectedIncomingEdgeIds();

	/**
	 * <p>
	 * Returns unique identifiers of all selected nodes outgoing edges if any.
	 * </p>
	 *
	 * @return a {@link java.util.Set} of {@link java.lang.String}s.
	 */
	Set<String> getSelectedOutgoingEdgeIds();

	/**
	 * <p>getSelectedNodesEdgeIds.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	Set<String> getSelectedNodesEdgeIds();

	/**
	 * <p>
	 * Returns current place styling.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.NodeStyleModel}
	 *         object.
	 */
	NodeStyleModel getPlaceStyle();

	/**
	 * <p>
	 * Returns current transition styling.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.NodeStyleModel}
	 *         object.
	 */
	NodeStyleModel getTransitionStyle();

	/**
	 * <p>
	 * Returns current edge styling.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.EdgeStyleModel}
	 *         object.
	 */
	EdgeStyleModel getEdgeStyle();

	/**
	 * <p>
	 * Returns current marking styling.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.core.model.MarkingStyleModel}
	 *         object.
	 */
	MarkingStyleModel getMarkingStyle();

	/**
	 * <p>isDirty.</p>
	 *
	 * @return a boolean.
	 */
	boolean isDirty();

	/**
	 * <p>setDirty.</p>
	 *
	 * @param b a boolean.
	 */
	void setDirty(boolean b);

}
