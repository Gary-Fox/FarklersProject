package VoxelWorldPackage;

import java.awt.Font;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Button extends GUIObject {
    private float[] normalColor, hoverColor, pressedColor;
    private boolean isPressed = false;
    private boolean isHovered = false;
    private boolean wasMouseDown = false;
    
    private String text;
    private TrueTypeFont font;
    private Color textColor = Color.white;

    public Button(float x, float y, float width, float height, String text, float[] normalColor, float[] hoverColor, float[] pressedColor) {
        super(x, y, width, height);
        this.text = text;
        Font awtFont = new Font("Arial", Font.BOLD, 18);
        TrueTypeFont guiFont = new TrueTypeFont(awtFont, true);
        this.font = guiFont;
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
        // 1. Disable textures so the solid button quad renders with pure color
        glDisable(GL_TEXTURE_2D);

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
      
        if (text != null && !text.isEmpty() && font != null) {
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            // Prevent 3D settings from hiding 2D GUI text
            glDisable(GL_DEPTH_TEST);
            glDisable(GL_CULL_FACE);

            float textX = x + (width - font.getWidth(text)) / 2f;
            float textY = y + (height - font.getHeight(text)) / 2f;

            font.drawString(textX, textY, text, textColor);

            glDisable(GL_BLEND);
            glColor3f(1f, 1f, 1f);
        }
    }
    
    public void setText(String text) {
        this.text = text;
    }
}
