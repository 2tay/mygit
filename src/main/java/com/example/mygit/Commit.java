package com.example.mygit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.example.mygit.Utils.FileHelper;
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
        Repositery.updateHead(commitId);
    }

    // Constructor to initialize from file content
    public Commit(String commitId, String comment, String timestamp, List<String> snapshots) {
        this.commitId = commitId;
        this.comment = comment;
        this.timestamp = timestamp;
        this.snapshots = snapshots;
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
        File commitFile = new File(GeneralHelper.commitsDirPath, commitId);
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


    public static Commit readCommitFromFile(String searchedCommit) {
        File commitDir = new File(GeneralHelper.commitsDirPath);
        File commitFile = FileHelper.searchFile(commitDir, searchedCommit);
        String commit = FileHelper.readFileAsString(commitFile.getAbsolutePath());

        String[] lines = commit.split("\n");
        String commitId = lines[0].split(": ")[1];
        String comment = lines[1].split(": ")[1];
        String timestamp = lines[2].split(": ")[1];
        List<String> snapshots = Arrays.asList(lines[3].split(": ")[1].split(", "));

        return new Commit(commitId, comment, timestamp, snapshots);
    }

    // Load all commits ids and store them in LinkedList from oldest to newest
    public void loadCommitIds() {
        File commitsDir = new File(GeneralHelper.commitsDirPath);
        File[] commitFiles = commitsDir.listFiles();

        if(commitFiles != null) {
            for(File commitFile : commitFiles) {
                allCommitIds.addLast(commitFile.getName());
            }
        }
    }

}
