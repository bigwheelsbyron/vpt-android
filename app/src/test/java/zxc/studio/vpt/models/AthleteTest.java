package zxc.studio.vpt.models;

import static org.junit.jupiter.api.Assertions.*;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

public class AthleteTest {

    private Athlete testAthlete;
    private String idOriginal = "Q1RSVORJV4ONRbGg9btr6Lpg4p43";
    private String nameFirstOriginal = "string_Name_First_original";
    private String nameLastOriginal = "string_Name_Last_original";
    private String idReplacement = "36dgjtV1DRYznTh74zbI3ZxIeCE3";
    private String nameFirstReplacement = "string_Name_First_Replacement";
    private String nameLastReplacement = "string_Name_Last_Replacement";


    @Before
    public void setUp() {
        testAthlete = new Athlete(idOriginal, nameFirstOriginal, nameLastOriginal);
    }

    @Test
    public void canGetValues() {
        getID();
        getFirstName();
        getLastName();
    }

    private void getID(){
        String athletesID = testAthlete.getAthleteUID();
        assertTrue(athletesID != null);
        assertTrue(athletesID == idOriginal);
    }
    private void getFirstName(){
        String athletesFirstName = testAthlete.getFirst_name();
        assertTrue(athletesFirstName != null);
        assertTrue(athletesFirstName == nameFirstOriginal);
    }
    private void getLastName(){
        String athletesLastName = testAthlete.getLast_name();
        assertTrue(athletesLastName != null);
        assertTrue(athletesLastName == nameLastOriginal);
    }

    @Test
    public void canSetValues(){
        set_AthleteUID();
        setFirst_name();
        setLast_name();
    }

    private void set_AthleteUID(){
        testAthlete.setAthleteUID(idReplacement);
        String athletesID = testAthlete.getAthleteUID();
        assertTrue(athletesID != null);
        assertTrue(athletesID != idOriginal);
        assertTrue(athletesID == idReplacement);
    }
    private void setFirst_name() {
        testAthlete.setFirst_name(nameFirstReplacement);
        String athletesFirstName = testAthlete.getFirst_name();
        assertTrue(athletesFirstName != null);
        assertTrue(athletesFirstName != nameFirstOriginal);
        assertTrue(athletesFirstName == nameFirstReplacement);
    }
    private void setLast_name() {
        testAthlete.setLast_name(nameLastReplacement);
        String athletesLastName = testAthlete.getLast_name();
        assertTrue(athletesLastName != null);
        assertTrue(athletesLastName != nameLastOriginal);
        assertTrue(athletesLastName == nameLastReplacement);
    }

    @Test
    public void wontSetIncorrectValues(){
        invalidIDSet();
    }

    private void invalidIDSet(){
        String invalidID = "36dgj tV1DR YznTh 74zbI 3ZxIe CE3";
        testAthlete.setAthleteUID(invalidID);
        String athletesID = testAthlete.getAthleteUID();
        assertTrue(athletesID == idOriginal);
    }


}