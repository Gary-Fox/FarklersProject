package VoxelWorldPackage;

import java.awt.Font;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class Button extends UIElement {
    private float[] hoverColor, pressedColor;
    public boolean isPressed = false;
    public boolean isHovered = false;
    public boolean wasMouseDown = false;
    
    private ButtonAction onClickAction;
    private int[] params;

    public Button(float x, float y, float width, float height, String text, int fontSize, float[] normalColor, float[] hoverColor, float[] pressedColor, ButtonAction onClickAction, int[] params) {
        super(x, y, width, height, text, fontSize, normalColor);
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
        this.onClickAction = onClickAction;
        this.params = params;
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
                    clickAction();
                }
                isPressed = false;
            }
        } else {
            isPressed = false;
        }

        wasMouseDown = isMouseDown;
    }
    
    public void clickAction() {
        onClickAction.onClick(params);
    }

    @Override
    public void render() {
        if (isPressed) {
            setActiveColor(pressedColor);
        } else if (isHovered) {
            setActiveColor(hoverColor);
        }
        else {
            setActiveColor(getNormalColor());
        }

        super.render();
    }
    
    public float[] getHoverColor() {
        return hoverColor;
    }
    
    public float[] getPressedColor() {
        return pressedColor;
    }
    
    public ButtonAction getAction() {
        return onClickAction;
    }
}
