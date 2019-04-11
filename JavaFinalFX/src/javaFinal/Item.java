/*
Author: Myles Wright
SDEV200
 */
package javaFinal;


public class Item extends Settings{
    private String item;
    private String size;
    private String[] options;
    
    Item(String name){
        setItem(name);
    }
    
    Item(){
        
    }
    
    void setItem(String itemName){ //sets the item name
        item = itemName;
    }
    String getItem(){ //this returns the name of the item. ex: "pizza"
        return item;
    }
    
    private void setSize(String Size){//sets the size of the item. Item sizes vary depending on what the item is.
        size = Size;
    }
    String getSize(){//returns the size of the item
        return size;
    }
    
    void setOptions(String[] Options){//this sets the options for the item, such as toppings on a pizza or type of wing and the sauce that goes with it
        options = Options;
    }
    synchronized String[] getOptions(){
        
        return options;
    }
    
    String[] getSizeComboBox(User user){
        String[] wingsSizeCombo = {user.getComponent("my.wingsize1"), user.getComponent("my.wingsize2"), user.getComponent("my.wingsize3")}; //string arrays for the options that are in comboBoxes
        String[] soupSizeCombo = {user.getComponent("my.soupsize1"),user.getComponent("my.soupsize2")};
        String[] saladSizeCombo = {user.getComponent("my.saladsize1"), user.getComponent("my.saladsize2")};
        String[] stuffedMushroomsSizeCombo = {user.getComponent("my.mushroomsize1"), user.getComponent("my.mushroomsize2")};
        String[] calamariSizeCombo = {user.getComponent("my.calamarisize1"), user.getComponent("my.calamarisize2")};
        String[] pastaSizeCombo = {user.getComponent("my.pastasize1"), user.getComponent("my.pastasize2")};
        String[] sandwichSizeCombo = {user.getComponent("my.sandwichsize1"), user.getComponent("my.sandwichsize2")};
        String[] tacosSizeCombo = {user.getComponent("my.tacosize1"), user.getComponent("my.tacosize2"),user.getComponent("my.tacosize3"), user.getComponent("my.tacosize4")};
        String[] sundaeSizeCombo = {user.getComponent("my.sundaesize1"), user.getComponent("my.sundaesize2"), user.getComponent("my.sundaesize3")};
        String[] cheeseCakeSizeCombo = {user.getComponent("my.cheesecake1"), user.getComponent("my.cheesecake2")};
        String[] cinnabonBitesCombo = {user.getComponent("my.cinnibon1"), user.getComponent("my.cinnibon2"), user.getComponent("my.cinnibon3")};
        
        
        
        if(item.equals(user.getComponent("my.wings"))){
            
            
            return wingsSizeCombo;
            
        }
        else if(item.equals(user.getComponent("my.soup"))){
            return soupSizeCombo;
        }
        else if(item.equals(user.getComponent("my.salad"))){
            return saladSizeCombo;
        }
        else if(item.equals(user.getComponent("my.stuffedmushrooms"))){
            return stuffedMushroomsSizeCombo;
        }
        else if(item.equals(user.getComponent("my.calamari"))){ 
            return calamariSizeCombo;
        }
        else if(item.equals(user.getComponent("my.pasta"))){
            
            return pastaSizeCombo;
        }
        else if(item.equals(user.getComponent("my.sandwich"))){
            
            return sandwichSizeCombo;
        }
        else if(item.equals("Tacos")){ //same in all four languages
            return tacosSizeCombo;
        }
        else if(item.equals(user.getComponent("my.sundae"))){
            return sundaeSizeCombo;
        }
        else if(item.equals(user.getComponent("my.cheesecake"))){
            return cheeseCakeSizeCombo;
        }
        else{
            return cinnabonBitesCombo;
        }
    }
        String[] getTypeComboBox(User user){
                String[] wingsTypeCombo = {user.getComponent("my.wingtype1"), user.getComponent("my.wingtype2")};
                String[] soupTypeCombo = {user.getComponent("my.souptype1"),user.getComponent("my.souptype2"),user.getComponent("my.souptype3"),user.getComponent("my.souptype4"),user.getComponent("my.souptype5"),user.getComponent("my.souptype6")};
                String[] saladTypeCombo = {user.getComponent("my.saladtype1"), user.getComponent("my.saladtype2"), user.getComponent("my.saladtype3"), user.getComponent("my.saladtype4"), user.getComponent("my.saladtype5")};
                String[] pastaTypeCombo = {user.getComponent("my.pastatype1"), user.getComponent("my.pastatype2"), user.getComponent("my.pastatype3"), user.getComponent("my.pastatype4"), user.getComponent("my.pastatype5")};
                String[] sandwichTypeCombo = {user.getComponent("my.sandwichtype1"), user.getComponent("my.sandwichtype2"), user.getComponent("my.sandwichtype3"), user.getComponent("my.sandwichtype4"), user.getComponent("my.sandwichtype5"), user.getComponent("my.sandwichtype6"), user.getComponent("my.sandwichtype7")};
                String[] tacosTypeCombo = {user.getComponent("my.tacostype1"), user.getComponent("my.tacostype2"), user.getComponent("my.tacostype3"), user.getComponent("my.tacostype4"), user.getComponent("my.tacostype5")};


                 if(item.equals(user.getComponent("my.wings"))){
                    return wingsTypeCombo;
                }
                else if(item.equals(user.getComponent("my.soup"))){
                    return soupTypeCombo;
                }
                else if(item.equals(user.getComponent("my.salad"))){
                    return saladTypeCombo;
                }
                else if(item.equals(user.getComponent("my.pasta"))){
                    return pastaTypeCombo;
                }
                else if(item.equals(user.getComponent("my.sandwich"))){
                    return sandwichTypeCombo;
                }
                else{
                    return tacosTypeCombo;
                }
    }
    String[] getWingSaucesCombo(User user){
        String[] wingsSaucesCombo = {user.getComponent("my.wingsauce1"), user.getComponent("my.wingsauce2"), user.getComponent("my.wingsauce3"),user.getComponent("my.wingsauce4"), user.getComponent("my.wingsauce5"), user.getComponent("my.wingsauce6")};
       
        return wingsSaucesCombo;
    }
    String[] getToppingsCombo(User user){
        String[] sundaeToppingsCombo = {user.getComponent("my.toppings1"), user.getComponent("my.toppings2"), user.getComponent("my.toppings3"), user.getComponent("my.toppings4"), user.getComponent("my.toppings5"), user.getComponent("my.toppings6"), user.getComponent("my.toppings7"), user.getComponent("my.toppings8")};
       
        return sundaeToppingsCombo;
        
    }
    String[] getTortillaTypeCombo(User user){
        String[] tacosTortillaCombo = {user.getComponent("my.tortilla1"), user.getComponent("my.tortilla2")};
        
        return tacosTortillaCombo;
    }
    String[] getFunnelCakeCombo(User user){
        String[] funnelCakeCombo = {user.getComponent("my.funnelcake1"), user.getComponent("my.funnelcake2")};
        
        return funnelCakeCombo;
    }
}
