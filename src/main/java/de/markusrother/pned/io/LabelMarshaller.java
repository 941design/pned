package de.markusrother.pned.io;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p>
 * A class transforming the {@link de.markusrother.pned.core.NodeImpl#getLabel}
 * to a {@link de.markusrother.pned.io.LabelVO} during conversion to pnml (xml).
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 * @see PetriNetMarshaller
 */
public class LabelMarshaller extends XmlAdapter<LabelVO, String> {

	/** {@inheritDoc} */
	@Override
	public String unmarshal(final LabelVO v) throws Exception {
		throw new UnsupportedOperationException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public LabelVO marshal(final String label) {
		return new LabelVO(label);
	}

}
