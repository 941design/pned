package de.markusrother.pned.gui.layout.style;

import javax.swing.event.ChangeListener;

import de.markusrother.swing.ChangeEventSource;

/**
 * Components that can be styled and listen to changes in that style model.
 * 
 * @param <T>
 */
public interface Stylable<T extends ChangeEventSource>
	extends
		ChangeListener {

	void setStyle(T style);

}
