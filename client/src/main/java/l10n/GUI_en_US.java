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
                {"chPass.incOld", "Incorrect password"},
                {"synopsis.title", "Synopsis"},
                {"synopsis.text", "I don't know what I should\n write here"},
                {"dialog.incorrect", "Some field is incorrect"},
                {"dragon.empty", "Age can be empty:"},
                {"dragon.name", "name"},
                {"dragon.weight", "weight"},
                {"dragon.age", "age"},
                {"dragon.eyes_count", "count of eyes"},
                {"table.filter", "filter regex"},
                {"table.refresh", "Refresh"},
                {"table.remove", "Remove allocated"},
                {"view.start", "Go to start"},
                {"ADD", "Add a new item to the collection"},
                {"ADD_IF_MIN", "Add a new item to the collection if its value is smaller than the smallest item in the collection"},
                {"AVERAGE_OF_WEIGHT", "Display the average value of the weight field for all items in the collection"},
                {"CLEAR", "Clear the collection (delete all your dragons)"},
                {"FILTER_LESS_THAN_WEIGHT", "Output the elements whose value of the weight field is less than the given one"},
                {"INFO", "Display information about the collection"},
                {"MIN_BY_AGE", "Output any object from the collection, the value of the age field of which is the minimum"},
                {"REMOVE_BY_ID", "Remove an item from the collection by its id"},
                {"REMOVE_GREATER", "Remove from the collection all items exceeding the specified"},
                {"REMOVE_LOWER", "Remove all items from the collection that are smaller than the specified"},
                {"UPDATE", "Update the value of the collection item whose id is equal to the given one"},
                {"EXECUTE_SCRIPT", "Execute script from any file"}
        };
    }
}
