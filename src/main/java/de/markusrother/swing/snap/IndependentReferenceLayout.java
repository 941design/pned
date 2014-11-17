package de.markusrother.swing.snap;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

/**
 * LayoutManager for positioning components relative to a given component, the
 * former NOT being children of the latter.
 */
public abstract class IndependentReferenceLayout implements LayoutManager {

	private final Component referenceComponent;
	private final List<Component> components;

	public IndependentReferenceLayout(final Component referenceComponent) {
		this.referenceComponent = referenceComponent;
		this.components = new LinkedList<>();
	}

	@Override
	public void addLayoutComponent(final String name, final Component comp) {
		components.add(comp);
	}

	@Override
	public void removeLayoutComponent(final Component comp) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		// TODO
		throw new RuntimeException("TODO");
	}

	@Override
	public void layoutContainer(final Container parent) {
		for (final Component relativeComponent : components) {
			final Rectangle r = getPreferredBounds(referenceComponent, relativeComponent);
			relativeComponent.setBounds(r);
		}
	}

	abstract Rectangle getPreferredBounds(final Component referenceComponent, Component relativeComponent);

}
