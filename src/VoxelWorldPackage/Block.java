/***************************************************************
* file: Block.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 2
* date last modified: 10/27/2026
*
* purpose: A container class for data of a given Block used to build a Chunk in Chunk.java.
*
****************************************************************
/*

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package VoxelWorldPackage;

public class Block {
    private boolean isActive;
    private BlockType Type;
    private float x, y, z;
    
    public enum BlockType {
        BlockType_Grass(0),
        BlockType_Sand(1),
        BlockType_Water(2),
        BlockType_Dirt(3),
        BlockType_Stone(4),
        BlockType_Bedrock(5),
        BlockType_Debug(6);
        
        private int BlockID;
        
        BlockType(int i) {
            BlockID = i;
        }
        
        // Method: GetID
        // Purpose: returns the ID of a given Block
        public int GetID() {
            return BlockID;
        }
        
        // Method: SetID
        // Purpose: sets the ID of a given Block
        public void SetID(int i) {
            BlockID = i;
        }
    }
    
    public Block(BlockType type) {
        Type = type;
    }
    
    // Method: setCoords
    // Purpose: sets the coordinate data for the Block
    
    public void setCoords(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    // Method: isActive
    // Purpose: returns the active state of the block
    public boolean isActive() {
        return isActive;
    }
    
    // Method: setActive
    // Purpose: sets the active state of the block
    public void setActive(boolean active) {
        isActive = active;
    }
    
    // Method: getID()
    // Purpose: returns the Block's Type ID
    public int getID() {
        return Type.GetID();
    }
}
