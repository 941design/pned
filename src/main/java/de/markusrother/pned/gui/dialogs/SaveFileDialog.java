package de.markusrother.pned.gui.dialogs;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.EventTarget;

/**
 * <p>SaveFileDialog class.</p>
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
	 * <p>open.</p>
	 *
	 * @param eventTarget a {@link de.markusrother.pned.gui.EventTarget} object.
	 */
	public static void open(final EventTarget eventTarget) {
		final SaveFileDialog dialog = new SaveFileDialog(eventTarget);
		dialog.showDialog();
	}

	private SaveFileDialog(final EventTarget eventTarget) {
		super(eventTarget, title, approveButtonLabel);
		setDialogType(JFileChooser.SAVE_DIALOG);
	}

	/** {@inheritDoc} */
	@Override
	protected void selectedFile(final File file) {
		// TODO - prompt for overwrite!
		try {
			eventTarget.exportPnml(new PetriNetIOCommand(this, file));
		} catch (final IOException e) {
			// TODO
			throw new RuntimeException("TODO");
		}
	}

}
