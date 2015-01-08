package de.markusrother.pned.gui.components;

import de.markusrother.pned.gui.actions.GuiState;
import de.markusrother.pned.gui.listeners.MarkingEditor;
import de.markusrother.pned.gui.listeners.SingleNodeSelector;

public class NodeFactoryImpl
	implements
		NodeFactory {

	private final MarkingEditor markingEditor;
	private final SingleNodeSelector singleNodeSelector;
	private final GuiState state;

	public NodeFactoryImpl(final GuiState state) {
		this.state = state;
		this.markingEditor = new MarkingEditor(state.getEventBus());
		this.singleNodeSelector = new SingleNodeSelector(state.getEventBus());
	}

	@Override
	public Place newPlace(final String placeId) {
		// TODO - use currentPlaceStyle!
		final Place place = new Place(state.getEventBus(), //
				placeId, //
				markingEditor, //
				state.getPlaceStyle());
		addListenersToNode(place);
		return place;
	}

	@Override
	public Transition newTransition(final String transitionId) {
		final Transition transition = new Transition(state.getEventBus(), //
				transitionId, //
				state.getTransitionStyle());
		addListenersToNode(transition);
		return transition;
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
