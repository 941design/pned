package de.markusrother.pned.gui.listeners;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import de.markusrother.pned.core.commands.PlaceCreationCommand;
import de.markusrother.pned.core.commands.PlaceEditCommand;
import de.markusrother.pned.core.commands.TransitionCreationCommand;
import de.markusrother.pned.core.listeners.NodeCreationListener;
import de.markusrother.pned.gui.components.Place;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.RightClickTextFieldEdit;
import de.markusrother.swing.CheckedTextField;

/**
 * <p>
 * MarkingEditListener class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class MarkingEditor extends RightClickTextFieldEdit<Place>
	implements
		NodeCreationListener,
		NodeSelectionListener {

	/** Constant <code>intPattern</code> */
	public static final Pattern markingPattern = Pattern.compile("0|[1-9][0-9]*");

	private final GuiEventBus eventBus;

	/**
	 * <p>
	 * Constructor for MarkingEditListener.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	public MarkingEditor(final GuiEventBus eventBus) {
		super(Place.class, markingPattern);

		this.eventBus = eventBus;

		// FIXME - dispose!
		eventBus.addListener(NodeCreationListener.class, this);
		eventBus.addListener(NodeSelectionListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getInitialText(final Place place) {
		return String.valueOf(place.getMarking());
	}

	/** {@inheritDoc} */
	@Override
	public void startEdit(final Place place, final MouseEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void cancelEdit(final Place place) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void finishEdit(final Place place, final String text) {
		eventBus.setMarking(new PlaceEditCommand(this, //
				PlaceEditCommand.Type.SET_MARKING, //
				place.getId(), //
				Integer.valueOf(text))); // Validated by markingPattern!
	}

	/** {@inheritDoc} */
	@Override
	public void addTextField(final Place place, final CheckedTextField textField) {
		// TODO - set location
		final Container parent = place.getParent();
		parent.add(textField);
		parent.invalidate();
		parent.repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void removeTextField(final Place place, final CheckedTextField textField) {
		final Container parent = place.getParent();
		parent.remove(textField);
		parent.invalidate();
		parent.repaint();
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent e) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void createPlace(final PlaceCreationCommand cmd) {
		doCancelEdit();
	}

	/** {@inheritDoc} */
	@Override
	public void createTransition(final TransitionCreationCommand cmd) {
		doCancelEdit();
	}

}
