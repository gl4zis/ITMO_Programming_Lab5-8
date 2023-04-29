package user;

import parsers.MyScanner;

import java.io.Serializable;

public class User implements Serializable {
    private static final MyScanner console = new MyScanner(System.in);
    private final String login;
    private String hashedPasswd;

    private User(String login) {
        this.login = login;
    }

    public static User authorize() {
        String login;
        do {
            System.out.print("Enter login: ");
            login = console.nextLine();
            if (login.matches("\\w")) break;
            System.out.println("You can use only a-z, A-Z, 0-9 and _ in the login");
        } while (true);
        System.out.print("Enter password: ");
        String passwd = console.nextLine();
        return signUp(login, passwd);
    }

    public static User signUp(String login, String passwd) {
        User user = new User(login);
        user.setHashedPasswd(PasswordHash.getMD5Hash(passwd));
        return user;
    }

    public static User signIn(String login, String hashedPasswd) {
        User user = new User(login);
        user.setHashedPasswd(hashedPasswd);
        return user;
    }

    private void setHashedPasswd(String hashedPasswd) {
        this.hashedPasswd = hashedPasswd;
    }

    public String getLogin() {
        return login;
    }

    public String getHashedPasswd() {
        return hashedPasswd;
    }

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
