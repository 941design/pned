package de.markusrother.pned.gui.dialogs;

import java.io.File;

import de.markusrother.pned.commands.PetriNetIOCommand;
import de.markusrother.pned.commands.listeners.PetriNetIOListener;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.pned.gui.GuiEventTarget;

/**
 * <p>
 * Factory for opening file dialogs.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class FileDialogFactory
	implements
		PetriNetIOListener {

	/** The event target to which resulting events are posted to. */
	private EventBus eventBus;
	/** */
	private File currentPath;

	/**
	 * <p>Constructor for FileDialogFactory.</p>
	 *
	 * @param eventBus a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	public FileDialogFactory(final EventBus eventBus) {
		this.eventBus = eventBus;
		eventBus.addListener(PetriNetIOListener.class, this);
	}

	/**
	 * <p>
	 * Getter for the field <code>eventTarget</code>.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.GuiEventTarget} to which
	 *         resulting events are posted to.
	 */
	public GuiEventTarget getEventTarget() {
		return eventBus;
	}

	/**
	 * <p>
	 * Setter for the field <code>eventTarget</code>.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.GuiEventTarget} to which
	 *            resulting events are posted to.
	 */
	public void setEventBus(final EventBus eventBus) {
		assert (this.eventBus != null);
		this.eventBus.removeListener(PetriNetIOListener.class, this);
		this.eventBus = eventBus;
		eventBus.addListener(PetriNetIOListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentPath(final PetriNetIOCommand cmd) {
		setCurrentPath(cmd.getFile());
	}

	/**
	 * <p>Setter for the field <code>currentPath</code>.</p>
	 *
	 * @param dir a {@link java.io.File} object.
	 */
	public void setCurrentPath(final File dir) {
		this.currentPath = dir;
	}

	/**
	 * <p>
	 * Opens an {@link de.markusrother.pned.gui.dialogs.OpenFileDialog}.
	 * </p>
	 */
	public void openImportDialog() {
		assert (eventBus != null);
		OpenFileDialog.open(eventBus, currentPath);
	}

	/**
	 * <p>
	 * Opens a {@link de.markusrother.pned.gui.dialogs.SaveFileDialog}.
	 * </p>
	 */
	public void openExportDialog() {
		assert (eventBus != null);
		SaveFileDialog.open(eventBus, currentPath);
	}

	/** {@inheritDoc} */
	@Override
	public void importPnml(final PetriNetIOCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void exportPnml(final PetriNetIOCommand cmd) {
		// IGNORE
	}

}
