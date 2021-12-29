/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.InHouse;
import Model.Outsourced;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Beth Culler
 */
public class AddPartScreenController implements Initializable {
    
    Inventory inv;
    Part part;
    
    @FXML
    private Button AddPartSave;
    @FXML
    private Button AddPartCancel;
    @FXML
    private RadioButton AddPart_InHouseButton;
    @FXML
    private RadioButton AddPart_OutsourcedButton;
    @FXML
    private Label AddPart_DynamicLabel;
    @FXML
    private TextField AddPart_ID;
    @FXML
    private TextField AddPart_Name;
    @FXML
    private TextField AddPart_Inv;
    @FXML
    private TextField AddPart_Price;
    @FXML
    private TextField AddPart_Max;
    @FXML
    private TextField AddPart_MachIDField;
    @FXML
    private TextField AddPart_Min;
    @FXML
    private AnchorPane AddPartScreen;
    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ToggleGroup typeOfPartToggleGroup;
    
    //Constructor
    
    public AddPartScreenController(Inventory inv){
        this.inv = inv;
    }
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeOfPartToggleGroup = new ToggleGroup();
        this.AddPart_InHouseButton.setToggleGroup(typeOfPartToggleGroup);
        this.AddPart_OutsourcedButton.setToggleGroup(typeOfPartToggleGroup);
        
    }    

    //Methods to allow user to select either "In-House" or "Outsourced"
    
    @FXML
    private void InHouseButtonHandler(MouseEvent event) {
        this.typeOfPartToggleGroup.getSelectedToggle().equals(this.AddPart_InHouseButton);
        AddPart_DynamicLabel.setText("Machine ID");
        AddPart_MachIDField.setPromptText("Mach ID");
    }
    
    @FXML
    private void OutsourcedButtonHandler(MouseEvent event) {
        this.typeOfPartToggleGroup.getSelectedToggle().equals(this.AddPart_OutsourcedButton);
        AddPart_DynamicLabel.setText("Company Name");
        AddPart_MachIDField.setPromptText("Comp Nm");
    }
    
    //Method to save the new part to the Inventory with a new partID#, then return to Main Screen 
    
    @FXML
    private void AddPart_SaveHandler(MouseEvent event) {  
        resetFieldsStyle();
        boolean end = false;
        TextField[] fieldCount = {AddPart_Inv, AddPart_Price, AddPart_Min, AddPart_Max};
        if(AddPart_InHouseButton.isSelected() || AddPart_OutsourcedButton.isSelected()) {
            for (int i = 0; i < fieldCount.length; i++) {
                boolean typeError = checkType(fieldCount[i]);
                if (typeError) {
                    end = true;
                    break;
                }
                boolean valueError = checkValue(fieldCount[i]);
                if (valueError) {
                    AlertMessage.errorPart(5, fieldCount[i]);
                    end = true;
                    break;
                }
            }
            
            if(AddPart_Name.getText().trim().isEmpty() || AddPart_Name.getText().trim().toLowerCase().equals("part name")) {
                AlertMessage.errorPart(1, AddPart_Name);
                return;
            }
            if(Integer.parseInt(AddPart_Min.getText().trim()) > Integer.parseInt(AddPart_Max.getText().trim())){
                AlertMessage.errorPart(8, AddPart_Min);
                return;
            }
            if(Integer.parseInt(AddPart_Inv.getText().trim()) < Integer.parseInt(AddPart_Min.getText().trim())){
                AlertMessage.errorPart(6, AddPart_Inv);
                return;
            }
            if(Integer.parseInt(AddPart_Inv.getText().trim()) > Integer.parseInt(AddPart_Max.getText().trim())){
                AlertMessage.errorPart(7, AddPart_Inv);
                return;
            }
            
            if(end) {
                return;
            } else if(AddPart_OutsourcedButton.isSelected() && AddPart_MachIDField.getText().trim().isEmpty()){
                AlertMessage.errorPart(1, AddPart_MachIDField);
                return;
            } else if(AddPart_InHouseButton.isSelected() && AddPart_MachIDField.getText().trim().isEmpty()){
                AlertMessage.errorPart(1, AddPart_MachIDField);
                return;
            } else if(AddPart_InHouseButton.isSelected() && !AddPart_MachIDField.getText().matches("[0-9]*")) {
                AlertMessage.errorPart(9 , AddPart_MachIDField);
                return;
            } else if (AddPart_InHouseButton.isSelected()) {
                addItemInHouse();
            } else if (AddPart_OutsourcedButton.isSelected()) {
                addItemOutsourced();
            }
        } else {
            AlertMessage.errorPart(2, null);
            return;
        }
        
        //Return to Main Screen
        
        mainScreen(event);
        
        
    }
    
    private void addItemInHouse() {
        partInventory.setAll(inv.getAllParts()); //Used to get new PartID#
        int partID = partInventory.size() + 1;
        Part newPart = new InHouse(partID, AddPart_Name.getText().trim(), 
                Double.parseDouble(AddPart_Price.getText().trim()),Integer.parseInt(AddPart_Inv.getText().trim()), 
                Integer.parseInt(AddPart_Min.getText().trim()), Integer.parseInt(AddPart_Max.getText().trim()), 
                Integer.parseInt(AddPart_MachIDField.getText().trim()));
        inv.addPart(newPart);
    }

    private void addItemOutsourced(){
        partInventory.setAll(inv.getAllParts()); //Used to get new PartID#
        int partID = partInventory.size() + 1;
        Part newPart = new Outsourced(partID, AddPart_Name.getText().trim(), 
                Double.parseDouble(AddPart_Price.getText().trim()),Integer.parseInt(AddPart_Inv.getText().trim()), 
                Integer.parseInt(AddPart_Min.getText().trim()), Integer.parseInt(AddPart_Max.getText().trim()), 
                AddPart_MachIDField.getText().trim());
        inv.addPart(newPart);
    }
    
    private void resetFieldsStyle() {
        AddPart_Name.setStyle("-fx-border-color: lightgray");
        AddPart_Inv.setStyle("-fx-border-color: lightgray");
        AddPart_Price.setStyle("-fx-border-color: lightgray");
        AddPart_Min.setStyle("-fx-border-color: lightgray");
        AddPart_Max.setStyle("-fx-border-color: lightgray");
        AddPart_MachIDField.setStyle("-fx-border-color: lightgray");
    }
    
    private boolean checkValue(TextField field){
        if(field.getText().isEmpty()){
            field.setPromptText("ENTER A VALID NUMBER");
            return true;
            }
        else if(Double.parseDouble(field.getText().trim()) < 0) {
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
     
    //Method to cancel without saving and return to main screen
    
    @FXML
    private void AddPart_CancelHandler(MouseEvent event) {
        boolean cancel = AlertMessage.cancel();
        if (cancel){
            
            //Return to Main Scren
            
            mainScreen(event);
        }
        else {
            return;
        }
    }
    
    private void mainScreen(MouseEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            MainScreenController controller = new MainScreenController(inv);
            
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
}
    
