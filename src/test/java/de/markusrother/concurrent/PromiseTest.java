package de.markusrother.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class PromiseTest {

	@Test
	public void testInstantiatePromise() {
		new Promise<String>();
	}

	@Test
	public void testGetFutureBeforeSetValue() {
		final Promise<String> promise = new Promise<>();
		final Future<String> future = promise.get();
		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
	}

	@Test
	public void testGetFutureAfterSetValue() throws Exception {
		final Promise<String> promise = new Promise<>();
		promise.set("foobar");
		final Future<String> future = promise.get();
		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
		assertEquals("foobar", future.get());
		assertTrue(future.isDone());
		assertFalse(future.isCancelled());
	}

	@Test
	public void testSetValue() {
		final Promise<String> promise = new Promise<>();
		promise.set("foobar");
	}

	@Test
	public void testSetAndGetValue() throws Exception {
		final Promise<String> promise = new Promise<>();
		promise.set("foobar");
		final Future<String> future = promise.get();
		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
		assertEquals("foobar", future.get());
		assertTrue(future.isDone());
		assertFalse(future.isCancelled());
	}

	@Test(expected = TimeoutException.class)
	public void testGetUnrealizedValue() throws Exception {
		final Promise<String> promise = new Promise<>();
		final Future<String> future = promise.get();
		future.get(1L, TimeUnit.SECONDS);
	}

}
