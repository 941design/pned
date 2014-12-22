package de.markusrother.pned.core;

import java.awt.Point;

import javax.xml.bind.annotation.XmlAttribute;

public class GraphicsVO {

	public static class PositionVO {

		private int x;

		private int y;

		public PositionVO() {
			// NOTHING
		}

		public PositionVO(final Point point) {
			this.x = point.x;
			this.y = point.y;
		}

		@XmlAttribute
		public int getX() {
			return x;
		}

		public void setX(final int x) {
			this.x = x;
		}

		@XmlAttribute
		public int getY() {
			return y;
		}

		public void setY(final int y) {
			this.y = y;
		}

	}

	private PositionVO position;

	public GraphicsVO() {
		// NOTHING
	}

	public GraphicsVO(final Point point) {
		this.position = new PositionVO(point);
	}

	public PositionVO getPosition() {
		return position;
	}

	public void setPosition(final PositionVO position) {
		this.position = position;
	}

}
