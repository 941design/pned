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

/**
 * <p>CreateTransitionAction class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class CreateTransitionAction extends AbstractNodeAction {

	/** Constant <code>label="Create transition"</code> */
	private static final String label = "Create transition";
	/** Constant <code>mnemonic=KeyEvent.VK_T</code> */
	private static final int mnemonic = KeyEvent.VK_T;

	/**
	 * <p>newMenuItem.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param locationProvider a {@link de.markusrother.pned.gui.menus.actions.LocationProvider} object.
	 * @return a {@link javax.swing.JRadioButtonMenuItem} object.
	 */
	public static JRadioButtonMenuItem newMenuItem(final EventBus eventMulticaster, final Object source,
			final LocationProvider locationProvider) {
		final CreateTransitionAction action = new CreateTransitionAction(eventMulticaster, source, locationProvider);
		final CustomRadioButtonMenuItem menuItem = new CustomRadioButtonMenuItem(action);
		menuItem.addItemListener(action);
		return menuItem;
	}

	/**
	 * <p>Constructor for CreateTransitionAction.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param locationProvider a {@link de.markusrother.pned.gui.menus.actions.LocationProvider} object.
	 */
	public CreateTransitionAction(final EventBus eventMulticaster, final Object source,
			final LocationProvider locationProvider) {
		super(eventMulticaster, source, locationProvider, mnemonic, label);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String nodeId = requestNodeId();
		eventBus.createTransition(new TransitionCreationCommand(source, nodeId, locationProvider.getLocation()));
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		setSelected(cmd.getMode() == TRANSITION);
	}

	/** {@inheritDoc} */
	@Override
	public void setSelected(final boolean enabled) {
		putValue(Action.NAME, label + (enabled ? " (default)" : ""));
	}

	/** {@inheritDoc} */
	@Override
	protected void selected() {
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(source, TRANSITION));
	}

}
