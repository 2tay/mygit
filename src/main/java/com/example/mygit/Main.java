package com.example.mygit;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File file = new File("projects");
        String path = file.getAbsolutePath();
        System.out.println(path);
        
        Scanner scanner = new Scanner(System.in);
        System.err.println("ENtre fiel path:");
        String projectPath = scanner.nextLine();

        

        scanner.close();
    }
}
