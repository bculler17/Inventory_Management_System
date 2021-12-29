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
public class Outsourced extends Part {
    
    private String companyName;
    
    public Outsourced(int partID, String partName, double partPrice, int partStock, int partMin, int partMax, String company) {
        super(partID, partName, partPrice, partStock, partMin, partMax);
        setCompanyName(company);
    }
    
    public void setCompanyName(String name){
        this.companyName = name;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
}
