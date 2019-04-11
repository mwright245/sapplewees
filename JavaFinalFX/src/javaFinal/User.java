/*
Author: Myles Wright
SDEV200
 */
package javaFinal;

import java.io.*;
import java.util.Scanner;


public class User extends Settings{
    private String name = getComponent("my.namenotset");
    private String address = getComponent("my.addressnotset");
    private String phone = getComponent("my.phonenumbernotset");
    private int totalOrders = 0;
    
    
    void setInfo(String name, String address, String phone){
        setName(name);
        setAddr(address);
        setPhone(phone);
    }
    private void setName(String userName){
        name = userName;
    }
    public String getName(){
        if(name.equals("Name not set")||name.equals("Ningún nombre selectionado")||name.equals("Aucun nom sélectionné")||name.equals("Kein Name ausgewählt")){
        return getComponent("my.namenotset"); //since no value was passed in, the exact values have to each be checked
        }
        else {
          return name; 
        }
    }
    private void setAddr(String userAddress){
        address = userAddress;
    }
    String getAddr(){
        if(address.equals("Address not set")||address.equals("Dirección no establecida")||address.equals("Adresse non définie")||address.equals("Adresse nicht festgelegt")){
            return getComponent("my.addressnotset"); //since no value was passed in, the exact values have to each be checked
        }
        else{
        return address;
        }
    }
    private void setPhone(String userPhone){
        phone = userPhone;
    }
    String getPhone(){
        if(phone.equals("Phone number not set")||phone.equals("Número de teléfono no configurado")||phone.equals("Numéro de téléphone non défini")||phone.equals("Telefonnummer nicht eingestellt")){
            return getComponent("my.phonenumbernotset");
        }
        else{
        return phone;
        }
    }
    void addOrder(){ 
        totalOrders++;  
    }
    int getOrders(){
        System.out.print("Got total orders\n");
        return totalOrders;
    }
    
    void writeOrdertoFile(User user, Order order)throws IOException{
        try(BufferedWriter write = new BufferedWriter(new FileWriter("src/save/ordersave.txt"))){
            write.append(order.getDate().toString()+"^");
            for(int i = 0; i < order.appetizerItems.size();i++){
                write.append(order.appetizerItems.get(i).getItem()+"^");
            }
            for(int i = 0; i < order.entreeItems.size();i++){
                write.append(order.entreeItems.get(i).getItem()+"^");
            }
            for(int i = 0; i < order.dessertItems.size();i++){
                write.append(order.dessertItems.get(i).getItem()+"^");
            }
            write.append("\n");
        }
       catch(IOException e){
           File thisfile = new File("src/save/ordersave.txt");
            thisfile.createNewFile();
       }
    }
    void saveToFile(User user) throws IOException {
        try(BufferedWriter write = new BufferedWriter(new FileWriter("src/save/usersave.txt"))){
            write.write("");
            String str[] = {user.getName(),"^",user.getAddr(),"^",user.getPhone(),"^",user.getStore(),"^",user.getLang(), "^",user.GUItoString()};
            for (String str1 : str) {
                write.write(str1);
            }
        }
    }
    void readFromFile(User user)throws IOException{
        try(Scanner read = new Scanner(new BufferedReader(new FileReader("src/save/usersave.txt")))){
            if(read.hasNext()){
            String line = read.nextLine();
            
            String[] str = line.split("\\^");
            
           for(int i = 0; i < str.length; i++){
              switch(i){
                  case 0: user.setName(str[i]);
                  break;
                  case 1: user.setAddr(str[i]);
                  break;
                  case 2: user.setPhone(str[i]);
                  break;
                  case 3: user.changeSettings(str[i]);
                  break;
                  case 4: user.setLang(str[i]);
                  break;
                  case 5: user.setGUI(str[i]);
                  break;
              } 
           }
         }
        }
        catch(IOException e){
            File thisfile = new File("src/save/usersave.txt");
            thisfile.createNewFile();
        }
    }
}
