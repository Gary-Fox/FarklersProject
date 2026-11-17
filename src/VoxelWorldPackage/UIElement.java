package VoxelWorldPackage;

import java.awt.Font;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class UIElement {
    public float x, y, width, height;
    private float[] normalColor;
    private float[] activeColor;

    private String text;
    private TrueTypeFont font;
    private Color textColor = Color.white;


    public UIElement(float x, float y, float width, float height, String text, int fontSize, float[] normalColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        Font awtFont = new Font("Arial", Font.BOLD, fontSize);
        TrueTypeFont guiFont = new TrueTypeFont(awtFont, true);
        this.font = guiFont;
        this.normalColor = normalColor;    
        activeColor = normalColor;
    }

    public void render() {
        glDisable(GL_TEXTURE_2D);
        glColor4f(activeColor[0], activeColor[1], activeColor[2], activeColor[3]);

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
    
    public float[] getNormalColor() {
        return normalColor;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setActiveColor(float[] a) {
        activeColor = a;
    }
    
}
