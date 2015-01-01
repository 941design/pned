package de.markusrother.pned.gui.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.EventTarget;

/**
 * <p>OpenFileDialog class.</p>
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
	 * <p>open.</p>
	 *
	 * @param eventTarget a {@link de.markusrother.pned.gui.EventTarget} object.
	 */
	public static void open(final EventTarget eventTarget) {
		final OpenFileDialog dialog = new OpenFileDialog(eventTarget);
		dialog.showDialog();
	}

	private OpenFileDialog(final EventTarget eventTarget) {
		super(eventTarget, title, approveButtonLabel);
		setDialogType(JFileChooser.OPEN_DIALOG);
	}

	/** {@inheritDoc} */
	@Override
	protected void selectedFile(final File file) {
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
