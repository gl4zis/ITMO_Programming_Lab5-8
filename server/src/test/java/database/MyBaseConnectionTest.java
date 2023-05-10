package database;

import exceptions.ExitException;
import org.junit.jupiter.api.Test;
import parsers.StringModificator;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyBaseConnectionTest {

    @Test
    void connect() {
        InputStream is = new ByteArrayInputStream("exit\n".getBytes());
        System.setIn(is);
        try {
            MyBaseConnection.connect();
        } catch (ExitException ignored) {
        }
    }

    @Test
    void parsePgPass() {
        try {
            File temp = new File(StringModificator.filePathFormat("~/.pgpass"));
            temp.createNewFile();
            FileOutputStream out = new FileOutputStream(temp);
            out.write("*:*:*:admin:qwerty".getBytes());
            out.close();

            Method parse = MyBaseConnection.class.getDeclaredMethod("parsePgPass");
            parse.setAccessible(true);
            InputStream is = (InputStream) parse.invoke(MyBaseConnection.class);
            assertEquals(new String(is.readAllBytes()), "user = admin\npassword = qwerty");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException ignored) {
        }
    }
}