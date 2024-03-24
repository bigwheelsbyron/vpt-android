package zxc.studio.vpt.API;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import zxc.studio.vpt.R;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Suggestion;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.sign_up;
import zxc.studio.vpt.ui.login.LoginActivity;


public class FirebaseAPI {

    private static final String TAG = "firebaseAPI";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    public void login(String username, String password, LoginActivity context){
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    context.successfulLogin();
                } else {
                    context.unsuccessfulLogin();
                }
            }
        });
    }

    public void forgotPassword(String email, LoginActivity context){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Email sent", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, R.string.LoginActivity_TOAST_invalidEmail, Toast.LENGTH_LONG).show();
            }
        });
    }

    public Task<DocumentSnapshot> getTOCs() {
        return FirebaseFirestore.getInstance()
                .collection("community")
                .document("tos")
                .get();
    }


    public Boolean[] signup(final String email, final String password, final String first, final String last){
        Boolean[] rValue = {null, false};
        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            rValue[0] = signUpUserSuccess(first,last,email);
                            rValue[1] = true;
                        } else {
                            signUpUserFailure(task);
                        }
                    }
                });
        return rValue;
    }

    private String signUpUserSuccess(String first, String last, String email){
        newFirebaseDocumentUser(first,last,email);
    }

    private void signUpUserFailure(Task<AuthResult> task){
        Log.w(TAG, "signupithEmail:failure", task.getException());
    }





    private String newFirebaseDocumentUser(String first, String last, String email){
        FirebaseUser user = mAuth.getCurrentUser();
        Map<String,Object> userinfo = new HashMap<>();
        userinfo.put("Name_First",first);
        userinfo.put("Name_Last",last);
        userinfo.put("email",email);
        userinfo.put("uid",mAuth.getUid());
        db.collection("users").document(user.getUid()).set(userinfo);
        return uid;
    }





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
