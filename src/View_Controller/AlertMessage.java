/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;
import javafx.scene.control.Alert;

/**
 *
 * @author Beth Culler
 */
public class AlertMessage {
    
    public static void errorPart(int code, TextField field) {
        fieldError(field);
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error adding part");
        alert.setHeaderText("Cannot add part");
        switch(code) {
            case 1: {
                alert.setContentText("Field is empty");
                break;
            }
            case 2: {
                alert.setContentText("Error: You forgot to select InHouse/OutSourced");
                break;
            }
            case 3: {
                alert.setContentText("Invalid format!");
                break;
            }
            case 4: {
                alert.setContentText("Name is invalid!");
                break;
            }
            case 5: {
                alert.setContentText("Value cannot be negative!");
                break;
            }
            case 6: {
                alert.setContentText("Inventory cannot be lower than min!");
                break;
            }
            case 7: {
                alert.setContentText("Inventory cannot be greater than max!");
                break;
            }
            case 8: {
                alert.setContentText("Min cannot be higher than max!");
                break;
            }
            case 9: {
                alert.setContentText("Machine ID must be a number!");
                break;
            }
            default: {
                alert.setContentText("Unknown error!");
                break;
            }
        }
        alert.showAndWait();
    }
  
    public static void errorProduct(int code, TextField field) {
        fieldError(field);
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error adding product");
        alert.setHeaderText("Cannot add product");
        switch(code) {
            case 1: {
                alert.setContentText("Field is empty");
                break;
            }
            case 2: {
                alert.setContentText("Invalid part(s)!");
                break;
            }
            case 3: {
                alert.setContentText("Invalid format!");
                break;
            }
            case 4: {
                alert.setContentText("Name is invalid!");
                break;
            }
            case 5: {
                alert.setContentText("Value cannot be negative!");
                break;
            }
            case 6: {
                alert.setContentText("Product price cannot be lower than the total price of its parts!");
                break;
            }
            case 7: {
                alert.setContentText("Product must have associated parts!");
                break;
            }
            case 8: {
                alert.setContentText("Inventory cannot be lower than min!");
                break;
            }
            case 9: {
                alert.setContentText("Inventory cannot be greater than max!");
                break;
            }
            case 10: {
                alert.setContentText("Min cannot be higher than max!");
                break;
            }
            default: {
                alert.setContentText("Unknown error!");
                break;
            }
        }
        alert.showAndWait();
    }
    
    private static void fieldError(TextField field){
        if(field != null) {
            field.setStyle("-fx-border-color: red");
        }
    }
    
    public static boolean confirmationWindow(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete part");
        alert.setHeaderText("Are you sure you want to delete: " + name);
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    
    public static boolean cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Click ok to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    
    public static void infoWindow(int code, String string){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Action Status");
        alert.setHeaderText("Available Parts");
        switch(code) {
            case 1: {
                alert.setContentText("Part: " + string + " was removed");
                break;
            }
            case 2: {
                alert.setContentText("No changes were made");
                break;
            }
            default: {
                alert.setContentText("Unknown error!");
                break;
            }
        }
    }
}
    
 
