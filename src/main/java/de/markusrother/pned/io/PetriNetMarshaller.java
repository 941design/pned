package de.markusrother.pned.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import de.markusrother.pned.core.DefaultEdge;
import de.markusrother.pned.core.DefaultPetriNet;
import de.markusrother.pned.core.DefaultPlace;
import de.markusrother.pned.core.DefaultTransition;

/**
 * <p>
 * A converter of {@link de.markusrother.pned.core.DefaultPetriNet} to pnml
 * (xml).
 * </p>
 *
 * <p>
 * TODO - The marshaller should be able to convert the
 * {@link de.markusrother.pned.core.model.PetriNetModel} interface instead of
 * the implementation, only.
 * </p>
 *
 * @author Markus Rother
 * @version 1.0
 */
public class PetriNetMarshaller {

    /**
     * <p>
     * Creates and returns pnml (xml) for a given Petri Net.
     * </p>
     *
     * @param net
     *            the {@link de.markusrother.pned.core.DefaultPetriNet} to be
     *            converted.
     * @return a {@link java.lang.String} - the generated pnml (xml).
     * @throws javax.xml.bind.JAXBException
     *             if any.
     * @throws java.io.IOException
     *             if any.
     */
    public static String createXml(final DefaultPetriNet net) throws JAXBException, IOException {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            writeXml(net, out);
            final String xml = new String(out.toByteArray());
            return xml;
        }
    }

    /**
     * <p>
     * Creates pnml (xml) from a given Petri Net and writes it to the given
     * stream.
     * </p>
     *
     * @param net
     *            the {@link de.markusrother.pned.core.DefaultPetriNet} to be
     *            converted.
     * @param out
     *            the {@link java.io.OutputStream} to be written to.
     * @throws javax.xml.bind.JAXBException
     *             if any.
     */
    public static void writeXml(final DefaultPetriNet net, final OutputStream out) throws JAXBException {
        final Class<?>[] context = { PNMLVO.class, DefaultPetriNet.class, DefaultPlace.class,
                DefaultTransition.class, DefaultEdge.class };
        final PNMLVO pnml = new PNMLVO(net);
        final JAXBContext jaxbContext = JAXBContext.newInstance(context);
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        jaxbMarshaller.marshal(pnml, out);
    }

}
