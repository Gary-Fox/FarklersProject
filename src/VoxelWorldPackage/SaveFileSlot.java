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
    private float savedSeed;
    
    private boolean isSelected = false;
    
    public SaveFileSlot(float x, float y, float width, float height, int fontSize, float[] normalColor, 
            float[] hoverColor, float[] pressedColor, ButtonAction onClickAction, int id, float seed, Instant timestamp) {
        float[] params = {};
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
        System.out.println("Selected Slot: SAVE ID " + saveID);
        
        if (!isSelected) {
            isSelected = true;
            SelectAction s = (SelectAction)getAction();
            s.setSelectedSaveFileSlot(this);
        } else {
            deselect();
        }     
    }
    
    public void deselect() {
        isSelected = false;
        isPressed = false;
    }
}
