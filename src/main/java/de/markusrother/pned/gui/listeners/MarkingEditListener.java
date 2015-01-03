package de.markusrother.pned.gui.listeners;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import de.markusrother.pned.core.commands.PlaceEditCommand;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.events.PlaceEventObject.Type;
import de.markusrother.pned.gui.components.Place;
import de.markusrother.pned.gui.events.NodeSelectionEvent;
import de.markusrother.swing.CheckedTextField;
import de.markusrother.swing.MultiClickListener;
import de.markusrother.swing.TextListener;

/**
 * <p>
 * MarkingEditListener class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class MarkingEditListener extends MultiClickListener
	implements
		NodeSelectionListener,
		TextListener {

	/** Constant <code>intPattern</code> */
	private static final Pattern intPattern = Pattern.compile("0|[1-9][0-9]*");

	CheckedTextField textField;

	final EventBus eventBus;

	/**
	 * <p>
	 * Constructor for MarkingEditListener.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	public MarkingEditListener(final EventBus eventBus) {
		super();
		this.eventBus = eventBus;
		// FIXME - dispose!!!
		eventBus.addListener(NodeSelectionListener.class, this);
	}

	/**
	 * <p>
	 * startEditMarking.
	 * </p>
	 *
	 * @param place
	 *            a {@link de.markusrother.pned.gui.components.Place} object.
	 * @param point
	 *            a {@link java.awt.Point} object.
	 */
	private void startEditMarking(final Place place, final Point point) {
		final CheckedTextField textField = createAndAddTextField(place, point);
		// TODO - suspend other listeners!
		// TODO - broadcast start edit event to notify other components to end
		// editing on click!
		textField.addTextListener(this);
		textField.addTextListener(new TextListener() {
			@Override
			public void textEntered(final ActionEvent e) {
				final String text = textField.getText();
				eventBus.setMarking(new PlaceEditCommand(this, Type.SET_MARKING, place.getId(), Integer.valueOf(text)));
			}

			@Override
			public void cancel(final AWTEvent e) {
				// TODO
				throw new RuntimeException("TODO");
			}
		});
	}

	/**
	 * <p>
	 * finishEditMarking.
	 * </p>
	 */
	private void finishEditMarking() {
		final Container parent = textField.getParent();
		parent.remove(textField);
		parent.invalidate();
		parent.repaint();
		textField = null;
	}

	/**
	 * <p>
	 * abortEditMarking.
	 * </p>
	 */
	public void abortEditMarking() {
		// TODO - alternatively listen for node selection and edge creation
		// events!
		if (textField != null) {
			finishEditMarking();
		}
	}

	/**
	 * <p>
	 * createAndAddTextField.
	 * </p>
	 *
	 * @param place
	 *            a {@link de.markusrother.pned.gui.components.Place} object.
	 * @param point
	 *            a {@link java.awt.Point} object.
	 * @return a {@link de.markusrother.swing.CheckedTextField} object.
	 */
	private CheckedTextField createAndAddTextField(final Place place, final Point point) {
		final Container parent = place.getParent();
		final Point origin = place.getLocation();
		origin.translate(point.x, point.y);
		final String marking = String.valueOf(place.getMarking());
		textField = new CheckedTextField(marking, intPattern, marking.length() + 10);
		textField.setBounds(new Rectangle(origin, textField.getPreferredSize()));
		parent.add(textField);
		parent.invalidate();
		parent.repaint();
		return textField;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedLeft(final MouseEvent e) {
		abortEditMarking();
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDoubleClickedLeft(final MouseEvent e) {
		abortEditMarking();
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedRight(final MouseEvent e) {
		if (e.getComponent() instanceof Place) {
			if (textField != null) {
				abortEditMarking();
			}
			final Place place = (Place) e.getComponent();
			startEditMarking(place, e.getPoint());
		} else {
			abortEditMarking();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedMiddle(final MouseEvent e) {
		abortEditMarking();
	}

	/** {@inheritDoc} */
	@Override
	public void textEntered(final ActionEvent e) {
		finishEditMarking();
	}

	/** {@inheritDoc} */
	@Override
	public void nodesSelected(final NodeSelectionEvent event) {
		abortEditMarking();
	}

	/** {@inheritDoc} */
	@Override
	public void nodesUnselected(final NodeSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionFinished(final NodeSelectionEvent event) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void nodeSelectionCancelled(final NodeSelectionEvent event) {
		// IGNORE
	}

	@Override
	public void cancel(final AWTEvent e) {
		// TODO
		throw new RuntimeException("TODO");
	}

}
