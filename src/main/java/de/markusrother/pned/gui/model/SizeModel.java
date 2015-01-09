package de.markusrother.pned.gui.model;

import de.markusrother.swing.ChangeEventSource;

public interface SizeModel
	extends
		ChangeEventSource {

	/**
	 * <p>
	 * getSize.
	 * </p>
	 *
	 * @return a int.
	 */
	int getSize();

	/**
	 * <p>
	 * setSize.
	 * </p>
	 *
	 * @param size
	 *            a int.
	 */
	void setSize(int size);

}
