package com.example.mygit.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeneralHelper {

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
}
