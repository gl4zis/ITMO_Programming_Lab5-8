package user;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User, having unique login and non-unique password.
 * Don't save non-hashed password
 */
public class User implements Serializable {
    private final String login;
    private String hashedPasswd;

    /**
     * Constructor sets only login
     */
    private User(String login) {
        this.login = login;
    }

    /**
     * Creates new instance, hashes password 500 times, using MD5
     *
     * @param login  login of user
     * @param passwd non-hashed password
     * @return user
     */
    public static User signUp(String login, String passwd) {
        User user = new User(login);
        user.hashedPasswd = hashPasswd(passwd, 500);
        return user;
    }

    /**
     * Creates new instance using already hashed password
     *
     * @return user
     */
    public static User signIn(String login, String hashedPasswd) {
        User user = new User(login);
        user.hashedPasswd = hashedPasswd;
        return user;
    }

    /**
     * Hashes string, using MD5 algorithm
     *
     * @param passwd non-hashed string
     * @return once hashed by MD5 string
     */
    public static String getMD5Hash(String passwd) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(passwd.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ignored) {
        } // There IS such algorithm!
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }

    /**
     * Hashes string several times
     */
    public static String hashPasswd(String passwd, int iter) {
        for (int i = 0; i < iter; i++) {
            passwd = getMD5Hash(passwd);
        }
        return passwd;
    }

    public String getLogin() {
        return login;
    }

    public String getHashedPasswd() {
        return hashedPasswd;
    }

    /**
     * Checks if two users are equals by login and hashedPassword
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        else {
            return login.equals(((User) obj).login);
        }
    }

    @Override
    public String toString() {
        return login;
    }
}
