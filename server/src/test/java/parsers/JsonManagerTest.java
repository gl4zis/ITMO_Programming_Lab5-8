package parsers;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class JsonManagerTest {

    static JsonManager manager;

    @BeforeAll
    static void init() {
        InputStream is = new ByteArrayInputStream("  \nnot_existable_path\n~/Desktop/ITMO/Programming/collection.json\n~/Desktop/ITMO/Programming/script1.txt\n".getBytes());
        System.setIn(is);
        manager = new JsonManager("not_exist");
    }

    @Test
    void readJSON() {
        JSONObject jsonColl = manager.readJSON();
        assertTrue(jsonColl.containsKey("creationDate"));
        assertTrue(jsonColl.containsKey("dragons"));
        manager = new JsonManager("");
        assertEquals(new JSONObject(), manager.readJSON());
    }

    @Test
    void writeJSON() {
        manager.writeJSON(manager.readJSON());
    }
}