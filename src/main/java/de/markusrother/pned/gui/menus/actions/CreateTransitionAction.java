package de.markusrother.pned.gui.menus.actions;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;

import de.markusrother.pned.gui.events.TransitionCreationRequest;

public class CreateTransitionAction extends AbstractAction {

	private static final String label = "Create transition";
	private static final int mnemonic = KeyEvent.VK_T;

	public static JMenuItem newMenuItem(final Object source, final LocationProvider locationProvider) {
		return new JMenuItem(new CreateTransitionAction(source, locationProvider));
	}

	private final Object source;
	private final LocationProvider locationProvider;

	public CreateTransitionAction(final Object source, final LocationProvider locationProvider) {
		super(label);
		this.source = source;
		this.locationProvider = locationProvider;
		putValue(Action.MNEMONIC_KEY, mnemonic);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		eventBus.createTransition(new TransitionCreationRequest(source, locationProvider.getLocation()));
	}

}
