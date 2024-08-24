package com.example.mygit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.example.mygit.Utils.FileHelper;
import com.example.mygit.Utils.GeneralHelper;

public class Snapshot {
    public String snapshotId; // snapshotId == hash(file content)
    public String filePath;
    public byte[] snapshotContent;

    public Snapshot(String filePath) {
        this.filePath = filePath;
        // Store file content as bytes 
        this.snapshotContent = FileHelper.readFileAsBytes(filePath);
        this.snapshotId = GeneralHelper.hashData(snapshotContent);
    }

    public Snapshot(String snapshotId, String filePath, byte[] snapshotContent) {
        this.snapshotId = snapshotId;
        this.filePath = filePath;
        this.snapshotContent = snapshotContent;
    }

    public void saveSnapshot() {
        // CHeck if mygit init already
        File snapshotDir = new File(GeneralHelper.snapshotsDirPath);

        if(!snapshotDir.exists()) {
            System.err.println("Failed: you must initialized mygit first");
            return;
        }

        // snapshot file
        File snapshotFile = new File(snapshotDir, snapshotId);
        
        // Save Snapshot
        try (FileWriter fwriter = new FileWriter(snapshotFile)) 
        {
            fwriter.write("snapshotID: " + snapshotId + "\n");
            fwriter.write("filePath: " + filePath + "\n");
            fwriter.write("snapshotContent: " + snapshotContent + "\n");
        } 
        catch (IOException e) {
            System.err.println("Failed to Write Snapshot for File: " + filePath);
            e.printStackTrace();
        }
    }

    public static Snapshot readSnapshotFromFile(String searchedSnapshotId) {
        File snapDir = new File(GeneralHelper.snapshotsDirPath);
        File snapFile = FileHelper.searchFile(snapDir, searchedSnapshotId);
        String snapContent = FileHelper.readFileAsString(snapFile.getAbsolutePath());
        
        String[] lines = snapContent.split("\n");
        String snapshotId = lines[0].split(": ")[1];
        String filePath = lines[1].split(": ")[1];
        String snapshotContent = lines[2].split(": ")[1];

        return new Snapshot(snapshotId, filePath, snapshotContent.getBytes());
    }
    
}
