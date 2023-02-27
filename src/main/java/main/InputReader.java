package main;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public abstract class InputReader {
    public static String readNextConsoleLine() {
        Scanner console = new Scanner(System.in);
        String line = console.nextLine();
        return line;
    }

    public static String readNextFileLine(Reader reader) throws IOException {
        StringBuilder line = new StringBuilder();
        boolean comment = false;
        int nextSym = reader.read();
        if (nextSym == -1) return null;
        if (nextSym == 13 || nextSym == 10) nextSym = reader.read();
        while (nextSym != 13 && nextSym != -1) {
            line.append((char) nextSym);
            if (nextSym == 47) comment = true;
            nextSym = reader.read();
            if (nextSym == 47 && comment) {
                while (nextSym != 13) nextSym = reader.read();
                return line.substring(0, line.length() - 2);
            }
        }
        return line.toString();
    }
}
