package l10n;

import java.util.ListResourceBundle;

public class GUI_ro_RO extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"leftPanel.home", "Prima pagină"},
                {"leftPanel.view", "Vizualizare"},
                {"leftPanel.table", "Tabelul"},
                {"leftPanel.commands", "Comenzi"},
                {"all.signIn", "Conectați-vă"},
                {"all.signUp", "Înscrieți-vă"},
                {"all.login", "autentificare"},
                {"all.password", "parola"},
                {"all.cancel", "Anulează"},
                {"signIn.saveMe", "Salvare login și parolă"},
                {"signIn.warning", "Autentificare sau parolă incorectă!"},
                {"signUp.loginCol", "Acest login este deja ocupat"},
                {"signUp.notMatch", "Parolele nu se potrivesc"},
                {"signUp.repPassword", "parola repepat"},
                {"signUp.loginMsg", "Puteți utiliza doar a-z, A-Z, 0-9 și _ în login."},
                {"home.darkTheme", "Setați tema Dark"},
                {"home.lightTheme", "Setați tema Light"},
                {"home.signOut", "Ieșire"},
                {"home.changePassword", "Schimbați parola"},
                {"chPass.old", "parola veche"},
                {"chPass.new", "parolă nouă"},
                {"all.confirm", "Confirmați"},
                {"chPass.incOld", "Parolă incorectă"},
                {"synopsis.title", "Sinopsis"},
                {"synopsis.text", "Nu știu ce ar trebui să scriu aici."},
                {"dialog.incorrect", "Un câmp este incorect"},
                {"dragon.empty", "Vârsta poate fi goală:"},
                {"dragon.name", "nume"},
                {"dragon.weight", "greutate"},
                {"dragon.age", "vârstă"},
                {"dragon.eyes_count", "număr de ochi"},
                {"table.filter", "filtru"},
                {"table.refresh", "Reîmprospătare"},
                {"table.remove", "Eliminați alocat"},
                {"view.start", "Mergeți la start"},
                {"ADD", "Adaugă un nou element în colecție"},
                {"ADD_IF_MIN", "Adaugă un nou element în colecție dacă valoarea sa este mai mică decât cel mai mic element din colecție"},
                {"AVERAGE_OF_WEIGHT", "Afișează valoarea medie a câmpului greutate pentru toate elementele din colecție"},
                {"CLEAR", "Șterge colecția (șterge toți dragonii)"},
                {"FILTER_LESS_THAN_WEIGHT", "Afișează elementele a căror valoare a câmpului de greutate este mai mică decât cea dată"},
                {"INFO", "Afișează informații despre colecție"},
                {"MIN_BY_AGE", "Afișează orice obiect din colecție, a cărui valoare a câmpului \"vârstă\" este minimă"},
                {"REMOVE_BY_ID", "Elimină un obiect din colecție în funcție de id-ul său"},
                {"REMOVE_GREATER", "Elimină din colecție toate obiectele care depășesc valoarea specificată"},
                {"REMOVE_LOWER", "Elimină din colecție toate elementele care sunt mai mici decât valoarea specificată"},
                {"UPDATE", "Actualizează valoarea elementului din colecție al cărui id este egal cu cel specificat"},
                {"EXECUTE_SCRIPT", "Execută un script din orice fișier"}
        };
    }
}
