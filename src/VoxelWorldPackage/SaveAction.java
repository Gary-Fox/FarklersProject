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
    private float savedSeed;
    private GUI GUIReference;
    
    public SaveAction(GUI g)
    {
        GUIReference = g;
        try {
            Path folder = Paths.get("Saves");

            if (Files.exists(folder)) {
                DirectoryStream<Path> stream = Files.newDirectoryStream(folder);
                for (Path f : stream) {                    
                    List<String> fileData = Files.readAllLines(f);
                    g.addSaveFileSlot(Integer.parseInt(fileData.get(0)), Float.parseFloat(fileData.get(1)), Instant.parse(fileData.get(2)));
                    saveID = Integer.parseInt(fileData.get(0)) + 1;
                }
            } 
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void onClick(float[] params) {
        if (GUIReference.getSaveFileCount() < 8) {
            try {
                Path saveDir = Path.of(System.getProperty("user.dir"),"Saves");
                Files.createDirectories(saveDir);

                File Obj = new File("Saves/save_" + saveID);
                savedSeed = params[0];

                timestamp = Instant.now();

                FileWriter fWriter = new FileWriter("Saves/save_" + saveID);
                fWriter.write(saveID + "\n" + savedSeed + "\n" + timestamp);
                fWriter.close();

                System.out.println("SAVED=Save ID: " + saveID + " // Saved seed: " + savedSeed + " // Timestamp: " + timestamp);
                GUIReference.addSaveFileSlot(saveID, savedSeed, timestamp);

                saveID++;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
