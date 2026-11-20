package VoxelWorldPackage;

import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import java.time.Instant;
import org.lwjgl.input.Mouse;

public class GUI {
    private boolean buildComplete = false;
    private Button saveBtn;
    private Button loadBtn;
    private ArrayList<SaveFileSlot> saveFiles = new ArrayList<>();
    
    private UIElement titleText;
    private UIElement saveFilesContainer;
    
    private int savedSeed; 
    private int saveSlotCount = 0;
    private LoadAction load;
    private SaveAction save;
    private SelectAction selectSlot;
    
    private SaveFileSlot selectedSaveFileSlot;
    
    private FPCameraController worldReference;

    public GUI(FPCameraController f) {
        selectSlot = new SelectAction(this);
        worldReference = f;
    }

    public boolean getBuildComplete() {
        return buildComplete;
    }

    public void setBuildComplete(boolean b) {
        buildComplete = b;
    }

    public void buildGUI() { 
        float[] text  = { 1.0f, 0.0f, 0.0f, 0.0f};
        
        titleText = new UIElement(((float)Display.getWidth() / 2.0f) - 800.0f, (float)Display.getHeight() - 1100.0f, 1600.0f, 300.0f, "Settings", 50, text);
        
        float[] normal  = { 0.2f, 0.2f, 0.2f, 1.0f };  // Dark gray
        float[] hover   = { 0.35f, 0.35f, 0.35f, 1.0f }; // Lighter gray
        float[] pressed = { 0.1f, 0.5f, 0.8f, 1.0f };  // Blue highlight
  
        int[] p = {savedSeed};
        
        saveFilesContainer = new UIElement(200.0f, ((float)Display.getHeight() / 2.0f) - 100.0f, 
                (float)Display.getWidth() - 400.0f, (float)Display.getHeight() - 500.0f, "", 1, normal);
        
        load = new LoadAction(this);
        save = new SaveAction(this);

        saveBtn = new Button((float)Display.getWidth() / 2.0f - 450.0f, (float)Display.getHeight() / 4.0f, 
                400.0f, 100.0f, "Save", 18, normal, hover, pressed, save, p);
        loadBtn = new Button((450.0f + (float)Display.getWidth() / 2.0f) - 425.0f, (float)Display.getHeight() / 4.0f, 
                400.0f, 100.0f, "Load", 18, normal, hover, pressed, load, p);
    
        setBuildComplete(true);
    }
    
    public void addSaveFileSlot(int id, int seed, Instant timestamp) { 
        float[] normal   = { 0.35f, 0.35f, 0.35f, 1.0f }; // Lighter gray
        float[] hover   = { 0.65f, 0.65f, 0.65f, 1.0f }; // Lighter gray
        float[] pressed = { 0.1f, 0.5f, 0.8f, 1.0f };  // Blue highlight
        
        float slotWidth = (saveFilesContainer.width / 2.0f) - 20.0f;
        float slotHeight = (saveFilesContainer.height / 4.0f) - 10.0f;
        float xVal = 0.0f;
        float yVal = 0.0f;
        
        if (saveFiles.size() >= 0 && saveFiles.size() <= 3) {
            xVal = saveFilesContainer.x + 10.0f;
            yVal = (saveFilesContainer.y + 5.0f) + ((saveFiles.size()) * (slotHeight + 10.0f));
        } else if (saveFiles.size() >= 4 && saveFiles.size() <= 7) {
            xVal = saveFilesContainer.x + slotWidth + 20.f;
            yVal = (saveFilesContainer.y + 5.0f) + (((saveFiles.size() - 4)) * (slotHeight + 10.0f));
        } else {
            return;
        }
        
        SaveFileSlot saveFileSlot = new SaveFileSlot(xVal, yVal, slotWidth, slotHeight, 18, normal, hover, pressed, selectSlot, id, seed, timestamp);
        saveFiles.add(saveFileSlot);
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
        
        glColor4f(0.0f, 0.0f, 0.0f, 0.5f); // Semi-transparent black (use 1.0f alpha for solid)
        glBegin(GL_QUADS);
            glVertex2f(0.0f, 0.0f);                                   // Top-Left
            glVertex2f(0.0f, (float) Display.getHeight());            // Bottom-Left
            glVertex2f((float) Display.getWidth(), (float) Display.getHeight()); // Bottom-Right
            glVertex2f((float) Display.getWidth(), 0.0f);             // Top-Right
        glEnd();

        saveFilesContainer.render();
        titleText.render();
        
        // Update and Draw Button
        saveBtn.update();
        saveBtn.render();
        loadBtn.update();
        loadBtn.render();
        for (SaveFileSlot s : saveFiles) {
            s.update();
            s.render();
        }

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
    
    public void setSavedSeed(int s) {
        savedSeed = s;
    }
    
    public int getSaveFileCount() {
        return saveFiles.size();
    }
    
    public SaveFileSlot getSelectedSaveFileSlot() {
        return selectedSaveFileSlot;
    }
    
    public void setSelectedSaveFileSlot(SaveFileSlot s) {
        selectedSaveFileSlot = s;  
        
        for (SaveFileSlot save : saveFiles){
            if (!save.equals(s)) {
                save.deselect();
            }
        }
    }
    
    public void loadSelectedSeed() {
        worldReference.updateSeed(selectedSaveFileSlot.getSeed());
        destroyGUI();
        Mouse.setGrabbed(true);
    }
}
