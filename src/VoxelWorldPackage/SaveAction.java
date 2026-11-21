/***************************************************************
* file: SaveAction.java
* authors: Isabeau Cantoran, Kian Naderi, Jacob Carrasco. (The Farklers)
* class: CS 4450.01 - Computer Graphics
*
* assignment: Check Point 1
* date last modified: 10/17/2026
*
* purpose: A class used to contain the function ran when the Save button is clicked
*
****************************************************************
/*

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VoxelWorldPackage;

import java.time.Instant;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.DirectoryStream;
import java.util.List;

/**
 *
 * @author crowbot
 * 
 */
public class SaveAction implements ButtonAction{
    private static int saveID = 0;
    private Instant timestamp;
    private int savedSeed;
    private GUI GUIReference;
    
    private int staleID = -1;
    private int filesInDir = 0;
    
    public SaveAction(GUI g)
    {
        GUIReference = g;
        try {
            Path folder = Paths.get("Saves");

            if (Files.exists(folder)) {
                DirectoryStream<Path> stream = Files.newDirectoryStream(folder);
                for (Path f : stream) {                    
                    List<String> fileData = Files.readAllLines(f);
                    g.addSaveFileSlot(Integer.parseInt(fileData.get(0)), Integer.parseInt(fileData.get(1)), Instant.parse(fileData.get(2)));
                    saveID = Integer.parseInt(fileData.get(0)) + 1;
                    filesInDir++;
                }
            } 
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void onClick(int[] params) {
        try {
            savedSeed = params[0];
            timestamp = Instant.now();
            
            SaveFileSlot selectedSlot = GUIReference.getSelectedSaveFileSlot();
            if ((GUIReference.getSaveFileCount() + 1 <= 8 && selectedSlot == null) || selectedSlot != null) {
                if (selectedSlot == null && GUIReference.getSaveFileCount() <= 8) {
                    GUIReference.addSaveFileSlot(saveID, savedSeed, timestamp);
                } 
                
                if (selectedSlot != null) {
                    staleID = selectedSlot.getSaveID();
                }
                
                Path saveDir = Path.of(System.getProperty("user.dir"),"Saves");
                Files.createDirectories(saveDir);

                File Obj = new File("Saves/save_" + saveID);            
                if (selectedSlot != null) {
                    selectedSlot.overwriteData(saveID, savedSeed, timestamp);
                    
                    Files.deleteIfExists(Paths.get("Saves/save_" + staleID));
                    staleID = -1;
                }

                FileWriter fWriter = new FileWriter("Saves/save_" + saveID);
                fWriter.write(saveID + "\n" + savedSeed + "\n" + timestamp);
                fWriter.close();

                System.out.println("SAVED=Save ID: " + saveID + " // Saved seed: " + savedSeed + " // Timestamp: " + timestamp);    
            }
            saveID++;
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
