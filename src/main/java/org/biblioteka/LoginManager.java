package org.biblioteka;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class LoginManager {
    private Map<String, String> userCredentials;

    public LoginManager() {
        this.userCredentials = new HashMap<>();
        loadUserCredentials();
    }

    private void loadUserCredentials() {
        Path path = Paths.get("src/main/java/org/biblioteka/employees.txt");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userCredentials.put(parts[0], parts[1]); // username, hashed password
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validateLogin(String username, String password) {
        String hashedPassword = hashPassword(password);
        return hashedPassword != null && hashedPassword.equals(userCredentials.get(username));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

