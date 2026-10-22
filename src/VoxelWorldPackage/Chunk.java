/***************************************************************
* file: Chunk.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 1
* date last modified: 10/22/2026
*
* purpose: Builds a Chunk object out of Blocks by inserting the geometry data into the GLBuffer,
* including position and color. 
*
****************************************************************
/*

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package VoxelWorldPackage;

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Chunk {
    static final int CHUNK_SIZE = 1;
    static final int CUBE_LENGTH = 2;
    private Block[][][] Blocks;
    
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int StartX, StartY, StartZ;
    
    // Method: render
    // Purpose: renders the data in the buffer to the screen
    public void render() {
        glPushMatrix();
    
        // Enable the client states so OpenGL processes pointer data
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        // Bind and point to your data
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);

        // Draw the quads
        glDrawArrays(GL_QUADS, 0, CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 24);

        // Disable client states to keep OpenGL state clean
        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glPopMatrix();
    }
    
    // Method: rebuildMesh
    // Purpose: fills the buffer with the Block data
    public void rebuildMesh(float startX, float startY, float startZ) {
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE* CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        for (float x = 0; x < CHUNK_SIZE; x += 1) {
            for (float z = 0; z < CHUNK_SIZE; z += 1) {
                for(float y = 0; y < CHUNK_SIZE; y++){
                VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH), (float)(y * CUBE_LENGTH + (int)(CHUNK_SIZE*.8)), (float) (startZ + z * CUBE_LENGTH)));
                // Inside rebuildMesh loops:
                VertexColorData.put(createCubeVertexCol(getCubeColor(Blocks[(int) x][(int) y][(int) z])));
                }
            }
        }
        VertexColorData.flip();
        VertexPositionData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
    
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW); 
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    // Method: createCubeVertexCol
    // Purpose: builds the color for each face from the color corresponding to BlockType
    private float[] createCubeVertexCol(float[][] faceColors) {
        float[] cubeColors = new float[6 * 4 * 3]; // 72 total floats [2]
        int index = 0;

        // Loop through each of the 6 faces
        for (int face = 0; face < 6; face++) {
            // Each face has 4 vertices 
            for (int vertex = 0; vertex < 4; vertex++) {
                cubeColors[index++] = faceColors[face][0]; // Red Component
                cubeColors[index++] = faceColors[face][1]; // Green Component
                cubeColors[index++] = faceColors[face][2]; // Blue Component
            }
        }
        return cubeColors;
    }

    // Method: createCube
    // Purpose: builds the vertex data for a cube
    public static float[] createCube(float x, float y, float z) {
        float offset = CUBE_LENGTH / 2.0f;
        
        return new float [] {
            // TOP QUAD
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - CUBE_LENGTH,
            x + offset, y + offset, z - CUBE_LENGTH,
            // BOTTOM QUAD
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z,
            x + offset, y - offset, z,
            // FRONT QUAD
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            // BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            // LEFT QUAD
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - CUBE_LENGTH,
            // RIGHT QUAD
            x + offset, y + offset, z,
            x + offset, y + offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z 
        };
    }
    
    // TODO: rework to get texture mapping working
    private float[][] getCubeColor(Block block) {
        if (block == null) {
        // Fallback default colors if block is null
        return new float[][] {
            {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}
        };
    }
        
        switch (block.getID()) {
            case 6: return new float[][] {
                {1f, 0f, 0f}, // Top: Red
                {0f, 1f, 0f}, // Bottom: Green
                {0f, 0f, 1f}, // Front: Blue
                {1f, 1f, 0f}, // Back: Yellow
                {1f, 0f, 1f}, // Left: Magenta
                {0f, 1f, 1f}  // Right: Cyan
            };
            default: return new float[][] {
                    {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}};  
        }
    }
    
    public Chunk(int startX, int startY, int startZ) {
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                for (int z = 0; z < CHUNK_SIZE; z++) {
                    Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Debug);
                }
            }
        }
        // Initialize handles exactly once here
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        StartX = startX;
        StartY = startY;
        StartZ = startZ;
        rebuildMesh(startX, startY, startZ);
    }
}
