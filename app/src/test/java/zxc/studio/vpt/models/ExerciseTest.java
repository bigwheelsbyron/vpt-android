package zxc.studio.vpt.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import zxc.studio.vpt.utilities.DateFunctions;

public class ExerciseTest {

    private Exercise testExercise;
    private String exercise_athlete_id = "AAAAAAAAAAAAAAAAAAAAAAA";
    private String exercise_coach_id = "BBBBBBBBBBBBBBBBBBBBBBB";
    private String exercise_classification = "Strength";
    private Boolean exercise_completedBoolean = true;
    private Date exercise_completedDate = DateFunctions.getCurrentDateAndTime();
    private int exercise_difficulty = 5;
    private float exercise_distance = (float) 10.1;
    private float exercise_duration = (float) 15.3;
    private String exercise_id = "XXXXXXXXXXXXXXXXXXXXXXX";
    private String exercise_name = "YYYYYYYYYYYYYYYYYYYYYYY";
    private String exercise_note = "Test Note";
    private int exercise_reps = 10;
    private int exercise_rpe = 9;
    private int exercise_sequence = 1;
    private String exercise_uom = "kg";
    private float exercise_weight = (float) 150.25;
    private String exercise_workout_id = "YYYYXXXYYYYYYYYYYYYYYYY";
    private String exercise_programme_id = "YYYXXXXXXXXXXXXXXXXXXXX";
    private String exercise_athlete_idReplacement = "BAAAAAAAAAAAAAAAAAAAAAA";
    private String exercise_coach_idReplacement = "ABBBBBBBBBBBBBBBBBBBBBB";
    private String exercise_classificationReplacement = "Distance";
    private Boolean exercise_completedBooleanReplacement = false;
    private Date exercise_completedDateReplacement = DateFunctions.getCurrentDateAndTime();
    private int exercise_difficultyReplacement = 1;
    private float exercise_distanceReplacement = (float) 100.1;
    private float exercise_durationReplacement = (float) 9.7;
    private String exercise_idReplacement = "YXXXXXXXXXXXXXXXXXXXXXX";
    private String exercise_nameReplacement = "ZYYYYYYYYYYYYYYYYYYYYYY";
    private String exercise_noteReplacement = "replacement note0";
    private int exercise_repsReplacement = 4;
    private int exercise_rpeReplacement = 2;
    private int exercise_sequenceReplacement = 2;
    private String exercise_uomReplacement = "km";
    private float exercise_weightReplacement = (float) 20.3;
    private String exercise_workout_idReplacement = "YYYYXXXYYYYYYYYNYYYYYYY";
    private String exercise_programme_idReplacement = "YYYXXXXXXXXXXXXXXXNXXXX";

    @Before
    public void setUp() {
        testExercise = new Exercise(exercise_athlete_id,exercise_coach_id,exercise_classification,exercise_completedBoolean,exercise_completedDate,exercise_difficulty,exercise_distance,exercise_duration,exercise_id,exercise_name,exercise_note,exercise_reps,exercise_rpe,exercise_sequence,exercise_uom,exercise_weight,exercise_workout_id,exercise_programme_id);
    }

    @Test
    public void canGetValues(){
        getExercise_athlete_id();
        getExercise_coach_id();
        getExercise_classification();
        getExercise_completedBoolean();
        getExercise_difficulty();
        getExercise_completedDate();
        getExercise_distance();
        getExercise_duration();
        getExercise_id();
        getExercise_name();
        getExercise_note();
        getExercise_reps();
        getExercise_rpe();
        getExercise_sequence();
        getExercise_uom();
        getExercise_weight();
        getExercise_workout_id();
        getExercise_programme_id();
    }

    private void getExercise_athlete_id() {
        String testVariable = testExercise.getExercise_athlete_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_athlete_id);
    }
    private void getExercise_coach_id() {
        String testVariable = testExercise.getExercise_coach_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_coach_id);
    }
    private void getExercise_classification() {
        String testVariable = testExercise.getExercise_classification();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_classification);
    }
    private void getExercise_completedBoolean() {
        Boolean testVariable = testExercise.getExercise_completedBoolean();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_completedBoolean);
    }
    private void getExercise_difficulty() {
        int testVariable = testExercise.getExercise_difficulty();
        assertTrue(testVariable == exercise_difficulty);
    }
    private void getExercise_completedDate() {
        Date testVariable = testExercise.getExercise_completedDate();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_completedDate);
    }
    private void getExercise_distance() {
        float testVariable = testExercise.getExercise_distance();
        assertTrue(testVariable == exercise_distance);
    }
    private void getExercise_duration() {
        float testVariable = testExercise.getExercise_duration();
        assertTrue(testVariable == exercise_duration);
    }
    private void getExercise_id() {
        String testVariable = testExercise.getExercise_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_id);
    }
    private void getExercise_name() {
        String testVariable = testExercise.getExercise_name();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_name);
    }
    private void getExercise_note() {
        String testVariable = testExercise.getExercise_note();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_note);
    }
    private void getExercise_reps() {
        int testVariable = testExercise.getExercise_reps();
        assertTrue(testVariable == exercise_reps);
    }
    private void getExercise_rpe() {
        int testVariable = testExercise.getExercise_rpe();
        assertTrue(testVariable == exercise_rpe);
    }
    private void getExercise_sequence() {
        int testVariable = testExercise.getExercise_sequence();
        assertTrue(testVariable == exercise_sequence);
    }
    private void getExercise_uom() {
        String testVariable = testExercise.getExercise_uom();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_uom);
    }
    private void getExercise_weight() {
        float testVariable = testExercise.getExercise_weight();
        assertTrue(testVariable == exercise_weight);
    }
    private void getExercise_workout_id() {
        String testVariable = testExercise.getExercise_workout_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_workout_id);
    }
    private void getExercise_programme_id() {
        String testVariable = testExercise.getExercise_programme_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable == exercise_programme_id);
    }

    @Test
    public void canSetValues(){
        setExercise_athlete_id();
        setExercise_coach_id();
        setExercise_classification();
        setExercise_completedBoolean();
        setExercise_completedDate();
        setExercise_difficulty();
        setExercise_distance();
        setExercise_duration();
        setExercise_id();
        setExercise_name();
        setExercise_note();
        setExercise_reps();
        setExercise_rpe();
        setExercise_sequence();
        setExercise_uom();
        setExercise_weight();
        setExercise_workout_id();
        setExercise_programme_id();
    }

    private void setExercise_athlete_id() {
        testExercise.setExercise_athlete_id(exercise_athlete_idReplacement);
        String testVariable = testExercise.getExercise_athlete_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_athlete_id);
        assertTrue(testVariable == exercise_athlete_idReplacement);
    }
    private void setExercise_coach_id() {
        testExercise.setExercise_coach_id(exercise_coach_idReplacement);
        String testVariable = testExercise.getExercise_coach_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_coach_id);
        assertTrue(testVariable == exercise_coach_idReplacement);
    }
    private void setExercise_classification() {
        testExercise.setExercise_classification(exercise_classificationReplacement);
        String testVariable = testExercise.getExercise_classification();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_classification);
        assertTrue(testVariable == exercise_classificationReplacement);
    }
    private void setExercise_completedBoolean() {
        testExercise.setExercise_completedBoolean(exercise_completedBooleanReplacement);
        boolean testVariable = testExercise.getExercise_completedBoolean();
        assertTrue(testVariable != exercise_completedBoolean);
        assertTrue(testVariable == exercise_completedBooleanReplacement);
    }
    private void setExercise_completedDate() {
        testExercise.setExercise_completedDate(exercise_completedDateReplacement);
        Date testVariable = testExercise.getExercise_completedDate();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_completedDate);
        assertTrue(testVariable == exercise_completedDateReplacement);
    }
    private void setExercise_difficulty() {
        testExercise.setExercise_difficulty(exercise_difficultyReplacement);
        int testVariable = testExercise.getExercise_difficulty();
        assertTrue(testVariable != exercise_difficulty);
        assertTrue(testVariable == exercise_difficultyReplacement);
    }
    private void setExercise_distance() {
        testExercise.setExercise_distance(exercise_distanceReplacement);
        float testVariable = testExercise.getExercise_distance();
        assertTrue(testVariable != exercise_distance);
        assertTrue(testVariable == exercise_distanceReplacement);
    }
    private void setExercise_duration() {
        testExercise.setExercise_duration(exercise_durationReplacement);
        float testVariable = testExercise.getExercise_duration();
        assertTrue(testVariable != exercise_duration);
        assertTrue(testVariable == exercise_durationReplacement);
    }
    private void setExercise_id() {
        testExercise.setExercise_id(exercise_idReplacement);
        String testVariable = testExercise.getExercise_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_id);
        assertTrue(testVariable == exercise_idReplacement);
    }
    private void setExercise_name() {
        testExercise.setExercise_name(exercise_nameReplacement);
        String testVariable = testExercise.getExercise_name();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_name);
        assertTrue(testVariable == exercise_nameReplacement);
    }
    private void setExercise_note() {
        testExercise.setExercise_note(exercise_noteReplacement);
        String testVariable = testExercise.getExercise_note();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_note);
        assertTrue(testVariable == exercise_noteReplacement);
    }
    private void setExercise_reps() {
        testExercise.setExercise_reps(exercise_repsReplacement);
        int testVariable = testExercise.getExercise_reps();
        assertTrue(testVariable != exercise_reps);
        assertTrue(testVariable == exercise_repsReplacement);
    }
    private void setExercise_rpe() {
        testExercise.setExercise_rpe(exercise_rpeReplacement);
        int testVariable = testExercise.getExercise_rpe();
        assertTrue(testVariable != exercise_rpe);
        assertTrue(testVariable == exercise_rpeReplacement);
    }
    private void setExercise_sequence() {
        testExercise.setExercise_sequence(exercise_sequenceReplacement);
        int testVariable = testExercise.getExercise_sequence();
        assertTrue(testVariable != exercise_sequence);
        assertTrue(testVariable == exercise_sequenceReplacement);
    }
    private void setExercise_uom() {
        testExercise.setExercise_uom(exercise_uomReplacement);
        String testVariable = testExercise.getExercise_uom();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_uom);
        assertTrue(testVariable == exercise_uomReplacement);
    }
    private void setExercise_weight() {
        testExercise.setExercise_weight(exercise_weightReplacement);
        float testVariable = testExercise.getExercise_weight();
        assertTrue(testVariable != exercise_weight);
        assertTrue(testVariable == exercise_weightReplacement);
    }
    private void setExercise_workout_id() {
        testExercise.setExercise_workout_id(exercise_workout_idReplacement);
        String testVariable = testExercise.getExercise_workout_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_workout_id);
        assertTrue(testVariable == exercise_workout_idReplacement);
    }
    private void setExercise_programme_id() {
        testExercise.setExercise_programme_id(exercise_programme_idReplacement);
        String testVariable = testExercise.getExercise_programme_id();
        assertTrue(testVariable != null);
        assertTrue(testVariable != exercise_programme_id);
        assertTrue(testVariable == exercise_programme_idReplacement);
    }
}