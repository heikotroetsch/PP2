package Uebung6;

import java.io.*;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
public class TestSax{
public final static String resourcePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator");

    public static void main(String[] args) {
	try {
		// XMLReader erzeugen
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();

		// Pfad zur XML Datei
		FileReader reader = new FileReader(resourcePath + "waren.xml");
		InputSource inputSource = new InputSource(reader);

		// DTD kann optional uebergeben werden
		// inputSource.setSystemId(".../personen.dtd");

		// PersonenContentHandler wird uebergeben
		xmlReader.setContentHandler(new WarenContentHandler());
		// Parsen wird gestartet
		xmlReader.parse(inputSource);
		Document doc = null;
		try {
			doc = new SAXBuilder().build(resourcePath);
		} catch (JDOMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		List<Element> list = root.getChildren("Ware");
		int i = 0;
		for(Element e: list){
			System.out.println(i+" "+e.getChildText("Name"));
			i++;
		}
	    } catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e){
	    e.printStackTrace();
	} catch (SAXException e){
	    e.printStackTrace();
	}
    }
}
