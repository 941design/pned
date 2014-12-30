package de.markusrother.pned.gui.events;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.markusrother.concurrent.Promise;
import de.markusrother.pned.gui.components.AbstractNode;

// TODO - Future stuff could go to subclass...
// FIXME - get rid of reference to AbstractNode!
public class EdgeCreationCommand extends ActionEvent {

	private static final String NO_EDGE_ID = null;

	private final String edgeId;
	private final String sourceId;
	private final String targetId;
	private final Promise<AbstractNode> sourceNodePromise;
	private final Promise<AbstractNode> targetNodePromise;

	public EdgeCreationCommand(final Object source, final String sourceId, final String targetId) {
		this(source, NO_EDGE_ID, sourceId, targetId);
	}

	public EdgeCreationCommand(final Object source, final String edgeId, final String sourceId, final String targetId) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.edgeId = edgeId;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.sourceNodePromise = new Promise<>();
		this.targetNodePromise = new Promise<>();
	}

	public String getEdgeId() {
		return edgeId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void fulfillSourceNodePromise(final AbstractNode node) {
		sourceNodePromise.fulfill(node);
	}

	public void fulfillTargetNodePromise(final AbstractNode node) {
		targetNodePromise.fulfill(node);
	}

	public AbstractNode getSourceNode() {
		// TODO - add timeout
		try {
			return sourceNodePromise.ask().get(500L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException e) {
			// TODO
			throw new RuntimeException("TODO");
		} catch (final TimeoutException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

	public AbstractNode getTargetNode() {
		// TODO - add timeout
		try {
			return targetNodePromise.ask().get(500L, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException e) {
			// TODO
			throw new RuntimeException("TODO");
		} catch (final TimeoutException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}
}
