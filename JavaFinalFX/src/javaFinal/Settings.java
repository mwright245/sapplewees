/*
Author: Myles Wright
SDEV200
 */
package javaFinal;

public class Settings extends LanguagePack{
    private String Location = getComponent("my.locationtextfield");
    private String GUI = "/resources/styles.css";
    private String lang = "English";
    
    void changeSettings(String lang, String Loc, String gui){
        setGUI(gui);
        setLang(lang);
        setStore(Loc);
        
    }
    void changeSettings(String Loc){
        setStore(Loc);
    }
    protected void setLang(String lang){ //checks if String lang is equal to the value of the bundles
        String temp;
        switch (lang) {
            case "English":
            case "Inglés":
            case "Anglais":
            case "Englisch":
                temp = "English";
                break;
            case "Spanish":
            case "Español":
            case "Espanol":
            case "Spanisch":
                temp = "Spanish";
                break;
            case "French":
            case "Francés":
            case "Français":
            case "Französisch":
                temp = "French";
                break;
            default:
                temp = "German";
                break;
        }
       this.setBundle(temp); //the string is then sent to setBundle and the language changes
        this.lang= temp;
    }
    String getLang(){
    return getComponent("my."+lang.toLowerCase());
    }
    
    String getComponent(String value){
        String str = bundle.getString(value);
        return str;
    }
   private void setStore(String Loc){
        Location = Loc;
    }
   String getStore(){
       if(Location.equals("Enter location here")||Location.equals("Ingresa la ubicación aquí")||Location.equals("Entrez l'emplacement ici")||Location.equals("Geben Sie den Ort hier ein")){
           return getComponent("my.locationtextfield"); //checks if Location is equal to the my.locationtextfield value in the bundles
       }                                                //since no value was passed in, the exact values have to each be checked
       else{
       return Location;
       }
   }
   void setGUI(String gui){
        if(gui.equals(getComponent("my.default"))){ //checks which gui string got passed through according to the language bundles then sets the stylesheet accordingly
            GUI = "/resources/styles.css";
        }
        else if(gui.equals(getComponent("my.springtime"))){
            GUI = "/resources/flowerStyle.css";
        }
        else if(gui.equals(getComponent("my.oasis"))){
            GUI = "/resources/oasisSyle.css";
        }
        else if(gui.equals(getComponent("my.forest"))){
            GUI = "/resources/forestStyle.css";
        }
        else if(gui.equals(getComponent("my.winterwonderland"))){
            GUI = "/resources/winterStyle.css";
        }
        else if(gui.equals(getComponent("my.shrek"))){
            GUI = "/resources/shrekStyle.css";
        }
   }
   String getGUI(){
       return GUI;
   }
   String GUItoString(){            //this takes whatever stylesheet is set and returns a string representation of it based on the language bundles
       String str = "Default";
       switch (GUI) {
            case "/resources/styles.css":
                str = getComponent("my.default"); 
                break;
            case "/resources/flowerStyle.css":
                str = getComponent("my.springtime");
                break;
            case "/resources/oasisSyle.css":
                str = getComponent("my.oasis");
                break;
            case "/resources/forestStyle.css":
                str = getComponent("my.forest");
                break;
            case "/resources/winterStyle.css":
                str = getComponent("my.winterwonderland");
                break;
            case "/resources/shrekStyle.css":
                str = getComponent("my.shrek");
                break;
            default:
                break;
        }
      return str;
   }
}
