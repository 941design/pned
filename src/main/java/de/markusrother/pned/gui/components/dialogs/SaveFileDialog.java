package de.markusrother.pned.gui.components.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.control.commands.PetriNetIOCommand.Type;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.core.model.PnStateModel;

/**
 * <p>
 * File dialog for saving Petri nets to pnml (xml). Successful file selection
 * posts a {@link de.markusrother.pned.control.commands.PetriNetIOCommand} to
 * the provided
 * {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}.
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
	/** Constant <code>PNML_SUFFIX=".pnml"</code> */
	private static final String PNML_SUFFIX = ".pnml";

	/**
	 * <p>
	 * Opens this dialog.
	 * </p>
	 *
	 * @param commandTarget
	 *            an
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	public static void open(final PnStateModel state, final PnCommandTarget commandTarget) {
		final SaveFileDialog dialog = new SaveFileDialog(state, commandTarget);
		dialog.showDialogAndProcessResult();
	}

	/**
	 * <p>
	 * Constructor for SaveFileDialog.
	 * </p>
	 *
	 * @param commandTarget
	 *            an
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	private SaveFileDialog(final PnStateModel state, final PnCommandTarget commandTarget) {
		super(state, commandTarget, title, approveButtonLabel);
		setDialogType(JFileChooser.SAVE_DIALOG);
	}

	/** {@inheritDoc} */
	@Override
	protected void processSelectedFile(final File file) {
		try {
			String path = file.getAbsolutePath();
			path = path.endsWith(PNML_SUFFIX) ? path : path + PNML_SUFFIX;
			commandTarget.setCurrentDirectory(new PetriNetIOCommand(this, Type.CWD, new File(path)));
			if (file.exists()) {
				final int result = JOptionPane.showConfirmDialog(null,
						"The selected file already exists and will be overwritten. Proceed?", "Confirm save",
						JOptionPane.YES_NO_OPTION);
				if (result != 0) {
					return;
				}
			}
			commandTarget.exportPnml(new PetriNetIOCommand(this, Type.SAVE, file));
		} catch (final IOException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
