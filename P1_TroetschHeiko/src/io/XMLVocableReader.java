package io;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.jdom2.Element;

import model.Vocable;

/**
 * This class extends the {@link XMLReader} and is used to Read a XML file of Vocables. The Specific format is declared in the dtd File.
 * @author heikotroetsch
 *
 */
public class XMLVocableReader extends XMLReader {

	/**
	 * This method creates a Vocable from a given element. 
	 */
	@Override
	protected Vocable parseVocableElement(Element vEle) {
		Locale src = IOSettings.getLocale((vEle.getChildText("sLanguage")));
		Locale target = IOSettings.getLocale((vEle.getChildText("tLanguage")));
		String word = vEle.getChildText("word");
		List<String> translations = new LinkedList<String>();
		List<Element> traValues = vEle.getChild("translation").getChildren();
		for(Element e: traValues){
			translations.add(e.getValue());
		}
		
		List<String> examples = new LinkedList<String>();
		List<Element> exValues = vEle.getChild("examples").getChildren();
		for(Element e: exValues){
			examples.add(e.getValue());
		}
		
		int unit = Integer.parseInt(vEle.getChildText("unit"));
		int section = Integer.parseInt(vEle.getChildText("section"));
		Vocable result = new Vocable(src, target, word, translations,  examples, unit, section);
		result.setLFactor(Double.parseDouble(vEle.getChildText("lFactor")));
		return result;
	}

	/**
	 * This Method is used to return a list of Elements. Here it returns all Vocables as a Elements List. 
	 */
	@Override
	protected Collection<Element> getVocableElements(Element root) {
		List<Element> result;
		result = root.getChildren();
		return result;
	}

	
}
