package com.example.mygit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Commit {
    public String commitId;
    public String comment;
    public List<String> snapshots;

    public Commit(String comment) {
        this.commitId = UUID.randomUUID().toString();
        this.comment = comment;
        this.snapshots = new ArrayList<>();
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
}
