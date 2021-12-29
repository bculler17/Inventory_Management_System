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
public class Inventory {
    
    private ArrayList<Product>allProducts;
    private ArrayList<Part>allParts;
    
    public Inventory(){
        allProducts = new ArrayList<>();
        allParts = new ArrayList<>();
    }
    
    public void addPart(Part partToAdd){
       if(partToAdd != null){
           this.allParts.add(partToAdd);
       }
    }
    
    public void addProduct(Product productToAdd){
        if(productToAdd != null){
            this.allProducts.add(productToAdd);
        }
    }
    
    public Part lookupPart(int partToLookUp){
        if (!allParts.isEmpty()){
            for (int i = 0; i < allParts.size(); i++){
                if (allParts.get(i).getPartID() == partToLookUp){
                    return allParts.get(i);
                }
            }
        }
        return null;
    }
    
    public Product lookupProduct(int productToLookUp){
        if (!allProducts.isEmpty()){
            for (int i = 0; i < allProducts.size(); i++){
                if (allProducts.get(i).getProductID() == productToLookUp){
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }
    
    public ArrayList<Part> lookupPart(String partNameToLookUp){
        if (!allParts.isEmpty()){
            for (int i = 0; i < allParts.size(); i++){
                if (allParts.get(i).getPartName().equals("partNameToLookUp")){
                    return allParts;
                }
            }
        }
        return null;
    }
    
    public ArrayList<Product> lookupProduct(String productNameToLookUp){
        if (!allProducts.isEmpty()){
            for (int i = 0; i < allProducts.size(); i++){
                if (allProducts.get(i).getProductName().equals("productNameToLookUp")){
                    return allProducts;
                }
            }
        }
        return null;
    }
    
    public void updatePart(int index, Part part){
                allParts.set(index, part);
    } 
    
    public void updateProduct(int index, Product product){
                allProducts.set(index, product);
    } 
    
    public boolean deletePart(Part selectedPart){
        for (int i = 0; i < allParts.size(); i++){
            if (allParts.get(i).getPartID() == selectedPart.getPartID()){
                allParts.remove(i);
                break;
            }
            else{
                return false;
            }
        }
        return true;
    }
    
    public boolean deleteProduct(Product selectedProduct){
        for (int i = 0; i < allProducts.size(); i++){
            if (allProducts.get(i).getProductID() == selectedProduct.getProductID()){
                allProducts.remove(i);
                break;
            }
            else{
                return false;
            }
        }
        return true;
    }
    
    public ArrayList<Part> getAllParts(){
            return allParts;
    }
    
    public ArrayList<Product> getAllProducts(){
            return allProducts;
    }
}
