/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Beth Culler
 */
public class ModifyProductController implements Initializable {
    
    Inventory inv;
    Product prod;
    Part part;
    
    @FXML
    private TableView<Part> ModProdTable_Add;
    @FXML
    private TableColumn<?, ?> partID;
    @FXML
    private TableColumn<?, ?> partName;
    @FXML
    private TableColumn<?, ?> partStock;
    @FXML
    private TableColumn<?, ?> partPrice;
    @FXML
    private TableView<Part> ModProdTable_Delete;
    @FXML
    private TableColumn<?, ?> ID_D;
    @FXML
    private TableColumn<?, ?> Name_D;
    @FXML
    private TableColumn<?, ?> InventoryLevel_D;
    @FXML
    private TableColumn<?, ?> Price_D;
    @FXML
    private Button ModProdDeleteButton;
    @FXML
    private Button ModProdAddButton;
    @FXML
    private Button ModProdCancel;
    @FXML
    private Button ModProdSave;
    @FXML
    private Button ModProdSearchButton;
    @FXML
    private TextField ModProdSearchField;
    @FXML
    private AnchorPane ModifyProductScreen;
    @FXML
    private TextField productIDField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productInvField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;
    
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch= FXCollections.observableArrayList();
    
    //Constructor
    
    public ModifyProductController(Inventory inv, Product product){
        this.inv = inv;
        this.prod = product;
    }
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartsTable();
        setData();
    }    

    //Method to populate the "Add Parts" table with parts available to associate 
    
    private void generatePartsTable(){
        partInventory.setAll(inv.getAllParts());
        
        ModProdTable_Add.setItems(partInventory);
        ModProdTable_Add.refresh();
        
    }
        
    //Method to initialize data in the text fields with the product to be modified
    //Also adds associated parts into the "Delete Parts" Table
    
    public void setData(){
        productIDField.setText(Integer.toString(prod.getProductID()));
        productNameField.setText(prod.getProductName());
        productInvField.setText(Integer.toString(prod.getProductStock()));
        productPriceField.setText(Double.toString(prod.getProductPrice()));
        productMaxField.setText(Integer.toString(prod.getProductMax()));
        productMinField.setText(Integer.toString(prod.getProductMin()));
        
        //Populate the Delete Table with associated parts
        
        associatedPartsInventory.setAll(prod.getAllAssociatedParts());
        ModProdTable_Delete.setItems(associatedPartsInventory);
        ModProdTable_Delete.refresh();
        
    }
    
    //Method to remove or disassociate a part from the product
    
    @FXML
    private void ModProdDeleteHandler(MouseEvent event) {
        Part PTD = (Part) ModProdTable_Delete.getSelectionModel().getSelectedItem();
        if(associatedPartsInventory.isEmpty()){
            errorWindow(1);
            return;
        }
        if(!associatedPartsInventory.isEmpty() && PTD == null) {
            errorWindow(2);
            return;
        }
        boolean deleted = false;
        if(PTD != null) {
            boolean remove = AlertMessage.confirmationWindow(PTD.getPartName());
            if (remove) {
                deleted = prod.deleteAssociatedPart(PTD);
                associatedPartsInventory.remove(PTD);
                ModProdTable_Delete.refresh();
            }
        }else {
            return;
        }
        if (deleted) {
            AlertMessage.infoWindow(1, PTD.getPartName());
        }
        else {
            AlertMessage.infoWindow(2, "");
        } 
    }
    
    private void errorWindow(int code) {
        if(code == 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Inventory!");
            alert.setContentText("There's nothing to select!");
            alert.showAndWait();
        }
        if(code == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection!");
            alert.setContentText("You must select an item!");
            alert.showAndWait();
        }
    }

    //Method to associate one or more parts with a product
    
    @FXML
    private void ModProdAddHandler(MouseEvent event) {
        Part addPart = ModProdTable_Add.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;
        if(addPart == null){
            return;
        } else {
            int id = addPart.getPartID();
            for (int i = 0; i < associatedPartsInventory.size(); i++) {
                if(associatedPartsInventory.get(i).getPartID() == id) {
                    AlertMessage.errorProduct(2, null);
                    repeatedItem = true;
                }
            }
            
            if (!repeatedItem) {
                associatedPartsInventory.add(addPart);
                prod.addAssociatedPart(addPart);
            }
            ModProdTable_Delete.setItems(associatedPartsInventory);
        }
    }
    
    //Method to cancel without saving and return to the Main Screen

    @FXML
    private void ModProdCancelHandler(MouseEvent event) {
       boolean cancel = AlertMessage.cancel();
        if (cancel){
            
            //Return to Main Scren
            
            mainScreen(event);
        }
        else {
            return;
        }
    }

    //Method to save the modified product to the Inventory and return to the Main Screen
    
    @FXML
    private void ModProdSaveHandler(MouseEvent event) {
        resetFieldsStyle();
        boolean end = false;
        TextField[] fieldCount = {productInvField, productPriceField, productMinField, productMaxField};
        if(!prod.getAllAssociatedParts().isEmpty()) {
            for (int i = 0; i < fieldCount.length; i++) {
                boolean typeError = checkType(fieldCount[i]);
                if (typeError) {
                    end = true;
                    break;
                }
                boolean valueError = checkValue(fieldCount[i]);
                if (valueError) {
                    end = true;
                    break;
                }
            }
            
            double minCost = 0;
            for(int i = 0; i < associatedPartsInventory.size(); i++) {
                minCost += associatedPartsInventory.get(i).getPartPrice();
            }
            
            if(productNameField.getText().trim().isEmpty() || productNameField.getText().trim().toLowerCase().equals("product name")) {
                AlertMessage.errorProduct(4, productNameField);
                return;
            }
            if(Integer.parseInt(productMinField.getText().trim()) > Integer.parseInt(productMaxField.getText().trim())){
                AlertMessage.errorProduct(10, productMinField);
                return;
            }
            if(Integer.parseInt(productInvField.getText().trim()) < Integer.parseInt(productMinField.getText().trim())){
                AlertMessage.errorProduct(8, productInvField);
                return;
            }
            if(Integer.parseInt(productInvField.getText().trim()) > Integer.parseInt(productMaxField.getText().trim())){
                AlertMessage.errorProduct(9, productInvField);
                return;
            }
            if(Double.parseDouble(productPriceField.getText().trim()) < minCost) {
                AlertMessage.errorProduct(6, productPriceField);
                return;
            }
            if(end) {
                return;
            } else{
                updateProduct();
            }
                
            //Return to Main Screen
        
            mainScreen(event);
                
        }else{
                AlertMessage.errorProduct(7, null);
                return;
        }  
    }
    
    private void updateProduct() {
        productInventory.setAll(inv.getAllProducts());
        Product modProd = new Product(Integer.parseInt(productIDField.getText().trim()), productNameField.getText().trim(), 
                Double.parseDouble(productPriceField.getText().trim()),Integer.parseInt(productInvField.getText().trim()), 
                Integer.parseInt(productMinField.getText().trim()), Integer.parseInt(productMaxField.getText().trim()));
        
        for (int i = 0; i < associatedPartsInventory.size(); i++){
                    modProd.addAssociatedPart(associatedPartsInventory.get(i));
        }
        
        for (int i = 0; i < productInventory.size(); i++){
            if(productInventory.get(i).getProductID() == prod.getProductID()) {
                inv.updateProduct(i,modProd );   
            }
        }
    }
    
    private void resetFieldsStyle() {
        productNameField.setStyle("-fx-border-color: lightgray");
        productInvField.setStyle("-fx-border-color: lightgray");
        productPriceField.setStyle("-fx-border-color: lightgray");
        productMaxField.setStyle("-fx-border-color: lightgray");
        productMinField.setStyle("-fx-border-color: lightgray");
    }
    
    private boolean checkValue(TextField field){
        if(field.getText().isEmpty()){
            field.setPromptText("ENTER A VALID NUMBER");
            return true;
            }
        else if(Double.parseDouble(field.getText().trim()) < 0) {
            AlertMessage.errorPart(5, field);
            return true;
        } else{
            return false;
        }
    }
    
    private boolean checkType(TextField field) {
        try{
            Double.parseDouble(field.getText().trim()); 
            return false;
        } catch(NumberFormatException e) {
            AlertMessage.errorPart(3, field);
            return true;
        }
    }

    //Method to search for Parts and display matching results
    
    @FXML
    private void ModProdSearchHandler(MouseEvent event) { 
        if (!ModProdSearchField.getText().trim().isEmpty()) {
            partsInventorySearch.clear();
            for (Part p : inv.getAllParts()) {
                if(p.getPartName().contains(ModProdSearchField.getText().trim())){
                    partsInventorySearch.add(p);
                }
                ModProdTable_Add.setItems(partsInventorySearch);
                ModProdTable_Add.refresh();
            }
        }
        else{
            ModProdTable_Add.setItems(partInventory);
        }
    }
    
    private void mainScreen(MouseEvent event) {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        MainScreenController controller = new MainScreenController(inv);
            
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    
                    
        } catch (IOException e) {
            
        }
    }
}
