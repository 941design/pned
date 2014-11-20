package de.markusrother.swing;

import java.awt.Component;
import java.awt.Point;

public class HoverAdapter extends HoverListener {

	@Override
	protected boolean inHoverArea(final Point p) {
		return true;
	}

	@Override
	protected void startHover(final Component component) {
		// IGNORE
	}

	@Override
	protected void endHover(final Component component) {
		// IGNORE
	}

}
