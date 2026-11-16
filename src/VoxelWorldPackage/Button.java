package VoxelWorldPackage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class Button extends GUIObject {
    private float[] normalColor, hoverColor, pressedColor;
    private boolean isPressed = false;
    private boolean isHovered = false;
    private boolean wasMouseDown = false;
    
    
    public Button (float x, float y, float width, float height, float[] normalColor, float[] hoverColor, float[] pressedColor) {
        super(x, y, width, height);
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
    }
    
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
                    System.out.println("button pressed!");
                }
                isPressed = false;
            }
        } else {
            isPressed = false;
        }

        wasMouseDown = isMouseDown;
    }
    
    public void render() {
        float[] activeColor = normalColor;
        if (isPressed) {
            activeColor = pressedColor;
        } else if (isHovered) {
            activeColor = hoverColor;
        }

        glColor3f(activeColor[0], activeColor[1], activeColor[2]);

        glBegin(GL_QUADS);
            glVertex2f(x, y);
            glVertex2f(x + width, y);
            glVertex2f(x + width, y + height);
            glVertex2f(x, y + height);
        glEnd();
    }
}
