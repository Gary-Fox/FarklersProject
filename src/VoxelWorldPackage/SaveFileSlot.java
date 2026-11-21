/***************************************************************
* file: SaveFileSlot.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 1
* date last modified: 10/17/2026
*
* purpose: A child of the Button class with specialized data containers and functionality for save file slots
*
****************************************************************
/*

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VoxelWorldPackage;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import java.time.Instant;

/**
 *
 * @author crowbot
 */
public class SaveFileSlot extends Button {    
    private int saveID;
    private Instant timestamp;
    private int savedSeed;
    
    private boolean isSelected = false;
    
    public SaveFileSlot(float x, float y, float width, float height, int fontSize, float[] normalColor, 
            float[] hoverColor, float[] pressedColor, ButtonAction onClickAction, int id, int seed, Instant timestamp) {
        int[] params = {};
        String text = "SAVE " + id + " saved " + timestamp + " // SEED: " + seed;
        super(x, y, width, height, text, fontSize, normalColor, hoverColor, pressedColor, onClickAction, params);
        saveID = id;
        savedSeed = seed;
        this.timestamp = timestamp;
    }
    
    @Override 
    public void update() {
        int mouseX = Mouse.getX();
        int mouseY = Display.getHeight() - Mouse.getY();
        boolean isMouseDown = Mouse.isButtonDown(0);

        isHovered = mouseX >= x && mouseX <= x + width &&
                    mouseY >= y && mouseY <= y + height;

        if (isHovered) {
            if (isMouseDown) {
                isPressed = true;
            } else {
                if (isPressed && wasMouseDown) {
                    clickAction();
                }
            }
        }

        wasMouseDown = isMouseDown; 
    }
    
    @Override
    public void render() {
        if (isSelected) {
            setActiveColor(getPressedColor());
        } 
        super.render();
    }
    
    @Override
    public void clickAction() {
        SelectAction s = (SelectAction)getAction();
        if (!isSelected) {
            isSelected = true;
            
            s.setSelectedSaveFileSlot(this);
            System.out.println("Selected Slot: SAVE ID " + saveID);
        } else {
            deselect();
            s.clearSelectedSaveFileSlot();
        }  
    }
    
    public void deselect() {
        isSelected = false;
        isPressed = false;
    }
    
    public int getSaveID() {
        return saveID;
    }
    
    public int getSeed() {
        return savedSeed;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public void overwriteData(int id, int seed, Instant timestamp) {
        this.timestamp = timestamp;
        saveID = id;
        savedSeed = seed;
        setText("SAVE " + id + " saved " + timestamp + " // SEED: " + seed);
    }
}
