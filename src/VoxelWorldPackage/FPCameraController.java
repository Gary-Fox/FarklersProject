package VoxelWorldPackage;

/***************************************************************
* file: FPCameraController.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 2
* date last modified: 10/27/2026
*
* purpose: Provide camera control to the user, along with the render() method that displays to the screen.
* It not only handles the mechanical shifting of the pitch and yaw, but also handles keyboard inputs
*
****************************************************************
/*

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import VoxelWorldPackage.Vector3Float;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;

/**
 *
 * @author Fur4l
 */
public class FPCameraController 
{
    //3d vector to store the camera's position in
    private Vector3f position = null;
    private Vector3f lPosition = null;
    //the rotation around the Y axis of the camera
    private float yaw = 0.0f;
    //the rotation around the X axis of the camera
    private float pitch = 0.0f;
    private Vector3Float me;
    
    private Chunk testChunk;
    
    //Method: FPCameraController
    //Purpose: Initializes a camera position within 3D world coordinates
    public FPCameraController(float x, float y, float z)
    {
        //instantiate position Vector3f to the x y z params.
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x,y,z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
    }
    
    //Method: yaw
    //Purpose: Increment the camera's current yaw rotation by a spesific amount
    public void yaw(float amount)
    {
        //increment the yaw by the amount param
        yaw += amount;
    }
    //Method: pitch
    //Purpose: Increment the pitch by a spesific amount
    public void pitch(float amount)
    {
        //increment the pitch by the amount param
        pitch -= amount;
    }
    
    //Method: moveUp
    //Purpose: Moves the camera up relative to it's yaw
    public void moveUp(float distance)
    {
    position.y -= distance;
    }
    //Method: moveDown
    //Purpose: Moves the camera down relative to its yaw
    public void moveDown(float distance)
    {
    position.y += distance;
    }
    
    //Method: walkForward
    //Purpose: moves the camera forward relative to its current yaw
    public void walkForward(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    //Method: walkBackwards
    //Purpose: moves the camera backward relative to its current yaw
    public void walkBackwards(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }
    
    //Method: strafeLeft
    //Purpose: strafes the camera left relative to its current rotation
    public void strafeLeft(float distance) 
    {        
        float xOffset = distance * (float) Math.sin(Math.toRadians(yaw - 90));
        float zOffset = distance * (float) Math.cos(Math.toRadians(yaw - 90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    //Method: strafeRight
    //Purpose: strafes the camera right relative to its current yaw
    public void strafeRight(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
    }
    
    //Method: lookThrough
    //Purpose: translates and rotate the matrix so that it looks through the camera
    //this does basically what gluLookAt() does
    public void lookThrough()
    {
        //roatate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
    }
    
    //Method: gameLoop
    //Purpose: loops until the program is closed, handle all keybaord inputs and translate them into movement in world space.
    public void gameLoop()
    {
        //FPCameraController camera = new FPCameraController(0, 0, -5);
        // theres already a camera in main except its called fp, having camera in here makes a second one
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; //length of frame
        float lastTime = 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .35f;
        //hide the mouse
        Mouse.setGrabbed(true);
        
        testChunk = new Chunk(0, 0, 0);
        
        while (!Display.isCloseRequested() &&
        !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            time = Sys.getTime();
            lastTime = time;
            //distance in mouse movement
            //from the last getDX() call.
            dx = Mouse.getDX();
            //distance in mouse movement
            //from the last getDY() call.
            dy = Mouse.getDY();
            
            this.yaw(dx * mouseSensitivity);
            this.pitch(dy * mouseSensitivity);
            //when passing in the distance to move
            //we times the movementSpeed with dt this is a time scale
            //so if its a slow frame u move more then a fast frame
            //so on a slow computer you move just as fast as on a fast computer
            //REPLACE WITH A SWITCH CASE??? Jeez
            if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
            {
                this.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
            {
                this.walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left 
            {
                this.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right 
            {
                this.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))//move up 
            {
                this.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) 
            {
                this.moveDown(movementSpeed);
            }
            //set the modelview matrix back to the identity
            glLoadIdentity();
            //look through the camera before you draw anything
            this.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            //you would draw your scene here.
            render();
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
            Display.destroy();
        
    }
    
    //Method: Render
    //Purpose: Draw out graphics into world space, creating the world for exploration
    private void render() 
    {
        try
        {
            testChunk.render();
        }catch(Exception e){e.printStackTrace();}
    }
}

