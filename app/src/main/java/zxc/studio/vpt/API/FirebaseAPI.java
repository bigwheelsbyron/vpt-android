package zxc.studio.vpt.API;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Suggestion;
import zxc.studio.vpt.models.User;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.utilities.DateFunctions;


public class FirebaseAPI {

    private static final String TAG = "firebaseAPI";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    static public void updateExistingWorkout(ArrayList<Exercise> mExercise, Workout mWorkout){
        String username = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference workoutRef = db.collection("users").document(username).collection("workouts").document(mWorkout.getWorkout_id());
        firebaseUpdateExercises(mExercise, workoutRef);
        firebaseUpdateWorkout(mWorkout,workoutRef);
    }

    static public void createNewWorkout(ArrayList<Exercise> mExercise, Workout mWorkout){
        String username = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference newWorkoutRef = db.collection("users").document(username).collection("workouts").document();
        mExercise = updateExercisesWorkoutID(mExercise, newWorkoutRef.getId());
        mWorkout = updateWorkoutID(mWorkout,newWorkoutRef.getId());
        firebaseUpdateExercises(mExercise, newWorkoutRef);
        firebaseUpdateWorkout(mWorkout,newWorkoutRef);
    }

    static private void firebaseUpdateExercises(ArrayList<Exercise> mExercise,DocumentReference workoutRef){
        for (int i = 0; i < mExercise.size(); i++){
            Exercise exercise = mExercise.get(i);
            if (isANewExercise(exercise)){
                firebaseCommitNewExercise(exercise, workoutRef);
            } else {
                firebaseCommitExistingExercise(exercise, workoutRef);
            }
        }
    }

    static private boolean isANewExercise(Exercise exercise){
        return exercise.getExercise_id().equals("Blank");
    }

    static private void firebaseCommitNewExercise(Exercise exercise, DocumentReference workoutRef){
        DocumentReference docRef = workoutRef.collection("Exercises").document();
        exercise.setExercise_workout_id(workoutRef.getId());
        exercise.setExercise_id(docRef.getId());
        docRef.set(exercise);
    }

    static private void firebaseCommitExistingExercise(Exercise exercise, DocumentReference workoutRef){
        DocumentReference docRef = workoutRef.collection("Exercises").document(exercise.getExercise_id());
        docRef.update(
                "exercise_athlete_id",exercise.getExercise_athlete_id(),
                "exercise_coach_id",exercise.getExercise_coach_id(),
                "exercise_classification", exercise.getExercise_classification(),
                "exercise_completedBoolean", exercise.getExercise_completedBoolean(),
                "exercise_completedDate", exercise.getExercise_completedDate(),
                "exercise_difficulty",exercise.getExercise_difficulty(),
                "exercise_distance",exercise.getExercise_distance(),
                "exercise_duration",exercise.getExercise_duration(),
                "exercise_id",exercise.getExercise_id(),
                "exercise_name",exercise.getExercise_name(),
                "exercise_note",exercise.getExercise_note(),
                "exercise_reps",exercise.getExercise_reps(),
                "exercise_rpe",exercise.getExercise_rpe(),
                "exercise_sequence",exercise.getExercise_sequence(),
                "exercise_uom",exercise.getExercise_uom(),
                "exercise_weight",exercise.getExercise_weight(),
                "exercise_workout_id",exercise.getExercise_workout_id(),
                "exercise_programme_id",exercise.getExercise_programme_id()
        );
    }

    static private void firebaseUpdateWorkout(Workout mWorkout, DocumentReference workoutRef){
        Map<String, Object> completedData = new HashMap<>();
        completedData.put("workout_athlete_id",mWorkout.getWorkout_athlete_id());
        completedData.put("workout_coach_id",mWorkout.getWorkout_coach_id());
        completedData.put("workout_completedDate",mWorkout.getWorkout_completedDate());
        completedData.put("workout_dateFor",mWorkout.getWorkout_dateFor());
        completedData.put("workout_food",mWorkout.getWorkout_food());
        completedData.put("workout_mood",mWorkout.getWorkout_mood());
        completedData.put("workout_sleep",mWorkout.getWorkout_sleep());
        completedData.put("workout_weight",mWorkout.getWorkout_weight());
        completedData.put("workout_id",mWorkout.getWorkout_id());
        completedData.put("workout_programme_id",mWorkout.getWorkout_programme_id());
        workoutRef.set(completedData, SetOptions.merge());
    }

    static private ArrayList<Exercise> updateExercisesWorkoutID(ArrayList<Exercise> mExercise, String workoutRefID){
        for (int i = 0; i < mExercise.size(); i++){
            Exercise exercise = mExercise.get(i);
            exercise.setExercise_workout_id(workoutRefID);
        }
        return mExercise;
    }

    static private Workout updateWorkoutID(Workout mWorkout, String workoutRefID){
        mWorkout.setWorkout_id(workoutRefID);
        return mWorkout;
    }

    static public void newSuggestion(Suggestion suggestion){
        DocumentReference newSuggestion =  db.collection("community").document("suggestion").collection("suggestions").document();
        newSuggestion.set(suggestion);
    }

    static public boolean authenticateUser(String username, String password){
        return true;
    }


}
