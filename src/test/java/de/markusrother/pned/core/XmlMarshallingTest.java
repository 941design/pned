package de.markusrother.pned.core;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Assert;
import org.junit.Test;

public class XmlMarshallingTest extends AbstractPetriNetTest {

	private static final String DOT = ".*?";
	private static final String textRegex = "<text>" + DOT + "(\\w*)" + DOT + "</text>"; //
	private static final String markingRegex = "<initialMarking>" + DOT + "(\\d+)" + DOT + "</initialMarking>"; //
	private static final String positionRegex = "" //
			+ "<graphics>" + DOT //
			+ "<position x=\"(\\d+)\" y=\"(\\d+)\"/>" + DOT //
			+ "</graphics>";
	private static final String offsetRegex = "" //
			+ "<graphics>" + DOT //
			+ "<offset x=\"(\\d+)\" y=\"(\\d+)\"/>" + DOT //
			+ "</graphics>";
	private static final String labelRegex = "" //
			+ "<name>" + DOT //
			+ textRegex + DOT //
			+ offsetRegex + DOT //
			+ "</name>";
	private static final String placeRegex = "" //
			+ "<place id=\"(\\w*)\">" + DOT //
			+ "(" + labelRegex + DOT + ")?" //
			+ positionRegex + DOT //
			+ markingRegex + DOT //
			+ "</place>";
	private static final String transitionRegex = "" //
			+ "<transition id=\"(\\w*)\">" + DOT //
			+ positionRegex + DOT //
			+ "</transition>";
	private static final String edgeRegex = "<arc id=\"(\\w*)\" source=\"(\\w*)\" target=\"(\\w*)\"/>";

	private String xml;

	private void buildXml() throws JAXBException {
		final Class<?>[] context = { PetriNetImpl.class, PlaceImpl.class, TransitionImpl.class, EdgeImpl.class };
		final JAXBContext jaxbContext = JAXBContext.newInstance(context);
		final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(net, out);
		xml = new String(out.toByteArray());
	}

	private void assertXmlContains(final String format, final Object... args) throws JAXBException {
		final String substring = String.format(format, args);
		buildXml();
		Assert.assertTrue(xml.contains(substring));
	}

	private void assertXmlMatches(final String regex) throws JAXBException {
		final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		assertXmlMatches(pattern);
	}

	private void assertXmlMatches(final Pattern pattern) throws JAXBException {
		buildXml();
		final Matcher matcher = pattern.matcher(xml);
		Assert.assertTrue(matcher.find());
	}

	private void assertRegexCaptures(final String regex, final String... captures) throws JAXBException {
		final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
		buildXml();
		final Matcher matcher = pattern.matcher(xml);

		Assert.assertTrue(matcher.find());
		Assert.assertTrue(captures.length <= matcher.groupCount());
		for (int i = 0; i < captures.length; i++) {
			final String capture = captures[i];
			Assert.assertEquals(capture, matcher.group(i + 1));
			i++;
		}
	}

	@Test
	public void testPlaceIsMarshalled() throws Exception {
		createPlace(p1);
		assertXmlContains("<place id=\"%s\"", p1);
		assertXmlMatches(placeRegex);
		assertRegexCaptures(placeRegex, p1);
		assertRegexCaptures(positionRegex, "100", "100");
	}

	@Test
	public void testTransitionIsMarshalled() throws Exception {
		createTransition(t1);
		assertXmlContains("<transition id=\"%s\"", t1);
		assertXmlMatches(transitionRegex);
		assertRegexCaptures(transitionRegex, t1);
		assertRegexCaptures(positionRegex, "100", "100");
	}

	@Test
	public void testEdgeIsMarshalled() throws Exception {
		createPlace(p1);
		createTransition(t1);
		createEdge(e1, p1, t1);
		assertXmlContains("<arc id=\"%s\" source=\"%s\" target=\"%s\"", e1, p1, t1);
		assertXmlMatches(edgeRegex);
		assertRegexCaptures(edgeRegex, e1, p1, t1);
	}

	@Test
	public void testPlaceLabelIsMarshalled() throws Exception {
		createPlace(p1, l1);
		assertXmlContains("<name>");
		assertXmlContains("<text>%s</text>", l1);
		assertXmlMatches(textRegex);
		assertXmlMatches(labelRegex);
		assertRegexCaptures(labelRegex, l1);
	}

	@Test
	public void testLabelOffsetIsMarshalled() throws Exception {
		createPlace(p1, l1);
		assertXmlMatches(offsetRegex);
		assertRegexCaptures(offsetRegex, "0", "0");
	}

	@Test
	public void testPlaceMarkingIsMarshalled() throws Exception {
		createPlace(p1, 23);
		assertXmlMatches(markingRegex);
		assertRegexCaptures(markingRegex, "23");
	}

	@Test
	public void testPlacePositionIsMarshalled() throws Exception {
		createPlace(p1);
		assertXmlMatches(positionRegex);
		assertRegexCaptures(positionRegex, "100", "100");
	}

	@Test
	public void testTransitionPositionIsMarshalled() throws Exception {
		createTransition(t1);
		assertXmlMatches(positionRegex);
		assertRegexCaptures(positionRegex, "100", "100");
	}

	@Test
	public void testMarshallingStructure() throws Exception {
		createPlace(p1, 0);
		createPlace(p2, 23);
		createPlace(p3, 42);
		createTransition(t1);
		createTransition(t2);
		createEdge(e1, p1, t1);
		createEdge(e2, p2, t1);
		createEdge(e3, p1, t2);
		createEdge(e4, t1, p3);
		buildXml();
	}
}
