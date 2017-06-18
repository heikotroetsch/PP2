package Uebung6;

import java.io.*;
import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.output.*;
import org.xml.sax.XMLReader;

import java.util.*;

import javax.xml.stream.XMLInputFactory;

public class Adressbuch extends AbsAdressbuch{

    public Adressbuch(AdrGUI parent){
	super(parent);
	// leeres Dokument anlegen, falls neue Adressen erzeugt werden
	Element r = new Element("adressbuch");
	Element bez = new Element("bezeichnung");
	bez.addContent("Neues Adressbuch");
	r.addContent(bez);
	DocType doctype = new DocType("adressbuch");
	this.doc = new Document(r,doctype);
	this.adressen = this.doc.getRootElement().getChildren("adresse"); // leere Liste
	current = -1; // aktuelles Adresselement auf -1, da Liste leer
    }

    public boolean loadDocument(File file){
    	Document document = null;
		try {
			this.doc = new SAXBuilder().build(file);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.adressen = this.doc.getRootElement().getChildren("adresse"); 
    	return true;
    }

    public void saveDocument(File file){
    	XMLOutputter xmlout = new XMLOutputter();
    	try {
			xmlout.output(this.doc, new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public Adresse getFirstEntry(){
    	Element e = this.adressen.get(0);
    	Adresse a = new Adresse(e.getChildText("vorname"),e.getChildText("nachname"),e.getChildText("email"), 
				e.getChild("telefon").getAttributeValue("land"), e.getChild("telefon").getAttributeValue("vorwahl"), 
				e.getChildText("telefon"), e.getChildText("notiz"));
    	return a;
    }

    public Adresse getCurrent(){
    	Element e = this.adressen.get(this.current);
    	Adresse a = new Adresse(e.getChildText("vorname"),e.getChildText("nachname"),e.getChildText("email"), 
				e.getChild("telefon").getAttributeValue("land"), e.getChild("telefon").getAttributeValue("vorwahl"), 
				e.getChildText("telefon"), e.getChildText("notiz"));
    	return a;
    }

    public Adresse getNext(){
    	this.current++;
    	Element e = this.adressen.get(this.current);
    	Adresse a = new Adresse(e.getChildText("vorname"),e.getChildText("nachname"),e.getChildText("email"), 
				e.getChild("telefon").getAttributeValue("land"), e.getChild("telefon").getAttributeValue("vorwahl"), 
				e.getChildText("telefon"), e.getChildText("notiz"));
    	return a;
    }

    public Adresse getPrev(){
    	this.current--;
    	Element e = this.adressen.get(this.current);
    	Adresse a = new Adresse(e.getChildText("vorname"),e.getChildText("nachname"),e.getChildText("email"), 
				e.getChild("telefon").getAttributeValue("land"), e.getChild("telefon").getAttributeValue("vorwahl"), 
				e.getChildText("telefon"), e.getChildText("notiz"));
    	return a;
    }
    


    public void save(Adresse a){
    	Element e = new Element("Adresse");
    	e.addContent(new Element("vorname").setText(a.getVorname()));
    	e.addContent(new Element("nachname").setText(a.getNachname()));
    	e.addContent(new Element("email").setText(a.getEmail()));
    	Element tele = new Element("telefon").setText(a.getNummer());
    	tele.setAttribute("land", a.getLand());
    	tele.setAttribute("vorwahl", a.getVorwahl());
    	e.addContent(tele);
    	e.addContent(new Element("notiz").setText(a.getNotiz()));
    	doc.addContent(e);
    }


    // holt aus Adresselement die Daten und erzeugt eine Instanz der Klasse
    // Adresse
    protected  Adresse getAdresse(Element e){
    	return new Adresse(e.getChildText("vorname"),e.getChildText("nachname"),e.getChildText("email"), 
				e.getChild("telefon").getAttributeValue("land"), e.getChild("telefon").getAttributeValue("vorwahl"), 
				e.getChildText("telefon"), e.getChildText("notiz"));
    }

    public Adresse addNeueAdresse(Adresse a){
    	return a;
   }


    public Adresse sort(int compBy){
	if (adressen.size() > 1){
	    // Adressliste kopieren (Elemente ohne Parent)
	    List<Element> cpAdressen = new ArrayList<Element>();
	    for(Element e : adressen) 
		cpAdressen.add(e.clone());

	    Adresse.COMP_BY = compBy;
	    final Element e = adressen.get(current);
	    Comparator<Element> comparator = new Comparator<Element>(){
		public int compare(Element e1, Element e2){
		    return Adressbuch.this.getAdresse(e1).compareTo(Adressbuch.this.getAdresse(e2));
		}
		public boolean equals(Object o2){
		    return Adressbuch.this.getAdresse(e).equals(Adressbuch.this.getAdresse((Element) o2));
		}
	    };
	    Collections.sort(cpAdressen,comparator);
	    // Content (Adress-Elemente) ersetzen
	    // Content besteht aus Bezeichner und Adressen
	    Element r = doc.getRootElement();
	    r.removeChildren("adresse"); // alte Adressen entfernen
	    r.addContent(cpAdressen); //sortierte Liste einfügen
	    adressen = r.getChildren("adresse");
	}
	return getFirstEntry();
    }

    private Adresse addAdressElementAt(int index, Element ele){
	    List<Element> cpAdressen = new ArrayList<Element>();
	    for(Element e : this.adressen) 
		cpAdressen.add(e.clone());
	    cpAdressen.add(index,ele);
	    Element r = doc.getRootElement();
	    r.removeChildren("adresse"); // alte Adressen entfernen
	    r.addContent(cpAdressen); //sortierte Liste einfügen
	    adressen = r.getChildren("adresse");
	    return this.getAdresse(adressen.get(index));
    }

}
