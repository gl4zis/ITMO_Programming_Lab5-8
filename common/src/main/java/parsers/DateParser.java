package parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class for formatting date in 'dd.mm.yyyy hh.mm.ss' format
 */

public abstract class DateParser {
    /**
     * Parses Date object to the String
     *
     * @param date inputted Date object, parses into string
     * @return stringDate
     */
    public static String dateToString(Date date) {
        Map<String, String> months = new HashMap<>();
        months.put("Jan", "01");
        months.put("Feb", "02");
        months.put("Mar", "03");
        months.put("Apr", "04");
        months.put("May", "05");
        months.put("Jun", "06");
        months.put("Jul", "07");
        months.put("Aug", "08");
        months.put("Sep", "09");
        months.put("Oct", "10");
        months.put("Nov", "11");
        months.put("Dec", "12");

        String input = date.toString();
        String[] data = input.split(" ");
        String day = data[2];
        String month = months.get(data[1]);
        String year = data[5];
        String time = data[3];

        return year + "-" + month + "-" + day + " " + time;
    }

    /**
     * Parses String to the Date object
     *
     * @param str inputted line, parses into Date object
     * @return dateObject
     * @throws ParseException if date format in inputted string is incorrect
     */
    public static Date stringToDate(String str) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(str);
    }
}
