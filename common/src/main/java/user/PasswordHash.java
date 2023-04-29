package user;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class PasswordHash {

    public static String getMD5Hash(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ignored) {
        } // There ARE such algorithm!
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }
}
