package VoxelWorldPackage;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class GUI {
    private boolean buildComplete = false;
    private Button startBtn;

    public GUI() {
    }

    public boolean getBuildComplete() {
        return buildComplete;
    }

    public void setBuildComplete(boolean b) {
        buildComplete = b;
    }

    public void buildGUI() {
        float[] normal  = { 0.2f, 0.2f, 0.2f };  // Dark gray
        float[] hover   = { 0.35f, 0.35f, 0.35f }; // Lighter gray
        float[] pressed = { 0.1f, 0.5f, 0.8f };  // Blue highlight

        // Make sure your Button constructor matches the arguments you pass here
        startBtn = new Button(100.0f, 150.0f, 200.0f, 50.0f, normal, hover, pressed);
        setBuildComplete(true);
    }

    public void renderGUI() {
        if (!buildComplete) return;

        // Save 3D Projection Matrix & switch to 2D Top-Left Ortho
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);

        // Save 3D ModelView Matrix
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();

        // Disable 3D states so flat colors draw cleanly
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Update and Draw Button
        startBtn.update();
        startBtn.render();

        // Restore 3D states and matrices
        glDisable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_LIGHTING);
        glEnable(GL_DEPTH_TEST);

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
    }

    public void destroyGUI() {
        setBuildComplete(false);
    }
}
