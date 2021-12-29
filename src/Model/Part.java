/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Beth Culler
 */
public abstract class Part {
    
    private int partID;
    private String partName;
    private double partPrice = 0.0;
    private int partStock = 0;
    private int partMin;
    private int partMax;
    
    public Part(int partID, String partName, double partPrice, int partStock, int partMin,int partMax) {
        setPartID(partID);
        setPartName(partName);
        setPartPrice(partPrice);
        setPartStock(partStock);
        setPartMin(partMin);
        setPartMax(partMax);
    }
    
    public void setPartID(int id){
        this.partID = id;
    }
    
    public int getPartID(){
        return this.partID;
    }
    
    public void setPartName(String name){
        this.partName = name;
    }
    
    public String getPartName(){
        return this.partName;
    }
    
    public void setPartPrice(double price){
        this.partPrice = price;
    }
    
    public double getPartPrice(){
        return this.partPrice;
    }
    
    public void setPartStock(int stock) {
        this.partStock = stock;
    }
    
    public int getPartStock(){
        return this.partStock;
    }
    
    public void setPartMin(int min){
        this.partMin = min;
    }
    
    public int getPartMin(){
        return this.partMin;
    }
    
    public void setPartMax(int max){
        this.partMax = max;
    }
    
    public int getPartMax(){
        return this.partMax;
    }
}

   
