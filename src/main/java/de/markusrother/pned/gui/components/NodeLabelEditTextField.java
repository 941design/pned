package de.markusrother.pned.gui.components;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import de.markusrother.pned.core.commands.LabelEditCommand;
import de.markusrother.pned.core.commands.LabelEditCommand.Type;
import de.markusrother.pned.gui.listeners.GuiCommandTarget;
import de.markusrother.swing.CheckedTextField;
import de.markusrother.swing.TextListener;

/**
 * <p>NodeLabelEditTextField class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeLabelEditTextField extends CheckedTextField
	implements
		TextListener {

	// TODO - move
	/** Constant <code>labelPattern</code> */
	public final static Pattern labelPattern = Pattern.compile("[\\w\\s\\d]*");

	private final GuiCommandTarget eventTarget;

	private final NodeLabel label;

	/**
	 * <p>Constructor for NodeLabelEditTextField.</p>
	 *
	 * @param eventTarget a {@link de.markusrother.pned.gui.listeners.GuiCommandTarget} object.
	 * @param label a {@link de.markusrother.pned.gui.components.NodeLabel} object.
	 */
	public NodeLabelEditTextField(final GuiCommandTarget eventTarget, final NodeLabel label) {
		super(label.getText(), labelPattern, label.getText().length() + 10);
		this.eventTarget = eventTarget;
		this.label = label;
		addTextListener(this);
	}

	/** {@inheritDoc} */
	@Override
	public void textEntered(final ActionEvent e) {
		eventTarget.setLabel(new LabelEditCommand(this, //
				Type.SET_LABEL, //
				label.getNodeId(), //
				getText()));
		removeFromParent();
	}

	/** {@inheritDoc} */
	@Override
	public void cancel(final AWTEvent e) {
		removeFromParent();
	}

	private void removeFromParent() {
		label.cancelEditLabel();
	}

}
