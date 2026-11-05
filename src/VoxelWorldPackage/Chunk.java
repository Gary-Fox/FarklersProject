/***************************************************************
* file: Chunk.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 2
* date last modified: 10/27/2026
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
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Chunk {
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Block[][][] Blocks;
    
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int StartX, StartY, StartZ;
    
    private int VBOTextureHandle;
    private Texture texture;
    
    // Terrain settings (tweak these to change the look)
    private static final int SEA_LEVEL = CHUNK_SIZE / 3;
    private static final int SEED = 1234;   // fixed seed = same world every run
    private int blockCount = 0;             // number of blocks actually drawn
    
    // Method: render
    // Purpose: renders the data in the buffer to the screen
    public void render() {
        glPushMatrix();
    
        // Enable the client states so OpenGL processes pointer data
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        // Bind and point to your data
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        // glBindTexture(GL_TEXTURE_2D, 1);
        texture.bind();
        glTexCoordPointer(2, GL_FLOAT,0,0L);

        // Draw the quads
        glDrawArrays(GL_QUADS, 0, blockCount * 24);

        // Disable client states to keep OpenGL state clean
        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        
        glPopMatrix();
    }
    
    // Method: rebuildMesh
    // Purpose: fills the buffer with the Block data
    public void rebuildMesh(float startX, float startY, float startZ) 
    {
//        //adding noise
//        Random r = new Random();
//        //0.05 persistence adds some naturalness
//        //0.03 allows for mostly flat land with some minor hills
//        SimplexNoise noise = new SimplexNoise(10, 0.03, r.nextInt());
//
//        //Replace r.nextInt() with x, y, z???
//
//        
//        VBOColorHandle = glGenBuffers();
//        VBOVertexHandle = glGenBuffers();
//        VBOTextureHandle = glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer((CHUNK_SIZE* CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        
        // Important if we change base.png
        FloatBuffer VertexTextureData = BufferUtils.createFloatBuffer((CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE) * 6 * 12);
        
        blockCount = 0;
        
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                for (int y = 0; y < CHUNK_SIZE; y++) {
                    Block b = Blocks[x][y][z];
                    if (b == null) continue;    // air: nothing to draw
                    
                    VertexPositionData.put(createCube(
                            startX + x * CUBE_LENGTH,
                            y * CUBE_LENGTH + (int) (CHUNK_SIZE * .8),
                            startZ + z * CUBE_LENGTH));
                    VertexColorData.put(createCubeVertexCol(getCubeColor(b)));
                    VertexTextureData.put(createTexCube(0f, 0f, b));
                    blockCount++;
                }
            }
        }
        
//        for (float x = 0; x < CHUNK_SIZE; x += 1)
//        {
//
//
//            for (float z = 0; z < CHUNK_SIZE; z += 1) 
//            {
//                float maxHeight = Math.abs(((startY + (int)(100*noise.getNoise((int)x, 1, 1) * CUBE_LENGTH)) % 10) + 1);
//                System.out.println("maxHeight: " + maxHeight);
//                if(maxHeight <= 0)
//                {
//                    maxHeight = 1;
//                }
//                for(float y = 0; y < maxHeight; y++)
//                {
//                    //Trying my darndest to make the top blocks grass.
//                    if( (y + 1) > maxHeight )
//                    {
//                        Blocks[(int)(x)][(int)(y)][(int)(z)].setID(0);
//                    }
//                        VertexPositionData.put(createCube((float) (startX + x * CUBE_LENGTH), (float)(y * CUBE_LENGTH + (int)(CHUNK_SIZE*.8)), (float) (startZ + z * CUBE_LENGTH)));
//                        // Inside rebuildMesh loops:
//                        VertexColorData.put(createCubeVertexCol(getCubeColor(Blocks[(int) x][(int) y][(int) z])));
//                        VertexTextureData.put(createTexCube((float) 0, (float) 0, Blocks[(int)(x)][(int)(y)][(int)(z)]));
//
//                }
//            }
//        }
        
//        for(float y = 0; y<=max; y++)
//        {
//            VertexPositionData.put(
//            createCube((float) (startX + x * CUBE_LENGTH),
//            (float) (y*CUBE_LENGTH+(int)(CHUNK_SIZE*.8)),
//            (float) (startZ + z * CUBE_LENGTH))
//        }
        
        VertexColorData.flip();
        VertexPositionData.flip();
        VertexTextureData.flip();
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
    
        //glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexColorData, GL_STATIC_DRAW); 
        //glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        //glBindBuffer(GL_ARRAY_BUFFER, 0);
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
//        if (block == null) {
//        // Fallback default colors if block is null
//        return new float[][] {
//            {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}
//        };
//    }

        float[][] white = {
            {1, 1, 1}, {1, 1, 1}, {1, 1, 1},
            {1, 1, 1}, {1, 1, 1}, {1, 1, 1}
        };
        if (block == null) return white;
        
        switch (block.getID()) {
            case 6: return new float[][] {
                {1f, 0f, 0f}, // Top: Red
                {0f, 1f, 0f}, // Bottom: Green
                {0f, 0f, 1f}, // Front: Blue
                {1f, 1f, 0f}, // Back: Yellow
                {1f, 0f, 1f}, // Left: Magenta
                {0f, 1f, 1f}  // Right: Cyan
            };
            default: return white;  
        }
    }
       
    public static float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f/16)/1024f;
        
        // TODO: we need cases for every block we want to texture
        switch (block.getID()) {
            // Sand - tile (2,1)
            case 1:
                return new float[] {
                    // BOTTOM QUAD(DOWN=+Y)
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    // TOP!
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    // FRONT QUAD
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    // BACK QUAD
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    // LEFT QUAD
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2,
                    // RIGHT QUAD
                    x + offset*2, y + offset*1,
                    x + offset*3, y + offset*1,
                    x + offset*3, y + offset*2,
                    x + offset*2, y + offset*2};
            // Water - tile (15, 12)
            case 2:
                return new float[] {
                    // BOTTOM QUAD(DOWN=+Y)
                    x + offset*16, y + offset*13,
                    x + offset*15, y + offset*13,
                    x + offset*15, y + offset*12,
                    x + offset*16, y + offset*12,
                    // TOP!
                    x + offset*16, y + offset*13,
                    x + offset*15, y + offset*13,
                    x + offset*15, y + offset*12,
                    x + offset*16, y + offset*12,
                    // FRONT QUAD
                    x + offset*15, y + offset*12,
                    x + offset*16, y + offset*12,
                    x + offset*16, y + offset*13,
                    x + offset*15, y + offset*13,
                    // BACK QUAD
                    x + offset*16, y + offset*13,
                    x + offset*15, y + offset*13,
                    x + offset*15, y + offset*12,
                    x + offset*16, y + offset*12,
                    // LEFT QUAD
                    x + offset*15, y + offset*12,
                    x + offset*16, y + offset*12,
                    x + offset*16, y + offset*13,
                    x + offset*15, y + offset*13,
                    // RIGHT QUAD
                    x + offset*15, y + offset*12,
                    x + offset*16, y + offset*12,
                    x + offset*16, y + offset*13,
                    x + offset*15, y + offset*13};
            // Dirt - tile (2, 0)
            case 3:
                return new float[] {
                    // BOTTOM QUAD(DOWN=+Y)
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    // TOP!
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    // FRONT QUAD
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    // BACK QUAD
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    // LEFT QUAD
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    // RIGHT QUAD
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1};
            // Stone - (tile 1,0)
            case 4:
                return new float[] {
                    // BOTTOM QUAD(DOWN=+Y)
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    // TOP!
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    // FRONT QUAD
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    // BACK QUAD
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    // LEFT QUAD
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1,
                    // RIGHT QUAD
                    x + offset*1, y + offset*0,
                    x + offset*2, y + offset*0,
                    x + offset*2, y + offset*1,
                    x + offset*1, y + offset*1};
            // Bedrock - tile (1,1)
            case 5:
                return new float[] {
                    // BOTTOM QUAD(DOWN=+Y)
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    // TOP!
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    // FRONT QUAD
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    // BACK QUAD
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    // LEFT QUAD
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2,
                    // RIGHT QUAD
                    x + offset*1, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*2,
                    x + offset*1, y + offset*2};
            // Grass - top (2,9), bottom dirt (2,0), sides (3,0)
            // Also used for Debug (6) and any unknown ID
            case 0:
            // TODO: better default case
            default: 
                return new float[] {
                    // TOP (first face drawn by createCube) - grass top
                    x + offset*3, y + offset*10,
                    x + offset*2, y + offset*10,
                    x + offset*2, y + offset*9,
                    x + offset*3, y + offset*9,
                    // BOTTOM QUAD - dirt
                    x + offset*3, y + offset*1,
                    x + offset*2, y + offset*1,
                    x + offset*2, y + offset*0,
                    x + offset*3, y + offset*0,
                    // FRONT QUAD
                    x + offset*3, y + offset*0,
                    x + offset*4, y + offset*0,
                    x + offset*4, y + offset*1,
                    x + offset*3, y + offset*1,
                    // BACK QUAD
                    x + offset*4, y + offset*1,
                    x + offset*3, y + offset*1,
                    x + offset*3, y + offset*0,
                    x + offset*4, y + offset*0,
                    // LEFT QUAD
                    x + offset*3, y + offset*0,
                    x + offset*4, y + offset*0,
                    x + offset*4, y + offset*1,
                    x + offset*3, y + offset*1,
                    // RIGHT QUAD
                    x + offset*3, y + offset*0,
                    x + offset*4, y + offset*0,
                    x + offset*4, y + offset*1,
                    x + offset*3, y + offset*1};
        
        }
    }
    
    // Method: generateTerrain
    // Purpose: fills Blocks with a layered heightmap from two simplex noises,
    // small rolling hills plus rare large mountains, like Minecraft
    private void generateTerrain() {
        // Low persistence = smooth hills; larger feature size = wider shapes
        SimplexNoise hills = new SimplexNoise(48, 0.35, SEED);
        SimplexNoise mountains = new SimplexNoise(128, 0.5, SEED + 1);
        
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                // World block coordinates, so neighboring chunks would line up
                int wx = StartX / CUBE_LENGTH + x;
                int wz = StartZ / CUBE_LENGTH + z;
                
                // 2D noise per column: uses BOTH x and z
                double base = hills.getNoise(wx, wz);               // about -1 to 1
                double m = (mountains.getNoise(wx, wz) + 1) / 2;    // 0 to 1
                m = m * m * m;                                      // cubing makes mountains rare
                
                int height = (int) (SEA_LEVEL + base * 4 + m * (CHUNK_SIZE - SEA_LEVEL - 2));
                
                height = Math.max(1, Math.min(CHUNK_SIZE - 1, height));
                
                // Fill the column in layers, bottom to top
                for (int y = 0; y < CHUNK_SIZE; y++) {
                    Block.BlockType type = null;    // null = air
                    
                    if (y == 0) {
                        type = Block.BlockType.BlockType_Bedrock;
                    } else if (y < height - 3) {
                        type = Block.BlockType.BlockType_Stone;
                    } else if (y < height) {
                        type = Block.BlockType.BlockType_Dirt;
                    } else if (y == height) {
                        type = (height <= SEA_LEVEL + 1) 
                                ? Block.BlockType.BlockType_Sand     // beaches
                                : Block.BlockType.BlockType_Grass;
                    } else if (y <= SEA_LEVEL) {
                        type = Block.BlockType.BlockType_Water;     // lakes
                    }
                    
                    if (type != null) {
                        Blocks[x][y][z] = new Block(type);
                    }
                }
            }
        }
    }
    
    public Chunk(int startX, int startY, int startZ) 
    {
        //height = (startY + (int)(100*noise.getNoise(i,j,k)) * CUBE_LENGTH);
        // This is how we find the sprite file in our directory
        try {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("VoxelWorldPackage/terrain.png"), GL_NEAREST);
        } catch(Exception e) {
            System.out.print("ER-ROAR!");
            e.printStackTrace();
        }
        
//        Random r = new Random();
//        Block.BlockType[] types = Block.BlockType.values();
//        
//        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
//        for (int x = 0; x < CHUNK_SIZE; x++) {
//            for (int y = 0; y < CHUNK_SIZE; y++) {
//                for (int z = 0; z < CHUNK_SIZE; z++) {
//                    //Blocks[x][y][z] = new Block(Block.BlockType.BlockType_Grass);
//                    Blocks[x][y][z] = new Block(types[r.nextInt(types.length - 1)]); // - 1 to make sure debug blocks don't generate 
//                }
//            }
//        }
        
        // Initialize handles exactly once here
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        
        StartX = startX;
        StartY = startY;
        StartZ = startZ;
        
        generateTerrain();
        rebuildMesh(startX, startY, startZ);
    }
}
