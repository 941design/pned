package de.markusrother.pned.gui.components;

import de.markusrother.pned.gui.components.listeners.MarkingEditor;
import de.markusrother.pned.gui.components.listeners.SingleNodeSelector;
import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.core.model.EdgeStyleModel;

/**
 * <p>
 * Factory for {@link de.markusrother.pned.gui.components.PlaceComponent}s,
 * {@link de.markusrother.pned.gui.components.TransitionComponent}s,
 * {@link de.markusrother.pned.gui.components.EdgeComponent}s, and
 * {@link de.markusrother.pned.gui.components.MarkingComponent}s.
 * </p>
 * 
 *
 * @author Markus Rother
 * @version 1.0
 */
public class ComponentFactory
    implements
        NodeComponentFactory,
        EdgeComponentFactory,
        MarkingComponentFactory {

    private final MarkingEditor markingEditor;
    private final SingleNodeSelector singleNodeSelector;
    private final PnState state;

    /**
     * <p>
     * Constructor for NodeFactoryImpl.
     * </p>
     *
     * @param state
     *            a {@link de.markusrother.pned.gui.control.PnState} object.
     */
    public ComponentFactory(final PnState state) {
        this.state = state;
        this.markingEditor = new MarkingEditor(state.getEventBus());
        this.singleNodeSelector = new SingleNodeSelector(state.getEventBus());
    }

    /** {@inheritDoc} */
    @Override
    public PlaceComponent newPlace(final String placeId) {
        // TODO - use currentPlaceStyle!
        final MarkingComponent marking = newMarking();
        final PlaceComponent place = new PlaceComponent(state.getEventBus(), //
                placeId, //
                marking, //
                markingEditor, //
                state.getPlaceStyle());
        addListenersToNode(place);
        return place;
    }

    /** {@inheritDoc} */
    @Override
    public TransitionComponent newTransition(final String transitionId) {
        final TransitionComponent transition = new TransitionComponent(state.getEventBus(), //
                transitionId, //
                state.getTransitionStyle());
        addListenersToNode(transition);
        return transition;
    }

    /** {@inheritDoc} */
    @Override
    public MarkingComponent newMarking() {
        final MarkingComponent marking = new MarkingComponent(state.getEventBus(), //
                state.getMarkingStyle());
        return marking;
    }

    /**
     * <p>
     * addListeners.
     * </p>
     *
     * @param node
     *            a
     *            {@link de.markusrother.pned.gui.components.AbstractNodeComponent}
     *            object.
     */
    private void addListenersToNode(final AbstractNodeComponent node) {
        node.setSingleNodeSelector(singleNodeSelector);
    }

    /** {@inheritDoc} */
    @Override
    public EdgeComponent newEdge(final String edgeId, final String sourceId, final String targetId) {
        // TODO
        throw new RuntimeException("TODO");
    }

    /** {@inheritDoc} */
    @Override
    public EdgeStyleModel getEdgeStyle() {
        return state.getEdgeStyle();
    }

}
