package de.markusrother.pned.gui.components.settings;

import static java.awt.BorderLayout.WEST;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.markusrother.pned.commands.EdgeLayoutCommand;
import de.markusrother.pned.commands.LayoutCommand.ChangeType;
import de.markusrother.pned.commands.MarkingLayoutCommand;
import de.markusrother.pned.commands.PlaceLayoutCommand;
import de.markusrother.pned.commands.TransitionLayoutCommand;
import de.markusrother.pned.gui.EventBus;
import de.markusrother.swing.ScaleGroup;

/**
 * <p>EditSettingsDialog class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class EditSettingsDialog extends JDialog {

	/** Constant <code>title="Settings"</code> */
	private static final String title = "Settings";
	/** Constant <code>preferredSize</code> */
	private static final Dimension preferredSize = new Dimension(700, 1000);

	/**
	 * <p>open.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	public static void open(final EventBus eventMulticaster) {
		// TODO - take parent component and model
		final EditSettingsDialog editSettingsDialog = new EditSettingsDialog(eventMulticaster);
		editSettingsDialog.pack();
		editSettingsDialog.setVisible(true);
	}

	final EventBus eventBus;

	/**
	 * <p>Constructor for EditSettingsDialog.</p>
	 *
	 * @param eventMulticaster a {@link de.markusrother.pned.gui.EventBus} object.
	 */
	private EditSettingsDialog(final EventBus eventMulticaster) {
		this.eventBus = eventMulticaster;
		setTitle(title);
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
	 * <p>createPlaceScale.</p>
	 *
	 * @return a {@link de.markusrother.swing.ScaleGroup} object.
	 */
	private ScaleGroup createPlaceScale() {
		final ScaleGroup scale = new ScaleGroup("Place", ScaleGroup.Orientation.HORIZONTAL);
		scale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final int size = scale.getValue();
				final PlaceLayoutCommand cmd = new PlaceLayoutCommand(this, ChangeType.SIZE, size);
				eventBus.setSize(cmd);
			}
		});
		return scale;
	}

	/**
	 * <p>createTransitionScale.</p>
	 *
	 * @return a {@link de.markusrother.swing.ScaleGroup} object.
	 */
	private ScaleGroup createTransitionScale() {
		final ScaleGroup scale = new ScaleGroup("Transition", ScaleGroup.Orientation.HORIZONTAL);
		scale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final int size = scale.getValue();
				final TransitionLayoutCommand cmd = new TransitionLayoutCommand(this, ChangeType.SIZE, size);
				eventBus.setSize(cmd);
			}
		});
		return scale;
	}

	/**
	 * <p>createMarkingScale.</p>
	 *
	 * @return a {@link de.markusrother.swing.ScaleGroup} object.
	 */
	private ScaleGroup createMarkingScale() {
		final ScaleGroup scale = new ScaleGroup("Marking", ScaleGroup.Orientation.HORIZONTAL);
		scale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final int size = scale.getValue();
				final MarkingLayoutCommand cmd = new MarkingLayoutCommand(this, ChangeType.SIZE, size);
				eventBus.setSize(cmd);
			}
		});
		return scale;
	}

	/**
	 * <p>createEdgeTipScale.</p>
	 *
	 * @return a {@link de.markusrother.swing.ScaleGroup} object.
	 */
	private ScaleGroup createEdgeTipScale() {
		final ScaleGroup scale = new ScaleGroup("Edge tip", ScaleGroup.Orientation.HORIZONTAL);
		scale.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final int size = scale.getValue();
				final EdgeLayoutCommand cmd = new EdgeLayoutCommand(this, ChangeType.SIZE, size);
				eventBus.setSize(cmd);
			}
		});
		return scale;
	}
}
