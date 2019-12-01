package capstone.placer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ssZ";

    public static long convert(String timestampStr) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT);
        Date result = dateFormat.parse(timestampStr);

        return result.getTime();
    }
}
