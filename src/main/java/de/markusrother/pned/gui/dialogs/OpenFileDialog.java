package de.markusrother.pned.gui.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * File dialog for opening Petri nets from pnml (xml). Successful file selection
 * posts a {@link de.markusrother.pned.commands.PetriNetIOCommand} to the provided {@link de.markusrother.pned.gui.GuiEventTarget}.
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
	 *            a {@link de.markusrother.pned.gui.GuiEventTarget} object.
	 */
	public static void open(final GuiEventTarget eventTarget) {
		final OpenFileDialog dialog = new OpenFileDialog(eventTarget);
		dialog.showDialogAndProcessResult();
	}

	/**
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.GuiEventTarget} to be
	 *            posted to.
	 */
	private OpenFileDialog(final GuiEventTarget eventTarget) {
		super(eventTarget, title, approveButtonLabel);
		setDialogType(JFileChooser.OPEN_DIALOG);
	}

	/** {@inheritDoc} */
	@Override
	protected void processSelectedFile(final File file) {
		// TODO - confirm if unsaved.
		// TODO - maintain dirty flag!
		// If current net is to be purged send appropriate command
		try {
			eventTarget.importPnml(new PetriNetIOCommand(null, file));
		} catch (final IOException e1) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
