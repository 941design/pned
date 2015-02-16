package de.markusrother.pned.gui.control;

import java.util.EventObject;

import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.util.CommandAdapter;

public class DirtyStateListener extends CommandAdapter {

	private boolean dirty;

	public boolean isDirty() {
		return dirty;
	}

	/** {@inheritDoc} */
	@Override
	protected void process(final EventObject e) {
		dirty = true;
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		// IGNORE
	}

	/** {@inheritDoc} */
	@Override
	public void importPnml(final PetriNetIOCommand cmd) {
		dirty = false;
	}

	/** {@inheritDoc} */
	@Override
	public void exportPnml(final PetriNetIOCommand cmd) {
		dirty = false;
	}

}
