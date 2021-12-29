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
public class InHouse extends Part {
   
    private int machineID;
    
    public InHouse(int partID, String partName, double partPrice, int partStock, int partMin, int partMax, int machID) {
        super(partID, partName, partPrice, partStock, partMin, partMax);
        setMachineID(machID);
    }
    
    public void setMachineID(int id) {
        this.machineID = id;
    }
    
    public int getMachineID() {
        return machineID;
    }
}
