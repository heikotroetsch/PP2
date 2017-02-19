package io;

import java.io.IOException;
import java.util.Collection;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import model.Vocable;
import model.VocableList;

public abstract class XMLReader  {
	protected abstract Vocable parseVocableElement(Element vEle);
	protected abstract Collection<Element> getVocableElements(Element root);

	/* opens a XML file with name given by the parameter filename,
	 * tries to get all vocables defined in this file and to parse them
	 * using the parseVocable method.
	 * The method returns a new VocableList instance
	 * */
	public VocableList readFile(String filename){
		VocableList vList = new VocableList();
		Collection<Element> vocables;
		try {
		    Document doc = new SAXBuilder().build(filename);
		    if (doc != null){
		    	vocables = this.getVocableElements(doc.getRootElement());
		    	for (Element vEle : vocables){
		    		Vocable v = this.parseVocableElement(vEle);
		    		if (v != null){
		    			vList.add(v);
		    		}
		    	}
		    }
		} catch (IOException ioe){
		    ioe.printStackTrace();
		} catch (JDOMException jde){
		    jde.printStackTrace();
		}
		
		return vList;
	}
	

}
