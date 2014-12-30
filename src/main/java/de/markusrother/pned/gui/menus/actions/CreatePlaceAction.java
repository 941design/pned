package de.markusrother.pned.gui.menus.actions;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.SetNodeTypeCommand;
import de.markusrother.swing.CustomRadioButtonMenuItem;

/**
 * <p>CreatePlaceAction class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class CreatePlaceAction extends AbstractNodeAction {

	/** Constant <code>label="Create place"</code> */
	private static final String label = "Create place";
	/** Constant <code>mnemonic=KeyEvent.VK_P</code> */
	private static final int mnemonic = KeyEvent.VK_P;

	/**
	 * <p>newMenuItem.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param locationProvider a {@link de.markusrother.pned.gui.menus.actions.LocationProvider} object.
	 * @return a {@link javax.swing.JRadioButtonMenuItem} object.
	 */
	public static JRadioButtonMenuItem newMenuItem(final EventBus eventBus, final Object source,
			final LocationProvider locationProvider) {
		final CreatePlaceAction action = new CreatePlaceAction(eventBus, source, locationProvider);
		final CustomRadioButtonMenuItem menuItem = new CustomRadioButtonMenuItem(action);
		menuItem.addItemListener(action);
		return menuItem;
	}

	/**
	 * <p>Constructor for CreatePlaceAction.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.gui.EventBus} object.
	 * @param source a {@link java.lang.Object} object.
	 * @param locationProvider a {@link de.markusrother.pned.gui.menus.actions.LocationProvider} object.
	 */
	public CreatePlaceAction(final EventBus eventBus, final Object source, final LocationProvider locationProvider) {
		super(eventBus, source, locationProvider, mnemonic, label);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String nodeId = requestNodeId();
		eventBus.createPlace(new PlaceCreationCommand(source, nodeId, locationProvider.getLocation()));
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		setSelected(cmd.getMode() == PLACE);
	}

	/** {@inheritDoc} */
	@Override
	public void setSelected(final boolean enabled) {
		putValue(Action.NAME, label + (enabled ? " (default)" : ""));
	}

	/** {@inheritDoc} */
	@Override
	protected void selected() {
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(source, PLACE));
	}

}
