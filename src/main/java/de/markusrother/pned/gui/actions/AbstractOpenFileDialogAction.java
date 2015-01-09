package de.markusrother.pned.gui.actions;

import de.markusrother.pned.gui.components.dialogs.FileDialogFactory;

/**
 * <p>
 * Abstract AbstractOpenFileDialogAction class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
abstract class AbstractOpenFileDialogAction extends AbstractOpenDialogAction {

	protected final FileDialogFactory fileDialogFactory;

	/**
	 * <p>
	 * Constructor for AbstractOpenFileDialogAction.
	 * </p>
	 *
	 * @param fileDialogFactory
	 *            a {@link de.markusrother.pned.gui.components.dialogs.FileDialogFactory}
	 *            object.
	 * @param label
	 *            a {@link java.lang.String} object.
	 * @param mnemonic
	 *            a int.
	 */
	protected AbstractOpenFileDialogAction(final FileDialogFactory fileDialogFactory, final String label,
			final int mnemonic) {
		super(fileDialogFactory.getCommandTarget(), label, mnemonic);
		this.fileDialogFactory = fileDialogFactory;
	}

}
