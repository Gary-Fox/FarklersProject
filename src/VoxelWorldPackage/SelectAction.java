/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VoxelWorldPackage;

/**
 *
 * @author crowbot
 */
public class SelectAction implements ButtonAction{
    private GUI GUIReference;
    
    public SelectAction(GUI g) {
        GUIReference = g;
    }
    
    @Override
    public void onClick(int[] params) {
        
    }
    
    public void setSelectedSaveFileSlot(SaveFileSlot s) {
        GUIReference.setSelectedSaveFileSlot(s);
    }
    
    public void clearSelectedSaveFileSlot() {
        GUIReference.setSelectedSaveFileSlot(null);
    }
}
