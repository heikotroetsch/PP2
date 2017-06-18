package Uebung6;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xml.sax.*;

public class WarenContentHandler implements ContentHandler {

    private ArrayList<Ware> waren = new ArrayList<Ware>();
    private String currentValue;
    private Ware ware;

    // Aktuelle Zeichen die gelesen werden, werden in eine 
    // Zwischenvariable gespeichert (Aufr. der Meth. durch Parser)
    public void characters(char[] ch, int start, int length)throws SAXException {
	currentValue = new String(ch,start,length);
    }
    // Methode wird aufgerufen wenn der Parser zu einem Start Tag 
    // kommt
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

	if (localName.equals("Ware")) {
	    // Neues Warenobjekt erzeugen
	    ware = new Ware();

	    // Attributwerte werden als Strings geholt, 
	    // ggf. umgewandelt und in der Waren-Instanz gespeichert
	    ware.setId(atts.getValue("id"));
	    ware.setPreis(Double.parseDouble(atts.getValue("Preis")));
	    ware.setAnzahl(Integer.parseInt(atts.getValue("Anzahl")));
	}
    }
    // Methode wird aufgerufen wenn der Parser zu einem End-Tag 
    // kommt
    public void endElement(String uri, String localName,String qName) throws SAXException {

	// Name setzen
	if (localName.equals("Name")) {
	    ware.setName(currentValue);
	}

	// Kategorie setzen
	if (localName.equals("Kategorie")) {
	    ware.setKategorie(currentValue);
	}

	// Ware in Liste abspeichern falls End-Tag von Ware erreicht
	if (localName.equals("Ware")) {
	    waren.add(ware);
	    System.out.println(ware); // Ausgabe-Kontrolle
	}}

    // leere Implementierungen für alle weiteren Methoden
    // des Interfaces
    public void endDocument() throws SAXException {}
    public void endPrefixMapping(String prefix) throws SAXException {}
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
    public void processingInstruction(String target,String data) throws SAXException {}
    public void setDocumentLocator(Locator locator){}
    public void skippedEntity(String name) throws SAXException {}
    public void startDocument() throws SAXException {}
    public void startPrefixMapping(String prefix,String uri)throws SAXException{}
}
