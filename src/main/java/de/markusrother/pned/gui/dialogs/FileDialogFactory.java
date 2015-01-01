package de.markusrother.pned.gui.dialogs;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * Dialog for importing a Petri net.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class FileDialogFactory {

	/** Constant <code>filter</code> */
	private static FileFilter filter = new FileNameExtensionFilter(".pnml", "pnml");
	/** Constant <code>dialogTitle="Import file"</code> */
	private static final String immportDialogTitle = "Import file";
	/** Constant <code>approveButtonLabel="Import"</code> */
	private static final String approveImportButtonLabel = "Import";
	/** Constant <code>dialogTitle="Export file"</code> */
	private static final String exportDialogTitle = "Export file";
	/** Constant <code>approveButtonLabel="Save"</code> */
	private static final String approveExportButtonLabel = "Save";
	/** Constant <code>NO_PARENT</code> */
	private static final Component NO_PARENT = null;

	/**
	 * <p>
	 * Opens {@link ImportDialog}.
	 * </p>
	 *
	 * NOTE - The <code>parent</code> argument determines two things: the frame
	 * on which the open dialog depends and the component whose position the
	 * look and feel should consider when placing the dialog. If the parent is a
	 * <code>Frame</code> object (such as a <code>JFrame</code>) then the dialog
	 * depends on the frame and the look and feel positions the dialog relative
	 * to the frame (for example, centered over the frame). If the parent is a
	 * component, then the dialog depends on the frame containing the component,
	 * and is positioned relative to the component (for example, centered over
	 * the component). If the parent is <code>null</code>, then the dialog
	 * depends on no visible window, and it's placed in a
	 * look-and-feel-dependent position such as the center of the screen.
	 */
	public void openImportDialog() {
		// TODO - open with current path:
		final JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(immportDialogTitle);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
		chooser.addChoosableFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);
		// setCurrentDirectory()

		final int result = chooser.showDialog(NO_PARENT, approveImportButtonLabel);
		if (result == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = chooser.getSelectedFile();
			// TODO - confirm if unsaved.
			// TODO - maintain dirty flag!
			// If current net is to be purged send appropriate command
			try {
				eventTarget.importPnml(new PetriNetIOCommand(null, selectedFile));
			} catch (final IOException e1) {
				// TODO
				throw new RuntimeException("TODO");
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			// IGNORE
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * <p>
	 * Opens {@link ExportDialog}.
	 * </p>
	 */
	public void openExportDialog() {
		// TODO - open with current path:
		final JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle(exportDialogTitle);
		chooser.setMultiSelectionEnabled(false);
		// setCurrentDirectory()
		// addChoosableFileFilter()
		// setFileFilter()
		final int result = chooser.showDialog(NO_PARENT, approveExportButtonLabel);
		if (result == JFileChooser.APPROVE_OPTION) {
			final File selectedFile = chooser.getSelectedFile();
			// TODO - prompt for overwrite!
			try {
				eventTarget.exportPnml(new PetriNetIOCommand(this, selectedFile));
			} catch (final IOException e1) {
				// TODO
				throw new RuntimeException("TODO");
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			// IGNORE
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * The event target to be posted to.
	 */
	private GuiEventTarget eventTarget;

	/**
	 * <p>Getter for the field <code>eventTarget</code>.</p>
	 *
	 * @return a {@link de.markusrother.pned.gui.GuiEventTarget} object.
	 */
	public GuiEventTarget getEventTarget() {
		return eventTarget;
	}

	/**
	 * <p>Setter for the field <code>eventTarget</code>.</p>
	 *
	 * @param eventTarget a {@link de.markusrother.pned.gui.GuiEventTarget} object.
	 */
	public void setEventTarget(final GuiEventTarget eventTarget) {
		this.eventTarget = eventTarget;
	}

}
