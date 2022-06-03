package network;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Session {
    private final String userName;
    private final String password;

    private Session(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    static Session sessionInstance = null;

    public static Session createInstance(String userName, String password) {
        sessionInstance = new Session(userName, password);
        return sessionInstance;
    }

    public static Session getInstance() {
        return sessionInstance;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public static byte[] getHash(String line) throws NoSuchAlgorithmException {
        MessageDigest digest;
        digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(line.getBytes(StandardCharsets.UTF_8));
    }
}
