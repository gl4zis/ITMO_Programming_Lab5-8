package parsers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScriptParserTest {

    static File temp;

    @BeforeAll
    static void createFile() {
        try {
            temp = new File("C:/Windows/Temp/testFile.txt");
            temp.createNewFile();
            FileOutputStream out = new FileOutputStream(temp);
            out.write("line1 //Comment\r\nline2\r\n\nline3 //Comment".getBytes());
            out.close();
        } catch (IOException ignored) {
        }
    }

    @AfterAll
    static void removeFile() {
        temp.delete();
    }

    @Test
    void readLines() throws IOException {
        InputStream is = ScriptParser.readLines("C:/Windows/Temp/testFile.txt");
        Scanner scanner = new Scanner(is);
        StringBuilder output = new StringBuilder();
        while (scanner.hasNext()) output.append(scanner.nextLine()).append('\n');
        assertEquals("line1\nline2\n\nline3", output.substring(0, output.length() - 1));
    }
}