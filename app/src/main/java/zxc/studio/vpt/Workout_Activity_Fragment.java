package zxc.studio.vpt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import zxc.studio.vpt.adapters.WorkoutRecyclerAdapter;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.ui.login.LoginActivity;
import zxc.studio.vpt.utilities.ItemDeco;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Workout_Activity_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Workout_Activity_Fragment extends Fragment implements WorkoutRecyclerAdapter.OnNoteListenerWorkout, View.OnClickListener {

    private static final String TAG = "workout_activity_fragt";

    private RecyclerView mRecyclerView;
    private Button mMove;
    private ArrayList<Workout> mWorkouts = new ArrayList<>();
    private WorkoutRecyclerAdapter mWorkoutAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String username = "Byron";
    private CollectionReference workoutsRef = db.collection("Notebook").document(username).collection("workouts");

    public Workout_Activity_Fragment() {
        // Required empty public constructor
    }

    public static Workout_Activity_Fragment newInstance(String param1, String param2) {
        Workout_Activity_Fragment fragment = new Workout_Activity_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWorkouts.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout__activity_, container, false);
        userTest();
        final SharedPreferences userDetails = this.getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        mRecyclerView = view.findViewById(R.id.recyclerView_workouts_fragment);
        initRecyclerView();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d(TAG, "onCreateView: user exists");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            username = user.getUid();
            Log.d(TAG, "onCreateView: " + username);
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
        return view;
    }



    private void userTest() {
//        if (FirebaseAuth.getInstance().getCurrentUser() == null){
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//        }
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
        Log.d(TAG, "findWorkouts: " + startdate);
        Log.d(TAG, "findWorkouts: " + enddate);
        Log.d(TAG, "findWorkouts: " + workoutsRef.getPath());
        workoutsRef.whereGreaterThan("workout_dateFor",startdate).whereLessThan("workout_dateFor",enddate).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: ");
            }
        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Workout workout = documentSnapshot.toObject(Workout.class);
                    workout.setWorkout_id(documentSnapshot.getId());
                    insertWorkouts(workout);
                    Log.d(TAG, "onSuccess for workout: " + workout);
                }
                missingWorkoutDates(startdate);
                sortArrayList();
            }
        });
    }

    private Date incrementDateByOne(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date nextDate = c.getTime();
        return nextDate;
    }

    private void missingWorkoutDates(Date longStartDate){
        Date monday = incrementDateByOne(longStartDate);
        Date tuesday = incrementDateByOne(monday);
        Date wednesday = incrementDateByOne(tuesday);
        Date thursday = incrementDateByOne(wednesday);
        Date friday = incrementDateByOne(thursday);
        Date saturday = incrementDateByOne(friday);
        Date sunday = incrementDateByOne(saturday);
        ArrayList<String> dates = new ArrayList<>();
        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",monday).toString());
        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",tuesday).toString());
        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",wednesday).toString());
        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",thursday).toString());
        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",friday).toString());
        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",saturday).toString());
        dates.add(android.text.format.DateFormat.format("yyyy-MM-dd",sunday).toString());
        ArrayList<String> existingDates = new ArrayList<>();
        for (int i = 0;i<mWorkouts.size();i++){
            Date longdate = mWorkouts.get(i).getWorkout_dateFor();
            Log.d(TAG, "missingWorkoutDates: " + longdate);
            String date = (android.text.format.DateFormat.format("yyyy-MM-dd",longdate).toString());
            for (int x = 0;x<7;x++) {
                if (date.equals(dates.get(x))) {
                    existingDates.add(date);
                }
            }
        }
        for (int i = 0;i<existingDates.size();i++) {
            dates.remove(existingDates.get(i));
        }
        addMissingWorkouts(dates);
    }

    private void addMissingWorkouts(ArrayList dates){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date completedDate = c.getTime();
        Date date = c.getTime();
        for (int i = 0;i<dates.size();i++){
            String shortDate = dates.get(i).toString();
            try {
                date = format.parse(shortDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Workout workout = new Workout();
            workout.setWorkout_athlete_id(username);
            workout.setWorkout_coach_id(username);
            workout.setWorkout_completedDate(completedDate);
            workout.setWorkout_dateFor(date);
            workout.setWorkout_food(0);
            workout.setWorkout_mood(0);
            workout.setWorkout_sleep(0);
            workout.setWorkout_weight(0);
            workout.setWorkout_id("Blank");
            workout.setWorkout_programme_id("Programme ID");
            insertWorkouts(workout);
        }
    }

    private void insertWorkouts(Workout mWorkout){
        mWorkouts.add(mWorkout);
    }

    private void sortArrayList() {
        Collections.sort(mWorkouts, new Comparator<Workout>() {
            @Override
            public int compare(Workout o1, Workout o2) {
                return o1.getWorkout_dateFor().compareTo(o2.getWorkout_dateFor());
            }
        });
        mWorkoutAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ItemDeco itemDeco=new ItemDeco(20);
        mRecyclerView.addItemDecoration(itemDeco);
        mWorkoutAdapter = new WorkoutRecyclerAdapter(mWorkouts,this);
        mRecyclerView.setAdapter(mWorkoutAdapter);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_workout_activity_fragment:{
                NavController navController = Navigation.findNavController(getActivity(), R.id.frag);
                navController.navigate(R.id.action_workout_Activity_Fragment_to_individual_workout_activity);
            }
        }
    }

    @Override
    public void onNoteClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("message", "From Activity");
        bundle.putParcelable("selected_workout",mWorkouts.get(position));
        NavController navController = Navigation.findNavController(getActivity(), R.id.frag);
        navController.navigate(R.id.action_workout_Activity_Fragment_to_individual_workout_activity,bundle);
    }
}