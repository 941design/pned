package de.markusrother.pned.gui.menus.actions;

import static de.markusrother.pned.gui.NodeCreationMode.TRANSITION;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.pned.gui.events.TransitionCreationCommand;
import de.markusrother.swing.CustomRadioButtonMenuItem;

public class CreateTransitionAction extends AbstractNodeAction {

	private static final String label = "Create transition";
	private static final int mnemonic = KeyEvent.VK_T;

	public static JRadioButtonMenuItem newMenuItem(final EventBus eventMulticaster, final Object source,
			final LocationProvider locationProvider) {
		final CreateTransitionAction action = new CreateTransitionAction(eventMulticaster, source, locationProvider);
		final CustomRadioButtonMenuItem menuItem = new CustomRadioButtonMenuItem(action);
		menuItem.addItemListener(action);
		return menuItem;
	}

	public CreateTransitionAction(final EventBus eventMulticaster, final Object source,
			final LocationProvider locationProvider) {
		super(eventMulticaster, source, locationProvider, mnemonic, label);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String nodeId = requestNodeId();
		eventBus.createTransition(new TransitionCreationCommand(source, nodeId, locationProvider.getLocation()));
	}

	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		setSelected(cmd.getMode() == TRANSITION);
	}

	@Override
	public void setSelected(final boolean enabled) {
		putValue(Action.NAME, label + (enabled ? " (default)" : ""));
	}

	@Override
	protected void selected() {
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(source, TRANSITION));
	}

}
