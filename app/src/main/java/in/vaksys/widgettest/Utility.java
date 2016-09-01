package in.vaksys.widgettest;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Harsh on 31-08-2016.
 */
public class Utility {
    public static String getCurrentTime(String timeformat) {
        Format formatter = new SimpleDateFormat(timeformat, Locale.US);
        return formatter.format(new Date());
    }
}
