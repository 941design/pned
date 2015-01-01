package de.markusrother.pned.gui.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import de.markusrother.pned.core.events.PetriNetIOCommand;
import de.markusrother.pned.gui.events.GuiEventTarget;

/**
 * <p>
 * File dialog for opening Petri nets from pnml (xml). Successful file selection
 * posts a {@link de.markusrother.pned.core.events.PetriNetIOCommand} to the
 * provided {@link de.markusrother.pned.gui.events.GuiEventTarget}.
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
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.events.GuiEventTarget} object.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	public static void open(final GuiEventTarget eventTarget, final File dir) {
		final OpenFileDialog dialog = new OpenFileDialog(eventTarget, dir);
		dialog.showDialogAndProcessResult();
	}

	/**
	 * <p>
	 * Constructor for OpenFileDialog.
	 * </p>
	 *
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.events.GuiEventTarget} to be
	 *            posted to.
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	private OpenFileDialog(final GuiEventTarget eventTarget, final File dir) {
		super(eventTarget, title, dir, approveButtonLabel);
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
			eventTarget.setCurrentDirectory(new PetriNetIOCommand(this, new File(path)));
			eventTarget.importPnml(new PetriNetIOCommand(this, file));
		} catch (final IOException e1) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
