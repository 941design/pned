package de.markusrother.pned.gui.dialogs;

import java.io.File;

import de.markusrother.pned.core.commands.PetriNetIOCommand;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.listeners.GuiCommandTarget;

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

	/**
	 * The event target to which resulting events are posted to, and which is
	 * listened to for current directory changes.
	 */
	private GuiEventBus eventBus;
	/** The current directory in which to do file operations. */
	private File currentDirectory;

	/**
	 * <p>
	 * Constructor for FileDialogFactory.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.core.control.EventBus} to which
	 *            dialogs are to post their events.
	 */
	public FileDialogFactory(final GuiEventBus eventBus) {
		this.eventBus = eventBus;
		eventBus.addListener(PetriNetIOListener.class, this);
	}

	/**
	 * <p>
	 * Getter for this factories current {@link de.markusrother.pned.gui.listeners.GuiCommandTarget}.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.listeners.GuiCommandTarget} to
	 *         which events are posted to.
	 */
	public GuiCommandTarget getCommandTarget() {
		return eventBus;
	}

	/**
	 * <p>
	 * Setter for the field <code>eventBus</code>.
	 * </p>
	 *
	 * @param eventBus
	 *            a {@link de.markusrother.pned.gui.control.GuiEventBus} to
	 *            which resulting events are posted to and to which is listened
	 *            to for directory changes.
	 */
	public void setEventBus(final GuiEventBus eventBus) {
		assert (this.eventBus != null);
		this.eventBus.removeListener(PetriNetIOListener.class, this);
		this.eventBus = eventBus;
		eventBus.addListener(PetriNetIOListener.class, this);
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		setCurrentDirectory(cmd.getFile());
	}

	/**
	 * <p>
	 * Setter for the field <code>currentPath</code>.
	 * </p>
	 *
	 * @param dir
	 *            a {@link java.io.File} - the current directory.
	 */
	public void setCurrentDirectory(final File dir) {
		this.currentDirectory = dir;
	}

	/**
	 * <p>
	 * Opens an {@link de.markusrother.pned.gui.dialogs.OpenFileDialog}.
	 * </p>
	 */
	public void openImportDialog() {
		assert (eventBus != null);
		OpenFileDialog.open(eventBus, currentDirectory);
	}

	/**
	 * <p>
	 * Opens a {@link de.markusrother.pned.gui.dialogs.SaveFileDialog}.
	 * </p>
	 */
	public void openExportDialog() {
		assert (eventBus != null);
		SaveFileDialog.open(eventBus, currentDirectory);
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
