package de.markusrother.pned.gui.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand.Type;
import de.markusrother.pned.gui.commands.PnCommandTarget;

/**
 * <p>
 * File dialog for opening Petri nets from pnml (xml). Successful file selection
 * posts a {@link de.markusrother.pned.control.commands.PetriNetIOCommand} to the
 * provided {@link de.markusrother.pned.gui.commands.PnCommandTarget}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class OpenFileDialog extends AbstractFileDialog {

	/** Constant <code>dialogTitle="Open file"</code> */
	private static final String title = "Open file";
	/** Constant <code>approveButtonLabel="Open"</code> */
	private static final String approveButtonLabel = "Open";

	/**
	 * <p>
	 * Opens this dialog.
	 * </p>
	 *
	 * @param commandTarget
	 *            a {@link de.markusrother.pned.gui.commands.PnCommandTarget}
	 *            object.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	public static void open(final PnCommandTarget commandTarget, final File dir) {
		final OpenFileDialog dialog = new OpenFileDialog(commandTarget, dir);
		dialog.showDialogAndProcessResult();
	}

	/**
	 * <p>
	 * Constructor for OpenFileDialog.
	 * </p>
	 *
	 * @param commandTarget
	 *            an {@link de.markusrother.pned.gui.commands.PnCommandTarget}
	 *            to be posted to.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	private OpenFileDialog(final PnCommandTarget commandTarget, final File dir) {
		super(commandTarget, title, dir, approveButtonLabel);
		setDialogType(JFileChooser.OPEN_DIALOG);
	}

	/** {@inheritDoc} */
	@Override
	protected void processSelectedFile(final File file) {
		// TODO - confirm if unsaved.
		// TODO - maintain dirty flag!
		// If current net is to be purged send appropriate command
		try {
			final String path = file.getAbsolutePath();
			commandTarget.setCurrentDirectory(new PetriNetIOCommand(this, Type.CWD, new File(path)));
			commandTarget.importPnml(new PetriNetIOCommand(this, Type.OPEN, file));
		} catch (final IOException e1) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
