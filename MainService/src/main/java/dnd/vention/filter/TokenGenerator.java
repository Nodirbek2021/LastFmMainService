package dnd.vention.filter;

import dnd.vention.filter.payload.TokenInfo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class TokenGenerator {
    private static final String SECRET_KEY = "MySuperSecretKey";
    private static final long EXPIRATION_TIME_MS = 3600000; // 1 hour expiration

    public static String generateBase64Token(String username, String password) {
        // Create a token payload with username, password, and expiration
        String tokenPayload = String.format("%s:%s:%d", username, password, System.currentTimeMillis() + EXPIRATION_TIME_MS);

        // Encode the token payload in Base64
        return Base64.getEncoder().encodeToString(tokenPayload.getBytes());
    }
    public static TokenInfo decodeBase64Token(String base64Token) {
        // Decode the Base64-encoded token payload
        byte[] decodedBytes = Base64.getDecoder().decode(base64Token);
        String tokenPayload = new String(decodedBytes);

        // Split the payload into parts (username:password:expirationTimestamp)
        String[] parts = tokenPayload.split(":");
        if (parts.length == 3) {
            String username = parts[0];
            String password = parts[1];
            long expirationTimestamp = Long.parseLong(parts[2]);

            // Check if the token is not expired
            if (expirationTimestamp > System.currentTimeMillis()) {
                return new TokenInfo(username, password,expirationTimestamp);
            }
        }

        return null; // Invalid or expired token
    }


    public static boolean isValid(TokenInfo tokenPayload) {
            // Check if the token is not expired
            return tokenPayload.getExpirationTimestamp() > System.currentTimeMillis();
    }


    ///With Role

    public static String generateToken(String username, String password, Integer roleId) {
        // Concatenate the components with a delimiter (, comma)
        String tokenPayload = String.format("%s,%s,%s,%d", username, password, roleId, System.currentTimeMillis()+EXPIRATION_TIME_MS);

        // Encode the token payload using Base64
        String base64Token = Base64.getEncoder().encodeToString(tokenPayload.getBytes(StandardCharsets.UTF_8));

        return base64Token;
    }
    public static TokenInfo decodeTokenWithRole(String base64Token) {
        // Decode the Base64-encoded token
        byte[] decodedBytes = Base64.getDecoder().decode(base64Token.getBytes(StandardCharsets.UTF_8));
        String decodedToken = new String(decodedBytes, StandardCharsets.UTF_8);

        // Split the decoded string to get individual components
        String[] components = decodedToken.split(",");

        // Ensure the correct number of components
        if (components.length == 4) {
            String username = components[0];
            String password = components[1];
            Integer roleId = Integer.parseInt( components[2]);
            long expiration = Long.parseLong(components[3]);

            return new TokenInfo(username, password, roleId, expiration);
        } else {
            throw new IllegalArgumentException("Invalid token format");
        }
    }







    public static void main(String[] args) {

    }



}
