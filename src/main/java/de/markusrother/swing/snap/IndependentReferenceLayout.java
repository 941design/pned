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
 *
 * @author Markus Rother
 * @version 1.0
 */
public abstract class IndependentReferenceLayout implements LayoutManager {

	private final Component referenceComponent;
	private final List<Component> components;

	/**
	 * <p>Constructor for IndependentReferenceLayout.</p>
	 *
	 * @param referenceComponent a {@link java.awt.Component} object.
	 */
	public IndependentReferenceLayout(final Component referenceComponent) {
		this.referenceComponent = referenceComponent;
		this.components = new LinkedList<>();
	}

	/** {@inheritDoc} */
	@Override
	public void addLayoutComponent(final String name, final Component comp) {
		components.add(comp);
	}

	/** {@inheritDoc} */
	@Override
	public void removeLayoutComponent(final Component comp) {
		// TODO
		throw new RuntimeException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		// TODO
		throw new RuntimeException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		// TODO
		throw new RuntimeException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public void layoutContainer(final Container parent) {
		for (final Component relativeComponent : components) {
			final Rectangle r = getPreferredBounds(referenceComponent, relativeComponent);
			relativeComponent.setBounds(r);
		}
	}

	/**
	 * <p>getPreferredBounds.</p>
	 *
	 * @param referenceComponent a {@link java.awt.Component} object.
	 * @param relativeComponent a {@link java.awt.Component} object.
	 * @return a {@link java.awt.Rectangle} object.
	 */
	abstract Rectangle getPreferredBounds(final Component referenceComponent, Component relativeComponent);

}
