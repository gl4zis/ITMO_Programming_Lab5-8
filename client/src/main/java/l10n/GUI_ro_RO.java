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
                {"dragon.eyes_count", "număr de ochi"}
        };
    }
}
