package io;

import org.jdom2.Element;

import model.Vocable;
/**
 * This class is used to write the XML representation of the vocables to a file. It extends the XMLWriter Class.
 * @author heikotroetsch
 *
 */
public class XMLVocableWriter extends XMLWriter {

	/**
	 * This method maps a Vocable to the doc variable. It creates a xml representation of a single vocable and adds it to doc. 
	 */
	@Override
	protected void mapVoc(Vocable v) {
		Element e = new Element("vocable");
		
		Element sLanguage = new Element("sLanguage");
		sLanguage.addContent(v.getSourceLanguage().getISO3Language());
		e.addContent(sLanguage);
		
		Element tLanguage = new Element("tLanguage");
		tLanguage.addContent(v.getTargetLanguage().getISO3Language());
		e.addContent(tLanguage);
		
		Element word = new Element("word");
		word.addContent(v.getWord());
		e.addContent(word);
		
		Element translation = new Element("translation");
		for(String s : v.getTranslations()){
			Element value = new Element("value");
			value.addContent(s);
			translation.addContent(value);
		}
		e.addContent(translation);
		
		Element examples = new Element("examples");
		for(String s : v.getExamples()){
			Element value = new Element("value");
			value.addContent(s);
			examples.addContent(value);
		}
		e.addContent(examples);
		
		Element unit = new Element("unit");
		unit.addContent(v.getUnit()+"");
		e.addContent(unit);
		
		Element section = new Element("section");
		section.addContent(v.getSection()+"");
		e.addContent(section);

		Element lFactor = new Element("lFactor");
		lFactor.addContent(v.getlFactor()+"");
		e.addContent(lFactor);
		
		Element root = doc.getRootElement();
		root.addContent(e);

	}

}
