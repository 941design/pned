package de.markusrother.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

public abstract class AbstractRightClickTextFieldEditor<T extends Component> extends RightClickListener
	implements
		TextListener {

	private static final int trailingWS = 10;

	private final Class<? extends T> type;
	private final Pattern pattern;

	private T editedObject;
	private CheckedTextField textField;

	protected AbstractRightClickTextFieldEditor(final Class<? extends T> type, final Pattern pattern) {
		this.type = type;
		this.pattern = pattern;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClickedRight(final MouseEvent e) {
		if (!type.isInstance(e.getComponent())) {
			return;
		}
		final T source = type.cast(e.getSource());
		if (editedObject == source) {
			return;
		} else {
			doCancelEdit();
		}
		doStartEdit(source, e);
	}

	private void doStartEdit(final T source, final MouseEvent e) {
		editedObject = source;

		final String initialText = getInitialText(editedObject);

		textField = new CheckedTextField(initialText, pattern, initialText.length() + trailingWS);
		textField.setBounds(new Rectangle(editedObject.getLocation(), textField.getPreferredSize()));
		textField.addTextListener(this);
		addTextField(editedObject, textField);

		startEdit(source, e);

		textField.requestFocus();
	}

	public abstract String getInitialText(T editedObject);

	public abstract void startEdit(T editedObject, MouseEvent e);

	public abstract void finishEdit(T editedObject, String text);

	protected void doCancelEdit() {
		if (editedObject != null) {
			cancelEdit(editedObject);
			removeTextField(editedObject, textField);
			editedObject = null;
			textField = null;
		}
	}

	public abstract void cancelEdit(T editedObject);

	public abstract void addTextField(T editedObject, CheckedTextField textField);

	public abstract void removeTextField(T editedObject, CheckedTextField textField);

	@Override
	public void textEntered(final ActionEvent e) {
		finishEdit(editedObject, textField.getText());
		removeTextField(editedObject, textField);
		editedObject = null;
		textField = null;
	}

	@Override
	public void cancel(final AWTEvent e) {
		doCancelEdit();
	}

}
