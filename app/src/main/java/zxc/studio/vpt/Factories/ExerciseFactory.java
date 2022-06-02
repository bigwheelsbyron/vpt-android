package zxc.studio.vpt.Factories;

import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.utilities.DateFunctions;

public class ExerciseFactory {

    public static Exercise newExercise(String username,int sequence,String workoutID,String programmeID) {
        Exercise exercise = new Exercise(username, username, "Stength", false, DateFunctions.getBaselineCompletedDate(), 0, 0, 0, "Blank", "New Exercise", "New Note", 0, 0, sequence, "kg", 0, workoutID, programmeID);
        return exercise;
    }
}
