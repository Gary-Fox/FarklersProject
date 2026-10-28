package VoxelWorldPackage;

/***************************************************************
* file: Main.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 1
* date last modified: 10/17/2026
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
//import org.lwjgl.util.glu.GLU;

public class Main 
{
    private final FPCameraController fp = new FPCameraController(0f,0f,0f);
    private DisplayMode displayMode;
    
    //method: createWindow
    //purpose: Initialize window size, title, and fullscreeness
    private void createWindow() throws Exception
    {
        //Creating a window.
        Display.setFullscreen(false);
        //I believe this dynamically setting the window size for each frame?
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for(int i = 0; i<d.length; i++)
        {
            if(d[i].getWidth() == 640 && d[i].getHeight() == 480
                && d[i].getBitsPerPixel() == 32)
            {displayMode = d[i]; break;}
        }
        if (displayMode == null) {
            displayMode = Display.getDesktopDisplayMode();
        }
        Display.setDisplayMode(displayMode);
        
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
        glEnable(GL_DEPTH_TEST);
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