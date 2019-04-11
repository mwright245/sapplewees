/*
Author: Myles Wright
SDEV200
 */
package javaFinal;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;


public class JavaFinal extends Application {
   Boolean boolVal = true; //this is to keep track of what is supposed to be added and what isn't
   //setting up the starting scene
   
   private double xOffset=0;
   private double yOffset=0;
    @Override
    public void start(Stage primaryStage) { 
    User user = new User();  
    
       try {
           user.readFromFile(user);
       } catch (IOException ex) {
           Logger.getLogger(JavaFinal.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    
    String media = this.getClass().getResource("/resources/audio/allstar.mp3").toString(); //this is the song All Star by Smash Mouth
    MediaPlayer mediaplayer = new MediaPlayer(new Media(media));
    mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
    if(user.getGUI().equals("/resources/shrekStyle.css")){
        mediaplayer.play();
    }
    Button startBtn = new Button(user.getComponent("my.start")); //all of the text in the application is based off of what bundle the value GUI  is set to in the set object
    
   Button userBtn = new Button(user.getComponent("my.user"));
   userBtn.setId("menuBtn");
   Button orderBtn = new Button(user.getComponent("my.placeorder"));
   orderBtn.setId("menuBtn");
   Button settingsBtn = new Button(user.getComponent("my.settings"));
   settingsBtn.setId("menuBtn");
   
  
   ArrayList<String> entreeItems = new ArrayList<>();
   ArrayList<String> appetizerItems = new ArrayList<>();
   ArrayList<String> dessertItems = new ArrayList<>();
   ArrayList<Order> orders = new ArrayList<>();
   
   Button close = new Button("🗙");
   close.setId("topBar");
   Button mini = new Button("—");
   mini.setId("topBar");
   GridPane gridpane = new GridPane();//this will hold contents to the right of the VBox when a button is clicked
   gridpane.setId("gridpane");
   
   ScrollPane scroll = new ScrollPane(); //Scrollpane for the menu page
   scroll.setMinHeight(355.0);
   scroll.setMaxHeight(355.0);
   scroll.setMinWidth(265);
   scroll.setMaxWidth(265);
   scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
   scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
   
   
   gridpane.add(startBtn,2,0);
   
   final StackPane root = new StackPane();
   root.setId("root");
   
   AnchorPane editorRoot = new AnchorPane();
   editorRoot.setId("editorRoot");
   
   HBox topBar = new HBox();  //this adds custom close and minimize buttons in an HBox on the top right of the stage
   topBar.setId("topNav");
   topBar.getChildren().add(mini);
   topBar.getChildren().add(close);
   topBar.setPickOnBounds(false);
   
   HBox fileRoot = new HBox();
   fileRoot.setId("fileRoot");
   
   VBox menu = new VBox(8); //the VBox here holds the buttons that the user can click on to navigate through different "windows"
   menu.setId("menu");
   menu.setFillWidth(true);
   menu.getChildren().addAll(userBtn, orderBtn, settingsBtn);
   
   fileRoot.getChildren().add(menu);
   fileRoot.getChildren().add(gridpane);
   
   editorRoot.getChildren().add(fileRoot);
   root.getChildren().add(editorRoot);
   root.getChildren().add(topBar);
   
   Scene scene = new Scene(root, 550, 350);
   scene.getStylesheets().add(this.getClass().getResource(user.getGUI()).toExternalForm());
   scene.setFill(Color.TRANSPARENT); //the color of the scene is transparent so the custom minimize and close buttons work
   primaryStage.getIcons().add(new Image("/resources/images/food.png"));
   primaryStage.setTitle("SappleWee's Restaurant");
   primaryStage.initStyle(StageStyle.UNDECORATED);
   primaryStage.setScene(scene);
   primaryStage.show();
   close.setOnAction(ActionEvent -> primaryStage.close()); //code for close and minimize buttons
   
   mini.setOnAction(ActionEvent -> primaryStage.setIconified(true));
   
   startBtn.setOnAction((ActionEvent b) -> { //the start button cannot be seen again once the user clicks it.
   userBtn.fire();
   });  
   
   
   
   
   userBtn.setOnAction((ActionEvent event) -> { //when a user clicks on the different buttons in the side menu, the gridpane to the right of it
       gridpane.getChildren().clear();          //reloads every time
       fileRoot.getChildren().clear();
       fileRoot.getChildren().add(menu);
       scroll.setContent(null);
       
       TextField name = new TextField(user.getName());
       name.setId("textfield");
       TextField phone = new TextField(user.getPhone());
       phone.setId("textfield");
       TextField address = new TextField(user.getAddr());
       address.setId("textfield");
       Button save = new Button(user.getComponent("my.save"));  
       
       gridpane.add(new Label(user.getComponent("my.name")), 2, 2);
       gridpane.add(name, 3,2);    
       gridpane.add(new Label(user.getComponent("my.phonenumber")), 2,3);
       gridpane.add(phone, 3,3);    
       gridpane.add(new Label(user.getComponent("my.address")), 2, 4);
       gridpane.add(address, 3, 4);
       gridpane.add(save, 3, 5);
       
       fileRoot.getChildren().add(gridpane);
       
       save.setOnAction((ActionEvent e) ->{ //when the user hits the save button, the values get passed to the setInfo method in the user class
       user.setInfo(name.getText(), address.getText(), phone.getText()); 
           try {
               user.saveToFile(user);
           } catch (IOException ex) {
               Logger.getLogger(JavaFinal.class.getName()).log(Level.SEVERE, null, ex);
           }
       }); 
   });
   
   
   
   
   orderBtn.setOnAction((ActionEvent event) -> {
       gridpane.getChildren().clear();
       fileRoot.getChildren().clear();
       fileRoot.getChildren().add(menu);
       
       appetizerItems.clear();
       entreeItems.clear();
       dessertItems.clear();
       
      
       
       Button addorder = new Button(user.getComponent("my.addorder"));
       
       gridpane.add(addorder,0,0);
       fileRoot.getChildren().add(gridpane);
       
       addorder.setOnAction((ActionEvent ion)->{
       Boolean add = setLambdaBoolean();
       if(add){
       orders.add(new Order());//adds an order to the arraylist
       user.addOrder(); //adds the order to the users total number of orders
       orders.get(user.getOrders()-1).setOrderNum(user.getOrders());} //sets the order number for later reference
           
       Alert notif = new Alert(AlertType.INFORMATION); //alert so the user knows items were added
       notif.setHeaderText(null);
       
       notif.setTitle(null);
       
       
       gridpane.getChildren().clear();
       fileRoot.getChildren().clear();
       fileRoot.getChildren().add(menu);
       
       ArrayList<ComboBox> itemList = new ArrayList<>(); //holds the comboboxes in a list so they can be iterated through to see how many of each item was selected
       
       Label app = new Label(user.getComponent("my.appetizers"));
       Button additems = new Button(user.getComponent("my.additems"));
       Button cont = new Button(user.getComponent("my.entrees")+ "->");
 
       
       ComboBox<Integer> one = new ComboBox<>();
            one.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
            one.getSelectionModel().selectFirst();
            one.setId("textfield");
       
       
       ComboBox<Integer> two = new ComboBox<>();
            two.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
            two.getSelectionModel().selectFirst();
            two.setId("textfield");
       
        ComboBox<Integer> three = new ComboBox<>();
            three.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
            three.getSelectionModel().selectFirst();
            three.setId("textfield");
       
       ComboBox<Integer> four = new ComboBox<>();
            four.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
            four.getSelectionModel().selectFirst();
            four.setId("textfield");
       
       ComboBox<Integer> five = new ComboBox<>();
            five.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10);
            five.getSelectionModel().selectFirst();
            five.setId("textfield");
       
       itemList.add(one); //adds the selected comboboxes to the list so they can be iterated through
       itemList.add(two);
       itemList.add(three);
       itemList.add(four);
       itemList.add(five);
       
       gridpane.add(app, 0, 0);
       
       gridpane.add(new Label(user.getComponent("my.wings")), 0,1);
       gridpane.add(one, 1,1);
       
       gridpane.add(new Label(user.getComponent("my.soup")), 0, 2);
       gridpane.add(two, 1, 2);
       
       gridpane.add(new Label(user.getComponent("my.salad")), 0, 3);
       gridpane.add(three,1,3);
       
       gridpane.add(new Label(user.getComponent("my.stuffedmushrooms")),0,4);
       gridpane.add(four, 1, 4);
       
       gridpane.add(new Label(user.getComponent("my.calamari")),0,5);
       gridpane.add(five, 1,5);
       
       gridpane.add(additems, 0,6);
       gridpane.add(cont, 1, 6);
       
       fileRoot.getChildren().add(gridpane);
       
       
       
       additems.setOnAction((ActionEvent anotherevent) -> {
           String item = user.getComponent("my.none");
           boolean anythingOrdered=false;
           
           for(int i=0; i<5; i++){
               switch(i){
                   case 0: item = user.getComponent("my.wings"); //this decides what item is going to be added to the items vector
                   break;
                   case 1: item = user.getComponent("my.soup");
                   break;
                   case 2: item = user.getComponent("my.salad");
                   break;
                   case 3: item = user.getComponent("my.stuffedmushrooms");
                   break;
                   case 4: item = user.getComponent("my.calamari");
                   break;
                   default: break;
               }
               int amt = (int) itemList.get(i).getValue();
               
               if(amt>0){anythingOrdered=true;}
               
               for(int j = 0; j < amt; j++){
                   appetizerItems.add(item);
               }
               
           }
           setFalse(); //sets the boolean as false so another order isnt added
           addorder.fire();//this reloads the page so the user knows something happened
           setTrue(); //sets it as true after so items can be added later on
           if(anythingOrdered==false){
               notif.setContentText(user.getComponent("my.nothingadded"));
               notif.showAndWait();
           }
           else{
                notif.setContentText(user.getComponent("my.itemsadded"));
                notif.showAndWait();
           }
       });
           
       cont.setOnAction((ActionEvent e) ->{
           
            gridpane.getChildren().clear();
            fileRoot.getChildren().clear();
            fileRoot.getChildren().add(menu);
           Button additems2 = new Button(user.getComponent("my.additems")); 
           Button moveback = new Button("<-" + user.getComponent("my.appetizers")); 
           Button moveon = new Button(user.getComponent("my.desserts")+"->");
           Label entree = new Label(user.getComponent("my.entrees"));
           
           one.getSelectionModel().selectFirst();
           two.getSelectionModel().selectFirst();
           three.getSelectionModel().selectFirst();
           four.getSelectionModel().selectFirst();
           
            gridpane.add(entree, 0,1);
            gridpane.add(moveback, 0,0);
           

           gridpane.add(new Label(user.getComponent("my.pasta")), 0, 2);
           gridpane.add(two, 1, 2);

           gridpane.add(new Label(user.getComponent("my.sandwich")), 0, 3);
           gridpane.add(three,1,3);

           gridpane.add(new Label(user.getComponent("my.tacos")),0,4);
           gridpane.add(four, 1, 4);
           
           gridpane.add(additems2, 0,5);
           gridpane.add(moveon,1,5);
           
           fileRoot.getChildren().add(gridpane);
           moveback.setOnAction((ActionEvent n)->{setFalse(); addorder.fire(); setTrue();});
           
           additems2.setOnAction((ActionEvent anotherevent) -> {
               boolean anythingOrdered=false;
           for(int i=1; i<=4; i++){
               String item;
               switch(i){
                   case 1: item = user.getComponent("my.pasta");
                   break;
                   case 2: item = user.getComponent("my.sandwich");
                   break;
                   case 3: item = user.getComponent("my.tacos");
                   break;
                   default: item = user.getComponent("my.none");
               }
               int amt = (int) itemList.get(i).getValue();
               
               if(amt>0){anythingOrdered=true;}
               
               for(int j = 0; j < amt; j++){
                   entreeItems.add(item);
                   
               }
           }
           cont.fire();
            if(anythingOrdered==false){
               notif.setContentText(user.getComponent("my.nothingadded"));
               notif.showAndWait();
           }
           else{
                notif.setContentText(user.getComponent("my.itemsadded"));
                notif.showAndWait();
           }
       });
         
          moveon.setOnAction((ActionEvent action)->{
            gridpane.getChildren().clear();
            fileRoot.getChildren().clear();
            fileRoot.getChildren().add(menu);
           
           Button additems3 = new Button(user.getComponent("my.additems"));
           Button back = new Button("<-" + user.getComponent("my.entrees")); 
           Button next = new Button(user.getComponent("my.choices")+ "->");
           Label dessert = new Label(user.getComponent("my.desserts"));
           
           one.getSelectionModel().selectFirst();
           two.getSelectionModel().selectFirst();
           three.getSelectionModel().selectFirst();
           four.getSelectionModel().selectFirst();
          
           gridpane.add(back,0,0);
           gridpane.add(dessert,0,1);
           
           gridpane.add(new Label(user.getComponent("my.sundae")),0,2);
           gridpane.add(one,1,2);
           
           gridpane.add(new Label(user.getComponent("my.funnelcake")),0,3);
           gridpane.add(two,1,3);
           
           gridpane.add(new Label(user.getComponent("my.cheesecake")),0,4);
           gridpane.add(three, 1,4);
           
           gridpane.add(new Label(user.getComponent("my.cinnibonbites")),0,5);
           gridpane.add(four,1,5);
           
           gridpane.add(additems3, 0, 6);
           gridpane.add(next, 1,6);
           
           fileRoot.getChildren().add(gridpane);
           
           back.setOnAction((ActionEvent b)-> {cont.fire();});
           
           additems3.setOnAction((ActionEvent f)-> {
               boolean anythingOrdered=false;
               for(int i=0; i<4; i++){
               String item;
               switch(i){
                   case 0: item = user.getComponent("my.sundae");
                   break;
                   case 1: item = user.getComponent("my.funnelcake");
                   break;
                   case 2: item = user.getComponent("my.cheesecake");
                   break;
                   case 3: item = user.getComponent("my.cinnibonbites");
                   break;
                   default: item = user.getComponent("my.none");
               }
               int amt = (int) itemList.get(i).getValue();
               
               if(amt>0){anythingOrdered=true;}
               
               for(int j = 0; j < amt; j++){
                   dessertItems.add(item);
               }
             }  
             moveon.fire();
              if(anythingOrdered==false){
               notif.setContentText(user.getComponent("my.nothingadded"));
               notif.showAndWait();
           }
           else{
                notif.setContentText(user.getComponent("my.itemsadded"));
                notif.showAndWait();
           }
           });
           next.setOnAction((ActionEvent y)-> {
                gridpane.getChildren().clear();
                fileRoot.getChildren().clear();
                fileRoot.getChildren().add(menu);
                scroll.setContent(null);
                Label options = new Label(user.getComponent("my.appetizers")+" "+user.getComponent("my.options"));
                Button entreeOptions = new Button(user.getComponent("my.entrees")+" "+user.getComponent("my.options")+"->");
                
                Button remove = new Button(user.getComponent("my.removeitems"));
               
                gridpane.add(options, 0, 1);
                Boolean addIt = setLambdaBoolean();
                
               if(addIt){
               (orders.get(user.getOrders()-1)).addAppetizerItems(appetizerItems); //adds the items from the vector to the order
               (orders.get(user.getOrders()-1)).addEntreeItems(entreeItems);
               (orders.get(user.getOrders()-1)).addDessertItems(dessertItems);}
               
               
               int i;
               int yValue;
               ArrayList<ComboBox> optionsList = new ArrayList<>();
               for(i=0; i < appetizerItems.size(); i++){
                   
                   yValue = getRowCount(gridpane); //sets the value of the row that the elements will be placed at
                       
                   if( appetizerItems.get(i).equals(user.getComponent("my.wings"))){
                        
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.wings")); //this sets the item that the item class will reference
                       ComboBox<String> opt1  = new ComboBox<>();
                       
                       opt1.getItems().addAll((orders.get(user.getOrders()-1)).getSizeComboBox(user)); //this calls the function that determines what array will be used for the combobox selection
                       opt1.getSelectionModel().selectFirst(); 
                       gridpane.add(new Label(user.getComponent("my.wings")+ " " + user.getComponent("my.size")),0, (yValue+1)); 
                       gridpane.add(opt1, 1, (yValue+1));
                       optionsList.add(opt1);
                       
                       ComboBox<String> opt2 = new ComboBox<>();
                       
                       opt2.getItems().addAll(orders.get(user.getOrders()-1).getTypeComboBox(user));
                       opt2.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.wings")+" "+user.getComponent("my.type")), 0, (yValue+2));
                       gridpane.add(opt2,1, (yValue+2));
                       optionsList.add(opt2);
                       
                       ComboBox<String> opt3 = new ComboBox<>();
                       
                       opt3.getItems().addAll(orders.get(user.getOrders()-1).getWingSaucesCombo(user));
                       opt3.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.wings")+ " " + user.getComponent("my.sauce")),0, (yValue+3));
                       gridpane.add(opt3, 1, (yValue+3));
                       optionsList.add(opt3);
                       }
                   else if(appetizerItems.get(i).equals(user.getComponent("my.soup"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.soup"));
                       ComboBox<String> opt1  = new ComboBox<>();
                       
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user));
                       opt1.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.soup")+ " " + user.getComponent("my.size")),0,yValue+1);
                       gridpane.add(opt1, 1, yValue+1);
                       optionsList.add(opt1);
                       
                       ComboBox<String> opt2 = new ComboBox<>();
                       
                       opt2.getItems().addAll(orders.get(user.getOrders()-1).getTypeComboBox(user));
                       opt2.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.soup")+" "+user.getComponent("my.type")), 0, yValue+2);
                       gridpane.add(opt2,1, yValue+2);
                       optionsList.add(opt2);
                   }
                   else if(appetizerItems.get(i).equals(user.getComponent("my.salad"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.salad"));
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user));
                       opt1.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.salad")+ " " + user.getComponent("my.size")),0,yValue+1);
                       gridpane.add(opt1, 1, yValue+1);
                       optionsList.add(opt1);
                       
                       ComboBox<String> opt2 = new ComboBox<>();
                       opt2.getItems().addAll(orders.get(user.getOrders()-1).getTypeComboBox(user));
                       opt2.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.salad")+" "+user.getComponent("my.type")), 0, yValue+2);
                       gridpane.add(opt2,1, yValue+2);
                       optionsList.add(opt2);
                   }
                   else if(appetizerItems.get(i).equals(user.getComponent("my.stuffedmushrooms"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.stuffedmushrooms"));
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user));
                       opt1.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.stuffedmushrooms")),0,yValue+1);
                       gridpane.add(opt1, 1, yValue+1);
                       optionsList.add(opt1);
                   }
                   else if(appetizerItems.get(i).equals(user.getComponent("my.calamari"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.calamari"));
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user));
                       opt1.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.calamari")),0,yValue+1);
                       gridpane.add(opt1, 1, yValue+1);
                       optionsList.add(opt1);
                   }
               }
               yValue = getRowCount(gridpane); //this updates the row number so remove isn't placed on top of another object
               gridpane.add(remove, 0, (yValue+1));
               gridpane.add(entreeOptions, 1,(yValue+1));
               scroll.setContent(gridpane); //adds the gridpane content to the scrollpane
               fileRoot.getChildren().add(scroll); 
               
               
               
               remove.setOnAction((ActionEvent rem)->{ //this is so users can remove appetizer items from their order
                
                gridpane.getChildren().clear();
                fileRoot.getChildren().clear();
                fileRoot.getChildren().add(menu);
                scroll.setContent(null);
                
                ArrayList<CheckBox> removeList = new ArrayList<>(appetizerItems.size());

                Button remo = new Button(user.getComponent("my.removeitems"));
                Button backto = new Button("<-"+user.getComponent("my.options"));
                
                gridpane.add(backto, 0, 0);
                
                int value;
                for(int j=0; j < appetizerItems.size(); j++){
                    value = getRowCount(gridpane);
                    if(appetizerItems.get(j).equals(user.getComponent("my.wings"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.wings"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.wings")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                    
                    
                    if(appetizerItems.get(j).equals(user.getComponent("my.soup"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.soup"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.soup")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                    
                    
                    if(appetizerItems.get(j).equals(user.getComponent("my.salad"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.salad"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.salad")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                    
                    
                    if(appetizerItems.get(j).equals(user.getComponent("my.stuffedmushrooms"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.stuffedmushrooms"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.stuffedmushrooms")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                   
                    
                    if(appetizerItems.get(j).equals(user.getComponent("my.calamari"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.calamari"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.calamari")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                    
                }
                
                value = getRowCount(gridpane);
                
                
                gridpane.add(remo, 0, (value+1));
                scroll.setContent(gridpane);
                fileRoot.getChildren().add(scroll);
                
                remo.setOnAction((ActionEvent remov)->{
                    ArrayList<String> remi = new ArrayList<>();
                     for(int a = 0; a < removeList.size(); a++){
                         if(removeList.get(a).isSelected()){
                              orders.get(user.getOrders()-1).removeAppetizerItem(appetizerItems.get(a));
                              remi.add(appetizerItems.get(a));
                         }
                     }
                     
                    for(int l = 0; l<remi.size();l++){
                        appetizerItems.remove(remi.get(l));
                    }
                    setFalse();
                    next.fire();
                    setTrue();
                });
                
                backto.setOnAction((ActionEvent b)->{setFalse(); next.fire();setTrue();});
               });
               
               entreeOptions.setOnAction((ActionEvent entreeOpt)->{
                   boolean go = setLambdaBoolean();
                   ArrayList<String> op = new ArrayList<>();
                   if(go){

                   if(!appetizerItems.isEmpty()){
                   for(int n = 0; n <optionsList.size();n++){
                       op.add((String) optionsList.get(n).getValue());    
                   }
                   orders.get(user.getOrders()-1).setAppetizerOptions(appetizerItems, op, user);
                   op.clear();
                   optionsList.clear();
                   }
                   }
                    gridpane.getChildren().clear();
                    fileRoot.getChildren().clear();
                    fileRoot.getChildren().add(menu);         
                    scroll.setContent(null);
                    
                Label entreeoptions = new Label(user.getComponent("my.entrees")+" "+user.getComponent("my.options"));
                Button dessertOptions = new Button(user.getComponent("my.desserts")+" "+user.getComponent("my.options")+"->");
                
                Button reme = new Button(user.getComponent("my.removeitems"));
                
                gridpane.add(entreeoptions, 0, 1);
                   int Value;
                for(int b=0; b < entreeItems.size(); b++){
                   
                        Value = getRowCount(gridpane);
                       
                   if(entreeItems.get(b).equals(user.getComponent("my.pasta"))){
                        
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.pasta")); //this sets the item that the item class will reference
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user)); //this calls the function that determines what array will be used for the combobox selection
                       opt1.getSelectionModel().selectFirst(); 
                       gridpane.add(new Label(user.getComponent("my.pasta")+ " " + user.getComponent("my.size")),0, (Value+1)); 
                       gridpane.add(opt1, 1, (Value+1));
                       optionsList.add(opt1);
                       
                       ComboBox<String> opt2 = new ComboBox<>();
                       opt2.getItems().addAll(orders.get(user.getOrders()-1).getTypeComboBox(user));
                       opt2.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.pasta")+" "+user.getComponent("my.type")), 0, (Value+2));
                       gridpane.add(opt2,1, (Value+2));
                       optionsList.add(opt2);
                       
                   }
                   else if(entreeItems.get(b).equals(user.getComponent("my.sandwich"))){
                        
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.sandwich")); //this sets the item that the item class will reference
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user)); //this calls the function that determines what array will be used for the combobox selection
                       opt1.getSelectionModel().selectFirst(); 
                       gridpane.add(new Label(user.getComponent("my.sandwich")+ " " + user.getComponent("my.size")),0, (Value+1)); 
                       gridpane.add(opt1, 1, (Value+1));
                       optionsList.add(opt1);
                       
                       ComboBox<String> opt2 = new ComboBox<>();
                       opt2.getItems().addAll(orders.get(user.getOrders()-1).getTypeComboBox(user));
                       opt2.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.sandwich")+" "+user.getComponent("my.type")), 0, (Value+2));
                       gridpane.add(opt2,1, (Value+2));
                       optionsList.add(opt2);
                       
                   }
                   else if(entreeItems.get(b).equals(user.getComponent("my.tacos"))){
                        
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.tacos")); //this sets the item that the item class will reference
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user)); //this calls the function that determines what array will be used for the combobox selection
                       opt1.getSelectionModel().selectFirst(); 
                       gridpane.add(new Label(user.getComponent("my.tacos")+ " " + user.getComponent("my.size")),0, (Value+1)); 
                       gridpane.add(opt1, 1, (Value+1));
                       optionsList.add(opt1);
                       
                       ComboBox<String> opt2 = new ComboBox<>();
                       opt2.getItems().addAll(orders.get(user.getOrders()-1).getTypeComboBox(user));
                       opt2.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.tacos")+" "+user.getComponent("my.toppings")), 0, (Value+2));
                       gridpane.add(opt2,1, (Value+2));
                       optionsList.add(opt2);
                       
                       ComboBox<String> opt3 = new ComboBox<>();
                        opt3.getItems().addAll(orders.get(user.getOrders()-1).getTortillaTypeCombo(user));
                       opt3.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.tortilla")+" "+user.getComponent("my.type")), 0, (Value+3));
                       gridpane.add(opt3,1, (Value+3));
                       optionsList.add(opt3);
                   } 
                }
                
                Value = getRowCount(gridpane);
                gridpane.add(reme, 0,(Value+1));
                gridpane.add(dessertOptions, 1, (Value+1));
                
                scroll.setContent(gridpane);
                fileRoot.getChildren().add(scroll);
                
                reme.setOnAction((ActionEvent set)->{ 
                    
                gridpane.getChildren().clear();
                fileRoot.getChildren().clear();
                fileRoot.getChildren().add(menu);
                scroll.setContent(null);
                
                ArrayList<CheckBox> removeList = new ArrayList<>(entreeItems.size());
                
                
                Button remo = new Button(user.getComponent("my.removeitems"));
                Button backto = new Button("<-"+user.getComponent("my.options"));
                
                gridpane.add(backto, 0, 0);
                
                int value;
                for(int j=0; j < entreeItems.size(); j++){
                    value = getRowCount(gridpane);
                    
                    
                    if(entreeItems.get(j).equals(user.getComponent("my.pasta"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.pasta"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.pasta")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                    
                    
                    if(entreeItems.get(j).equals(user.getComponent("my.sandwich"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.sandwich"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.sandwich")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                    
                    
                    if(entreeItems.get(j).equals(user.getComponent("my.tacos"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.tacos"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.tacos")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                   
                    
                    
                }
                
                value = getRowCount(gridpane);
                
                
                gridpane.add(remo, 0, (value+1));
                scroll.setContent(gridpane);
                fileRoot.getChildren().add(scroll);
                
                remo.setOnAction((ActionEvent remov)->{
                     ArrayList<String> remi = new ArrayList<>();
                     for(int a = 0; a < removeList.size(); a++){
                         if(removeList.get(a).isSelected()){
                              orders.get(user.getOrders()-1).removeEntreeItem(entreeItems.get(a));
                              remi.add(entreeItems.get(a));
                         }
                     }
                     
                    for(int l = 0; l<remi.size();l++){
                        entreeItems.remove(remi.get(l));
                    }
                    setFalse();
                    entreeOptions.fire();
                    setTrue();
                });
                
                backto.setOnAction((ActionEvent b)->{setFalse();entreeOptions.fire();setTrue();});
                });
                
                dessertOptions.setOnAction((ActionEvent on)->{
                   op.clear();
                   boolean jip = setLambdaBoolean();
                   if(jip){
                   if(!entreeItems.isEmpty()){
                   for(int n = 0; n <optionsList.size();n++){
                       op.add((String) optionsList.get(n).getValue());
                       
                   }
                   orders.get(user.getOrders()-1).setEntreeOptions(entreeItems, op, user);
                   
                   op.clear();
                   optionsList.clear();
                   }
                   }
                    
                    gridpane.getChildren().clear();
                    fileRoot.getChildren().clear();
                    fileRoot.getChildren().add(menu);         
                    scroll.setContent(null);
                    
                    Label dessertOp = new Label(user.getComponent("my.desserts")+ " "+ user.getComponent("my.options"));
                    Button confirm = new Button(user.getComponent("my.confirmorder")+ "->");
                    Button removeDessert = new Button(user.getComponent("my.removeitems"));
                    
                    gridpane.add(dessertOp, 0, 1);
                    
                    int x;
                   for(int c = 0; c < dessertItems.size(); c++){
                      x = getRowCount(gridpane);
                       
                    if(dessertItems.get(c).equals(user.getComponent("my.sundae"))){
                        
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.sundae")); //this sets the item that the item class will reference
                       ComboBox<String> opt1  = new ComboBox<>();
                       
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user)); //this calls the function that determines what array will be used for the combobox selection
                       opt1.getSelectionModel().selectFirst(); 
                       gridpane.add(new Label(user.getComponent("my.sundae")+ " " + user.getComponent("my.size")),0, (x+1)); 
                       gridpane.add(opt1, 1, (x+1));
                       optionsList.add(opt1);
                       
                       ComboBox<String> opt2 = new ComboBox<>();
                       opt2.getItems().addAll(orders.get(user.getOrders()-1).getToppingsCombo(user));
                       opt2.getSelectionModel().selectFirst();
                       gridpane.add(new Label(user.getComponent("my.sundae")+" "+user.getComponent("my.toppings")), 0, (x+2));
                       gridpane.add(opt2,1, (x+2));
                       optionsList.add(opt2);
                   }
                    else if(dessertItems.get(c).equals(user.getComponent("my.funnelcake"))){
                        
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.funnelcake")); //this sets the item that the item class will reference
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getFunnelCakeCombo(user)); //this calls the function that determines what array will be used for the combobox selection
                       opt1.getSelectionModel().selectFirst(); 
                       gridpane.add(new Label(user.getComponent("my.powderedsugar")),0, (x+1)); 
                       gridpane.add(opt1, 1, (x+1));
                       optionsList.add(opt1);
                    }
                   
                   else if(dessertItems.get(c).equals(user.getComponent("my.cheesecake"))){
                        
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.cheesecake")); //this sets the item that the item class will reference
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user)); //this calls the function that determines what array will be used for the combobox selection
                       opt1.getSelectionModel().selectFirst(); 
                       gridpane.add(new Label(user.getComponent("my.cheesecake")),0, (x+1)); 
                       gridpane.add(opt1, 1, (x+1));
                       optionsList.add(opt1);
                   }
                   else if(dessertItems.get(c).equals(user.getComponent("my.cinnibonbites"))){
                        
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.cinnibonbites")); //this sets the item that the item class will reference
                       ComboBox<String> opt1  = new ComboBox<>();
                       opt1.getItems().addAll(orders.get(user.getOrders()-1).getSizeComboBox(user)); //this calls the function that determines what array will be used for the combobox selection
                       opt1.getSelectionModel().selectFirst(); 
                       gridpane.add(new Label(user.getComponent("my.cinnibonbites")),0, (x+1)); 
                       gridpane.add(opt1, 1, (x+1));
                       optionsList.add(opt1);
                    }
                   }
                    x = getRowCount(gridpane);
                    gridpane.add(removeDessert, 0,(x+1));
                    gridpane.add(confirm, 1,(x+1));
                    scroll.setContent(gridpane);
                    fileRoot.getChildren().add(scroll);
                    
                    removeDessert.setOnAction((ActionEvent k)->{
                        
                        
                        gridpane.getChildren().clear();
                        fileRoot.getChildren().clear();
                        fileRoot.getChildren().add(menu);
                        scroll.setContent(null);
                
                        ArrayList<CheckBox> removeList = new ArrayList<>(dessertItems.size());
                
                
                        Button remo = new Button(user.getComponent("my.removeitems"));
                        Button backto = new Button("<-"+user.getComponent("my.options"));
                
                        gridpane.add(backto, 0, 0);
                
                        int value;
                        for(int q=0; q < dessertItems.size(); q++){
                        value = getRowCount(gridpane);
                        if(dessertItems.get(q).equals(user.getComponent("my.sundae"))){
                            orders.get(user.getOrders()-1).setItem(user.getComponent("my.sundae"));
                            CheckBox check = new CheckBox();
                            gridpane.add(new Label(user.getComponent("my.sundae")),0,value);
                            gridpane.add(check, 1, value);
                            removeList.add(check);
                        }
                    
                    
                    if(dessertItems.get(q).equals(user.getComponent("my.funnelcake"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.funnelcake"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.funnelcake")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                    
                    
                    if(dessertItems.get(q).equals(user.getComponent("my.cheesecake"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.cheesecake"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.cheesecake")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                    
                    
                    if(dessertItems.get(q).equals(user.getComponent("my.cinnibonbites"))){
                       orders.get(user.getOrders()-1).setItem(user.getComponent("my.cinnibonbites"));
                       CheckBox check = new CheckBox();
                       gridpane.add(new Label(user.getComponent("my.cinnibonbites")),0,value);
                       gridpane.add(check, 1, value);
                       removeList.add(check);
                   }
                   
                    
                    
                }
                
                value = getRowCount(gridpane);
                
                
                gridpane.add(remo, 0, (value+1));
                scroll.setContent(gridpane);
                fileRoot.getChildren().add(scroll);
                
                remo.setOnAction((ActionEvent remov)->{
                     ArrayList<String> remi = new ArrayList<>();
                     for(int a = 0; a < removeList.size(); a++){
                         if(removeList.get(a).isSelected()){
                              orders.get(user.getOrders()-1).removeDessertItem(dessertItems.get(a));
                              remi.add(dessertItems.get(a));
                         }
                     }
                     
                    for(int l = 0; l<remi.size();l++){
                        dessertItems.remove(remi.get(l));
                    }
                    setFalse();
                    dessertOptions.fire();
                    setTrue();
                });
                
                backto.setOnAction((ActionEvent b)->{setFalse();dessertOptions.fire();setTrue();});
                    });
                    
             confirm.setOnAction((ActionEvent conf)->{
                   op.clear();
                   if(!dessertItems.isEmpty()){
                   for(int n = 0; n <optionsList.size();n++){
                       op.add((String) optionsList.get(n).getValue());    
                   }
                   
                   orders.get(user.getOrders()-1).setDessertOptions(dessertItems, op, user);
                   op.clear();
                   optionsList.clear();
                   }
                    
                    
                    gridpane.getChildren().clear();
                    fileRoot.getChildren().clear();
                    fileRoot.getChildren().add(menu);         
                    scroll.setContent(null); 
                    
                    TextField name = new TextField(user.getName());
                    name.setId("textfield");
                    TextField phone = new TextField(user.getPhone());
                    phone.setId("textfield");
                    TextField address = new TextField(user.getAddr());
                    address.setId("textfield");
                    TextField location = new TextField(user.getStore());
                    location.setId("textfield");
                      
                    Button notify = new Button(user.getComponent("my.notification")+"->");
                    
                   
                    
                    gridpane.add(new Label(user.getComponent("my.name")), 2, 2);
                    gridpane.add(name, 3,2);    
                    gridpane.add(new Label(user.getComponent("my.phonenumber")), 2,3);
                    gridpane.add(phone, 3,3);    
                    gridpane.add(new Label(user.getComponent("my.address")), 2, 4);
                    gridpane.add(address, 3, 4);
                    gridpane.add(new Label(user.getComponent("my.storelocation")), 2, 5);
                    gridpane.add(location, 3, 5);
                    
                    
                    gridpane.add(notify, 3,6);

                    fileRoot.getChildren().add(gridpane);

                    notify.setOnAction((ActionEvent lo)->{
                    if(location.getText().equals(user.getComponent("my.locationtextfield"))||name.getText().equals(user.getComponent("my.namenotset"))||address.getText().equals(user.getComponent("my.addressnotset"))||phone.getText().equals(user.getComponent("my.phonenumbernotset"))){
                        Alert no = new Alert(AlertType.INFORMATION); //alert so the user knows items were added
                        no.setHeaderText(null);
                        no.setTitle(null);
                        no.setContentText(user.getComponent("my.allfieldsrequired"));
                        no.showAndWait();
                    }
                    else{
                        user.setInfo(name.getText(), address.getText(), phone.getText());
                        user.changeSettings(location.getText());
                        try {
                            user.saveToFile(user);
                        } catch (IOException ex) {
                            Logger.getLogger(JavaFinal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        gridpane.getChildren().clear();
                        fileRoot.getChildren().clear();
                        fileRoot.getChildren().add(menu);  

                        Button text = new Button(user.getComponent("my.textmessage"));
                        Button call = new Button(user.getComponent("my.phonecall"));
                        Label textorcall = new Label(user.getComponent("my.notifiedwith"));
                        
                        gridpane.add(textorcall, 0,0);
                        gridpane.add(text, 0, 1);
                        gridpane.add(call,0,2);
                        
                        fileRoot.getChildren().add(gridpane);
                        
                        text.setOnAction((ActionEvent tex)->{
                        orders.get(user.getOrders()-1).chooseType(false);    
                        setFalse();
                        call.fire();
                        setTrue();
                        });
                        
                        call.setOnAction((ActionEvent cal)->{
                            
                            Boolean c = setLambdaBoolean();

                            if(c){
                            orders.get(user.getOrders()-1).chooseType(true);    
                            }
                            gridpane.getChildren().clear();
                            fileRoot.getChildren().clear();
                            fileRoot.getChildren().add(menu);
                            
                            orders.get(user.getOrders()-1).confirmOrder();
                            
                            Button orderSummary = new Button(user.getComponent("my.ordersummary"));
                            
                            Label confirmed = new Label(user.getComponent("my.isconfirmed"));
                            Label date = new Label(orders.get(user.getOrders()-1).placeOrder().toString());
                            Label estTime = new Label(user.getComponent("my.estimatedpreptime")+" "+(orders.get(user.getOrders()-1).prepTime()) + " "+ user.getComponent("my.minutes"));
                            
                            Label closeWindow = new Label(user.getComponent("my.finished")+" "+user.getPhone()+" "+user.getComponent("my.isnotright"));
                            
                            gridpane.add(confirmed,0,0);
                            gridpane.add(estTime, 0,1);
                            gridpane.add(closeWindow,0,2);
                            gridpane.add(orderSummary,0,3);
                            fileRoot.getChildren().add(gridpane);
                            
                            orderSummary.setOnAction((ActionEvent summary)->{
                            gridpane.getChildren().clear();
                            fileRoot.getChildren().clear();
                            fileRoot.getChildren().add(menu);
                            scroll.setContent(null);
                            
                                try {
                                    user.writeOrdertoFile(user, (orders.get(user.getOrders()-1)));
                                } catch (IOException ex) {
                                    Logger.getLogger(JavaFinal.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            
                            gridpane.add(date,0,1);
                            
                            for(int p = 0; p < appetizerItems.size();p++){
                                int value = getRowCount(gridpane);
                                Label ap = new Label(appetizerItems.get(p));
                                gridpane.add(ap,0,value);
                                
                            for(int w = 0; w < orders.get(user.getOrders()-1).appetizerItems.get(p).getOptions().length; w++){
                                value = getRowCount(gridpane);
                                Label ap2 = new Label((orders.get(user.getOrders()-1).appetizerItems.get(p).getOptions())[w]);
                                gridpane.add(ap2,1,value);
                            }
                            }
                            
                            for(int p = 0; p < entreeItems.size();p++){
                                int value = getRowCount(gridpane);
                                Label ap = new Label(entreeItems.get(p));
                                gridpane.add(ap,0,value);
                                
                            for(int w = 0; w < orders.get(user.getOrders()-1).entreeItems.get(p).getOptions().length; w++){
                                value = getRowCount(gridpane);
                                Label ap2 = new Label((orders.get(user.getOrders()-1).entreeItems.get(p).getOptions())[w]);
                                gridpane.add(ap2,1,value);
                            }
                            }
                            
                            for(int p = 0; p < dessertItems.size();p++){
                                int value = getRowCount(gridpane);
                                Label ap = new Label(dessertItems.get(p));
                                gridpane.add(ap,0,value);
                                
                            for(int w = 0; w < orders.get(user.getOrders()-1).dessertItems.get(p).getOptions().length; w++){
                                value = getRowCount(gridpane);
                                Label ap2 = new Label((orders.get(user.getOrders()-1).dessertItems.get(p).getOptions())[w]);
                                gridpane.add(ap2,1,value);
                            }
                            }
                            scroll.setContent(gridpane);
                            fileRoot.getChildren().add(scroll);
                            });
                        });
                    }
                    
                  });
                });
              });
            });
         });  
      });
     });
   });
   
   });
   
   settingsBtn.setOnAction((ActionEvent ev) -> {  
      
       Button save = new Button(user.getComponent("my.save"));
       gridpane.getChildren().clear();
       fileRoot.getChildren().clear();
       fileRoot.getChildren().add(menu);
       scroll.setContent(null);
       
       ComboBox<String> lang = new ComboBox<>();
       lang.getItems().addAll(user.getComponent("my.english"), user.getComponent("my.spanish"), user.getComponent("my.french"), user.getComponent("my.german"));
       lang.setId("textfield");
       lang.setValue(user.getLang());
       
       ComboBox<String> gui = new ComboBox<>();
       gui.getItems().addAll(user.getComponent("my.default"), user.getComponent("my.springtime"), user.getComponent("my.oasis"), user.getComponent("my.forest"), user.getComponent("my.winterwonderland"), user.getComponent("my.shrek"));
       gui.setId("textfield");
       gui.setValue(user.GUItoString());
       
       TextField location = new TextField(user.getStore());
       location.setId("textfield");
       
       gridpane.add(location, 3, 2);
       gridpane.add(new Label(user.getComponent("my.storelocation")), 2, 2);
       
       gridpane.add(new Label(user.getComponent("my.language")), 2, 3);
       gridpane.add(lang, 3, 3);
       
       gridpane.add(new Label(user.getComponent("my.apptheme")), 2,4);
       gridpane.add(gui, 3,4);
       
       gridpane.add(save, 3, 5);
       fileRoot.getChildren().add(gridpane);
       
       save.setOnAction((ActionEvent e) -> { 
        user.changeSettings(lang.getValue(), location.getText(), gui.getValue());
           try {
               user.saveToFile(user);
           } catch (IOException ex) {
               Logger.getLogger(JavaFinal.class.getName()).log(Level.SEVERE, null, ex);
           }
        menu.getChildren().clear();
        userBtn.setText(user.getComponent("my.user")); //reloads the menu buttons
        orderBtn.setText(user.getComponent("my.placeorder"));
        settingsBtn.setText(user.getComponent("my.settings"));
        menu.getChildren().addAll(userBtn, orderBtn, settingsBtn);
        scene.getStylesheets().clear(); //the scene needs to clear the existing style sheet before it can start using another one
        scene.getStylesheets().add(this.getClass().getResource(user.getGUI()).toExternalForm());
        if(user.getGUI().equals("/resources/shrekStyle.css")){ //if a user chooses the shrek theme, they will be greeted with a song
           mediaplayer.play();
        }
        else {
           mediaplayer.stop();
        }
       
        settingsBtn.fire(); //programatically clicks the settings button so the page will load
       });
   });
   
   
   editorRoot.setOnMousePressed((MouseEvent event) -> {
       xOffset = event.getSceneX();
       yOffset = event.getSceneY();
    });
    editorRoot.setOnMouseDragged((MouseEvent event) -> {
        primaryStage.setX(event.getScreenX() - xOffset);
        primaryStage.setY(event.getScreenY() - yOffset);
    });
   
    }
    
    public static void main(String[] args) {
        launch(args);
       
       
    }
    private int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }
    
    private boolean setLambdaBoolean(){
        return boolVal;
    }
    synchronized private void setFalse(){
        boolVal = false;
    }
    synchronized private void setTrue(){
        boolVal = true;
    }
}

