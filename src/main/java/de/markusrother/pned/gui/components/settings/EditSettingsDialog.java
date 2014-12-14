package de.markusrother.pned.gui.components.settings;

import static de.markusrother.pned.gui.components.PnGridPanel.eventBus;
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

import de.markusrother.pned.commands.LayoutCommand.ChangeType;
import de.markusrother.pned.commands.PlaceLayoutCommand;
import de.markusrother.pned.commands.TransitionLayoutCommand;
import de.markusrother.swing.ScaleGroup;

public class EditSettingsDialog extends JDialog {

	private static final String title = "Settings";
	private static final Dimension preferredSize = new Dimension(700, 1000);

	public static void open() {
		// TODO - take parent component and model
		final EditSettingsDialog editSettingsDialog = new EditSettingsDialog();
		editSettingsDialog.pack();
		editSettingsDialog.setVisible(true);
	}

	private EditSettingsDialog() {
		setTitle(title);
		setPreferredSize(preferredSize);
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		final JPanel sideBar = new JPanel();
		sideBar.setBorder(new TitledBorder("sizes"));
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

		sideBar.add(createPlaceScale());
		sideBar.add(createTransitionScale());

		contentPane.add(sideBar, WEST);
	}

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
}
