package user;

import parsers.MyScanner;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User, having unique login and non-unique password.
 * Don't save non-hashed password
 */
public class User implements Serializable {
    private static final MyScanner CONSOLE = new MyScanner(System.in);
    private final String login;
    private String hashedPasswd;

    /**
     * Constructor sets only login
     */
    private User(String login) {
        this.login = login;
    }

    /**
     * Authorizes current user.
     * Reads login and non-hashed password
     *
     * @return user
     */
    public static User authorize() {
        String login;
        do {
            System.out.print("Enter login: ");
            login = CONSOLE.nextLine();
            if (login.matches("\\w+")) break;
            System.out.println("You can use only a-z, A-Z, 0-9 and _ in the login");
        } while (true);
        System.out.print("Enter password: ");
        String passwd = CONSOLE.nextLine();
        return signUp(login, passwd);
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
        user.hashPasswd(passwd, 500);
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
        } // There ARE such algorithm!
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }

    /**
     * Hashes string several times
     */
    private void hashPasswd(String passwd, int iter) {
        for (int i = 0; i < iter; i++) {
            passwd = getMD5Hash(passwd);
        }
        hashedPasswd = passwd;
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
            return login.equals(((User) obj).login) && hashedPasswd.equals(((User) obj).hashedPasswd);
        }
    }

    @Override
    public String toString() {
        return login;
    }
}
