package de.markusrother.pned.gui.actions;

import static de.markusrother.pned.gui.NodeCreationMode.PLACE;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JRadioButtonMenuItem;

import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.swing.CustomRadioButtonMenuItem;

/**
 * <p>
 * Class for compound actions: Setting the default node type to place,
 * <b>and</b> creating a place. This action combines an
 * {@link java.awt.event.ActionListener} with an
 * {@link java.awt.event.ItemListener} for selectable items. It can be used for
 * e.g. {@link javax.swing.JRadioButtonMenuItem}s where toggle and selection
 * (click) trigger separate {@link java.awt.event.ActionEvent}s, such as in
 * {@link #newMenuItem(GuiEventBus, Object, LocationProvider)}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see CustomRadioButtonMenuItem
 */
public class CreatePlaceAction extends AbstractCreateNodeAction {

	/** Constant <code>label="Create place"</code> */
	private static final String label = "Create place";
	/** Constant <code>mnemonic=KeyEvent.VK_P</code> */
	private static final int mnemonic = KeyEvent.VK_P;

	/**
	 * <p>
	 * Creates and returns a {@link javax.swing.JRadioButtonMenuItem} where
	 * toggle and click on label trigger separate actions as described here
	 * {@link de.markusrother.pned.gui.actions.CreatePlaceAction}.
	 * </p>
	 *
	 * @param eventBus
	 *            an {@link de.markusrother.pned.gui.control.GuiEventBus} to be
	 *            posted to.
	 * @param source
	 *            an {@link java.lang.Object} - the posted
	 *            {@link java.util.EventObject}s' source.
	 * @param locationProvider
	 *            a {@link de.markusrother.pned.gui.actions.LocationProvider} to
	 *            provide coordinates for newly created nodes.
	 * @return a {@link javax.swing.JRadioButtonMenuItem} with this action
	 *         bound.
	 * @see CustomRadioButtonMenuItem
	 */
	public static JRadioButtonMenuItem newMenuItem(final GuiEventBus eventBus, final Object source,
			final LocationProvider locationProvider) {
		final CreatePlaceAction action = new CreatePlaceAction(eventBus, source, locationProvider);
		final CustomRadioButtonMenuItem menuItem = new CustomRadioButtonMenuItem(action);
		menuItem.addItemListener(action);
		return menuItem;
	}

	/**
	 * <p>
	 * Constructor for CreatePlaceAction.
	 * </p>
	 *
	 * @param eventBus
	 *            an {@link de.markusrother.pned.gui.control.GuiEventBus} to be
	 *            posted to.
	 * @param source
	 *            an {@link java.lang.Object} - the posted
	 *            {@link java.util.EventObject}s' source.
	 * @param locationProvider
	 *            a {@link de.markusrother.pned.gui.actions.LocationProvider} to
	 *            provide coordinates for newly created nodes.
	 */
	private CreatePlaceAction(final GuiEventBus eventBus, final Object source, final LocationProvider locationProvider) {
		super(eventBus, source, locationProvider, mnemonic, label);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String nodeId = eventBus.requestId();
		eventBus.createPlace(new PlaceCreationCommand(source, nodeId, locationProvider.getLocation()));
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentNodeType(final SetNodeTypeCommand cmd) {
		setSelected(cmd.getMode() == PLACE);
	}

	/** {@inheritDoc} */
	@Override
	public void setSelected(final boolean selected) {
		putValue(Action.NAME, label + (selected ? " (default)" : ""));
	}

	/** {@inheritDoc} */
	@Override
	protected void fireSetNodeTypeCommand() {
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(source, PLACE));
	}

}
