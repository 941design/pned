package de.markusrother.pned.gui.menus;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.menus.actions.OpenExportDialogAction;
import de.markusrother.pned.gui.menus.actions.OpenImportDialogAction;
import de.markusrother.pned.gui.menus.actions.CreatePetriNetAction;

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
	 * @param eventMulticaster
	 *            a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	PnedFileMenu(final EventBus eventMulticaster) {
		super(label);
		setMnemonic(KeyEvent.VK_F);
		add(CreatePetriNetAction.newMenuItem(eventMulticaster));
		add(OpenImportDialogAction.newMenuItem(eventMulticaster));
		add(OpenExportDialogAction.newMenuItem(eventMulticaster));
	}

}
