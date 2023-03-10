package org.application.lab5;

import org.application.lab5.commands.Command;

public class Main {

    public static void main(String[] args) {
        Command.generateCollection();
        while (true) {
            System.out.print("-> ");
            Command.parse();
        }
    }
}