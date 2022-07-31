package de.markusrother.pned.gui;

import de.markusrother.pned.gui.components.PnFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final String TITLE = "Markus Rother (8544832)";

    public static void main(final String[] args) {

        // TODO - Take path to log to.
        // TODO - Take flag to log model events only or all events.
        // final boolean isLogging = args.length > 0 && args[0].equals("-log");
        final boolean isLogging = true;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.exit(1);
        }

        final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        final int width = Math.min(gd.getDisplayMode().getWidth(), 1400);
        final int height = Math.min(gd.getDisplayMode().getHeight(), 800);

        final PnFrame frame = new PnFrame(TITLE, new Dimension(width, height), isLogging);

        frame.pack();
        frame.setVisible(true);
    }

}
