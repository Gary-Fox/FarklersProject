/***************************************************************
* file: SelectAction.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 1
* date last modified: 10/17/2026
*
* purpose: A class used to contain the function ran when save file slots are selected
*
****************************************************************
/*

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
