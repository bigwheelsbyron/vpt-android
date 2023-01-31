package zxc.studio.vpt.utilities;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateFunctions {

    private static final String TAG = "Date Functions";
    private static int DEFAULT_BACK_DATE_ONEMONTH = 31;
    private static int DEFAULT_BACK_DATE_TWOMONTHS = 62;
    private static int DEFAULT_BACK_DATE_QUARTER = 121;
    private static int DEFAULT_BACK_DATE_HALFYEAR = 183;
    private static int DEFAULT_BACK_DATE_YEAR = 365;

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

    public static Date dateAndTimeToDate(Date dateAndTime){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse(formatter.format(dateAndTime));
        } catch (ParseException e) {
            return Calendar.getInstance().getTime();
        }
    }

    public static String dateAndTimeToSimpleDateString(Date dateAndTime){
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(dateAndTime);
    }

    public static String dateAndTimeToSimpleDateStringShort(Date dateAndTime){
        DateFormat formatter = new SimpleDateFormat("dd/MM");
        return formatter.format(dateAndTime);
    }

    public static ArrayList<Date> returnLastMonth(){
        Date currentDate = DateFunctions.getCurrentDateAndTime();
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(currentDate);
        for (int i = 0; i < DEFAULT_BACK_DATE_ONEMONTH; i++){
            Date newDate = decrementDate(dates.get(i),1);
            dates.add(newDate);
        }
        return dates;
    }

    public static ArrayList<Date> returnLastTwoMonths(){
        Date currentDate = DateFunctions.getCurrentDateAndTime();
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(currentDate);
        for (int i = 0; i < DEFAULT_BACK_DATE_TWOMONTHS; i++){
            Date newDate = decrementDate(dates.get(i),1);
            dates.add(newDate);
        }
        return dates;
    }

    public static ArrayList<Date> returnLastQuarter(){
        Date currentDate = DateFunctions.getCurrentDateAndTime();
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(currentDate);
        for (int i = 0; i < DEFAULT_BACK_DATE_QUARTER; i++){
            Date newDate = decrementDate(dates.get(i),1);
            dates.add(newDate);
        }
        return dates;
    }

    public static ArrayList<Date> returnLastLastHalfYear(){
        Date currentDate = DateFunctions.getCurrentDateAndTime();
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(currentDate);
        for (int i = 0; i < DEFAULT_BACK_DATE_HALFYEAR; i++){
            Date newDate = decrementDate(dates.get(i),1);
            dates.add(newDate);
        }
        return dates;
    }

    public static ArrayList<Date> returnLastYear(){
        Date currentDate = DateFunctions.getCurrentDateAndTime();
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(currentDate);
        for (int i = 0; i < DEFAULT_BACK_DATE_YEAR; i++){
            Date newDate = decrementDate(dates.get(i),1);
            dates.add(newDate);
        }
        return dates;
    }

    public static Date decrementDate(Date date,int reduction) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -reduction);
        Date nextDate = c.getTime();
        return nextDate;
    }

    public static String[] DatesDictionary(ArrayList<String> Dates){
        String[] returnDates2 = new String[Dates.size()];
        Dates.toArray(returnDates2);
        Log.d(TAG, "DatesDictionary: " + returnDates2);
        return returnDates2;
    }

    private static int deltaBetweenDays(Date finalDate, Date startDate){
        long diff = finalDate.getTime() - startDate.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
    }

    public static ArrayList<Date> returnRangeArrayFromTwoDates(Date finalDate, Date startDate){
        int difference = deltaBetweenDays(finalDate,startDate);
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(finalDate);
        for (int i = 0; i < difference; i++){
            Date newDate = decrementDate(dates.get(i),1);
            dates.add(newDate);
        }
        return dates;
    }

    public static Date roundDateToDay(Date dateToRound){
        Calendar c = Calendar.getInstance();
        c.setTime(dateToRound);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }







}
