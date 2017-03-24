package io;

import java.io.FileWriter;

import java.io.IOException;
import java.util.Collection;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import model.Vocable;

public abstract class XMLWriter extends VocableWriter {
	protected Document doc; 
	protected Element vList; 
	

	protected abstract void mapVoc(Vocable v);

	protected void writeAll(FileWriter fw, Collection<Vocable> vList) {
		if(doc == null){
			doc = new Document();
			doc.setRootElement(new Element("book"));
		}
		if (doc != null){
			Element root = this.doc.getRootElement();
			for (Vocable v : vList){
				mapVoc(v);
			}

			try {
				XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
				System.out.println(doc.toString());
				out.output(doc,fw);
			} catch(IOException ioe){
				System.out.println("Writing XML document to file failed.");
				ioe.printStackTrace();
			}
		}
	}

}
