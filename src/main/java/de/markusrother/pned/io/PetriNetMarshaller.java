package de.markusrother.pned.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import de.markusrother.pned.core.EdgeImpl;
import de.markusrother.pned.core.PetriNet;
import de.markusrother.pned.core.PetriNetImpl;
import de.markusrother.pned.core.PlaceImpl;
import de.markusrother.pned.core.TransitionImpl;

public class PetriNetMarshaller {

	public static String createXml(final PetriNet net) throws JAXBException, IOException {
		try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			writeXml(net, out);
			final String xml = new String(out.toByteArray());
			return xml;
		}
	}

	public static void writeXml(final PetriNet net, final OutputStream out) throws JAXBException {
		final Class<?>[] context = { PetriNetImpl.class, PlaceImpl.class, TransitionImpl.class, EdgeImpl.class };
		final JAXBContext jaxbContext = JAXBContext.newInstance(context);
		final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(net, out);
	}

}
