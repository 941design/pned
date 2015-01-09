package de.markusrother.pned.gui.actions;

import javax.swing.AbstractAction;

import de.markusrother.pned.gui.control.GuiEventBus;

/**
 * <p>
 * AbstractGuiAction class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class AbstractGuiAction extends AbstractAction {

	protected final Object source;
	protected final GuiState state;

	/**
	 * <p>
	 * Constructor for AbstractGuiAction.
	 * </p>
	 *
	 * @param label
	 *            a {@link java.lang.String} object.
	 * @param source
	 *            a {@link java.lang.Object} object.
	 * @param state
	 *            a {@link de.markusrother.pned.gui.actions.GuiState} object.
	 */
	public AbstractGuiAction(final String label, final Object source, final GuiState state) {
		super(label);
		this.source = source;
		this.state = state;
	}

	/**
	 * <p>
	 * getEventBus.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.gui.control.GuiEventBus} object.
	 */
	protected GuiEventBus getEventBus() {
		return state.getEventBus();
	}

}
