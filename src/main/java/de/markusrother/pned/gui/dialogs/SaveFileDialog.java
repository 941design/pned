package de.markusrother.pned.gui.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import de.markusrother.pned.core.events.PetriNetIOCommand;
import de.markusrother.pned.gui.events.GuiEventTarget;

/**
 * <p>
 * File dialog for saving Petri nets to pnml (xml). Successful file selection
 * posts a {@link de.markusrother.pned.core.events.PetriNetIOCommand} to the
 * provided {@link de.markusrother.pned.gui.events.GuiEventTarget}.
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
	 *            an {@link de.markusrother.pned.gui.events.GuiEventTarget} to be
	 *            posted to.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	public static void open(final GuiEventTarget eventTarget, final File dir) {
		final SaveFileDialog dialog = new SaveFileDialog(eventTarget, dir);
		dialog.showDialogAndProcessResult();
	}

	/**
	 * <p>
	 * Constructor for SaveFileDialog.
	 * </p>
	 *
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.events.GuiEventTarget} to be
	 *            posted to.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
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
			eventTarget.setCurrentDirectory(new PetriNetIOCommand(this, new File(path)));
			eventTarget.exportPnml(new PetriNetIOCommand(this, file));
		} catch (final IOException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
