package de.markusrother.pned.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import de.markusrother.pned.gui.EventTarget;
import de.markusrother.pned.gui.events.EdgeCreationCommand;
import de.markusrother.pned.gui.events.LabelEditEvent;
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.PlaceEditEvent;
import de.markusrother.pned.gui.events.TransitionCreationCommand;

/**
 * <p>
 * A simple pnml (xml) parser. Given some sort of readable input the
 * {@code PNMLParser} interprets it as pnml, generating {@code EventObject}s
 * which are posted on the provided {@link EventTarget}.
 * </p>
 * <p>
 * This class does <b>not</b> actually create a Petri Net. It only provides the
 * events necessary to do so by posting them to the provided {@link EventTarget}
 * . To create a Petri Net the provided {@code EventObject}s have to be
 * interpreted, e.g. by:
 * </p>
 * 
 * <pre>
 * ...
 * EventBus eventBus = new EventBus();
 * PetriNet petriNet = new EventAwarePetriNet(eventBus);
 * PNMLParser.parse(pnmlResource, eventBus);
 * ...
 * </pre>
 * <p>
 * ... where {@code EventBus} implements {@link EventTarget},
 * {@code de.markusrother.pned.core.EventAwarePetriNet} implements all necessary
 * {@link java.util.EventListener}s, and {@code pnmlResource} is any first
 * parameter of one of the following business methods.
 * </p>
 * Available business methods:
 * <ul>
 * <li>{@link #parse(File, EventTarget)}</li>
 * <li>{@link #parse(InputStream, EventTarget)}</li>
 * <li>{@link #parse(URL, EventTarget)}</li>
 * <li>{@link #parse(XMLEventReader, EventTarget)}</li>
 * </ul>
 * <p>
 * TODO - There should be a single interface similar to {@link EventTarget}
 * extending all listeners necessary to create a Petri Net.
 * </p>
 * <p>
 * FIXME - Test against malformed xml/pnml.
 * </p>
 * <p>
 * FIXME - Rename all events needed here, to commands.
 * </p>
 * <p>
 * FIXME - Move {@code EventTarget} and all events needed here, to a different
 * package.
 * </p>
 * 
 * @author unknown
 * @author Markus Rother
 * @version 1.0
 * @see EventTarget
 */
public class PNMLParser {

	/**
	 * <p>
	 * Parses given {@link java.net.URL}, and broadcasts resulting events on
	 * given {@link EventTarget}.
	 * </p>
	 *
	 * @param resource
	 *            a {@link java.net.URL} to be read.
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.EventTarget} to which
	 *            parsed results are broadcasted as events.
	 * @throws javax.xml.stream.XMLStreamException
	 *             if any.
	 * @throws java.io.IOException
	 *             if any.
	 */
	public static void parse(final URL resource, final EventTarget eventTarget) throws XMLStreamException, IOException {
		try (InputStream inputStream = resource.openStream()) {
			parse(inputStream, eventTarget);
		}
	}

	/**
	 * <p>
	 * Parses given {@link java.io.File}, and broadcasts resulting events on
	 * given {@link EventTarget}.
	 * </p>
	 *
	 * @param file
	 *            a {@link java.io.File} to be read.
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.EventTarget} to which
	 *            parsed results are broadcasted as events.
	 * @throws java.io.FileNotFoundException
	 *             if any.
	 * @throws javax.xml.stream.XMLStreamException
	 *             if any.
	 */
	public static void parse(final File file, final EventTarget eventTarget) throws FileNotFoundException,
			XMLStreamException {
		parse(new FileInputStream(file), eventTarget);
	}

	/**
	 * <p>
	 * Parses given {@link java.io.InputStream}, and broadcasts resulting events
	 * on given {@link EventTarget}.
	 * </p>
	 *
	 * @param inputStream
	 *            a {@link java.io.InputStream} to be read.
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.EventTarget} to which
	 *            parsed results are broadcasted as events.
	 * @throws javax.xml.stream.XMLStreamException
	 *             if any.
	 */
	public static void parse(final InputStream inputStream, final EventTarget eventTarget) throws XMLStreamException {
		final XMLInputFactory factory = XMLInputFactory.newInstance();
		parse(factory.createXMLEventReader(inputStream), eventTarget);
	}

	/**
	 * <p>
	 * Parses given {@link javax.xml.stream.XMLEventReader}, and broadcasts
	 * resulting events on given {@link EventTarget}.
	 * </p>
	 *
	 * @param xmlEventReader
	 *            a {@link javax.xml.stream.XMLEventReader} to be read.
	 * @param eventTarget
	 *            an {@link de.markusrother.pned.gui.EventTarget} to which
	 *            parsed results are broadcasted as events.
	 */
	public static void parse(final XMLEventReader xmlEventReader, final EventTarget eventTarget) {
		final PNMLParser parser = new PNMLParser(xmlEventReader, eventTarget);
		parser.parse();
	}

	/**
	 * Dies ist eine Referenz zum XML Parser. Diese Referenz wird im Konstruktor
	 * initialisiert.
	 */
	private final XMLEventReader xmlParser;

	/**
	 * Diese Variable dient als Zwischenspeicher für die ID des zuletzt
	 * gefundenen Elements.
	 */
	private String lastId = null;

	/**
	 * Dieses Flag zeigt an, ob der Parser gerade innerhalb eines Token Elements
	 * liest.
	 */
	private boolean isToken = false;

	/**
	 * Dieses Flag zeigt an, ob der Parser gerade innerhalb eines Name Elements
	 * liest.
	 */
	private boolean isName = false;

	/**
	 * Dieses Flag zeigt an, ob der Parser gerade innerhalb eines Value Elements
	 * liest.
	 */
	private boolean isValue = false;

	/**
	 * The receiver of all generated events.
	 */
	private final EventTarget eventTarget;

	/**
	 * <p>
	 * Constructor for PNMLParser.
	 * </p>
	 *
	 * @param xmlParser
	 *            a {@link javax.xml.stream.XMLEventReader} to be read from.
	 * @param eventTarget
	 *            a {@link de.markusrother.pned.gui.EventTarget} to be
	 *            broadcasted to.
	 */
	private PNMLParser(final XMLEventReader xmlParser, final EventTarget eventTarget) {
		this.xmlParser = xmlParser;
		this.eventTarget = eventTarget;
	}

	/**
	 * Diese Methode liest die XML Datei und delegiert die gefundenen XML
	 * Elemente an die entsprechenden Methoden.
	 */
	private final void parse() {
		while (xmlParser.hasNext()) {
			try {
				final XMLEvent event = xmlParser.nextEvent();
				switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					handleStartEvent(event);
					break;
				case XMLStreamConstants.END_ELEMENT:
					final String name = event.asEndElement().getName().toString().toLowerCase();
					if (name.equals("token")) {
						isToken = false;
					} else if (name.equals("name")) {
						isName = false;
					} else if (name.equals("value")) {
						isValue = false;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if (isValue && lastId != null) {
						final Characters ch = event.asCharacters();
						if (!ch.isWhiteSpace()) {
							handleValue(ch.getData());
						}
					}
					break;
				case XMLStreamConstants.END_DOCUMENT:
					// schließe den Parser
					xmlParser.close();
					break;
				default:
				}
			} catch (final XMLStreamException e) {
				System.err.println("Fehler beim Parsen des PNML Dokuments. " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Diese Methode behandelt den Start neuer XML Elemente, in dem der Name des
	 * Elements überprüft wird und dann die Behandlung an spezielle Methoden
	 * delegiert wird.
	 *
	 * @param event
	 *            {@link javax.xml.stream.events.XMLEvent}
	 */
	private void handleStartEvent(final XMLEvent event) {
		final StartElement element = event.asStartElement();
		final String lowercaseName = element.getName().toString().toLowerCase();
		// TODO - use predicates!
		if (lowercaseName.equals("transition")) {
			handleTransition(element);
		} else if (lowercaseName.equals("place")) {
			handlePlace(element);
		} else if (lowercaseName.equals("arc")) {
			handleArc(element);
		} else if (lowercaseName.equals("name")) {
			isName = true;
		} else if (lowercaseName.equals("position")) {
			handlePosition(element);
		} else if (lowercaseName.equals("token")) {
			isToken = true;
		} else if (lowercaseName.equals("value")) {
			isValue = true;
		}
	}

	/**
	 * Diese Methode wird aufgerufen, wenn Text innerhalb eines Value Elements
	 * gelesen wird.
	 *
	 * @param value
	 *            Der gelesene Text als String
	 */
	private void handleValue(final String value) {
		if (isName) {
			setName(lastId, value);
		} else if (isToken) {
			setMarking(lastId, value);
		}
	}

	/**
	 * Diese Methode wird aufgerufen, wenn ein Positionselement gelesen wird.
	 *
	 * @param element
	 *            das Positionselement
	 */
	private void handlePosition(final StartElement element) {
		String x = null;
		String y = null;
		final Iterator<?> attributes = element.getAttributes();
		while (attributes.hasNext()) {
			final Attribute attr = (Attribute) attributes.next();
			if (attr.getName().toString().toLowerCase().equals("x")) {
				x = attr.getValue();
			} else if (attr.getName().toString().toLowerCase().equals("y")) {
				y = attr.getValue();
			}
		}
		if (x != null && y != null && lastId != null) {
			setPosition(lastId, x, y);
		} else {
			System.err.println("Unvollständige Position wurde verworfen!");
		}
	}

	/**
	 * Diese Methode wird aufgerufen, wenn ein Transitionselement gelesen wird.
	 *
	 * @param element
	 *            das Transitionselement
	 */
	private void handleTransition(final StartElement element) {
		String transitionId = null;
		final Iterator<?> attributes = element.getAttributes();
		while (attributes.hasNext()) {
			final Attribute attr = (Attribute) attributes.next();
			if (attr.getName().toString().toLowerCase().equals("id")) {
				transitionId = attr.getValue();
				break;
			}
		}
		if (transitionId != null) {
			newTransition(transitionId);
			lastId = transitionId;
		} else {
			System.err.println("Transition ohne id wurde verworfen!");
			lastId = null;
		}
	}

	/**
	 * Diese Methode wird aufgerufen, wenn ein Stellenelement gelesen wird.
	 *
	 * @param element
	 *            das Stellenelement
	 */
	private void handlePlace(final StartElement element) {
		String placeId = null;
		final Iterator<?> attributes = element.getAttributes();
		while (attributes.hasNext()) {
			final Attribute attr = (Attribute) attributes.next();
			if (attr.getName().toString().toLowerCase().equals("id")) {
				placeId = attr.getValue();
				break;
			}
		}
		if (placeId != null) {
			newPlace(placeId);
			lastId = placeId;
		} else {
			System.err.println("Stelle ohne id wurde verworfen!");
			lastId = null;
		}
	}

	/**
	 * Diese Methode wird aufgerufen, wenn ein Kantenelement gelesen wird.
	 *
	 * @param element
	 *            das Kantenelement
	 */
	private void handleArc(final StartElement element) {
		String arcId = null;
		String source = null;
		String target = null;
		final Iterator<?> attributes = element.getAttributes();
		while (attributes.hasNext()) {
			final Attribute attr = (Attribute) attributes.next();
			if (attr.getName().toString().toLowerCase().equals("id")) {
				arcId = attr.getValue();
			} else if (attr.getName().toString().toLowerCase().equals("source")) {
				source = attr.getValue();
			} else if (attr.getName().toString().toLowerCase().equals("target")) {
				target = attr.getValue();
			}
		}
		if (arcId != null && source != null && target != null) {
			newArc(arcId, source, target);
		} else {
			System.err.println("Unvollständige Kante wurde verworfen!");
		}
		// Die id von Kanten wird nicht gebraucht
		lastId = null;
	}

	/**
	 * Posts {@link TransitionCreationCommand} on the provided
	 * {@link EventTarget}.
	 *
	 * @param transitionId
	 *            Identifikationstext der Transition
	 */
	private void newTransition(final String transitionId) {
		final TransitionCreationCommand cmd = new TransitionCreationCommand(this, transitionId);
		eventTarget.createTransition(cmd);
	}

	/**
	 * Posts {@link PlaceCreationCommand} on the provided {@link EventTarget}.
	 *
	 * @param placeId
	 *            Identifikationstext der Stelle
	 */
	private void newPlace(final String placeId) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(this, placeId);
		eventTarget.createPlace(cmd);
	}

	/**
	 * Posts {@link EdgeCreationCommand} on the provided {@link EventTarget}.
	 *
	 * @param edgeId
	 *            Identifikationstext der Kante
	 * @param sourceId
	 *            Identifikationstext des Startelements der Kante
	 * @param targetId
	 *            Identifikationstext des Endelements der Kante
	 */
	private void newArc(final String edgeId, final String sourceId, final String targetId) {
		final EdgeCreationCommand cmd = new EdgeCreationCommand(this, edgeId, sourceId, targetId);
		eventTarget.createEdge(cmd);
	}

	/**
	 * Posts {@link NodeMovedEvent} on the provided {@link EventTarget}.
	 *
	 * @param elementId
	 *            Identifikationstext des Elements
	 * @param x
	 *            x Position des Elements
	 * @param y
	 *            y Position des Elements
	 */
	private void setPosition(final String elementId, final String x, final String y) {
		// FIXME - catch NumberFormatException!
		final int deltaX = Integer.valueOf(x);
		final int deltaY = Integer.valueOf(y);
		final NodeMovedEvent e = new NodeMovedEvent(this, Arrays.asList(elementId), deltaX, deltaY);
		eventTarget.nodeMoved(e);
	}

	/**
	 * Posts {@link LabelEditEvent} on the provided {@link EventTarget}.
	 *
	 * @param elementId
	 *            Identifikationstext des Elements
	 * @param label
	 *            Beschriftungstext des Elements
	 */
	private void setName(final String elementId, final String label) {
		final LabelEditEvent e = new LabelEditEvent(this, elementId, label);
		eventTarget.setLabel(e);
	}

	/**
	 * Posts {@link PlaceEditEvent} on the provided {@link EventTarget}.
	 *
	 * @param placeId
	 *            Identifikationstext des Elements
	 * @param marking
	 *            Markierung des Elements
	 */
	private void setMarking(final String placeId, final String marking) {
		final PlaceEditEvent e = new PlaceEditEvent(this, placeId, Integer.valueOf(marking));
		eventTarget.setMarking(e);
	}
}
