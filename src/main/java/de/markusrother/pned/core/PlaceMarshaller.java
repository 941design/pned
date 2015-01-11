package de.markusrother.pned.core;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Currently unused!
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PlaceMarshaller extends XmlAdapter<PlaceVO, DefaultPlace> {

	/** {@inheritDoc} */
	@Override
	public DefaultPlace unmarshal(final PlaceVO v) throws Exception {
		throw new UnsupportedOperationException("TODO");
	}

	/** {@inheritDoc} */
	@Override
	public PlaceVO marshal(final DefaultPlace place) {
		return new PlaceVO(place);
	}

}
