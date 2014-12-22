package de.markusrother.pned.io;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LabelMarshaller extends XmlAdapter<LabelVO, String> {

	@Override
	public String unmarshal(final LabelVO v) throws Exception {
		throw new UnsupportedOperationException("TODO");
	}

	@Override
	public LabelVO marshal(final String label) throws Exception {
		return new LabelVO(label);
	}

}
