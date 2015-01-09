package de.markusrother.pned.gui.actions;

import de.markusrother.pned.gui.components.dialogs.FileDialogFactory;

/**
 * <p>
 * Abstract superclass for actions opening file dialogs upon performing.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
abstract class AbstractOpenFileDialogAction extends AbstractStatelessAction {

	protected final FileDialogFactory fileDialogFactory;

	/**
	 * <p>
	 * Constructor for AbstractOpenFileDialogAction.
	 * </p>
	 *
	 * @param fileDialogFactory
	 *            a
	 *            {@link de.markusrother.pned.gui.components.dialogs.FileDialogFactory}
	 *            - a creator of file dialogs.
	 * @param name
	 *            a {@link java.lang.String} - a textual representation of this
	 *            action.
	 * @param mnemonic
	 *            a int.
	 */
	protected AbstractOpenFileDialogAction(final FileDialogFactory fileDialogFactory, final String name,
			final int mnemonic) {
		// TODO - The factory could be retrieved from PnState.
		super(fileDialogFactory.getCommandTarget(), name, mnemonic);
		this.fileDialogFactory = fileDialogFactory;
	}

}
