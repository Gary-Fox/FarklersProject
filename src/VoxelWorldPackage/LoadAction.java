/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VoxelWorldPackage;

/**
 *
 * @author crowbot
 */
public class LoadAction implements ButtonAction{
    private GUI GUIReference;
    
    public LoadAction(GUI g) {
        GUIReference = g;
    }
    
    @Override
    public void onClick(int[] params) {
        if (GUIReference.getSelectedSaveFileSlot() == null) 
        {
            System.out.println("No save selected to load.");
        } else {
            float loadedSeed = GUIReference.getSelectedSaveFileSlot().getSeed();
        }
    }
}
