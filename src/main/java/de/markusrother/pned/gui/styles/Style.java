package de.markusrother.pned.gui.styles;

import java.awt.Component;

public interface Style<T extends Component> {

	void apply(T t);
}
