package de.markusrother.pned.gui.dialogs;

import javax.swing.JDialog;

import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * Abstract superclass for event producing dialogs.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class AbstractDialog extends JDialog {

	/**
	 * The event target to be posted to.
	 */
	protected final GuiEventTarget eventTarget;

	/**
	 ** <p>
	 * Constructor for AbstractDialog.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.GuiEventTarget} object.
	 * @param title a {@link java.lang.String} object.
	 */
	protected AbstractDialog(final GuiEventTarget eventTarget, final String title) {
		this.eventTarget = eventTarget;
		setTitle(title);
	}

}
