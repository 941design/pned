package de.markusrother.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class PromiseTest {

	@Test
	public void testInstantiatePromise() {
		final @SuppressWarnings("unused") Promise<String> promise = new Promise<>();
	}

	@Test
	public void testGetFutureBeforeSetValue() {
		final Promise<String> promise = new Promise<>();
		final Future<String> future = promise.ask();
		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
	}

	@Test
	public void testGetFutureAfterSetValue() throws Exception {
		final Promise<String> promise = new Promise<>();
		promise.fulfill("foobar");
		final Future<String> future = promise.ask();
		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
		assertEquals("foobar", future.get(1L, TimeUnit.SECONDS));
		assertTrue(future.isDone());
		assertFalse(future.isCancelled());
	}

	@Test
	public void testSetValue() {
		final Promise<String> promise = new Promise<>();
		promise.fulfill("foobar");
	}

	@Test
	public void testSettingValueFulfillsPromise() {
		final Promise<String> promise = new Promise<>();
		promise.fulfill("foobar");
		assertTrue(promise.isFulfilled());
	}

	@Test
	public void testSettingNullValueFulfillsPromise() {
		final Promise<String> promise = new Promise<>();
		promise.fulfill((String) null);
		assertTrue(promise.isFulfilled());
	}

	@Test
	public void testSetAndGetValue() throws Exception {
		final Promise<String> promise = new Promise<>();
		promise.fulfill("foobar");
		final Future<String> future = promise.ask();
		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
		assertEquals("foobar", future.get(1L, TimeUnit.SECONDS));
		assertTrue(future.isDone());
		assertFalse(future.isCancelled());
	}

	@Test(expected = TimeoutException.class)
	public void testGetUnrealizedValue() throws Exception {
		final Promise<String> promise = new Promise<>();
		final Future<String> future = promise.ask();
		future.get(1L, TimeUnit.SECONDS);
	}

	@Test
	public void testAskforNullValue() throws Exception {
		final Promise<String> promise = new Promise<>();
		promise.fulfill((String) null);
		final Future<String> future = promise.ask();
		assertEquals(null, future.get(1L, TimeUnit.SECONDS));
		assertTrue(future.isDone());
	}

	@Test
	public void testAskTwice() throws Exception {
		final Promise<String> promise = new Promise<>();
		promise.fulfill("foobar");
		promise.ask();
		final Future<String> future = promise.ask();
		assertFalse(future.isDone());
		assertFalse(future.isCancelled());
		assertEquals("foobar", future.get(1L, TimeUnit.SECONDS));
		assertTrue(future.isDone());
		assertFalse(future.isCancelled());
	}

	@Test(expected = RejectedExecutionException.class)
	public void testFulfillTwice() {
		final Promise<String> promise = new Promise<>();
		promise.fulfill("foo");
		promise.fulfill("bar");
	}

}
