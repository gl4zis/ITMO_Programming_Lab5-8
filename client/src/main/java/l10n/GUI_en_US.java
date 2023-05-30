package l10n;

import java.util.ListResourceBundle;

public class GUI_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"leftPanel.home", "Home"},
                {"leftPanel.view", "View"},
                {"leftPanel.table", "Table"},
                {"leftPanel.commands", "Commands"},
                {"all.signIn", "Sign In"},
                {"all.signUp", "Sign Up"},
                {"all.login", "login"},
                {"all.password", "password"},
                {"all.cancel", "Cancel"},
                {"signIn.saveMe", "Save login and password"},
                {"signIn.warning", "Incorrect login or password!"},
                {"signUp.loginCol", "This login is already taken"},
                {"signUp.notMatch", "The passwords don't match"},
                {"signUp.repPassword", "repeat password"},
                {"signUp.loginMsg", "You can use only a-z, A-Z, 0-9 and _ in the login"},
                {"home.darkTheme", "Set Dark theme"},
                {"home.lightTheme", "Set Light theme"},
                {"home.signOut", "Sign Out"},
                {"home.changePassword", "Change password"},
                {"chPass.old", "old password"},
                {"chPass.new", "new password"},
                {"all.confirm", "Confirm"},
                {"chPass.incOld", "Incorrect password"}
        };
    }
}