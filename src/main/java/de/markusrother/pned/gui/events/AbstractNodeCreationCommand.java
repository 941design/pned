package de.markusrother.pned.gui.events;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.markusrother.concurrent.Promise;

public abstract class AbstractNodeCreationCommand extends ActionEvent {

	public static final Point defaultNodeOrigin = new Point(100, 100);

	private final Point point;
	private final Promise<String> nodeIdPromise;

	public AbstractNodeCreationCommand(final Object source, final String nodeId) {
		this(source, Promise.fulfilled(nodeId), defaultNodeOrigin);
	}

	public AbstractNodeCreationCommand(final Object source, final Point point) {
		this(source, new Promise<String>(), point);
	}

	public AbstractNodeCreationCommand(final Object source, final Promise<String> nodeIdPromise, final Point point) {
		super(source, ActionEvent.ACTION_PERFORMED, "no command string");
		this.nodeIdPromise = nodeIdPromise;
		this.point = point;
	}

	public String getNodeId() {
		final Future<String> id = nodeIdPromise.ask();
		try {
			// TODO - to constant:
			return id.get(500L, TimeUnit.MILLISECONDS);
		} catch (final InterruptedException e) {
			// TODO
			throw new RuntimeException("TODO");
		} catch (final ExecutionException e) {
			// TODO
			throw new RuntimeException("TODO");
		} catch (final TimeoutException e) {
			// FIXME - create custom NoIdProvidedException();
			throw new RuntimeException("TODO");
		}
	}

	public Point getPoint() {
		return point.getLocation();
	}

	public void fulfillNodeIdPromise(final String value) {
		nodeIdPromise.fulfill(value);
	}

	public boolean hasNodeId() {
		return nodeIdPromise.isFulfilled();
	}

}
