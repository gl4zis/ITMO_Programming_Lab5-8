package main.commands;

import java.io.Reader;

public class HelpCommand extends NonArgsCommand {
    private static HelpCommand helpCommand;

    private HelpCommand(String name) {
        super(name);
    }

    public static HelpCommand getInstance() {
        if (helpCommand == null) helpCommand = new HelpCommand("help");
        return helpCommand;
    }

    @Override
    public void scriptExecute(Reader reader) {
        execute();
    }

    @Override
    public void execute() {
        System.out.println("""
                help : вывести справку по доступным командам
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов, наибольший Id)
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                add {element} : добавить новый элемент в коллекцию
                update id {element} : обновить значение элемента коллекции, id которого равен заданному
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                save : сохранить коллекцию в файл
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся командыв таком же виде, в котором их вводит пользователь в интерактивном режиме.
                exit : завершить программу (без сохранения в файл)
                add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
                remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
                remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
                average_of_weight : вывести среднее значение поля weight для всех элементов коллекции
                min_by_age : вывести любой объект из коллекции, значение поля age которого является минимальным
                filter_less_than_weight weight : вывести элементы, значение поля weight которых меньше заданного""");
    }

}
