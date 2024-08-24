package com.example.mygit;

import java.io.File;
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
        String mygitDirPath = projectPath + File.separator + "mygit";
        String snapshotsDirPath = mygitDirPath + File.separator + "snapshots";
        String commitsDirPath = mygitDirPath + File.separator + "commits";
        String HeadFilePath = mygitDirPath + File.separator + "HEAD";

        if(!FileHelper.checkFolderExist(mygitDirPath)) {
            // Create mygit Repo in porject
            FileHelper.createDirectory(mygitDirPath);
            FileHelper.createDirectory(snapshotsDirPath);
            FileHelper.createDirectory(commitsDirPath);
            FileHelper.createFile(HeadFilePath);
        }
        else {
            System.out.println("mygit ALready Initialized!");
        }
    }




}
