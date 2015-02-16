package de.markusrother.pned.gui.control;

import java.util.EventObject;

import de.markusrother.pned.control.EventBus;
import de.markusrother.pned.control.commands.PetriNetIOCommand;
import de.markusrother.pned.gui.control.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.control.commands.PetriNetListener;
import de.markusrother.pned.util.CommandAdapter;

public class DirtyStateListener extends CommandAdapter
	implements
		PetriNetListener {

	private boolean dirty;

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(final boolean dirty) {
		this.dirty = dirty;
	}

	@Override
	public void setEventBus(final EventBus eventBus) {
		super.setEventBus(eventBus);
		eventBus.addListener(PetriNetListener.class, this);
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

	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		dirty = false;
	}

}
