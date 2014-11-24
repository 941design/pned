package de.markusrother.pned.gui.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

public class NewFileMenuItem extends JMenuItem {

	public static final JMenuItem INSTANCE = new NewFileMenuItem();

	private static final String NEW = "New";

	private NewFileMenuItem() {
		super(NEW);
		setMnemonic(KeyEvent.VK_N);
	}

}
