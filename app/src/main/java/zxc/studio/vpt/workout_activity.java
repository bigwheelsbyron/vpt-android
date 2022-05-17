package zxc.studio.vpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import zxc.studio.vpt.adapters.WorkoutRecyclerAdapter;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.ui.login.LoginActivity;
import zxc.studio.vpt.utilities.ItemDeco;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class workout_activity extends AppCompatActivity implements WorkoutRecyclerAdapter.OnNoteListenerWorkout, View.OnClickListener {
    private static final String TAG = "workout_activity";

    //UI Components
//    private RecyclerView mRecyclerView;
//    private Button mMove;
//    private String backButtonVariable = "main";
    private View mSubtract;


    //Variables
    private ArrayList<Workout> mWorkouts = new ArrayList<>();
    private WorkoutRecyclerAdapter mWorkoutAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String username = "Byron";
    private Button logoutButton;
    private TextView mWelcomeMessage;
    private CollectionReference workoutsRef = db.collection("Notebook").document(username).collection("workouts");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mWorkouts.clear();
        final SharedPreferences userDetails = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        userTest();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_activity);
//        mRecyclerView = findViewById(R.id.recyclerView_workouts);
        mSubtract = findViewById(R.id.background_subtract);
        logoutButton = findViewById(R.id.logout_button_main);
        logoutButton.setOnClickListener(this);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        Log.d(TAG, "onCreate: " + dpHeight);
        Log.d(TAG, "onCreate: " + dpWidth);
        mSubtract.getLayoutParams().width = (int) dpHeight*5;
        mSubtract.getLayoutParams().height = (int) Math.round(dpHeight/2);
        initRecyclerView();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this,R.id.frag);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        setTitle("VPT");
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            username = user.getUid();
            workoutsRef = db.collection("users").document(username).collection("workouts");
            Calendar sundayOne = Calendar.getInstance();
            sundayOne.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            Calendar sundayTwo = Calendar.getInstance();
            sundayTwo.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            sundayTwo.add(Calendar.DATE,7);
            sundayCheck(sundayOne.getTime(),sundayTwo.getTime());
            db.collection("users").document(username).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d(TAG, "onSuccess: " + documentSnapshot.getData());
                    SharedPreferences.Editor editor = userDetails.edit();
                    editor.putString("Name_First", (String) documentSnapshot.getData().get("Name_First"));
                    editor.putString("uid", (String) documentSnapshot.getData().get("uid"));
                    editor.putString("Name_Last", (String) documentSnapshot.getData().get("Name_Last"));
                    editor.putString("coach", (String) documentSnapshot.getData().get("coach"));
                    editor.putString("email", (String) documentSnapshot.getData().get("email"));
                    editor.putString("coach_First", (String) documentSnapshot.getData().get("coach_Name_First"));
                    editor.putString("coach_Last", (String) documentSnapshot.getData().get("coach_Name_Last"));
                    username = (String) documentSnapshot.getData().get("uid");
                    editor.commit();
                }
            });
        }
        else {
            Log.d(TAG, "onCreate: fire user doesn't exist");
        }
    }

    private void userTest() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void sundayCheck(final Date startdate, final Date enddate){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1){
            c.setTime(startdate);
            c.add(Calendar.DATE,-7);
            Date startDate = c.getTime();
            c.setTime(enddate);
            c.add(Calendar.DATE,-7);
            Date endDate = c.getTime();
            findWorkouts(startDate, endDate);
        } else {
            findWorkouts(startdate, enddate);
        }
    }

    public void findWorkouts(final Date startdate, final Date enddate){
//        Log.d(TAG, "findWorkouts: " + startdate);
//        Log.d(TAG, "findWorkouts: " + enddate);
//        workoutsRef.whereGreaterThan("date",startdate).whereLessThan("date",enddate).get().addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: ");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
//                    Workout workout = documentSnapshot.toObject(Workout.class);
//                    workout.setWorkout_ID(documentSnapshot.getId());
//                    insertWorkouts(workout);
//                }
//                missingWorkoutDates(startdate);
//                sortArrayList();
//            }
//        });
    }

    private Date incrementDateByOne(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date nextDate = c.getTime();
        return nextDate;
    }

    private void missingWorkoutDates(Date longStartDate){
//        Date monday = incrementDateByOne(longStartDate);
//        Date tuesday = incrementDateByOne(monday);
//        Date wednesday = incrementDateByOne(tuesday);
//        Date thursday = incrementDateByOne(wednesday);
//        Date friday = incrementDateByOne(thursday);
//        Date saturday = incrementDateByOne(friday);
//        Date sunday = incrementDateByOne(saturday);
//        ArrayList<String> dates = new ArrayList<>();
//        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",monday).toString());
//        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",tuesday).toString());
//        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",wednesday).toString());
//        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",thursday).toString());
//        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",friday).toString());
//        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",saturday).toString());
//        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",sunday).toString());
//        ArrayList<String> existingDates = new ArrayList<>();
//        for (int i = 0;i<mWorkouts.size();i++){
//            Date longdate = mWorkouts.get(i).getDate();
//            String date = (android.text.format.DateFormat.format("yyyy-MM-dd",longdate).toString());
//            for (int x = 0;x<7;x++) {
//                if (date.equals(dates.get(x))) {
//                    existingDates.add(date);
//                }
//            }
//        }
//        for (int i = 0;i<existingDates.size();i++) {
//            dates.remove(existingDates.get(i));
//        }
//        addMissingWorkouts(dates);
    }

    private void addMissingWorkouts(ArrayList dates){
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, 2000);
//        c.set(Calendar.MONTH, Calendar.JANUARY);
//        c.set(Calendar.DAY_OF_MONTH, 1);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date completedDate = c.getTime();
//        Date date = null;
//        for (int i = 0;i<dates.size();i++){
//            String shortDate = dates.get(i).toString();
//            try {
//                date = format.parse(shortDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Workout workout = new Workout(date, "Blank", username, 0, completedDate, 0, 0, 0,username);
//            insertWorkouts(workout);
//        }
    }

    private void insertWorkouts(Workout mWorkout){
        mWorkouts.add(mWorkout);
    }

    private void sortArrayList() {
//        Collections.sort(mWorkouts, new Comparator<Workout>() {
//            @Override
//            public int compare(Workout o1, Workout o2) {
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        });
////        mWorkoutAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        ItemDeco itemDeco=new ItemDeco(20);
//        mRecyclerView.addItemDecoration(itemDeco);
//        mWorkoutAdapter = new WorkoutRecyclerAdapter(mWorkouts,this);
//        mRecyclerView.setAdapter(mWorkoutAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("selected_workout",mWorkouts.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.logout_button_main: {
                Log.d(TAG, "onClick: loggout button ");
                FirebaseAuth.getInstance().signOut();
                userTest();
                break;
            }
        }
    }

    private void statsUpdate(Exercise exercise){
//        if (exercise.getCompleteBinary()){
//            if (exercise.getClassification().equals("Strength")){
//                Map<String, Object> setData = new HashMap<>();
//                SimpleDateFormat dateFormater = new SimpleDateFormat("dd           /MM/yyyy hh:mm:ss.SSSS");
//                String date = dateFormater.format(exercise.getCompletedDate());
//                String difficult = String.valueOf(exercise.getDifficulty());
//                String reps = String.valueOf(exercise.getReps());
//                String weight = String.valueOf(exercise.getWeight());
//                String rpe = String.valueOf(exercise.getRPE());
//                String data = date.concat(".").concat(difficult).concat(".").concat(reps).concat(".").concat(weight).concat(".").concat(rpe);
//                Log.d(TAG, "updateFirebase: " + difficult);
//                setData.put(exercise.getExerciseID(),data);
//                DocumentReference fbref = db.collection("users").document(username).collection("stats").document(exercise.getClassification()).collection(exercise.getExerciseName()).document(exercise.getExerciseName());
//                Log.d(TAG, "statsUpdate: " + fbref.getPath());
//                fbref.set(setData, SetOptions.merge());
//            }
//        }
    }

    private Date getCompletedDate(int day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, day+1);
        Date completedDate = c.getTime();
        return completedDate;
    }

}
