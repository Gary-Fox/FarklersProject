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
    private boolean isPressed = false;
    private boolean isHovered = false;
    private boolean wasMouseDown = false;
    
    private int saveID;
    private Instant timestamp;
    private float savedSeed;
    
    public SaveFileSlot(float x, float y, float width, float height, int fontSize, float[] normalColor, 
            float[] hoverColor, float[] pressedColor, int id, float seed, Instant timestamp) {
        ButtonAction onClickAction = new LoadAction();
        float[] params = {};
        String text = "SAVE " + id + " saved " + timestamp + " // SEED: " + seed;
        super(x, y, width, height, text, fontSize, normalColor, hoverColor, pressedColor, onClickAction, params);
        saveID = id;
        savedSeed = seed;
        this.timestamp = timestamp;
    }
    
    @Override 
    public void update() {
        
    }
    
    @Override
    public void clickAction() {
        
    }
}
