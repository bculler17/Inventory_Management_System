/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

public class AddProductScreenController implements Initializable {
    
    Inventory inv;
    Product prod;
    Part part;

    @FXML
    private TableColumn<?, ?> partID;
    @FXML
    private TableColumn<?, ?> partName;
    @FXML
    private TableColumn<?, ?> partStock;
    @FXML
    private TableColumn<?, ?> partPrice;
    @FXML
    private TableView<Part> AddProductTable_Add;
    @FXML
    private TableView<Part> AddProductTable_Delete;
    @FXML
    private Button AddProdDeleteButton;
    @FXML
    private Button AddProdAddButton;
    @FXML
    private Button AddProdCancel;
    @FXML
    private Button AddProdSave;
    @FXML
    private Button AddProductSearchButton;
    @FXML
    private TextField AddProductSearchField;
    @FXML
    private TextField AddProd_ID;
    @FXML
    private TextField AddProd_Name;
    @FXML
    private TextField AddProd_Inv;
    @FXML
    private TextField AddProd_Price;
    @FXML
    private TextField AddProd_Max;
    @FXML
    private TextField AddProd_Min;
    @FXML
    private AnchorPane AddProductScreen;
    @FXML
    private TableColumn<?, ?> delPartID;
    @FXML
    private TableColumn<?, ?> delPartName;
    @FXML
    private TableColumn<?, ?> delPartStock;
    @FXML
    private TableColumn<?, ?> delPartPrice;
    
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartsInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch= FXCollections.observableArrayList();
     
    //Constructor 
    
    public AddProductScreenController(Inventory inv){
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartsTable();
    }
    
    //Method to populate the "Add Parts" table with parts available to associate  
    
    private void generatePartsTable(){
        partInventory.setAll(inv.getAllParts());
        
        AddProductTable_Add.setItems(partInventory);
        AddProductTable_Add.refresh();
        
    } 
 
    //Method to remove or disassociate a part from the product
    
    @FXML
    private void AddProdDeleteHandler(MouseEvent event) {
        Part PTD = (Part) AddProductTable_Delete.getSelectionModel().getSelectedItem();
        
        if(PTD != null) {
            boolean remove = AlertMessage.confirmationWindow(PTD.getPartName());
            if (remove) {
                associatedPartsInventory.remove(PTD);
            }
        }else {
            return;
        }
    }
    
    //Method to associate one or more parts with a product
    
    @FXML
    private void AddProdAddHandler(MouseEvent event) {
        Part addPart = AddProductTable_Add.getSelectionModel().getSelectedItem();
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
            }
            AddProductTable_Delete.setItems(associatedPartsInventory);
        }
    }
        
    //Method to cancel without saving and return to the Main Screen
    
    @FXML
    private void AddProdCancelHandler(MouseEvent event){
            
        boolean cancel = AlertMessage.cancel();
        if (cancel){
            
            //Return to Main Scren
            
            mainScreen(event);
        }
        else {
            return;
        }
    }
    
    //Method to save the new product to the Inventory with a new productID#, then return to the Main Screen
    
    @FXML
    private void AddProdSaveHandler(MouseEvent event) {
        productInventory.setAll(inv.getAllProducts()); //Used to get new ProductID#
        resetFieldStyle();
        boolean end = false;
        TextField[] fieldCount = {AddProd_Inv, AddProd_Price, AddProd_Min, AddProd_Max};
        if(!associatedPartsInventory.isEmpty()){
            for (int i = 0; i < fieldCount.length; i++) {
                boolean typeError = checkType(fieldCount[i]);
                if(typeError) {
                    end = true;
                    break;
                }
                boolean valueError = checkValue(fieldCount[i]);
                if(valueError) {
                    end = true;
                    break;
                }
            }
            
            double minCost = 0;
            for(int i = 0; i < associatedPartsInventory.size(); i++) {
                minCost += associatedPartsInventory.get(i).getPartPrice();
            }
            
            if(AddProd_Name.getText().trim().isEmpty() || AddProd_Name.getText().trim().toLowerCase().equals("part name")){
                AlertMessage.errorProduct(4, AddProd_Name);
                return;
                }
            if(Integer.parseInt(AddProd_Min.getText().trim()) > Integer.parseInt(AddProd_Max.getText().trim())){
                AlertMessage.errorProduct(10, AddProd_Min);
                return;
            }
            if(Integer.parseInt(AddProd_Inv.getText().trim()) < Integer.parseInt(AddProd_Min.getText().trim())){
                AlertMessage.errorProduct(8, AddProd_Inv);
                return;
            }
            if(Integer.parseInt(AddProd_Inv.getText().trim()) > Integer.parseInt(AddProd_Max.getText().trim())){
                AlertMessage.errorProduct(9, AddProd_Inv);
                return;
            }
            if(Double.parseDouble(AddProd_Price.getText().trim()) < minCost) {
                AlertMessage.errorProduct(6, AddProd_Price);
                return;
            }
            if(end){
                return;
            }else{
                saveProduct();
            }
            
            //Return to Main Screen
            
            mainScreen(event);
            
        }else{
                AlertMessage.errorProduct(7, null);
                return;
                }
    }
        
    private void saveProduct() {
        int productID = (productInventory.size() * 100) + 100;
        Product newProduct = new Product(productID, AddProd_Name.getText().trim(), 
                Double.parseDouble(AddProd_Price.getText().trim()),Integer.parseInt(AddProd_Inv.getText().trim()), 
                Integer.parseInt(AddProd_Min.getText().trim()), Integer.parseInt(AddProd_Max.getText().trim()));
        for (int i = 0; i < associatedPartsInventory.size(); i++){
                    newProduct.addAssociatedPart(associatedPartsInventory.get(i));
        }
        inv.addProduct(newProduct);
    }
    
    private void resetFieldStyle() {
        AddProd_Name.setStyle("-fx-border-color: lightgray");
        AddProd_Inv.setStyle("-fx-border-color: lightgray");
        AddProd_Price.setStyle("-fx-border-color: lightgray");
        AddProd_Min.setStyle("-fx-border-color: lightgray");
        AddProd_Max.setStyle("-fx-border-color: lightgray");
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

    //Method to search for Parts and display matching results in Add Table
    
    @FXML
    private void AddProductSearchHandler(MouseEvent event) {
        if (!AddProductSearchField.getText().trim().isEmpty()) {
            partsInventorySearch.clear();
            for (Part p : inv.getAllParts()) {
                if(p.getPartName().contains(AddProductSearchField.getText().trim())){
                    partsInventorySearch.add(p);
                }
                
                AddProductTable_Add.setItems(partsInventorySearch);
                AddProductTable_Add.refresh();
            }
        }
        else{
             AddProductTable_Add.setItems(partInventory);
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