package com.example.mygit.Utils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.mygit.Repositery;

public class GeneralHelper {
        public static String mygitDirPath = Repositery.projectPath + File.separator + "mygit";
        public static String snapshotsDirPath = mygitDirPath + File.separator + "snapshots";
        public static String commitsDirPath = mygitDirPath + File.separator + "commits";
        public static String HeadFilePath = mygitDirPath + File.separator + "HEAD";

    public static String hashData(byte[] data) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Convert the input data to bytes and compute the hash
            byte[] hashBytes = digest.digest(data);
            
            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Convert each byte to a two-digit hexadecimal representation
                String hex = Integer.toHexString(0xff & b);
                // Pad with leading zeros if necessary
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            // Return the hexadecimal string representation of the hash
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            // Handle the case where the algorithm is not available
            e.printStackTrace();
            return null;
        }
    }

    // Method to get the current timestamp
    public static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
