import java.util.*;

public class CalendarWeekDays {
    public static void main(String[] args) {
        findWeekDays(2010, 5, 1, 2015, 5, 9);
    }

    private static Long doDateDifference(Date start, Date end) {
        long difference = end.getTime() - start.getTime();
        difference /= (1000L * 60L * 60L * 24L);
        return difference;
    }

    public static boolean isAWeekday(Date date) {
        int day = date.getDay();
        if (day == 0 || day == 6) {
            return false;
        } else {
            return true;
        }
    }

    private static void findWeekDays(int startY, int startM, int startD, int endY, int endM, int endD) {
        Date start = new GregorianCalendar(startY, startM - 1, startD).getTime();
        Date end = new GregorianCalendar(endY, endM - 1, endD).getTime();

        List list = new ArrayList();

        long length = doDateDifference(start, end);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(start);
        for (int i = 0; i < length; i++) {
            Date day = calendar.getTime();
            if (isAWeekday(day)) {
                list.add(day);
                calendar.add(Calendar.DATE, 1);
            } else {
                calendar.add(Calendar.DATE, 1);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.printf("[i = %1d] Date: %2$tY/%2$td/%2$tm (%2$tA)\n", i, list.get(i));
        }

    }
}



