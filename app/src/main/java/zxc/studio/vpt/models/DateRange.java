package zxc.studio.vpt.models;

import java.util.Calendar;
import java.util.Date;

public class DateRange {

    private Date startingDate;
    private Date endingDate;

    public DateRange(Date start, Date end){
        startingDate = start;
        endingDate = end;
    }

    public Date getStartingDate() {
        return startingDate;
    }
    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }
    public Date getEndingDate() {
        return endingDate;
    }
    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }
}
