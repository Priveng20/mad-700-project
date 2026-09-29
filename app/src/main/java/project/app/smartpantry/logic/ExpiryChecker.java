package project.app.smartpantry.logic;

import java.util.Calendar;
import java.util.Locale;





//works out whether an expiry date (yyyy-MM-dd) has passed or is coming up soon
public class ExpiryChecker {

    // "soon" means within this many days from today
    public static final int SOON_DAYS = 3;

    // true if the date is before today
    public static boolean isExpired(String expiryDate, Calendar today) {
        return expiryDate.compareTo(format(today)) < 0;
    }

    // true if the date is today or within the next SOON_DAYS days
    public static boolean isExpiringSoon(String expiryDate, Calendar today) {
        Calendar limit = (Calendar) today.clone();
        limit.add(Calendar.DAY_OF_MONTH, SOON_DAYS);
        return !isExpired(expiryDate, today) && expiryDate.compareTo(format(limit)) <= 0;
    }




    private static String format(Calendar date) {
        return String.format(Locale.US, "%04d-%02d-%02d", date.get(Calendar.YEAR),
                date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH));
    }
}
