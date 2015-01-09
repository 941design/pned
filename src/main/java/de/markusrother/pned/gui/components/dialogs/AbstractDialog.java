package de.markusrother.pned.gui.components.dialogs;

import javax.swing.JDialog;

import de.markusrother.pned.gui.control.commands.PnCommandTarget;

/**
 * <p>
 * Abstract superclass for event producing dialogs.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class AbstractDialog extends JDialog {

	/** The event target to be posted to. */
	protected final PnCommandTarget commandTarget;

	/**
	 ** <p>
	 * Constructor for AbstractDialog.
	 * </p>
	 *
	 * @param title
	 *            a {@link java.lang.String} - this dialogs title.
	 * @param commandTarget a {@link de.markusrother.pned.gui.control.commands.PnCommandTarget} object.
	 */
	protected AbstractDialog(final PnCommandTarget commandTarget, final String title) {
		this.commandTarget = commandTarget;
		setTitle(title);
	}

}
