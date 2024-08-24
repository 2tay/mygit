package com.example.mygit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.example.mygit.Utils.GeneralHelper;

public class Commit {
    public String commitId;
    public String comment;
    public List<String> snapshots;
    public String timestamp; 
    public final static LinkedList<String> allCommitIds = new LinkedList<>();

    public Commit(String comment) {
        this.commitId = UUID.randomUUID().toString();
        this.comment = comment;
        this.snapshots = new ArrayList<>();
        this.timestamp = GeneralHelper.getCurrentTimestamp();
        InitializeSnapshots();
    }

    public void InitializeSnapshots() {
        for(String filepath : Repositery.trackedFiles)
        {
            Snapshot snap = new Snapshot(filepath);
            snap.saveSnapshot();
            snapshots.add(snap.snapshotId);
        }
    }

    public void saveCommitToFile(){
        File commitFile = new File(Repositery.projectPath + File.separator + "commits", commitId);
        try(FileWriter fwriter = new FileWriter(commitFile)) {
            fwriter.write("commit ID: " + commitId + "\n");
            fwriter.write("Comment: " + comment + "\n");
            fwriter.write("Timestamp: " + timestamp + "\n");
            fwriter.write("snapshots: " + String.join(", ", snapshots) + "\n");
        } catch(IOException e) {
            System.err.println("Failed to save Commit: " + commitId);
            e.printStackTrace();
        }
    }

    // Load all commits ids and store them in LinkedList from oldest to newest
    public void loadCommitIds() {
        File commitsDir = new File(Repositery.projectPath + File.separator + "mygit" + File.separator + "commits");
        File[] commitFiles = commitsDir.listFiles();

        if(commitFiles != null) {
            for(File commitFile : commitFiles) {
                allCommitIds.addLast(commitFile.getName());
            }
        }
    }
}
