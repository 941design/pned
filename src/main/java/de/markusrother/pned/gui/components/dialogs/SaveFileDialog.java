package de.markusrother.pned.gui.components.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand.Type;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;

/**
 * <p>
 * File dialog for saving Petri nets to pnml (xml). Successful file selection
 * posts a {@link de.markusrother.pned.control.commands.PetriNetIOCommand} to the
 * provided {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class SaveFileDialog extends AbstractFileDialog {

	/** Constant <code>dialogTitle="Save file"</code> */
	private static final String title = "Save file";
	/** Constant <code>approveButtonLabel="Save"</code> */
	private static final String approveButtonLabel = "Save";

	/**
	 * <p>
	 * Opens this dialog.
	 * </p>
	 *
	 * @param commandTarget
	 *            an {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	public static void open(final PnCommandTarget commandTarget, final File dir) {
		final SaveFileDialog dialog = new SaveFileDialog(commandTarget, dir);
		dialog.showDialogAndProcessResult();
	}

	/**
	 * <p>
	 * Constructor for SaveFileDialog.
	 * </p>
	 *
	 * @param commandTarget
	 *            an {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	private SaveFileDialog(final PnCommandTarget commandTarget, final File dir) {
		super(commandTarget, title, dir, approveButtonLabel);
		setDialogType(JFileChooser.SAVE_DIALOG);
	}

	/** {@inheritDoc} */
	@Override
	protected void processSelectedFile(final File file) {
		// TODO - prompt for overwrite!
		try {
			final String path = file.getAbsolutePath();
			commandTarget.setCurrentDirectory(new PetriNetIOCommand(this, Type.CWD, new File(path)));
			commandTarget.exportPnml(new PetriNetIOCommand(this, Type.SAVE, file));
		} catch (final IOException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
