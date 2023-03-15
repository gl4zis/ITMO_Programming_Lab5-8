package org.application.lab5.commands;

import org.application.lab5.collection.DragonCollection;
import org.application.lab5.parsers.InputConsoleReader;
import org.application.lab5.dragons.Dragon;
import org.application.lab5.exceptions.ObjectNotFoundException;
import org.application.lab5.parsers.InputScriptReader;

/**
 * Argument command "update id {dragon}". Change dragon vars on another. Finds dragon in collection by its id
 */

public class UpdateCommand extends ArgsCommand {
    private final DragonCollection collection;

    /** Constructor, sets collection, that the command works with, name and description of command
     */
    UpdateCommand(DragonCollection collection) {
        super("update", "update id {element} : обновить значение элемента коллекции, id которого равен заданному");
        this.collection = collection;
    }

    /** Change dragon vars on another. Finds dragon in collection by its id.
     * Outputs error message, if wrong argument or if there are no such element in collection
     * @param reader reader of file from that gives data, if null data gives from console
     * @param arg id of dragon, which you want to update
     */
    @Override
    public void execute(InputScriptReader reader, String arg) {
        try {
            int id = Integer.parseInt(arg);
            Dragon dragon;
            if (reader == null) {
                dragon = InputConsoleReader.readDragon();
            } else {
                dragon = reader.readDragon();
            }
            Dragon.decUniqNumber();
            collection.find(id).update(dragon);
            System.out.println("Объект успешно обновлен");
        } catch (NumberFormatException e) {
            System.out.println("Неверный аргумент");
        } catch (ObjectNotFoundException e) {
            System.out.println("Такого объекта нет в коллекции");
        }
    }
}