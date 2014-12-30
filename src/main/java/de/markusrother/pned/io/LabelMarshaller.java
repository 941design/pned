package de.markusrother.pned.io;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p>LabelMarshaller class.</p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class LabelMarshaller extends XmlAdapter<LabelVO, String> {

	/** {@inheritDoc} */
	@Override
	public String unmarshal(final LabelVO v) throws Exception {
		throw new UnsupportedOperationException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public LabelVO marshal(final String label) throws Exception {
		return new LabelVO(label);
	}

}
