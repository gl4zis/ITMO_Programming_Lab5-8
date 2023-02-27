package main.exceptions;

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException() {
        System.out.println("Объект не найден");
    }
}
