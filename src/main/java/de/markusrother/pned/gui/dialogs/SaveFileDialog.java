package de.markusrother.pned.gui.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * File dialog for saving Petri nets to pnml (xml). Successful file selection
 * posts a {@link de.markusrother.pned.commands.PetriNetIOCommand} to the
 * provided {@link de.markusrother.pned.gui.GuiEventTarget}.
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
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.GuiEventTarget} to be
	 *            posted to.
	 * @param path
	 */
	public static void open(final GuiEventTarget eventTarget, final File dir) {
		final SaveFileDialog dialog = new SaveFileDialog(eventTarget, dir);
		dialog.showDialogAndProcessResult();
	}

	/**
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.GuiEventTarget} to be
	 *            posted to.
	 * @param dir
	 */
	private SaveFileDialog(final GuiEventTarget eventTarget, final File dir) {
		super(eventTarget, title, dir, approveButtonLabel);
		setDialogType(JFileChooser.SAVE_DIALOG);
	}

	/** {@inheritDoc} */
	@Override
	protected void processSelectedFile(final File file) {
		// TODO - prompt for overwrite!
		try {
			final String path = file.getAbsolutePath();
			eventTarget.setCurrentPath(new PetriNetIOCommand(this, new File(path)));
			eventTarget.exportPnml(new PetriNetIOCommand(this, file));
		} catch (final IOException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
