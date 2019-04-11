/*
Author: Myles Wright
SDEV200
 */
package javaFinal;
import java.util.*;

public class Order extends Item{
    
    protected int orderNum;
    private String notify; //true = phone call, false = text
    private boolean orderType; //Delivery is true, pickup is false
    private int numItems;
    java.util.Date placedTime;
    int estTime;
    boolean isConfirmed;
    boolean isReady;
    
    private static int instances = 0;
    
    protected ArrayList<Item> appetizerItems = new ArrayList<>();
    protected ArrayList<Item> entreeItems = new ArrayList<>();
    protected ArrayList<Item> dessertItems = new ArrayList<>();
    public Order() {
        instances++;
    }
    
    public java.util.Date placeOrder(){
        java.util.Date date = new Date();
           placedTime = date;
        return placedTime;
    }
    public java.util.Date getDate(){
              return placedTime;
}
    public void setOrderNum(int num){
        orderNum = num;
    }
    
    public String prepTime(){
        estTime = 0;
        for (int i = 0; i < numItems; i++){
            estTime += 5;
        }
        if(orderType == true){
            estTime+=15;
        }
        return Integer.toString(estTime);
    }
    public void chooseType(Boolean type){
        if(type){
            notify = getComponent("my.phonecall");
        }
        else{
            notify = getComponent("my.textmessage");
        }
    }
    boolean isConfirmed(){
        return isConfirmed;
    }
    public void confirmOrder(){
        isConfirmed = true;
    }
    boolean isReady(){ 
        isReady = true;
        return isReady;
    }
   
     int getNumItems(){
        return numItems;
    }
    void addAppetizerItems(ArrayList Items){
        synchronized (this){
        for(int i = 0; i < Items.size(); i++){
            String it = (String) Items.get(i);
            appetizerItems.add(new Item(it));
           numItems++;
        }
        }
    }
    
    void addEntreeItems(ArrayList Items){
            synchronized (this){
       for(int i = 0; i < Items.size(); i++){
            String it = (String) Items.get(i);
            entreeItems.add(new Item(it));
           numItems++;
        }
            }
   }
    
   void addDessertItems(ArrayList Items){
       synchronized (this){
       for(int i = 0; i < Items.size(); i++){
            String it = (String) Items.get(i);
            dessertItems.add(new Item(it));
           numItems++;
        }
       }
   }
   
   void removeAppetizerItem(String i){
       synchronized (Order.class){
       for(int o=0;o<appetizerItems.size();o++){
           if((appetizerItems.get(o).getItem()).equals(i)){
               appetizerItems.remove(o);
               numItems--;
              break;
           }
       }
       }
    }
    
    void removeEntreeItem(String i){
        synchronized (Order.class){
       for(int o=0;o<entreeItems.size();o++){
           if((entreeItems.get(o).getItem()).equals(i)){
               entreeItems.remove(o);
               numItems--;
               break;
           }
       }
        }
    }
     
    void removeDessertItem(String i){
      synchronized (Order.class){
       for(int o=0;o<dessertItems.size();o++){
           if((dessertItems.get(o).getItem()).equals(i)){
               dessertItems.remove(o);
               numItems--;
               break;
           }
       }
        }
    }
    
   void setAppetizerOptions(ArrayList<String> appetizers, ArrayList<String> options, User user){
       synchronized (Order.class){
        for(int i = 0; i < appetizers.size(); i++){ 
            String j = appetizers.get(i);
            if(j.equals(user.getComponent("my.wings"))){
               String a = (String) options.get(0);
               options.remove(0);
               
               String b = (String) options.get(0);
               options.remove(0);
               
               String c = (String) options.get(0);
               options.remove(0);
               
               String[] d = {a, b, c};
               appetizerItems.get(i).setOptions(d);
        }
            else if(j.equals(user.getComponent("my.soup"))||j.equals(user.getComponent("my.salad"))){
               String a = (String) options.get(0);

               String b = (String) options.get(1);
               options.remove(0);
               options.remove(0);
               
               String[] d = {a, b};
               appetizerItems.get(i).setOptions(d);
        }
            else{
                String a = (String) options.get(0);
               options.remove(0);

               String[] d = {a};
               appetizerItems.get(i).setOptions(d);
            }
    }
   }
}
    
   void setEntreeOptions(ArrayList<String> entree, ArrayList<String> options, User user){
       synchronized (Order.class){
          
        for(int i = 0; i < entree.size(); i++){
            String j = entree.get(i);
            if(j.equals(user.getComponent("my.tacos"))){
               String a = (String) options.get(0);
               options.remove(0);
               
               String b = (String) options.get(0);
               options.remove(0);
               
               String c = (String) options.get(0);
               options.remove(0);
               
               
               String[] d = {a, b, c};
               entreeItems.get(i).setOptions(d);
        }
            else if(j.equals(user.getComponent("my.pasta"))||j.equals(user.getComponent("my.sandwich"))){
               String a = (String) options.get(0);
               options.remove(0);
               
               String b = (String) options.get(0);
               
               options.remove(0);
               System.out.print("Added " + a + " " + b+ " to entreeItems at " + entreeItems.get(i));
               String[] d = {a, b};
               entreeItems.get(i).setOptions(d);
        }
    }
   }
}
    
    void setDessertOptions(ArrayList<String> dessert, ArrayList<String> options, User user){
       synchronized (Order.class){
        for(int i = 0; i < dessert.size(); i++){
            String j = dessert.get(i);
            if(j.equals(user.getComponent("my.sundae"))){
               String a = (String) options.get(0);
               options.remove(0);
               
               String b = (String) options.get(0);
               options.remove(0);
               
               String[] d = {a, b};
               dessertItems.get(i).setOptions(d);
        }
            else{
               String a = (String) options.get(0);
               options.remove(0);
               
               String[] d = {a};
               dessertItems.get(i).setOptions(d);
        }
    }
  }
}
  
}