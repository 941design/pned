package de.markusrother.pned.gui.components;

import de.markusrother.pned.gui.actions.GuiState;
import de.markusrother.pned.gui.listeners.MarkingEditor;
import de.markusrother.pned.gui.listeners.SingleNodeSelector;

/**
 * <p>
 * NodeFactoryImpl class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeFactoryImpl
	implements
		NodeFactory,
		MarkingFactory {

	private final MarkingEditor markingEditor;
	private final SingleNodeSelector singleNodeSelector;
	private final GuiState state;

	/**
	 * <p>
	 * Constructor for NodeFactoryImpl.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.actions.GuiState} object.
	 */
	public NodeFactoryImpl(final GuiState state) {
		this.state = state;
		this.markingEditor = new MarkingEditor(state.getEventBus());
		this.singleNodeSelector = new SingleNodeSelector(state.getEventBus());
	}

	/** {@inheritDoc} */
	@Override
	public Place newPlace(final String placeId) {
		// TODO - use currentPlaceStyle!
		final Marking marking = newMarking();
		final Place place = new Place(state.getEventBus(), //
				placeId, //
				marking, //
				markingEditor, //
				state.getPlaceStyle());
		addListenersToNode(place);
		return place;
	}

	/** {@inheritDoc} */
	@Override
	public Transition newTransition(final String transitionId) {
		final Transition transition = new Transition(state.getEventBus(), //
				transitionId, //
				state.getTransitionStyle());
		addListenersToNode(transition);
		return transition;
	}

	@Override
	public Marking newMarking() {
		final Marking marking = new Marking(state.getEventBus(), //
				state.getMarkingStyle());
		return marking;
	}

	/**
	 * <p>
	 * addListeners.
	 * </p>
	 *
	 * @param node
	 *            a {@link de.markusrother.pned.gui.components.AbstractNode}
	 *            object.
	 */
	private void addListenersToNode(final AbstractNode node) {
		node.setSingleNodeSelector(singleNodeSelector);
	}

}
