package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.actions.CreatePetriNetAction;
import de.markusrother.pned.gui.actions.OpenExportDialogAction;
import de.markusrother.pned.gui.actions.OpenImportDialogAction;
import de.markusrother.pned.gui.dialogs.FileDialogFactory;
import de.markusrother.pned.gui.events.GuiEventTarget;

/**
 * <p>
 * PnedFileMenu class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnedFileMenu extends JMenu {

	/** Constant <code>label="File"</code> */
	private static final String label = "File";

	/**
	 * <p>
	 * Constructor for PnedFileMenu.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 * @param fileDialogFactory
	 *            a {@link FileDialogFactory}.
	 */
	PnedFileMenu(final GuiEventTarget eventTarget, final FileDialogFactory fileDialogFactory) {
		super(label);
		setMnemonic(KeyEvent.VK_F);
		add(CreatePetriNetAction.newMenuItem(eventTarget));
		add(OpenImportDialogAction.newMenuItem(fileDialogFactory));
		add(OpenExportDialogAction.newMenuItem(fileDialogFactory));
	}

}
