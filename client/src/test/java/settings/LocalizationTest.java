package settings;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalizationTest {

    private final String message = """
            COLL_TYPE LinkedHashSet
            DATE_CREATE - 12.08.2003 01:32:54
            Name - test
            Bye bye""";

    @Test
    void RULocalize() {
        String output = Localization.RULocalize(message);
        assertEquals("""
                Тип коллекции: LinkedHashSet
                Дата создания - 12.08.2003 01:32:54
                Имя - test
                Пока пока""", output);
    }

    @Test
    void ENLocalize() {
        String output = Localization.ENLocalize(message);
        assertEquals("""
                Collection type: LinkedHashSet
                Date of creation - 12.08.2003 01:32:54
                Name - test
                Bye bye""", output);
    }
}