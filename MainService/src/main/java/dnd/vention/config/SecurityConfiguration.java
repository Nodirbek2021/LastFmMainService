package dnd.vention.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecurityConfiguration {
    public static String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
//             Using Base64 encoding for better representation
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encoding password.", e);
        }
    }


    public static boolean validatePassword(String password, String storedEncodedPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            // Using Base64 encoding for comparison
            String encodedPassword = Base64.getEncoder().encodeToString(hash);

            return storedEncodedPassword.equals(encodedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error validating password.", e);
        }
    }

    public static String decodePassword(String storedEncodedPassword) {
        // This is a naive example for educational purposes only.
        // In a real-world scenario, this should not be possible.
        for (int i = 0; i < 1000000; i++) {
            String candidate = String.valueOf(i);
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(candidate.getBytes());

                // Using Base64 encoding for comparison
                String encodedCandidate = Base64.getEncoder().encodeToString(hash);

                if (storedEncodedPassword.equals(encodedCandidate)) {
                    return candidate;
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error decoding password.", e);
            }
        }
        return null;
    }


}
