/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
/**
 *
 * @author Beth Culler
 */
public class Product {
    
    private ArrayList<Part>associatedParts = new ArrayList<Part>();
    private int productID;
    private String productName;
    private double productPrice = 0.0;
    private int productStock = 0;
    private int productMin;
    private int productMax;
    
    public Product(int productID, String productName, double productPrice, int productStock, int productMin,
            int productMax) {
        setProductID(productID);
        setProductName(productName);
        setProductPrice(productPrice);
        setProductStock(productStock);
        setProductMin(productMin);
        setProductMax(productMax);
    }
    
    public void setProductID(int id){
        this.productID = id;
    }
    
    public int getProductID(){
        return this.productID;
    }
    
    public void setProductName(String name){
        this.productName = name;
    }
    
    public String getProductName(){
        return this.productName;
    }
    
    public void setProductPrice(double price){
        this.productPrice = price;
    }
    
    public double getProductPrice(){
        return this.productPrice;
    }
    
    public void setProductStock(int stock) {
        this.productStock = stock;
    }
    
    public int getProductStock(){
        return this.productStock;
    }
    
    public void setProductMin(int min){
        this.productMin = min;
    }
    
    public int getProductMin(){
        return this.productMin;
    }
    
    public void setProductMax(int max){
        this.productMax = max;
    }
    
    public int getProductMax(){
        return this.productMax;
    }
    
    public void addAssociatedPart(Part part){
         if(part != null){
           this.associatedParts.add(part);
         }
    }
    
    public boolean deleteAssociatedPart(Part partToDelete){
        for (int i = 0; i < associatedParts.size(); i++){
            if (associatedParts.get(i).getPartID() == partToDelete.getPartID()){
                associatedParts.remove(i);
                break;
            }
            else{
                return false;
            }
        }
        return true;
    }
    
    public ArrayList<Part> getAllAssociatedParts(){
        
            return associatedParts;
            
    }
    
}
