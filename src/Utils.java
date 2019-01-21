import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static Date datePlusDays(int days) {
        Date deadline = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deadline);
        calendar.add(Calendar.DATE, days); // 10 jours
        return calendar.getTime();
    }
}
