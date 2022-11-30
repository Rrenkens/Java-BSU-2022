package by.polina_kostyukovich.docks_and_hobos.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String format(LogRecord record) {
        Date date = new Date(record.getMillis());
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String strDate = dateFormat.format(date);
        return strDate + ": " + record.getMessage() + "\n";
    }
}
