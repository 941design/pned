package de.markusrother.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

/**
 * <p>
 * A pseudo popup, displaying a validated text field.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see de.markusrother.swing.CheckedTextField
 */
public abstract class RightClickTextFieldEdit<T extends Component> extends RightClickListener
    implements
        TextListener {

    /** Constant <code>SUCCESSFUL=true</code> */
    protected static final boolean SUCCESSFUL = true;
    /** Constant <code>NOT_SUCCESSFUL=false</code> */
    protected static final boolean NOT_SUCCESSFUL = false;

    /** Constant <code>trailingWS=10</code> */
    private static final int trailingWS = 10;

    private final Class<? extends T> type;
    private final Pattern pattern;

    private T editedObject;
    private CheckedTextField textField;

    /**
     * <p>
     * Constructor for AbstractRightClickTextFieldEditor.
     * </p>
     *
     * @param type
     *            a {@link java.lang.Class} object.
     * @param pattern
     *            a {@link java.util.regex.Pattern} object.
     */
    protected RightClickTextFieldEdit(final Class<? extends T> type, final Pattern pattern) {
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

    /**
     * <p>
     * doStartEdit.
     * </p>
     *
     * @param source
     *            a T object.
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
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

    /**
     * <p>
     * getInitialText.
     * </p>
     *
     * @param editedObject
     *            a T object.
     * @return a {@link java.lang.String} object.
     */
    public abstract String getInitialText(T editedObject);

    /**
     * <p>
     * startEdit.
     * </p>
     *
     * @param editedObject
     *            a T object.
     * @param editedObject
     *            a T object.
     * @param e
     *            a {@link java.awt.event.MouseEvent} object.
     */
    public abstract void startEdit(T editedObject, MouseEvent e);

    /**
     * <p>
     * finishEdit.
     * </p>
     *
     * @param editedObject
     *            a T object.
     * @param text
     *            a {@link java.lang.String} object.
     * @return a boolean.
     */
    public abstract boolean finishEdit(T editedObject, String text);

    /**
     * <p>
     * doCancelEdit.
     * </p>
     */
    protected void doCancelEdit() {
        if (editedObject != null) {
            cancelEdit(editedObject);
            removeTextField(editedObject, textField);
            editedObject = null;
            textField = null;
        }
    }

    /**
     * <p>
     * cancelEdit.
     * </p>
     *
     * @param editedObject
     *            a T object.
     */
    public abstract void cancelEdit(T editedObject);

    /**
     * <p>
     * addTextField.
     * </p>
     *
     * @param editedObject
     *            a T object.
     * @param textField
     *            a {@link de.markusrother.swing.CheckedTextField} object.
     */
    public abstract void addTextField(T editedObject, CheckedTextField textField);

    /**
     * <p>
     * removeTextField.
     * </p>
     *
     * @param editedObject
     *            a T object.
     * @param textField
     *            a {@link de.markusrother.swing.CheckedTextField} object.
     */
    public abstract void removeTextField(T editedObject, CheckedTextField textField);

    /**
     * <p>
     * isEditing.
     * </p>
     *
     * @param object
     *            a T object.
     * @return a boolean.
     */
    public boolean isEditing(final T object) {
        return editedObject == object;
    }

    /** {@inheritDoc} */
    @Override
    public void textEntered(final ActionEvent e) {
        final boolean successful = finishEdit(editedObject, textField.getText());
        if (successful) {
            removeTextField(editedObject, textField);
            editedObject = null;
            textField = null;
        } else {
            textField.markInvalid();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void cancel(final AWTEvent e) {
        doCancelEdit();
    }

}
