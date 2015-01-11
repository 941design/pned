package de.markusrother.pned.gui.components.dialogs;

import static java.awt.BorderLayout.WEST;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.markusrother.pned.gui.control.PnState;
import de.markusrother.pned.gui.control.commands.EdgeLayoutCommand;
import de.markusrother.pned.gui.control.commands.LayoutCommand.ChangeType;
import de.markusrother.pned.gui.control.commands.MarkingLayoutCommand;
import de.markusrother.pned.gui.control.commands.PlaceLayoutCommand;
import de.markusrother.pned.gui.control.commands.PnCommandTarget;
import de.markusrother.pned.gui.control.commands.TransitionLayoutCommand;
import de.markusrother.swing.ScaleGroup;

/**
 * <p>
 * Dialog for adjusting Petri net element sizes. Size changes are posted as
 * {@link java.util.EventObject}s to the provided
 * {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see ScaleGroup
 */
public class EditSettingsDialog extends AbstractDialog {

	/** Constant <code>title="Settings"</code> - this dialog's title. */
	private static final String title = "Settings";
	/** This dialog's preferred size. */
	private static final Dimension preferredSize = new Dimension(450, 300);

	/**
	 * <p>
	 * Opens this dialog.
	 * </p>
	 *
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} object.
	 * @param commandTarget
	 *            an
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 */
	public static void open(final PnState state, final PnCommandTarget commandTarget) {
		// TODO - take parent component and model
		final EditSettingsDialog editSettingsDialog = new EditSettingsDialog(state, commandTarget);
		editSettingsDialog.pack();
		editSettingsDialog.setVisible(true);
	}

	/**
	 * <p>
	 * Constructor for EditSettingsDialog.
	 * </p>
	 * 
	 * @param state
	 *            a {@link de.markusrother.pned.gui.control.PnState} object.
	 * @param commandTarget
	 *            an
	 *            {@link de.markusrother.pned.gui.control.commands.PnCommandTarget}
	 *            to be posted to.
	 */
	private EditSettingsDialog(final PnState state, final PnCommandTarget commandTarget) {
		super(state, commandTarget, title, true);

		setPreferredSize(preferredSize);

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		final JPanel sideBar = new JPanel();
		sideBar.setBorder(new TitledBorder("sizes"));
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

		sideBar.add(createPlaceScale());
		sideBar.add(createTransitionScale());
		sideBar.add(createMarkingScale());
		sideBar.add(createEdgeTipScale());

		contentPane.add(sideBar, WEST);
	}

	/**
	 * <p>
	 * Creates and returns {@link de.markusrother.swing.ScaleGroup} for
	 * adjusting place size for all nodes.
	 * </p>
	 *
	 * @return a {@link de.markusrother.swing.ScaleGroup} object.
	 */
	private ScaleGroup createPlaceScale() {
		final ScaleGroup scale = new ScaleGroup("Place", ScaleGroup.Orientation.HORIZONTAL);
		final BoundedRangeModel boundedRangeModel = scale.getModel();
		boundedRangeModel.setMinimum(10);
		boundedRangeModel.setMaximum(250);
		boundedRangeModel.setValue(state.getPlaceStyle().getSize());
		scale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final int size = scale.getValue();
				final PlaceLayoutCommand cmd = new PlaceLayoutCommand(this, ChangeType.SIZE, size);
				commandTarget.setSize(cmd);
			}
		});
		return scale;
	}

	/**
	 * <p>
	 * Creates and returns {@link de.markusrother.swing.ScaleGroup} for
	 * adjusting transition size for all nodes.
	 * </p>
	 *
	 * @return a {@link de.markusrother.swing.ScaleGroup} object.
	 */
	private ScaleGroup createTransitionScale() {
		final ScaleGroup scale = new ScaleGroup("Transition", ScaleGroup.Orientation.HORIZONTAL);
		final BoundedRangeModel boundedRangeModel = scale.getModel();
		boundedRangeModel.setMinimum(10);
		boundedRangeModel.setMaximum(250);
		boundedRangeModel.setValue(state.getTransitionStyle().getSize());
		scale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final int size = scale.getValue();
				final TransitionLayoutCommand cmd = new TransitionLayoutCommand(this, ChangeType.SIZE, size);
				commandTarget.setSize(cmd);
			}
		});
		return scale;
	}

	/**
	 * <p>
	 * Creates and returns {@link de.markusrother.swing.ScaleGroup} for
	 * adjusting marking size for all markings.
	 * </p>
	 *
	 * @return a {@link de.markusrother.swing.ScaleGroup} object.
	 */
	private ScaleGroup createMarkingScale() {
		final ScaleGroup scale = new ScaleGroup("Marking", ScaleGroup.Orientation.HORIZONTAL);
		final BoundedRangeModel boundedRangeModel = scale.getModel();
		boundedRangeModel.setMinimum(5);
		boundedRangeModel.setMaximum(125);
		boundedRangeModel.setValue(state.getMarkingStyle().getSize());
		scale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final int size = scale.getValue();
				final MarkingLayoutCommand cmd = new MarkingLayoutCommand(this, ChangeType.SIZE, size);
				commandTarget.setSize(cmd);
			}
		});
		return scale;
	}

	/**
	 * <p>
	 * Creates and returns {@link de.markusrother.swing.ScaleGroup} for
	 * adjusting edge tip size for all edges.
	 * </p>
	 *
	 * @return a {@link de.markusrother.swing.ScaleGroup} object.
	 */
	private ScaleGroup createEdgeTipScale() {
		final ScaleGroup scale = new ScaleGroup("Edge tip", ScaleGroup.Orientation.HORIZONTAL);
		final BoundedRangeModel boundedRangeModel = scale.getModel();
		boundedRangeModel.setMinimum(5);
		boundedRangeModel.setMaximum(50);
		boundedRangeModel.setValue(state.getEdgeStyle().getSize());
		scale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final int size = scale.getValue();
				final EdgeLayoutCommand cmd = new EdgeLayoutCommand(this, ChangeType.SIZE, size);
				commandTarget.setSize(cmd);
			}
		});
		return scale;
	}

}
