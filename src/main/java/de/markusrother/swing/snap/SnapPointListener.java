package de.markusrother.swing.snap;

import java.util.EventListener;

public interface SnapPointListener extends EventListener {

	void snapPointMoved(int deltaX, int deltaY);

}
