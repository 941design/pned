package de.markusrother.pned.core;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MarkingMarshaller extends XmlAdapter<MarkingVO, Integer> {

	/** {@inheritDoc} */
	@Override
	public Integer unmarshal(final MarkingVO v) throws Exception {
		throw new UnsupportedOperationException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public MarkingVO marshal(final Integer marking) {
		return new MarkingVO(marking);
	}

}
