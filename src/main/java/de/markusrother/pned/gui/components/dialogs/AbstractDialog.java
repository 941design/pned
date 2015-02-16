package de.markusrother.pned.gui.components.dialogs;

import javax.swing.JDialog;

import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.core.model.PnStateModel;

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
	protected final PnStateModel state;

	/**
	 ** <p>
	 * Constructor for AbstractDialog.
	 * </p>
	 *
	 * @param title
	 *            a {@link java.lang.String} - this dialogs title.
	 * @param commandTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            object.
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} object.
	 * @param modal
	 *            a boolean.
	 */
	protected AbstractDialog(final PnStateModel state,
			final PnCommandTarget commandTarget,
			final String title,
			final boolean modal) {
		this.state = state;
		this.commandTarget = commandTarget;
		setTitle(title);
		setModal(modal);
	}

}
