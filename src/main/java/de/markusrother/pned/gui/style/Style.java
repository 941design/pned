package de.markusrother.pned.gui.style;

import java.awt.Component;

public interface Style<T extends Component> {

	void apply(T t);
}
