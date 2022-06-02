package zxc.studio.vpt.Factories;

import java.util.Date;

import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.utilities.DateFunctions;

public class WorkoutFactory {

    static public Workout newWorkout(String username, Date dateFor){
        Workout newWorkout = new Workout(username,username, DateFunctions.getBaselineCompletedDate(),dateFor,0,0,0,0,"Blank","Programme ID");
        return newWorkout;
    }
}
