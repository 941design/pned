package de.markusrother.pned.gui.menus.items;

import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

public class NewNetMenuItem extends JMenuItem {

	public static final JMenuItem INSTANCE = new NewNetMenuItem();
	private static final String label = "New";

	private NewNetMenuItem() {
		super(label);
		setMnemonic(KeyEvent.VK_N);
	}

}
