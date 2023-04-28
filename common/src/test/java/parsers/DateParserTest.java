package parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateParserTest {

    Date date;
    String dateStr;

    @BeforeEach
    void setUp() {
        date = Date.from(Instant.ofEpochSecond(0));
        dateStr = "01.01.1970 03:00:00";
    }

    @Test
    void dateToString() {
        assertEquals(dateStr, DateParser.dateToString(date));
    }

    @Test
    void stringToDate() throws ParseException {
        assertEquals(date, DateParser.stringToDate(dateStr));
        assertThrows(ParseException.class, () -> DateParser.stringToDate("01.01.0113:00 MSK 01/23"));
    }
}