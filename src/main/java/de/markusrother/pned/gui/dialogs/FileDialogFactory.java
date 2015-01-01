package de.markusrother.pned.gui.dialogs;

import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * Dialog for importing a Petri net.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class FileDialogFactory {

	/**
	 * <p>
	 * Opens {@link de.markusrother.pned.gui.dialogs.OpenFileDialog}.
	 * </p>
	 */
	public void openImportDialog() {
		// TODO - open with current path:
		OpenFileDialog.open(eventTarget);
	}

	/**
	 * <p>
	 * Opens {@link de.markusrother.pned.gui.dialogs.SaveFileDialog}.
	 * </p>
	 */
	public void openExportDialog() {
		// TODO - open with current path:
		SaveFileDialog.open(eventTarget);
	}

	/**
	 * The event target to be posted to.
	 */
	private GuiEventTarget eventTarget;

	/**
	 * <p>
	 * Getter for the field <code>eventTarget</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.GuiEventTarget} object.
	 */
	public GuiEventTarget getEventTarget() {
		return eventTarget;
	}

	/**
	 * <p>
	 * Setter for the field <code>eventTarget</code>.
	 * </p>
	 *
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.GuiEventTarget} object.
	 */
	public void setEventTarget(final GuiEventTarget eventTarget) {
		this.eventTarget = eventTarget;
	}

}
