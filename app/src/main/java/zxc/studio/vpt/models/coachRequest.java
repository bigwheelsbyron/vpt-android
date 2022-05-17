package zxc.studio.vpt.models;

import androidx.room.TypeConverters;

import zxc.studio.vpt.DateConverter;

import java.util.Date;

public class coachRequest {

    private String requesterFBRequestID;
    private String receiverFBRequestID;
    private String AthleteID;
    private String CoachID;
    private String Athlete_Name_First;
    private String Athlete_Name_Last;
    private String Coach_Name_First;
    private String Coach_Name_Last;
    private String previousCoach;
    @TypeConverters(DateConverter.class)
    private Date dateSent;

    public coachRequest(){}
    public coachRequest(String requesterFBID,String receiverFBID,String athleteID,String coachID,Date date,String athlete_Name_First, String athlete_Name_Last, String coach_Name_First, String coach_Name_Last,String PreviousCoach){
        requesterFBRequestID = requesterFBID;
        receiverFBRequestID = receiverFBID;
        AthleteID = athleteID;
        CoachID = coachID;
        Athlete_Name_First = athlete_Name_First;
        Athlete_Name_Last = athlete_Name_Last;
        Coach_Name_First = coach_Name_First;
        Coach_Name_Last = coach_Name_Last;
        previousCoach = PreviousCoach;
        dateSent = date;
    }

    public String getRequesterFBRequestID() {
        return requesterFBRequestID;
    }
    public void setRequesterFBRequestID(String requesterFBRequestID) {
        this.requesterFBRequestID = requesterFBRequestID;
    }
    public String getReceiverFBRequestID() {
        return receiverFBRequestID;
    }
    public void setReceiverFBRequestID(String receiverFBRequestID) {
        this.receiverFBRequestID = receiverFBRequestID;
    }
    public String getAthleteID() {
        return AthleteID;
    }
    public void setAthleteID(String athleteID) {
        AthleteID = athleteID;
    }
    public String getCoachID() {
        return CoachID;
    }
    public void setCoachID(String coachID) {
        CoachID = coachID;
    }
    public Date getDateSent() {
        return dateSent;
    }
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
    public String getAthlete_Name_First() {
        return Athlete_Name_First;
    }
    public void setAthlete_Name_First(String athlete_Name_First) {
        Athlete_Name_First = athlete_Name_First;
    }
    public String getAthlete_Name_Last() {
        return Athlete_Name_Last;
    }
    public void setAthlete_Name_Last(String athlete_Name_Last) {
        Athlete_Name_Last = athlete_Name_Last;
    }
    public String getCoach_Name_First() {
        return Coach_Name_First;
    }
    public void setCoach_Name_First(String coach_Name_First) {
        Coach_Name_First = coach_Name_First;
    }
    public String getCoach_Name_Last() {
        return Coach_Name_Last;
    }
    public void setCoach_Name_Last(String coach_Name_Last) {
        Coach_Name_Last = coach_Name_Last;
    }
    public String getPreviousCoach() {
        return previousCoach;
    }
    public void setPreviousCoach(String previousCoach) {
        this.previousCoach = previousCoach;
    }

}
