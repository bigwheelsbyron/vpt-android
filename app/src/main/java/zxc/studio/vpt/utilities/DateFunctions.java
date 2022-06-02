package zxc.studio.vpt.utilities;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateFunctions {

    public static Date getCurrentDateAndTime(){
        Date currentTime = Calendar.getInstance().getTime();
        return currentTime;
    }

    public static Date getBaselineCompletedDate(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date completedDate = c.getTime();
        return completedDate;
    }

    public static Date dateFromLocallySavedString(String string){
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date savedDate = null;
        try {
            savedDate = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return savedDate;
    }





}
