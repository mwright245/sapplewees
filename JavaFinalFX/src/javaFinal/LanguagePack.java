/*
Author: Myles Wright
SDEV200
 */
package javaFinal;

import java.util.*;

public class LanguagePack {
    
    
    Locale englishLocale = new Locale("en", "US");      //four different language bundles, the files can be found in the resources package
    ResourceBundle English = ResourceBundle.getBundle("resources.Bundle_en_US", englishLocale);
    
    Locale spanishLocale = new Locale("es", "MX");
    ResourceBundle Spanish = ResourceBundle.getBundle("resources.Bundle_es_MX", spanishLocale);
    
    Locale frenchLocale = new Locale("fr", "FR");
    ResourceBundle French = ResourceBundle.getBundle("resources.Bundle_fr_FR", frenchLocale);
    
    Locale germanLocale = new Locale("ge", "GE");
    ResourceBundle German = ResourceBundle.getBundle("resources.Bundle_ge_GE", germanLocale);

    ResourceBundle bundle = English;
    
    public ResourceBundle setBundle(String lang){ //this takes a string passed from the settings and sets the language bundle that matches
        switch (lang){
            case "English": bundle = English;
            break;
            case "Spanish": bundle = Spanish;
            break;
            case "French": bundle = French;
            break;
            case "German": bundle = German;
    }
        return bundle;
    }
    public ResourceBundle getBundle(){
        return bundle;
    }
}

