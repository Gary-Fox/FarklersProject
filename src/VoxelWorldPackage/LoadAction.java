/***************************************************************
* file: LoadAction.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 1
* date last modified: 10/17/2026
*
* purpose: A class used to contain the function ran when the Load button is clicked
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
            GUIReference.loadSelectedSeed();
        }
    }
}
