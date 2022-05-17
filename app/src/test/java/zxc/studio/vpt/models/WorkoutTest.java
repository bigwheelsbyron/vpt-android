package zxc.studio.vpt.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import zxc.studio.vpt.utilities.DateFunctions;

public class WorkoutTest {

    private Workout testWorkout;
    private String workout_athlete_id = "AAAAAAAAAAAAAAAAAAAAAAA";
    private String workout_coach_id = "BBBBBBBBBBBBBBBBBBBBBBB";
    private Date workout_completedDate = DateFunctions.getCurrentDateAndTime();
    private Date workout_dateFor = DateFunctions.getCurrentDateAndTime();
    private int workout_food = 1300;
    private int workout_mood = 7;
    private float workout_sleep = (float) 8.2;
    private float workout_weight = (float) 90.2;
    private String workout_id = "XXXXXXXXXXXXXXXXXXXXXXX";
    private String workout_programme_id = "YYYYYYYYYYYYYYYYYYYYYYY";
    private String workout_athlete_idReplacement = "NAAAAAAAAAAAAAAAAAAAAAA";
    private String workout_coach_idReplacement = "NBBBBBBBBBBBBBBBBBBBBBB";
    private Date workout_completedDateReplacement = DateFunctions.getCurrentDateAndTime();
    private Date workout_dateForReplacement = DateFunctions.getCurrentDateAndTime();
    private int workout_foodReplacement = 1400;
    private int workout_moodReplacement = 8;
    private float workout_sleepReplacement = (float) 12.2;
    private float workout_weightReplacement = (float) 91.2;
    private String workout_idReplacement = "NXXXXXXXXXXXXXXXXXXXXXX";
    private String workout_programme_idReplacement = "NYYYYYYYYYYYYYYYYYYYYYY";

    @Before
    public void setUp(){
        testWorkout = new Workout(workout_athlete_id,workout_coach_id,workout_completedDate,workout_dateFor,workout_food,workout_mood,workout_sleep,workout_weight,workout_id,workout_programme_id);
    }

    @Test
    public void canGetValues(){
        getWorkout_athlete_id();
        getWorkout_coach_id();
        getWorkout_completedDate();
        getWorkout_dateFor();
        getWorkout_food();
        getWorkout_mood();
        getWorkout_sleep();
        getWorkout_weight();
        getWorkout_id();
        getWorkout_programme_id();
    }

    void getWorkout_athlete_id() {
        String testVariable = testWorkout.getWorkout_athlete_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == workout_athlete_id);
    }
    void getWorkout_coach_id() {
        String testVariable = testWorkout.getWorkout_coach_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == workout_coach_id);
    }
    void getWorkout_completedDate() {
        Date testVariable = testWorkout.getWorkout_completedDate();
        assertTrue(testVariable != null);
        assertTrue(testVariable == workout_completedDate);
    }
    void getWorkout_dateFor() {
        Date testVariable = testWorkout.getWorkout_dateFor();
        assertTrue(testVariable != null);
        assertTrue(testVariable == workout_dateFor);
    }
    void getWorkout_food() {
        int testVariable = testWorkout.getWorkout_food();
        assertTrue(testVariable == workout_food);
    }
    void getWorkout_mood() {
        int testVariable = testWorkout.getWorkout_mood();
        assertTrue(testVariable == workout_mood);
    }
    void getWorkout_sleep() {
        float testVariable = testWorkout.getWorkout_sleep();
        assertTrue(testVariable == workout_sleep);
    }
    void getWorkout_weight() {
        float testVariable = testWorkout.getWorkout_weight();
        assertTrue(testVariable == workout_weight);
    }
    void getWorkout_id() {
        String testVariable = testWorkout.getWorkout_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == workout_id);
    }
    void getWorkout_programme_id() {
        String testVariable = testWorkout.getWorkout_programme_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == workout_programme_id);
    }

    @Test
    public void canSetValues(){
        setWorkout_athlete_id();
        setWorkout_coach_id();
        setWorkout_completedDate();
        setWorkout_dateFor();
        setWorkout_food();
        setWorkout_mood();
        setWorkout_sleep();
        setWorkout_weight();
        setWorkout_id();
        setWorkout_programme_id();
    }

    void setWorkout_athlete_id() {
        testWorkout.setWorkout_athlete_id(workout_athlete_idReplacement);
        String testVariable = testWorkout.getWorkout_athlete_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != workout_athlete_id);
        assertTrue(testVariable == workout_athlete_idReplacement);
    }
    void setWorkout_coach_id() {
        testWorkout.setWorkout_coach_id(workout_coach_idReplacement);
        String testVariable = testWorkout.getWorkout_coach_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != workout_coach_id);
        assertTrue(testVariable == workout_coach_idReplacement);
    }
    void setWorkout_completedDate() {
        testWorkout.setWorkout_completedDate(workout_completedDateReplacement);
        Date testVariable = testWorkout.getWorkout_completedDate();
        assertTrue(testVariable != null);
        assertTrue(testVariable != workout_completedDate);
        assertTrue(testVariable == workout_completedDateReplacement);
    }
    void setWorkout_dateFor() {
        testWorkout.setWorkout_dateFor(workout_dateForReplacement);
        Date testVariable = testWorkout.getWorkout_dateFor();
        assertTrue(testVariable != null);
        assertTrue(testVariable != workout_dateFor);
        assertTrue(testVariable == workout_dateForReplacement);
    }
    void setWorkout_food() {
        testWorkout.setWorkout_food(workout_foodReplacement);
        int testVariable = testWorkout.getWorkout_food();
        assertTrue(testVariable != workout_food);
        assertTrue(testVariable == workout_foodReplacement);
    }
    void setWorkout_mood() {
        testWorkout.setWorkout_mood(workout_moodReplacement);
        int testVariable = testWorkout.getWorkout_mood();
        assertTrue(testVariable != workout_mood);
        assertTrue(testVariable == workout_moodReplacement);
    }
    void setWorkout_sleep() {
        testWorkout.setWorkout_sleep(workout_sleepReplacement);
        float testVariable = testWorkout.getWorkout_sleep();
        assertTrue(testVariable != workout_sleep);
        assertTrue(testVariable == workout_sleepReplacement);
    }
    void setWorkout_weight() {
        testWorkout.setWorkout_weight(workout_weightReplacement);
        float testVariable = testWorkout.getWorkout_weight();
        assertTrue(testVariable != workout_weight);
        assertTrue(testVariable == workout_weightReplacement);
    }
    void setWorkout_id() {
        testWorkout.setWorkout_id(workout_idReplacement);
        String testVariable = testWorkout.getWorkout_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != workout_id);
        assertTrue(testVariable == workout_idReplacement);
    }
    void setWorkout_programme_id() {
        testWorkout.setWorkout_programme_id(workout_programme_idReplacement);
        String testVariable = testWorkout.getWorkout_programme_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != workout_programme_id);
        assertTrue(testVariable == workout_programme_idReplacement);
    }
}