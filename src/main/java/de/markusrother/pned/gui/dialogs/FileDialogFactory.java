package de.markusrother.pned.gui.dialogs;

import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * Factory for opening file dialogs.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class FileDialogFactory {

	/**
	 * <p>
	 * Opens an {@link de.markusrother.pned.gui.dialogs.OpenFileDialog}.
	 * </p>
	 */
	public void openImportDialog() {
		// TODO - open with current path:
		OpenFileDialog.open(eventTarget);
	}

	/**
	 * <p>
	 * Opens a {@link de.markusrother.pned.gui.dialogs.SaveFileDialog}.
	 * </p>
	 */
	public void openExportDialog() {
		// TODO - open with current path:
		SaveFileDialog.open(eventTarget);
	}

	/** The event target to which resulting events are posted to. */
	private GuiEventTarget eventTarget;

	/**
	 * <p>
	 * Getter for the field <code>eventTarget</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.GuiEventTarget} to which
	 *         resulting events are posted to.
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
	 *            a {@link de.markusrother.pned.gui.GuiEventTarget} to which
	 *            resulting events are posted to.
	 */
	public void setEventTarget(final GuiEventTarget eventTarget) {
		this.eventTarget = eventTarget;
	}

}
