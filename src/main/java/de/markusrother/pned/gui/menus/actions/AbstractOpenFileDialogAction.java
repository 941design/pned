package de.markusrother.pned.gui.menus.actions;

import de.markusrother.pned.gui.dialogs.FileDialogFactory;

/**
 * <p>Abstract AbstractOpenFileDialogAction class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractOpenFileDialogAction extends AbstractOpenDialogAction {

	protected final FileDialogFactory fileDialogFactory;

	/**
	 * <p>Constructor for AbstractOpenFileDialogAction.</p>
	 *
	 * @param fileDialogFactory a {@link de.markusrother.pned.gui.dialogs.FileDialogFactory} object.
	 * @param label a {@link java.lang.String} object.
	 * @param mnemonic a int.
	 */
	protected AbstractOpenFileDialogAction(final FileDialogFactory fileDialogFactory, final String label,
			final int mnemonic) {
		super(fileDialogFactory.getEventTarget(), label, mnemonic);
		this.fileDialogFactory = fileDialogFactory;
	}

}
