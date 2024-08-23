package com.example.mygit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.mygit.Utils.FileHelper;
import com.example.mygit.Utils.GeneralHelper;

public class Snapshot {
    public String snapshotId; // snapshotId == hash(file content)
    public byte[] snapshotContent;
    public String filePath;

    public Snapshot(String filePath) {
        this.filePath = filePath;
        // Store file content as bytes 
        this.snapshotContent = FileHelper.readFileAsBytes(filePath);
        this.snapshotId = GeneralHelper.hashData(snapshotContent);
    }

    public void saveSnapshot() {
        // CHeck if mygit init already
        File snapshotDir = new File(Repositery.projectPath + File.separator +  "mygit" + File.separator + "snapshots");

        if(!snapshotDir.exists()) {
            System.err.println("Failed: you must initialized mygit first");
        }

        // snapshot file
        File snapshotFile = new File(snapshotDir, snapshotId);
        
        // Save Snapshot
        try (FileOutputStream fwriter = new FileOutputStream(snapshotFile)) 
        {
            fwriter.write(snapshotContent);
            System.out.println("Snapshot saved Successfully: " + snapshotFile.getAbsolutePath());
        } 
        catch (IOException e) {
            System.err.println("Failed to Write Snapshot for File: " + filePath);
            e.printStackTrace();
        }
    }
}
