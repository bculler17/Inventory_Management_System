/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;
        
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Beth Culler
 */
public class ModifyPartScreenController implements Initializable {
    
    Inventory inv;
    Part part;
    
    @FXML
    private Button ModifyPartSave;
    @FXML
    private Button ModifyPartCancel;
    @FXML
    private RadioButton ModifyPart_InHouseButton;
    @FXML
    private RadioButton ModifyPart_OutsourcedButton;
    @FXML
    private TextField ModifyPart_ID;
    @FXML
    private TextField ModifyPart_Name;
    @FXML
    private TextField ModifyPart_Inv;
    @FXML
    private TextField ModifyPart_Price;
    @FXML
    private TextField ModifyPart_Max;
    @FXML
    private TextField ModifyPart_MachIDField;
    @FXML
    private TextField ModifyPart_Min;
    @FXML
    private AnchorPane ModifyPartScreen;
    @FXML
    private Label dynamicLabel;
    
    private ToggleGroup typeOfPartToggleGroup;
    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
   
    //Constructor
    
    public ModifyPartScreenController(Inventory inv, Part part){
        this.inv = inv;
        this.part = part;
    }
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeOfPartToggleGroup = new ToggleGroup();
        this.ModifyPart_InHouseButton.setToggleGroup(typeOfPartToggleGroup);
        this.ModifyPart_OutsourcedButton.setToggleGroup(typeOfPartToggleGroup);;
        setData();
        
    }    
    
    private void setData() {
        if (part instanceof InHouse){
        
            InHouse part1 = (InHouse) part;
            ModifyPart_InHouseButton.setSelected(true);
            ModifyPart_OutsourcedButton.setSelected(false);
            dynamicLabel.setText("Machine ID");
            this.ModifyPart_ID.setText(Integer.toString(part1.getPartID()));
            this.ModifyPart_Name.setText(part1.getPartName());
            this.ModifyPart_Inv.setText(Integer.toString(part1.getPartStock()));
            this.ModifyPart_Price.setText(Double.toString(part1.getPartPrice()));
            this.ModifyPart_Max.setText(Integer.toString(part1.getPartMax()));
            this.ModifyPart_Min.setText(Integer.toString(part1.getPartMin()));
            this.ModifyPart_MachIDField.setText(Integer.toString(part1.getMachineID()));
        }
        
        if (part instanceof Outsourced){
            
            Outsourced part2 = (Outsourced) part;
            ModifyPart_OutsourcedButton.setSelected(true);
            ModifyPart_InHouseButton.setSelected(false);
            dynamicLabel.setText("Company Name");
            this.ModifyPart_ID.setText(Integer.toString(part2.getPartID()));
            this.ModifyPart_Name.setText(part2.getPartName());
            this.ModifyPart_Inv.setText(Integer.toString(part2.getPartStock()));
            this.ModifyPart_Price.setText(Double.toString(part2.getPartPrice()));
            this.ModifyPart_Max.setText(Integer.toString(part2.getPartMax()));
            this.ModifyPart_Min.setText(Integer.toString(part2.getPartMin()));
            this.ModifyPart_MachIDField.setText(part2.getCompanyName());
        }
    }

    //Method to allow user to select either "In-House" or "Outsourced"
    
    @FXML
    private void InHouseButtonHandler(MouseEvent event) {
        this.typeOfPartToggleGroup.getSelectedToggle().equals(this.ModifyPart_InHouseButton);
        dynamicLabel.setText("Machine ID");  
    }
    
    @FXML
    private void OutSourcedButtonHandler(MouseEvent event) { 
        this.typeOfPartToggleGroup.getSelectedToggle().equals(this.ModifyPart_OutsourcedButton);
        dynamicLabel.setText("Company Name");
    }
     
    //Method to save the modified part to the Inventory and return to main screen
        
    @FXML
    private void ModifyPart_SaveHandler(MouseEvent event) throws IOException {
        resetFieldsStyle();
        boolean end = false;
        TextField[] fieldCount = {ModifyPart_Inv, ModifyPart_Price, ModifyPart_Min, ModifyPart_Max};
        if(ModifyPart_InHouseButton.isSelected() || ModifyPart_OutsourcedButton.isSelected()) {
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
            
            if(ModifyPart_Name.getText().trim().isEmpty() || ModifyPart_Name.getText().trim().toLowerCase().equals("part name")) {
                AlertMessage.errorPart(4, ModifyPart_Name);
                return;
            }
            if(Integer.parseInt(ModifyPart_Min.getText().trim()) > Integer.parseInt(ModifyPart_Max.getText().trim())){
                AlertMessage.errorPart(8, ModifyPart_Min);
                return;
            }
            if(Integer.parseInt(ModifyPart_Inv.getText().trim()) < Integer.parseInt(ModifyPart_Min.getText().trim())){
                AlertMessage.errorPart(6, ModifyPart_Inv);
                return;
            }
            if(Integer.parseInt(ModifyPart_Inv.getText().trim()) > Integer.parseInt(ModifyPart_Max.getText().trim())){
                AlertMessage.errorPart(7, ModifyPart_Inv);
                return;
            }
            
            if(end) {
                return;
            } else if(ModifyPart_OutsourcedButton.isSelected() && ModifyPart_MachIDField.getText().trim().isEmpty()){
                AlertMessage.errorPart(1, ModifyPart_MachIDField);
                return;
            } else if(ModifyPart_InHouseButton.isSelected() && ModifyPart_MachIDField.getText().trim().isEmpty()){
                AlertMessage.errorPart(1, ModifyPart_MachIDField);
                return;
            } else if(ModifyPart_InHouseButton.isSelected() && !ModifyPart_MachIDField.getText().matches("[0-9]*")) {
                AlertMessage.errorPart(9 , ModifyPart_MachIDField);
                return;
            } else if (ModifyPart_InHouseButton.isSelected() & part instanceof InHouse) {
                updateItemInHouse();
            } else if (ModifyPart_InHouseButton.isSelected() & part instanceof Outsourced) {
                updateItemInHouse();
            } else if (ModifyPart_OutsourcedButton.isSelected() & part instanceof Outsourced) {
                updateItemOutsourced();
            } else if (ModifyPart_OutsourcedButton.isSelected() & part instanceof InHouse) {
                updateItemOutsourced();
            }
            
            //Return to Main Screen
            
            mainScreen(event);
            
        } else {
            AlertMessage.errorPart(2, null);
            return;
        }
    }
    
    private void updateItemInHouse() {
        partInventory.setAll(inv.getAllParts());
        for (int i = 0; i < partInventory.size(); i++){
            if(partInventory.get(i).getPartID() == part.getPartID()) {
                inv.updatePart(i, new InHouse(Integer.parseInt(ModifyPart_ID.getText().trim()), ModifyPart_Name.getText().trim(), 
                Double.parseDouble(ModifyPart_Price.getText().trim()),Integer.parseInt(ModifyPart_Inv.getText().trim()), 
                Integer.parseInt(ModifyPart_Min.getText().trim()), Integer.parseInt(ModifyPart_Max.getText().trim()), 
                Integer.parseInt(ModifyPart_MachIDField.getText().trim())));
            }
        }   
    }
    
    private void updateItemOutsourced() {
        partInventory.setAll(inv.getAllParts());
        for (int i = 0; i < partInventory.size(); i++){
            if(partInventory.get(i).getPartID() == part.getPartID()) {
                inv.updatePart(i, new Outsourced(Integer.parseInt(ModifyPart_ID.getText().trim()), ModifyPart_Name.getText().trim(), 
                Double.parseDouble(ModifyPart_Price.getText().trim()),Integer.parseInt(ModifyPart_Inv.getText().trim()), 
                Integer.parseInt(ModifyPart_Min.getText().trim()), Integer.parseInt(ModifyPart_Max.getText().trim()), 
                ModifyPart_MachIDField.getText().trim()));
            }
        }
    }
    
    private void resetFieldsStyle() {
        ModifyPart_Name.setStyle("-fx-border-color: lightgray");
        ModifyPart_Inv.setStyle("-fx-border-color: lightgray");
        ModifyPart_Price.setStyle("-fx-border-color: lightgray");
        ModifyPart_Min.setStyle("-fx-border-color: lightgray");
        ModifyPart_Max.setStyle("-fx-border-color: lightgray");
        ModifyPart_MachIDField.setStyle("-fx-border-color: lightgray");
    }
    
    private boolean checkValue(TextField field){
        if(field.getText().isEmpty()){
            field.setPromptText("ENTER A VALID NUMBER");
            return true;
            }
        else if(Double.parseDouble(field.getText().trim()) < 0) {
            return true;
        } 
        else{
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
    private void ModifyPart_CancelHandler(MouseEvent event) {
        boolean cancel = AlertMessage.cancel();
        if (cancel){
            
            //Return to Main Scren
            
            mainScreen(event);
        }
        else {
            return;
        }
    }
    
    private void mainScreen(MouseEvent event){
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