package zxc.studio.vpt.utilities;

import java.util.Calendar;
import java.util.Date;

public class DateFunctions {

    public static Date getCurrentDateAndTime(){
        Date currentTime = Calendar.getInstance().getTime();
        return currentTime;
    }

    private static Date getBaselineCompletedDate(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date completedDate = c.getTime();
        return completedDate;
    }


}
