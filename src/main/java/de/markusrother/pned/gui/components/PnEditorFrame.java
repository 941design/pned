package de.markusrother.pned.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.BoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.stream.XMLStreamException;

import de.markusrother.pned.core.commands.PetriNetIOCommand;
import de.markusrother.pned.core.control.EventAwarePetriNet;
import de.markusrother.pned.core.control.EventBus;
import de.markusrother.pned.core.listeners.PetriNetIOListener;
import de.markusrother.pned.gui.NodeCreationMode;
import de.markusrother.pned.gui.commands.PetriNetEditCommand;
import de.markusrother.pned.gui.commands.SetNodeTypeCommand;
import de.markusrother.pned.gui.control.GuiEventBus;
import de.markusrother.pned.gui.listeners.PetriNetListener;
import de.markusrother.pned.gui.menus.PnEditorMenuFactory;
import de.markusrother.pned.gui.menus.PnedMenuBar;
import de.markusrother.pned.io.PNMLParser;
import de.markusrother.pned.util.PetriNetGuiEventLogger;
import de.markusrother.swing.CustomScrollPaneUI;
import de.markusrother.swing.snap.SnapGridComponent;

/**
 * <p>
 * PnEditorFrame class.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PnEditorFrame extends JFrame
	implements
		PetriNetListener,
		PetriNetIOListener {

	/** Constant <code>preferredSize</code> */
	private static final Dimension preferredSize = new Dimension(2000, 1000);

	private GuiEventBus eventBus;
	private final PnEditorMenuFactory menuFactory;
	private PnGridPanel grid;
	private PnedMenuBar pnedMenuBar;

	private File currentPath;

	/**
	 * <p>
	 * Constructor for PnEditorFrame.
	 * </p>
	 *
	 * @param title
	 *            a {@link java.lang.String} object.
	 */
	public PnEditorFrame(final String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.eventBus = createNewContext();
		this.menuFactory = new PnEditorMenuFactory(eventBus);
		this.grid = new PnGridPanel(eventBus);
		this.pnedMenuBar = new PnedMenuBar(menuFactory);

		setPreferredSize(preferredSize);

		final SnapGridComponent sg = new SnapGridComponent(new Dimension(40, 40), Color.GRAY);
		sg.setPreferredSize(new Dimension(2000, 2000));
		final JScrollPane panel = new JScrollPane(sg, //
				// final JScrollPane panel = new JScrollPane(grid, //
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, //
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		final CustomScrollPaneUI ui = new CustomScrollPaneUI();
		ui.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final BoundedRangeModel model = (BoundedRangeModel) e.getSource();
				final double extent = model.getExtent();
				final double maximum = model.getMaximum();
				final int minimum = model.getMinimum();
				final double value = model.getValue();
				final Dimension preferredSize = sg.getPreferredSize();
				final double height = preferredSize.getHeight();
				final double width = preferredSize.getWidth();
				final double ratio = (value + extent) / maximum;
				System.out.println(height);
				System.out.println(value);
				System.out.println(maximum);
				System.out.println(ratio);
				// model.setValue((int) value);
				model.setValueIsAdjusting(true);
				if (ratio > 0.9) {
					sg.setPreferredSize(new Dimension((int) width, (int) (height * 1.001)));
				}
			}
		});
		panel.setUI(ui);

		add(panel, BorderLayout.CENTER);
		// add(grid, BorderLayout.CENTER);
		setJMenuBar(pnedMenuBar);
		pack();
		setVisible(true);

		PetriNetGuiEventLogger.log(eventBus);
	}

	/**
	 * <p>
	 * createNewContext.
	 * </p>
	 *
	 * @return a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	private GuiEventBus createNewContext() {
		final GuiEventBus eventBus = new GuiEventBus();
		createPetriNetModel(eventBus);
		eventBus.addListener(PetriNetListener.class, this);
		eventBus.addListener(PetriNetIOListener.class, this);
		eventBus.setCurrentNodeType(new SetNodeTypeCommand(this, NodeCreationMode.PLACE));
		if (menuFactory != null) {
			menuFactory.setEventBus(eventBus);
		}
		eventBus.setCurrentDirectory(new PetriNetIOCommand(this, PetriNetIOCommand.Type.STAT, currentPath));
		return eventBus;
	}

	/**
	 * <p>
	 * createPetriNetModel.
	 * </p>
	 *
	 * @param eventMulticaster
	 *            a {@link de.markusrother.pned.core.control.EventBus} object.
	 */
	private void createPetriNetModel(final EventBus eventMulticaster) {
		EventAwarePetriNet.create(eventMulticaster);
	}

	/**
	 * <p>
	 * main.
	 * </p>
	 *
	 * @param args
	 *            a {@link java.lang.String} object.
	 */
	public static void main(final String... args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final PnEditorFrame editorFrame = new PnEditorFrame("foobar");
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public void createPetriNet(final PetriNetEditCommand cmd) {
		// Assuming GC takes care of rest.
		// Can only be gc'ed if EventBus becomes garbage as well.
		getContentPane().remove(grid);
		eventBus.removeListener(PetriNetListener.class, this);
		eventBus.removeListener(PetriNetIOListener.class, this);

		this.eventBus = createNewContext();
		this.grid = new PnGridPanel(eventBus);
		this.pnedMenuBar = new PnedMenuBar(menuFactory);

		add(grid, BorderLayout.CENTER);
		setJMenuBar(pnedMenuBar);
		pack();
		// repaint(); // TODO ???
	}

	/** {@inheritDoc} */
	@Override
	public void setCurrentDirectory(final PetriNetIOCommand cmd) {
		this.currentPath = cmd.getFile();
	}

	/** {@inheritDoc} */
	@Override
	public void importPnml(final PetriNetIOCommand cmd) {
		// FIXME - Creation and import should be handled by separate commands!
		// Or export should also be handled here, instead of EventAwarePetriNet
		// itself!
		createPetriNet(null);
		final File file = cmd.getFile();
		try {
			PNMLParser.parse(file, eventBus);
		} catch (FileNotFoundException | XMLStreamException e) {
			// FIXME
			throw new RuntimeException("TODO");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void exportPnml(final PetriNetIOCommand cmd) {
		// IGNORE - exported by EventAwarePetriNet
	}

}
