package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.actions.CreatePetriNetAction;
import de.markusrother.pned.gui.actions.OpenExportDialogAction;
import de.markusrother.pned.gui.actions.OpenImportDialogAction;
import de.markusrother.pned.gui.commands.PnCommandTarget;
import de.markusrother.pned.gui.dialogs.FileDialogFactory;

/**
 * <p>
 * PnedFileMenu class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnFileMenu extends JMenu {

	/** Constant <code>label="File"</code> */
	private static final String label = "File";

	/**
	 * <p>
	 * Constructor for PnedFileMenu.
	 * </p>
	 *
	 * @param commandTarget
	 *            a
	 *            {@link de.markusrother.pned.gui.commands.PnCommandTarget}
	 *            object.
	 * @param fileDialogFactory
	 *            a {@link de.markusrother.pned.gui.dialogs.FileDialogFactory}.
	 */
	PnFileMenu(final PnCommandTarget commandTarget, final FileDialogFactory fileDialogFactory) {
		super(label);
		setMnemonic(KeyEvent.VK_F);
		add(CreatePetriNetAction.newMenuItem(commandTarget));
		add(OpenImportDialogAction.newMenuItem(fileDialogFactory));
		add(OpenExportDialogAction.newMenuItem(fileDialogFactory));
	}

}
