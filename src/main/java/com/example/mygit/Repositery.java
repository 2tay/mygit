package com.example.mygit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.mygit.Utils.FileHelper;
import com.example.mygit.Utils.GeneralHelper;

public class Repositery {
    public static String projectPath;
    public static Set<String> trackedFiles = new HashSet<>();

    public Repositery(String path) {
        projectPath = path;
        // Initialize mygit repo
        InitializeRepo();
    }

    public void InitializeRepo() {

        if(!FileHelper.checkFolderExist(GeneralHelper.mygitDirPath)) {
            // Create mygit Repo in porject
            FileHelper.createDirectory(GeneralHelper.mygitDirPath);
            FileHelper.createDirectory(GeneralHelper.snapshotsDirPath);
            FileHelper.createDirectory(GeneralHelper.commitsDirPath);
            FileHelper.createFile(GeneralHelper.HeadFilePath);
        }
        else {
            System.out.println("mygit ALready Initialized!");
        }
    }

    public static void updateHead(String commitId) {
        try(FileWriter fwriter = new FileWriter(GeneralHelper.HeadFilePath)) {
            fwriter.write(commitId);
        } catch (IOException e) {
            System.err.println("Failed to update head with commitID: " + commitId);
            e.printStackTrace();
        }
    }

    public void softReset(String resetCommitId) {
        updateHead(resetCommitId);
        // Read resetCOmmitID File
        Commit searchedCommit = Commit.readCommitFromFile(resetCommitId);
        List<String> snapshotIds = searchedCommit.snapshots;
        for(String snapId : snapshotIds) {
            Snapshot snap = Snapshot.readSnapshotFromFile(snapId);
            File file = new File(snap.filePath);
            try (FileOutputStream fwriter = new FileOutputStream(file)) 
            {
                fwriter.write(snap.snapshotContent);
            } 
            catch (IOException e) {
                System.err.println("Failed to soft reset file: " + file.getAbsolutePath());
                e.printStackTrace();
            }
        }
    }


}
