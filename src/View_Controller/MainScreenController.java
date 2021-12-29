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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

public class MainScreenController implements Initializable {

    Inventory inv;
    
    @FXML
    private Button AddProduct;
    @FXML
    private Button ModifyProduct;
    @FXML
    private Button DeleteProduct;
    @FXML
    private Button AddPart;
    @FXML
    private Button ModifyPart;
    @FXML
    private Button DeletePart;
    @FXML
    private Button ExitMain;
    @FXML
    private TextField ProductSearchField;
    @FXML
    private Button PartSearchButton;
    @FXML
    private TextField PartSearchField;
    @FXML
    private TableView<Part> PartsTableMain;
    @FXML
    private TableView<Product> ProductsTableMain;
    @FXML
    private Button ProductSearchButton;
    @FXML
    private AnchorPane MainScreen;
    @FXML
    private TableColumn<Product, Integer> productID;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productStock;
    @FXML
    private TableColumn<Part, Integer> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partStock;
    @FXML
    private TableColumn<Product, Double> productPrice;
    @FXML
    private TableColumn<Part, Double> partPrice;
    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();
    
    //Constructor
    
    public MainScreenController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartsTable();
        generateProductsTable();
    }    
    
    //Method to populate the Parts tables 
    
    private void generatePartsTable(){
        partInventory.setAll(inv.getAllParts());
        
        PartsTableMain.setItems(partInventory);
        PartsTableMain.refresh();
    }
    
    //Method to populate the Products Table
    
    private void generateProductsTable(){
        productInventory.setAll(inv.getAllProducts());
                
        ProductsTableMain.setItems(productInventory);
        ProductsTableMain.refresh();
    }
    
    //Method to redirect user to the Add Product screen

    @FXML
    private void ProductAddHandler(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductScreen.fxml"));
                AddProductScreenController controller = new AddProductScreenController(inv);
            
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                    
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //Method to redirect user to the Modify Product screen
    
    @FXML
    private void ProductModifyHandler(MouseEvent event) {
        try{
            
            Product product = ProductsTableMain.getSelectionModel().getSelectedItem();
            if (productInventory.isEmpty()) {
                errorWindow(1);
                return;
            }
            if (!productInventory.isEmpty() && product == null) {
                errorWindow(2);
                return;
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
                ModifyProductController controller = new ModifyProductController(inv, product);
            
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
                    
        } catch (IOException e) {
            
        }
    }

    //Method to delete the selected product from the inventory
    
    @FXML
    private void ProductDeleteHandler(MouseEvent event) {
        Product removeProduct = ProductsTableMain.getSelectionModel().getSelectedItem();
        if(productInventory.isEmpty()){
            errorWindow(1);
            return;
        }
        if(!productInventory.isEmpty() && removeProduct == null) {
            errorWindow(2);
            return;
        }
        boolean deleted = false;
        if(removeProduct != null) {
            boolean remove = confirmationWindow(removeProduct.getProductName());
            if (remove) {
                deleted = inv.deleteProduct(removeProduct);
                productInventory.remove(removeProduct);
                ProductsTableMain.refresh();
            }
        }else {
            return;
        }
        if (deleted) {
            AlertMessage.infoWindow(1, removeProduct.getProductName());
        }
        else {
            AlertMessage.infoWindow(2, "");
        } 
    }

    //Method to search for a product and display matching results
    
    @FXML
    private void ProductSearchHandler(MouseEvent event) {
        if (!ProductSearchField.getText().trim().isEmpty()) {
            productInventorySearch.clear();
            for (Product p : inv.getAllProducts()) {
                if(p.getProductName().contains(ProductSearchField.getText().trim())){
                    productInventorySearch.add(p);
                }
                ProductsTableMain.setItems(productInventorySearch);
                ProductsTableMain.refresh();
            }
        }
        else{
            ProductsTableMain.setItems(productInventory);
        }
    }

    //Method to search for a part and display matching results
    
    @FXML
    private void PartSearchHandler(MouseEvent event) {
        if (!PartSearchField.getText().trim().isEmpty()) {
            partsInventorySearch.clear();
            for (Part p : inv.getAllParts()) {
                if(p.getPartName().contains(PartSearchField.getText().trim())){
                    partsInventorySearch.add(p);
                }
                PartsTableMain.setItems(partsInventorySearch);
                PartsTableMain.refresh();
            }
        }
        else{
            PartsTableMain.setItems(partInventory);
        }
    }
    
    //Method to redirect user to the Add Part screen
    
    @FXML
    private void PartAddHandler(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartScreen.fxml"));
            AddPartScreenController controller = new AddPartScreenController(inv);
            
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
    
    //Method to redirect user to the Modify Part screen
    
    @FXML
    private void PartModifyHandler(MouseEvent event) {    
        try{
            
            Part selected = PartsTableMain.getSelectionModel().getSelectedItem();
            if (partInventory.isEmpty()) {
                errorWindow(1);
                return;
            }
            if (!partInventory.isEmpty() && selected == null) {
                errorWindow(2);
                return;
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPartScreen.fxml"));
                ModifyPartScreenController controller = new ModifyPartScreenController(inv, selected);
            
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
                    
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //Method to delete the selected part from the Inventory
    
    @FXML
    private void PartDeleteHandler(MouseEvent event) {
        Part removePart = PartsTableMain.getSelectionModel().getSelectedItem();
        if(partInventory.isEmpty()){
            errorWindow(1);
            return;
        }
        if(!partInventory.isEmpty() && removePart == null) {
            errorWindow(2);
            return;
        }
        boolean deleted = false;
        if(removePart != null) {
            boolean remove = confirmationWindow(removePart.getPartName());
            if (remove) {
                deleted = inv.deletePart(removePart);
                partInventory.remove(removePart);
                PartsTableMain.refresh();
            }
        }else {
            return;
        }
        if (deleted) {
            AlertMessage.infoWindow(1, removePart.getPartName());
        }
        else {
            AlertMessage.infoWindow(2, "");
        }
    }

    private void errorWindow(int code) {
        if(code == 1){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty Inventory!");
            alert.setContentText("There's nothing to select!");
            alert.showAndWait();
        }
        if(code == 2){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Selection!");
            alert.setContentText("You must select an item!");
            alert.showAndWait();
        }
    }
    
    private boolean confirmationWindow(String name){
        Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Item");
            alert.setHeaderText("Are you sure you want to delete: " + name);
            alert.setContentText("Click ok to delete: " + name);
            alert.showAndWait();
            return true;
    }
    
    //Method to exit the main screen
    
    @FXML
    private void ExitMainHandler(MouseEvent event) {
        Platform.exit();
    }
}
