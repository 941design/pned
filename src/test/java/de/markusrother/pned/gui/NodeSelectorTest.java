package de.markusrother.pned.gui;

import static org.junit.Assert.assertEquals;

import java.awt.Rectangle;

import org.junit.Test;

public class NodeSelectorTest {

	private static Rectangle r(final int x, final int y, final int w, final int h) {
		return new Rectangle(x, y, w, h);
	}

	private static Rectangle expected(final int x, final int y, final int w, final int h) {
		return r(x, y, w, h);
	}

	private static Rectangle resized(final Rectangle r, final int deltaX, final int deltaY) {
		return NodeSelector.resizeDragPanelBounds(r, deltaX, deltaY);
	}

	@Test
	public void testIdentity() {
		assertEquals(expected(1, 2, 3, 4), //
				resized(r(1, 2, 3, 4), 0, 0));
	}

	@Test
	public void testTranslateToRight() {
		assertEquals(expected(0, 0, 1, 0), //
				resized(r(0, 0, 0, 0), 1, 0));
		assertEquals(expected(0, 0, 11, 10), //
				resized(r(0, 0, 10, 10), 1, 0));
		assertEquals(expected(10, 10, 300, 100), //
				resized(r(10, 10, 100, 100), 200, 0));
	}

	@Test
	public void testTranslateToLeft() {
		assertEquals(expected(-1, 0, 1, 0), //
				resized(r(0, 0, 0, 0), -1, 0));
		assertEquals(expected(0, 0, 9, 10), //
				resized(r(0, 0, 10, 10), -1, 0));
		assertEquals(expected(10, 10, 100, 300), //
				resized(r(10, 10, 100, 100), 0, 200));
	}

	@Test
	public void testTranslateToBottom() {
		assertEquals(expected(0, 0, 0, 1), //
				resized(r(0, 0, 0, 0), 0, 1));
		assertEquals(expected(0, 0, 10, 11), //
				resized(r(0, 0, 10, 10), 0, 1));
	}

	@Test
	public void testTranslateToTop() {
		assertEquals(expected(0, -1, 0, 1), //
				resized(r(0, 0, 0, 0), 0, -1));
		assertEquals(expected(0, 0, 10, 9), //
				resized(r(0, 0, 10, 10), 0, -1));
	}

	@Test
	public void testCrossOriginHorizontally() {
		assertEquals(expected(-6, 0, 7, 10), //
				resized(r(1, 0, 3, 10), -10, 0));
	}

	@Test
	public void testCrossOriginVertically() {
		assertEquals(expected(0, -6, 10, 7), //
				resized(r(0, 1, 10, 3), 0, -10));
	}

	@Test
	public void testShrinkToZeroWidthAndHeight() {
		assertEquals(expected(1, 2, 0, 0), //
				resized(r(1, 2, 3, 4), -3, -4));
	}

}