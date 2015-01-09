package de.markusrother.pned.gui.dialogs;

import de.markusrother.pned.gui.commands.GuiCommandTarget;
import de.markusrother.pned.gui.core.PnState;

/**
 * <p>
 * Factory for opening file dialogs.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class FileDialogFactory {

	private final PnState state;

	/**
	 * <p>
	 * Constructor for FileDialogFactory.
	 * </p>
	 *
	 * @param state a {@link de.markusrother.pned.gui.core.PnState} object.
	 */
	public FileDialogFactory(final PnState state) {
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

	/**
	 * <p>getCommandTarget.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.commands.GuiCommandTarget} object.
	 */
	public GuiCommandTarget getCommandTarget() {
		return state.getEventBus();
	}

}
