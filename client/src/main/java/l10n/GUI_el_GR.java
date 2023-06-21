package l10n;

import java.util.ListResourceBundle;

public class GUI_el_GR extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"leftPanel.home", "Αρχική σελίδα"},
                {"leftPanel.view", "Απεικόνιση"},
                {"leftPanel.table", "Πίνακας"},
                {"leftPanel.commands", "Εντολές"},
                {"all.signIn", "Συνδεθείτε"},
                {"all.signUp", "Εγγραφείτε"},
                {"all.login", "Σύνδεση"},
                {"all.password", "κωδικός πρόσβασης"},
                {"all.cancel", "Ακύρωση"},
                {"signIn.saveMe", "Αποθήκευση σύνδεσης και κωδικού πρόσβασης"},
                {"signIn.warning", "Λανθασμένη σύνδεση ή κωδικός πρόσβασης!"},
                {"signUp.loginCol", "Αυτή η σύνδεση είναι ήδη κατειλημμένη"},
                {"signUp.notMatch", "Οι κωδικοί πρόσβασης δεν ταιριάζουν"},
                {"signUp.repPassword", "κωδικός πρόσβασης repepat"},
                {"signUp.loginMsg", "Μπορείτε να χρησιμοποιήσετε μόνο τα a-z, A-Z, 0-9 και _ στο login."},
                {"home.darkTheme", "Ορισμός σκοτεινού θέματος"},
                {"home.lightTheme", "Ρύθμιση θέματος Light"},
                {"home.signOut", "Υπογράψτε έξω"},
                {"home.changePassword", "Αλλαγή πρόσβασης"},
                {"chPass.old", "παλιός κωδικός πρόσβασης"},
                {"chPass.new", "νέος κωδικός πρόσβασης"},
                {"all.confirm", "Επιβεβαίωση"},
                {"chPass.incOld", "Λανθασμένος κωδικός πρόσβασης"},
                {"synopsis.title", "Σύνοψη"},
                {"synopsis.text", "Δεν ξέρω τι πρέπει να γράψω εδώ"},
                {"dialog.incorrect", "Κάποιο πεδίο είναι λανθασμένο"},
                {"dragon.empty", "Η ηλικία μπορεί να είναι κενή:"},
                {"dragon.name", "όνομα"},
                {"dragon.weight", "βάρος"},
                {"dragon.age", "ηλικία"},
                {"dragon.eyes_count", "aριθμός ματιών"},
                {"table.filter", "φίλτρο"},
                {"table.refresh", "Ανανέωση"},
                {"table.remove", "Αφαιρέστε το"},
                {"view.start", "Πηγαίνετε στην αρχή"}
        };
    }
}
