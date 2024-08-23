package com.example.mygit;

import java.util.HashSet;
import java.util.Set;

import com.example.mygit.Utils.FileHelper;

public class Repositery {
    public static String projectPath;
    public static Set<String> trackedFiles = new HashSet<>();

    public Repositery(String path) {
        projectPath = path;
        // Initialize mygit repo
        InitializeRepo();
    }

    public void InitializeRepo() {
        String mygitDirPath = projectPath + "/mygit";
        String snapshotsDirPath = mygitDirPath + "/snapshots";
        String HeadFilePath = mygitDirPath + "/HEAD";

        if(!FileHelper.checkFolderExist(mygitDirPath)) {
            // Create mygit Repo in porject
            FileHelper.createDirectory(mygitDirPath);
            FileHelper.createDirectory(snapshotsDirPath);
            FileHelper.createFile(HeadFilePath);
        }
    }

    public void trackFile(String filePath) {
        trackedFiles.add(filePath);
    }
}
