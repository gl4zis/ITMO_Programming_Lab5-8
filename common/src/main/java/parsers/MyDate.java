package parsers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyDate {
    private final static String PATTERN_RU = "dd.MM.yyyy HH:mm:ss";
    private final static String PATTERN_EN = "MM/dd/yy HH:mm:ss";
    private final static String PATTERN_RO = "dd-MM-yyyy HH:mm:ss";
    private final static String PATTERN_GR = "dd/MM/yy HH:mm:ss";

    private final SimpleDateFormat sDf = new SimpleDateFormat();
    private final Date date;

    public MyDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        String locale = Locale.getDefault().getCountry();
        switch (locale) {
            case "RU" -> sDf.applyPattern(PATTERN_RU);
            case "RO" -> sDf.applyPattern(PATTERN_RO);
            case "GR" -> sDf.applyPattern(PATTERN_GR);
            default -> sDf.applyPattern(PATTERN_EN);
        }
        return sDf.format(date);
    }
}
