package project.app.smartpantry.logic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import org.junit.Test;








public class ExpiryCheckerTest {

    private static Calendar today() {
        Calendar today = Calendar.getInstance();
        today.set(2026, Calendar.SEPTEMBER, 29);
        return today;
    }

    @Test
    public void yesterdayIsExpiredNotSoon() {
        assertTrue(ExpiryChecker.isExpired("2026-09-28", today()));
        assertFalse(ExpiryChecker.isExpiringSoon("2026-09-28", today()));
    }

    @Test
    public void todayIsSoonNotExpired() {
        assertFalse(ExpiryChecker.isExpired("2026-09-29", today()));
        assertTrue(ExpiryChecker.isExpiringSoon("2026-09-29", today()));
    }

    @Test
    public void inThreeDaysIsSoonAcrossTheMonthEnd() {
        assertTrue(ExpiryChecker.isExpiringSoon("2026-10-02", today()));
    }

    @Test
    public void inFourDaysIsNotSoon() {
        assertFalse(ExpiryChecker.isExpiringSoon("2026-10-03", today()));
    }

    @Test
    public void farFutureIsNeitherExpiredNorSoon() {
        assertFalse(ExpiryChecker.isExpired("2026-12-15", today()));
        assertFalse(ExpiryChecker.isExpiringSoon("2026-12-15", today()));
    }
}
