package main.dragons;

public enum Color {
    GREEN("Зеленый"),
    RED("Красный"),
    ORANGE("Оранжевый"),
    BROWN("Коричневый");

    private final String name;

    Color(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
