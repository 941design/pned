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
 * File dialog for opening Petri nets from pnml (xml). Successful file selection
 * posts a {@link de.markusrother.pned.control.commands.PetriNetIOCommand} to
 * the provided
 * {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}.
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
     * @param state
     *            a {@link de.markusrother.pned.gui.core.model.PnStateModel}
     *            object.
     * @param commandTarget
     *            a
     *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
     *            object.
     */
    public static void open(final PnStateModel state, final PnCommandTarget commandTarget) {
        final OpenFileDialog dialog = new OpenFileDialog(state, commandTarget);
        dialog.showDialogAndProcessResult();
    }

    /**
     * <p>
     * Constructor for OpenFileDialog.
     * </p>
     *
     * @param commandTarget
     *            an
     *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
     *            to be posted to.
     * @param state
     *            a {@link de.markusrother.pned.gui.core.model.PnStateModel}
     *            object.
     */
    private OpenFileDialog(final PnStateModel state, final PnCommandTarget commandTarget) {
        super(state, commandTarget, title, approveButtonLabel);
        setDialogType(JFileChooser.OPEN_DIALOG);
    }

    /** {@inheritDoc} */
    @Override
    protected void processSelectedFile(final File file) {
        // If current net is to be purged send appropriate commands
        try {
            final String path = file.getAbsolutePath();
            commandTarget.setCurrentDirectory(new PetriNetIOCommand(this, Type.CWD, new File(path)));
            if (state.isDirty()) {
                // TODO - Could also be wrapped in an action!
                final int result = JOptionPane.showConfirmDialog(null,
                        "The current net contains unsaved changes, which will be discarded. Proceed?",
                        "Confirm import", JOptionPane.YES_NO_OPTION);
                if (result != 0) {
                    return;
                }
            }
            commandTarget.importPnml(new PetriNetIOCommand(this, Type.OPEN, file));
            // We have to set clean manually, because the net was created by a
            // chain of commands, which did set the model dirty.
            state.setDirty(false);
        } catch (final IOException e) {
            // TODO
            throw new RuntimeException("TODO", e);
        }
    }

}
