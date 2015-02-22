package de.markusrother.pned.gui.components.listeners;

import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import de.markusrother.pned.control.commands.LabelEditCommand;
import de.markusrother.pned.control.commands.NodeCreationListener;
import de.markusrother.pned.control.commands.PlaceCreationCommand;
import de.markusrother.pned.control.commands.TransitionCreationCommand;
import de.markusrother.pned.gui.components.LabelComponent;
import de.markusrother.pned.gui.control.PnEventBus;
import de.markusrother.pned.gui.control.events.NodeMultiSelectionEvent;
import de.markusrother.pned.gui.control.events.NodeSelectionListener;
import de.markusrother.swing.CheckedTextField;
import de.markusrother.swing.RightClickTextFieldEdit;

/**
 * <p>
 * NodeLabelEditor class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class NodeLabelEditor extends RightClickTextFieldEdit<LabelComponent>
    implements
        NodeCreationListener,
        NodeSelectionListener {

    /** Constant <code>labelPattern</code> */
    public final static Pattern labelPattern = Pattern.compile("[\\w\\s\\d]*");

    private final PnEventBus eventBus;

    /**
     * <p>
     * Constructor for NodeLabelEditor.
     * </p>
     *
     * @param eventBus
     *            a {@link de.markusrother.pned.gui.control.PnEventBus} object.
     */
    public NodeLabelEditor(final PnEventBus eventBus) {
        super(LabelComponent.class, labelPattern);

        this.eventBus = eventBus;

        // TODO - dispose and assert removal of listeners!
        eventBus.addListener(NodeCreationListener.class, this);
        eventBus.addListener(NodeSelectionListener.class, this);
    }

    /** {@inheritDoc} */
    @Override
    public String getInitialText(final LabelComponent nodeLabel) {
        return nodeLabel.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void startEdit(final LabelComponent nodeLabel, final MouseEvent evt) {
        nodeLabel.setVisible(false);
    }

    /** {@inheritDoc} */
    @Override
    public void cancelEdit(final LabelComponent nodeLabel) {
        nodeLabel.setVisible(true);
    }

    /** {@inheritDoc} */
    @Override
    public boolean finishEdit(final LabelComponent nodeLabel, final String text) {
        eventBus.setLabel(new LabelEditCommand(this, //
                nodeLabel.getNodeId(), //
                text));
        nodeLabel.setVisible(true);
        return SUCCESSFUL;
    }

    /** {@inheritDoc} */
    @Override
    public void addTextField(final LabelComponent nodeLabel, final CheckedTextField textField) {
        // Not nice that we grab parent, here!
        nodeLabel.getParent().add(textField);
        nodeLabel.getParent().repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void removeTextField(final LabelComponent nodeLabel, final CheckedTextField textField) {
        // Not nice that we grab parent, here!
        nodeLabel.getParent().remove(textField);
        nodeLabel.getParent().repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void nodesSelected(final NodeMultiSelectionEvent e) {
        doCancelEdit();
    }

    /** {@inheritDoc} */
    @Override
    public void nodesUnselected(final NodeMultiSelectionEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void nodeSelectionFinished(final NodeMultiSelectionEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void nodeSelectionCancelled(final NodeMultiSelectionEvent e) {
        // IGNORE
    }

    /** {@inheritDoc} */
    @Override
    public void createPlace(final PlaceCreationCommand cmd) {
        doCancelEdit();
    }

    /** {@inheritDoc} */
    @Override
    public void createTransition(final TransitionCreationCommand cmd) {
        doCancelEdit();
    }

}
