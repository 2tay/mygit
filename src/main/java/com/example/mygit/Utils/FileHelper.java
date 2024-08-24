package com.example.mygit.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.example.mygit.Repositery;

public class FileHelper {
    
    public static boolean checkFolderExist(String folderPath) {
        File folder = new File(folderPath);

        if(folder.exists() && folder.isDirectory()) {
            return true;
        }
        
        return false;
    }

    public static void createDirectory(String path) {
        File dir = new File(path);
        if(!dir.mkdir()) {
            System.err.println("Failed to create Directory: " + path);
        }
    }

    public static void createFile(String path) {
        File file = new File(path);
        try {
            if(!file.createNewFile()) {
                System.err.println("File already exist: " + path);
            }
        } catch (IOException e) {
            System.err.println("Failed to create file: " + path);
            e.printStackTrace();
        } 
    }

    // Method to delete a file or directory
    public static void deleteFileOrDirectory(File fileOrDir) {
        if (fileOrDir.exists()) {
            if (fileOrDir.isDirectory()) {
                // Recursively delete contents of the directory
                for (File file : fileOrDir.listFiles()) {
                    deleteFileOrDirectory(file);
                }
            }
            if (fileOrDir.delete()) {
                System.out.println(fileOrDir.getName() + " was deleted.");
            } else {
                System.err.println("Failed to delete " + fileOrDir.getName());
            }
        } else {
            System.out.println(fileOrDir.getName() + " does not exist.");
        }
    }

    public static String readFileAsString(String filePath) {
        Path path = Paths.get(filePath);
        try {
            // Read all bytes from the file and convert to String
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readFileAsBytes(String filePath) {
        Path path = Paths.get(filePath);
        try {
            return Files.readAllBytes(path);
        } 
        catch (IOException e) {
            System.err.println("Failed to read as bytes File: " + filePath);
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static List<File> listAllDirFiles(String dirPath) {
        List<File> allFiles = new ArrayList<>();
        File dir = new File(dirPath);

        if(dir.exists() && dir.isDirectory()) 
        {
            // list all files and directories
            for(File file : dir.listFiles()) 
            {
                if(file.isDirectory())
                {
                    String mygitPath = Repositery.projectPath + File.separator + "mygit";

                    // Skip mygit directory
                    if(mygitPath.equals(file.getAbsolutePath()))
                    {
                        continue;
                    }

                    // Recuresively add files from subderictories
                    allFiles.addAll(listAllDirFiles(file.getAbsolutePath()));
                }
                else if(file.isFile())
                {
                    allFiles.add(file);
                }
            }
        }

        return allFiles;
    }

    public static File searchFile(File dir, String fileName) {
        File[] dirFiles = dir.listFiles();
        if(dirFiles != null) {
            for(File dirFile : dirFiles) {
                if(dirFile.isFile() && dirFile.getName().equals(fileName)) {
                    System.out.println("Found file: " + dirFile.getAbsolutePath());
                    return dirFile;
                }
                else if(dirFile.isDirectory()) {
                    searchFile(dirFile, fileName);
                }
            }
            System.out.println("Failed to found file: " + fileName);
        }
        System.out.println("Failed: no files in directory: " + dir.getAbsolutePath());
        return null;
    }
}
