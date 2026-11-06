package VoxelWorldPackage;

/***************************************************************
* file: Main.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 1
* date last modified: 11/5/2026
*
* purpose: This program will be capable of creating a camera within 3 dimensional space, along with
* rudimentary movement in world coordinates. Part i of a 3D voxel game world project.
*
****************************************************************
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author Fur4l
 */

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
import VoxelWorldPackage.FPCameraController;
import java.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
//import org.lwjgl.util.glu.GLU;

public class Main 
{
    // changed to -5 for z to start the camera back from the block
    private final FPCameraController fp = new FPCameraController(0f, -50f, -5f);
    private DisplayMode displayMode;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    
    //method: initLightArrays
    //purpose: This initializes the position of our light, as well as setting its color to white.
    private void initLightArrays() 
    {
        lightPosition = BufferUtils.createFloatBuffer(4);
        //DO NOT CHANGE THAT LAST 1.0f, it tells openGL we're dealing with the light's coordinates
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(0.8f).put(0.1f).put(0.1f).put(0.0f).flip();
    }
    
    //method: createWindow
    //purpose: Initialize window size, title, and fullscreeness
    private void createWindow() throws Exception
    {
        //Creating a window.
        Display.setFullscreen(false);
        //I believe this dynamically setting the window size for each frame?
        DisplayMode d[] = Display.getAvailableDisplayModes();
        
        for (int i = 0; i<d.length; i++)
        {
            if(d[i].getWidth() == 640 && d[i].getHeight() == 480
                && d[i].getBitsPerPixel() == 32)
            {displayMode = d[i]; break;}
        }
        if (displayMode == null) {
            displayMode = Display.getDesktopDisplayMode();
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("A beeper perhaps | His father pure evil");
        Display.create();
    }
    
    //method: initGL
    //purpose: Initializes background color, establishes coordinate positions relative to the window,
    // and generally makes GL commands run as intended
    private void initGL()
    {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        GLU.gluPerspective(
                100.0f,
                (float)displayMode.getWidth() / (float) displayMode.getHeight(), 
                0.1f, 
                300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        // back-face culling so that overlapping blocks don't begin z-fighting
        glEnable(GL_CULL_FACE);
        // tells gl that front faces of blocks are wound clockwise, so outward facing sides are kept and cull the inner ones with no flicker between neighbors
        glFrontFace(GL_CW);
        
        //Setting up light fixture
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); //sets our light’s position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);//sets our specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);//sets our diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);//sets our ambient light
        glEnable(GL_LIGHTING);//enables our lighting
        glEnable(GL_LIGHT0);//enables light0

    }
    
    //method: start
    //purpose: To iniitate all functions necessary for running our program
    public void start()
    {
        try 
        {
            createWindow();
            initGL();
            fp.gameLoop();//render() and //userInput();
        }catch (Exception e) { e.printStackTrace();}
    }
    
    /**
     * @param args the command line arguments
     */
    //method: main
    //purpose: create a new instance of our Main class and run the start method
    public static void main(String[] args) 
    {

        Main basic = new Main();
        basic.start();
    }
}