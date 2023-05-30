package l10n;

import java.util.ListResourceBundle;

public class GUI_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"leftPanel.home", "Главная"},
                {"leftPanel.view", "Визуализация"},
                {"leftPanel.table", "Таблица"},
                {"leftPanel.commands", "Команды"},
                {"all.signIn", "Авторизация"},
                {"all.signUp", "Регистрация"},
                {"all.login", "логин"},
                {"all.password", "пароль"},
                {"all.cancel", "Назад"},
                {"signIn.saveMe", "Сохранить логин и пароль"},
                {"signIn.warning", "Неверный логин или пароль!"},
                {"signUp.loginCol", "Этот логин уже занят"},
                {"signUp.notMatch", "Пароли не совпадают"},
                {"signUp.repPassword", "повтор пароля"},
                {"signUp.loginMsg", "Вы можете использовать только a-z, A-Z, 0-9 и _ в логине"},
                {"home.darkTheme", "Установить темную тему"},
                {"home.lightTheme", "Установить светлую тему"},
                {"home.signOut", "Выйти из аккаунта"},
                {"home.changePassword", "Сменить пароль"},
                {"chPass.old", "старый пароль"},
                {"chPass.new", "новый пароль"},
                {"all.confirm", "Ок"},
                {"chPass.incOld", "Неверный пароль"}
        };
    }
}
