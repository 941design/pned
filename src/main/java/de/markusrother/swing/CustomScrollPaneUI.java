package de.markusrother.swing;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollPaneUI;

public class CustomScrollPaneUI extends BasicScrollPaneUI
	implements
		ChangeListener {

	private final Collection<ChangeListener> listeners;

	public CustomScrollPaneUI() {
		super();
		this.listeners = new LinkedList<>();
	}

	@Override
	public void installListeners(final JScrollPane c) {
		super.installListeners(c);
		scrollpane.getVerticalScrollBar().getModel().addChangeListener(this);
	}

	@Override
	public void stateChanged(final ChangeEvent e) {
		for (final ChangeListener listener : listeners) {
			listener.stateChanged(e);
		}
	}

	public void addChangeListener(final ChangeListener l) {
		this.listeners.add(l);
	}

	public void removeChangeListener(final ChangeListener l) {
		this.listeners.remove(l);
	}

}
