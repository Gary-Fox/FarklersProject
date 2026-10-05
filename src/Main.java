/***************************************************************
* file: Main.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 1
* date last modified: 10/4/2026
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
//package Main.pkg01;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
import org.lwjgl.input.Keyboard;
import java.util.*;

public class Main 
{
    private void createWindow() throws Exception
    {
        //Creates a window with a title, and displays it.
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(640,480));
        Display.setTitle("A beeper perhaps | His father pure evil");
        Display.create();
    }
    private void initGL()
    {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        //glOrtho(0, 640, 0, 480, 1, -1);
        glOrtho(-320, 320, -240, 240, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    private void render()
    {
        while(!Display.isCloseRequested())
        {
            try
            {
                readInput();
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                
                glColor3f(1.0f,1.0f,0.0f);
                glPointSize(10);
                
                glBegin(GL_POINTS);
                glVertex2f(200.0f, 150.0f);
                glVertex2f(-50.0f, -50.0f);
                
                glEnd();
                
                glBegin(GL_LINE_STRIP);
                glVertex2f(50.0f, -150.0f);
                glVertex2f(150.0f, 300.0f);
                glEnd();

                //If it flickers, you did this more than once
                Display.update();
                    Display.sync(60);
                        
            }catch(Exception e)
            {
                Display.destroy();
            }
        }
    }
    
    public void start()
    {
        try
        {
            createWindow();
            initGL();
            render();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //method: readInput
    //purpose: checks for keybaord inputs
    // press he escape key to quit the program
    // Use WASD for camera control
    public void readInput()
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            Display.destroy();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Main basic = new Main();
        basic.start();
    }
    
}
