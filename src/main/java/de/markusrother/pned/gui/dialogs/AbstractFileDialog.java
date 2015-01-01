package de.markusrother.pned.gui.dialogs;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * Abstract superclass for file dialogs.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractFileDialog extends JFileChooser {

	/** Constant <code>NO_PARENT</code> */
	private static final Component NO_PARENT = null;
	/** Constant <code>filter</code> */
	private static FileFilter filter = new FileNameExtensionFilter(".pnml", "pnml");

	/** The {@link de.markusrother.pned.gui.GuiEventTarget} to be posted to. */
	protected final GuiEventTarget eventTarget;

	/** A {@link java.lang.String} - the approve button's label. */
	private final String approveButtonLabel;

	/**
	 * <p>
	 * Constructor for AbstractFileDialog.
	 * </p>
	 *
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.GuiEventTarget} to be
	 *            posted to.
	 * @param title
	 *            a {@link java.lang.String} - the dialog's title.
	 * @param approveButtonLabel
	 *            a {@link java.lang.String} - the approve button's label.
	 */
	protected AbstractFileDialog(final GuiEventTarget eventTarget, final String title, final String approveButtonLabel) {
		this.eventTarget = eventTarget;
		this.approveButtonLabel = approveButtonLabel;
		// setCurrentDirectory();
		setDialogTitle(title);
		setFileFilter(filter);
		addChoosableFileFilter(filter);
		setFileSelectionMode(JFileChooser.FILES_ONLY);
		setMultiSelectionEnabled(false);
	}

	/**
	 * <p>
	 * Opens (shows) this dialog and processes the result.
	 * </p>
	 */
	protected void showDialogAndProcessResult() {
		/*
		 * NOTE - The <code>parent</code> argument determines two things: the
		 * frame on which the open dialog depends and the component whose
		 * position the look and feel should consider when placing the dialog.
		 * If the parent is a <code>Frame</code> object (such as a
		 * <code>JFrame</code>) then the dialog depends on the frame and the
		 * look and feel positions the dialog relative to the frame (for
		 * example, centered over the frame). If the parent is a component, then
		 * the dialog depends on the frame containing the component, and is
		 * positioned relative to the component (for example, centered over the
		 * component). If the parent is <code>null</code>, then the dialog
		 * depends on no visible window, and it's placed in a
		 * look-and-feel-dependent position such as the center of the screen.
		 */
		final int result = showDialog(NO_PARENT, approveButtonLabel);
		if (result == APPROVE_OPTION) {
			final File file = getSelectedFile();
			processSelectedFile(file);
		} else if (result == CANCEL_OPTION) {
			// IGNORE
		} else {
			// Should never happen.
			throw new IllegalStateException();
		}
	}

	/**
	 * <p>
	 * Processes the selected file.
	 * </p>
	 *
	 * @param file
	 *            a {@link java.io.File} - the selected file.
	 */
	protected abstract void processSelectedFile(File file);

}
