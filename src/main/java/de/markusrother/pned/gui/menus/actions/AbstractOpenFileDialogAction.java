package de.markusrother.pned.gui.menus.actions;

import de.markusrother.pned.gui.dialogs.FileDialogFactory;

public abstract class AbstractOpenFileDialogAction extends AbstractOpenDialogAction {

	protected final FileDialogFactory fileDialogFactory;

	protected AbstractOpenFileDialogAction(final FileDialogFactory fileDialogFactory, final String label,
			final int mnemonic) {
		super(fileDialogFactory.getEventTarget(), label, mnemonic);
		this.fileDialogFactory = fileDialogFactory;
	}

}
