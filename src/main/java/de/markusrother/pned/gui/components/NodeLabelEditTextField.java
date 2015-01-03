package de.markusrother.pned.gui.components;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

import de.markusrother.pned.core.commands.LabelEditCommand;
import de.markusrother.pned.core.commands.LabelEditCommand.Type;
import de.markusrother.pned.gui.listeners.GuiCommandTarget;
import de.markusrother.swing.CheckedTextField;
import de.markusrother.swing.TextListener;

public class NodeLabelEditTextField extends CheckedTextField
	implements
		TextListener {

	// TODO - move
	public final static Pattern labelPattern = Pattern.compile("[\\w\\s\\d]*");

	private final GuiCommandTarget eventTarget;

	private final NodeLabel label;

	public NodeLabelEditTextField(final GuiCommandTarget eventTarget, final NodeLabel label) {
		super(label.getText(), labelPattern, label.getText().length() + 10);
		this.eventTarget = eventTarget;
		this.label = label;
		addTextListener(this);
	}

	@Override
	public void textEntered(final ActionEvent e) {
		eventTarget.setLabel(new LabelEditCommand(this, //
				Type.SET_LABEL, //
				label.getNodeId(), //
				getText()));
		removeFromParent();
	}

	@Override
	public void cancel(final AWTEvent e) {
		removeFromParent();
	}

	private void removeFromParent() {
		label.cancelEditLabel();
	}

}
