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
                {"chPass.incOld", "Неверный пароль"},
                {"synopsis.title", "Справка"},
                {"synopsis.text", "Не знаю что здесь написать"},
                {"dialog.incorrect", "Поле заполнено некорректно"},
                {"dragon.empty", "Можно оставить пустым:"},
                {"dragon.name", "имя"},
                {"dragon.weight", "вес"},
                {"dragon.age", "возраст"},
                {"dragon.eyes_count", "кол-во глаз"},
                {"table.filter", "фильтр"},
                {"table.refresh", "Обновить"},
                {"table.remove", "Удалить выбранный"},
                {"view.start", "В начало"},
                {"ADD", "Добавить новый элемент в коллекцию"},
                {"ADD_IF_MIN", "Добавить новый элемент в коллекцию, если его значение меньше самого маленького элемента в коллекции"},
                {"AVERAGE_OF_WEIGHT", "Вывести среднее значение веса для всех элементов коллекции"},
                {"CLEAR", "Очистить коллекцию (удалить всех драконов)"},
                {"FILTER_LESS_THAN_WEIGHT", "Вывести элементы, вес которых меньше заданного"},
                {"INFO", "Вывести информацию о коллекции"},
                {"MIN_BY_AGE", "Вывести любой объект из коллекции, возраст которого минимален"},
                {"REMOVE_BY_ID", "Удалить объект из коллекции по его id"},
                {"REMOVE_GREATER", "Удалить из коллекции все элементы, превышающие заданный"},
                {"REMOVE_LOWER", "Удалить из коллекции все элементы, которые меньше указанного"},
                {"UPDATE", "Обновить значение элемента коллекции, id которого равен заданному"},
                {"EXECUTE_SCRIPT", "Выполнить скрипт из любого файла"}
        };
    }
}
