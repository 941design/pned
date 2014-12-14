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
import de.markusrother.pned.gui.events.NodeMovedEvent;
import de.markusrother.pned.gui.events.PlaceCreationCommand;
import de.markusrother.pned.gui.events.PlaceEditEvent;
import de.markusrother.pned.gui.events.TransitionCreationCommand;

/**
 * Diese Klasse implementiert die Grundlage für einen einfachen PNML Parser.
 */
public class PNMLParser {

	public static void parse(final URL resource, final EventTarget eventTarget) throws XMLStreamException, IOException {
		@SuppressWarnings("resource")
		// Suppressed, because exception is propagated.
		final InputStream inputStream = resource.openStream();
		parse(inputStream, eventTarget);
		inputStream.close();
	}

	public static void parse(final File pnml, final EventTarget eventTarget) throws FileNotFoundException,
			XMLStreamException {
		parse(new FileInputStream(pnml), eventTarget);
	}

	public static void parse(final InputStream inputStream, final EventTarget eventTarget) throws XMLStreamException {
		final XMLInputFactory factory = XMLInputFactory.newInstance();
		parse(factory.createXMLEventReader(inputStream), eventTarget);
	}

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

	private final EventTarget eventTarget;

	private PNMLParser(final XMLEventReader xmlParser, final EventTarget eventTarget) {
		this.xmlParser = xmlParser;
		this.eventTarget = eventTarget;
	}

	/**
	 * Diese Methode liest die XML Datei und delegiert die gefundenen XML
	 * Elemente an die entsprechenden Methoden.
	 */
	public final void parse() {
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
	 *            {@link XMLEvent}
	 */
	private void handleStartEvent(final XMLEvent event) {
		final StartElement element = event.asStartElement();
		// TODO - use predicates!
		if (element.getName().toString().toLowerCase().equals("transition")) {
			handleTransition(element);
		} else if (element.getName().toString().toLowerCase().equals("place")) {
			handlePlace(element);
		} else if (element.getName().toString().toLowerCase().equals("arc")) {
			handleArc(element);
		} else if (element.getName().toString().toLowerCase().equals("name")) {
			isName = true;
		} else if (element.getName().toString().toLowerCase().equals("position")) {
			handlePosition(element);
		} else if (element.getName().toString().toLowerCase().equals("token")) {
			isToken = true;
		} else if (element.getName().toString().toLowerCase().equals("value")) {
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
	 * Diese Methode kann überschrieben werden, um geladene Transitionen zu
	 * erstellen.
	 * 
	 * @param id
	 *            Identifikationstext der Transition
	 */
	public void newTransition(final String id) {
		final TransitionCreationCommand cmd = new TransitionCreationCommand(this, id);
		eventTarget.createTransition(cmd);
	}

	/**
	 * Diese Methode kann überschrieben werden, um geladene Stellen zu
	 * erstellen.
	 * 
	 * @param id
	 *            Identifikationstext der Stelle
	 */
	public void newPlace(final String id) {
		final PlaceCreationCommand cmd = new PlaceCreationCommand(this, id);
		eventTarget.createPlace(cmd);
	}

	/**
	 * Diese Methode kann überschrieben werden, um geladene Kanten zu erstellen.
	 * 
	 * @param id
	 *            Identifikationstext der Kante
	 * @param source
	 *            Identifikationstext des Startelements der Kante
	 * @param target
	 *            Identifikationstext des Endelements der Kante
	 */
	public void newArc(final String id, final String source, final String target) {
		// TODO - Cannot create edge, yet, because order is not deterministic!
		// Must queue events in an intermediary eventTarget!
		System.out.println("Kante mit id " + id + " von " + source + " nach " + target + " wurde gefunden.");
	}

	/**
	 * Diese Methode kann überschrieben werden, um die Positionen der geladenen
	 * Elemente zu aktualisieren.
	 * 
	 * @param id
	 *            Identifikationstext des Elements
	 * @param x
	 *            x Position des Elements
	 * @param y
	 *            y Position des Elements
	 */
	public void setPosition(final String id, final String x, final String y) {
		final int deltaX = Integer.valueOf(x);
		final int deltaY = Integer.valueOf(y);
		final NodeMovedEvent e = new NodeMovedEvent(this, Arrays.asList(id), deltaX, deltaY);
		eventTarget.nodeMoved(e);
	}

	/**
	 * Diese Methode kann überschrieben werden, um den Beschriftungstext der
	 * geladenen Elemente zu aktualisieren.
	 * 
	 * @param id
	 *            Identifikationstext des Elements
	 * @param name
	 *            Beschriftungstext des Elements
	 */
	public void setName(final String id, final String name) {
		// TODO - id vs. label!
		System.out.println("Setze den Namen des Elements " + id + " auf " + name);
	}

	/**
	 * Diese Methode kann überschrieben werden, um die Markierung der geladenen
	 * Elemente zu aktualisieren.
	 * 
	 * @param id
	 *            Identifikationstext des Elements
	 * @param marking
	 *            Markierung des Elements
	 */
	public void setMarking(final String id, final String marking) {
		final PlaceEditEvent e = new PlaceEditEvent(this, id, Integer.valueOf(marking));
		eventTarget.setMarking(e);
	}
}
