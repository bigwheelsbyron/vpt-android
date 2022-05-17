package zxc.studio.vpt.models;

import static org.junit.jupiter.api.Assertions.*;

import androidx.room.TypeConverters;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import zxc.studio.vpt.DateConverter;
import zxc.studio.vpt.utilities.DateFunctions;

public class coachRequestTest {

    private coachRequest testCoachRequest;
    private String requesterFBRequestID = "XXXXXXXXXXXXXXXXXXXXXXX";
    private String receiverFBRequestID = "YYYYYYYYYYYYYYYYYYYYYYY";
    private String AthleteID = "AAAAAAAAAAAAAAAAAAAAAAA";
    private String CoachID = "BBBBBBBBBBBBBBBBBBBBBBB";
    private String Athlete_Name_First = "Athlete_Name_First";
    private String Athlete_Name_Last = "Athlete_Last_Name";
    private String Coach_Name_First = "Coach_Name_First";
    private String Coach_Name_Last = "Coach_Name_Last";
    private String previousCoach = "Previous_Coach";
    private Date dateSent = DateFunctions.getCurrentDateAndTime();
    private String requesterFBRequestIDReplace = "NXXXXXXXXXXXXXXXXXXXXXX";
    private String receiverFBRequestIDReplace = "YNYYYYYYYYYYYYYYYYYYYYY";
    private String AthleteIDReplace = "AAAAAAAAAAANAAAAAAAAAAA";
    private String CoachIDReplace = "BBBBBBBBBBBBBNBBBBBBBBB";
    private String Athlete_Name_FirstReplace = "Athlete_Name_First_Replace";
    private String Athlete_Name_LastReplace = "Athlete_Last_Name_replace";
    private String Coach_Name_FirstReplace = "Coach_Name_First_Replace";
    private String Coach_Name_LastReplace = "Coach_Name_Last_Replace";
    private String previousCoachReplace = "Previous_Coach_Replace";
    private Date dateSentReplace = DateFunctions.getCurrentDateAndTime();

    @Before
    public void setUp() {
        testCoachRequest = new coachRequest(requesterFBRequestID,receiverFBRequestID,AthleteID,CoachID,dateSent,Athlete_Name_First,Athlete_Name_Last,Coach_Name_First,Coach_Name_Last,previousCoach);
    }

    @Test
    public void canGetValues(){
        get_requestedFBID();
        get_receiverFBID();
        get_athleteID();
        get_coachID();
        get_AthleteFirst();
        get_AthleteLast();
        get_CoachFirst();
        get_CoachLast();
        get_previousCoach();
        get_date();
    }

    private void get_requestedFBID (){
        String requestFBID = testCoachRequest.getRequesterFBRequestID();
        assertTrue(requestFBID != null);
        assertTrue(requestFBID == requesterFBRequestID);
    }
    private void get_receiverFBID (){
        String receiverFBID = testCoachRequest.getReceiverFBRequestID();
        assertTrue(receiverFBID != null);
        assertTrue(receiverFBID == receiverFBRequestID);
    }
    private void get_athleteID (){
        String athleteID = testCoachRequest.getAthleteID();
        assertTrue(athleteID != null);
        assertTrue(athleteID == AthleteID);
    }
    private void get_coachID (){
        String coachID = testCoachRequest.getCoachID();
        assertTrue(coachID != null);
        assertTrue(coachID == CoachID);
    }
    private void get_AthleteFirst (){
        String athleteFirst = testCoachRequest.getAthlete_Name_First();
        assertTrue(athleteFirst != null);
        assertTrue(athleteFirst == Athlete_Name_First);
    }
    private void get_AthleteLast (){
        String athleteLast = testCoachRequest.getAthlete_Name_Last();
        assertTrue(athleteLast != null);
        assertTrue(athleteLast == Athlete_Name_Last);
    }
    private void get_CoachFirst (){
        String coachFirst = testCoachRequest.getCoach_Name_First();
        assertTrue(coachFirst != null);
        assertTrue(coachFirst == Coach_Name_First);
    }
    private void get_CoachLast (){
        String coachLast = testCoachRequest.getCoach_Name_Last();
        assertTrue(coachLast != null);
        assertTrue(coachLast == Coach_Name_Last);
    }
    private void get_previousCoach (){
        String preCoach = testCoachRequest.getPreviousCoach();
        assertTrue(preCoach != null);
        assertTrue(preCoach == previousCoach);
    }
    private void get_date (){
        Date requestDate = testCoachRequest.getDateSent();
        assertTrue(requestDate != null);
        assertTrue(requestDate == dateSent);
    }

    @Test
    public void canSetValues(){
        set_requestedFBID();
        set_receiverFBID();
        set_athleteID();
        set_coachID();
        set_AthleteFirst();
        set_AthleteLast();
        set_CoachFirst();
        set_CoachLast();
        set_prevCoach();
        set_Date();
    }

    private void set_requestedFBID (){
        testCoachRequest.setRequesterFBRequestID(requesterFBRequestIDReplace);
        String testVariable = testCoachRequest.getRequesterFBRequestID();
        assertTrue(testVariable != null);
        assertTrue(testVariable != requesterFBRequestID);
        assertTrue(testVariable == requesterFBRequestIDReplace);
    }
    private void set_receiverFBID(){
        testCoachRequest.setReceiverFBRequestID(receiverFBRequestIDReplace);
        String testVariable = testCoachRequest.getReceiverFBRequestID();
        assertTrue(testVariable != null);
        assertTrue(testVariable != receiverFBRequestID);
        assertTrue(testVariable == receiverFBRequestIDReplace);
    }
    private void set_athleteID (){
        testCoachRequest.setAthleteID(AthleteIDReplace);
        String testVariable = testCoachRequest.getAthleteID();
        assertTrue(testVariable != null);
        assertTrue(testVariable != AthleteID);
        assertTrue(testVariable == AthleteIDReplace);
    }
    private void set_coachID (){
        testCoachRequest.setCoachID(CoachIDReplace);
        String testVariable = testCoachRequest.getCoachID();
        assertTrue(testVariable != null);
        assertTrue(testVariable != CoachID);
        assertTrue(testVariable == CoachIDReplace);
    }
    private void set_AthleteFirst (){
        testCoachRequest.setAthlete_Name_First(Athlete_Name_FirstReplace);
        String testVariable = testCoachRequest.getAthlete_Name_First();
        assertTrue(testVariable != null);
        assertTrue(testVariable != Athlete_Name_First);
        assertTrue(testVariable == Athlete_Name_FirstReplace);
    }
    private void set_AthleteLast (){
        testCoachRequest.setAthlete_Name_Last(Athlete_Name_LastReplace);
        String testVariable = testCoachRequest.getAthlete_Name_Last();
        assertTrue(testVariable != null);
        assertTrue(testVariable != Athlete_Name_Last);
        assertTrue(testVariable == Athlete_Name_LastReplace);
    }
    private void set_CoachFirst (){
        testCoachRequest.setCoach_Name_First(Coach_Name_FirstReplace);
        String testVariable = testCoachRequest.getCoach_Name_First();
        assertTrue(testVariable != null);
        assertTrue(testVariable != Coach_Name_First);
        assertTrue(testVariable == Coach_Name_FirstReplace);
    }
    private void set_CoachLast (){
        testCoachRequest.setCoach_Name_Last(Coach_Name_LastReplace);
        String testVariable = testCoachRequest.getCoach_Name_Last();
        assertTrue(testVariable != null);
        assertTrue(testVariable != Coach_Name_Last);
        assertTrue(testVariable == Coach_Name_LastReplace);
    }
    private void set_prevCoach (){
        testCoachRequest.setPreviousCoach(previousCoachReplace);
        String testVariable = testCoachRequest.getPreviousCoach();
        assertTrue(testVariable != null);
        assertTrue(testVariable != previousCoach);
        assertTrue(testVariable == previousCoachReplace);
    }
    private void set_Date (){
        testCoachRequest.setDateSent(dateSentReplace);
        Date testVariable = testCoachRequest.getDateSent();
        assertTrue(testVariable != null);
        assertTrue(testVariable != dateSent);
        assertTrue(testVariable == dateSentReplace);
    }

}