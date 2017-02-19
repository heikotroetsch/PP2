package io;

import java.util.Locale;

public class IOSettings {
	public static char fieldSep = '|';  // separator between two columns 
	public static char entrySep = ';';  // separator between two elements in one column
	                                    // e.g. between two examples
	
	/* determines a matching Locale instance depending on the language
	 * code specified by the parameter iso3code
	 */
	public static Locale getLocale(String iso3Code){
		Locale[] locales = Locale.getAvailableLocales();
		Locale locale=null;
		
		for (Locale l : locales){
			if (iso3Code.equals(l.getISO3Language())){
				return l;
			}
		}
		
		return locale;
	}
}
