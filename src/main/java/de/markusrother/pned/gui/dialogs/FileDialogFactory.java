package de.markusrother.pned.gui.dialogs;

import de.markusrother.pned.gui.actions.GuiState;
import de.markusrother.pned.gui.listeners.GuiCommandTarget;

/**
 * <p>
 * Factory for opening file dialogs.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class FileDialogFactory {

	private final GuiState state;

	/**
	 * <p>
	 * Constructor for FileDialogFactory.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} to which
	 *            dialogs are to post their events.
	 */
	public FileDialogFactory(final GuiState state) {
		this.state = state;
	}

	/**
	 * <p>
	 * Opens an {@link de.markusrother.pned.gui.dialogs.OpenFileDialog}.
	 * </p>
	 */
	public void openImportDialog() {
		OpenFileDialog.open(state.getEventBus(), state.getCurrentDirectory());
	}

	/**
	 * <p>
	 * Opens a {@link de.markusrother.pned.gui.dialogs.SaveFileDialog}.
	 * </p>
	 */
	public void openExportDialog() {
		SaveFileDialog.open(state.getEventBus(), state.getCurrentDirectory());
	}

	public GuiCommandTarget getCommandTarget() {
		return state.getEventBus();
	}

}
