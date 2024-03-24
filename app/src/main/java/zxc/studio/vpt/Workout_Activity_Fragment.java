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

import zxc.studio.vpt.Factories.WorkoutFactory;
import zxc.studio.vpt.adapters.WorkoutRecyclerAdapter;
import zxc.studio.vpt.models.DateRange;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.ui.login.LoginActivity;
import zxc.studio.vpt.utilities.DateFunctions;
import zxc.studio.vpt.utilities.ItemDeco;

public class Workout_Activity_Fragment extends Fragment implements WorkoutRecyclerAdapter.OnNoteListenerWorkout, View.OnClickListener {

    private static final String TAG = "workout_activity_fragt";

    private RecyclerView recyclerView;
    private ArrayList<Workout> workoutsArray = new ArrayList<>();
    private WorkoutRecyclerAdapter workoutAdapter;
    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    private String usernameString = "Byron";
    private CollectionReference workoutsFirebaseRef = firebaseDB.collection("Notebook").document(usernameString).collection("workouts");

    public Workout_Activity_Fragment() {
        // Required empty public constructor
    }

    public static Workout_Activity_Fragment newInstance() {
        Workout_Activity_Fragment fragment = new Workout_Activity_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeWorkouts();
    }

    private void removeWorkouts(){
        workoutsArray.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout__activity_, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view){
        setIDS(view);
        signedInUser();
        hideLogout();
        setUpRecyclerView();
    }

    private void signedInUser() {
        if (firebaseSignedIn()){
            Log.d(TAG, "signedInUser: user signed in");
            getWorkouts();
        } else {
            Log.d(TAG, "signedInUser: no user signed in");
        }
//        TODO: Need to make like a trial account type set up
    }

    private void hideLogout(){
//        workout_activity activity = (workout_activity) getActivity();
//        activity.hideLogout();
        //todo: re hide logout
    }

    private boolean firebaseSignedIn(){
        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    private void setIDS(View view){
        recyclerView = view.findViewById(R.id.workoutsFragment_recyclerView);
    }

    private void setUpRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemDeco itemDeco=new ItemDeco(20);
        recyclerView.addItemDecoration(itemDeco);
        workoutAdapter = new WorkoutRecyclerAdapter(workoutsArray,this);
        recyclerView.setAdapter(workoutAdapter);
    }

    private void getWorkouts(){
        workoutsFirebaseRef = firebaseDB.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("workouts");
        DateRange dateRange = getWorkoutDateRange();
        dateRange = sundayCheck(dateRange);
        findWorkouts(dateRange);
    }

    private DateRange getWorkoutDateRange(){
        Calendar sundayOne = Calendar.getInstance();
        sundayOne.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Calendar sundayTwo = Calendar.getInstance();
        sundayTwo.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        sundayTwo.add(Calendar.DATE,7);
        DateRange dateRange = new DateRange(sundayOne.getTime(),sundayTwo.getTime());
        return dateRange;
    }

    private DateRange sundayCheck(DateRange dateRange){
        Date startRangeDate = dateRange.getStartingDate();
        Date endRangeDate = dateRange.getEndingDate();
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Log.d(TAG, "sundayCheck: before start" + dateRange.getStartingDate());
        Log.d(TAG, "sundayCheck: before end" + dateRange.getEndingDate());
        if (dayOfWeek == 1){
            c.setTime(startRangeDate);
            c.add(Calendar.DATE,-7);
            dateRange.setStartingDate(c.getTime());
            c.setTime(endRangeDate);
            c.add(Calendar.DATE,-7);
            dateRange.setEndingDate(c.getTime());
        } else {

        }
        Log.d(TAG, "sundayCheck: after start" + dateRange.getStartingDate());
        Log.d(TAG, "sundayCheck: after end" + dateRange.getEndingDate());
        return dateRange;
    }

    private void findWorkouts(DateRange dateRange){
        Date startDate = DateFunctions.dateAndTimeToDate(dateRange.getStartingDate());
        Date endDate = DateFunctions.dateAndTimeToDate(dateRange.getEndingDate());
        Log.d(TAG, "findWorkouts: startDate" + startDate);
        Log.d(TAG, "findWorkouts: endDate" + endDate);
        Log.d(TAG, "findWorkouts: " + workoutsFirebaseRef.getPath());
        workoutsFirebaseRef.whereGreaterThan("workout_dateFor",startDate).whereLessThan("workout_dateFor",endDate).get()
                .addOnFailureListener(new OnFailureListener() {
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
                            Log.d(TAG, "onSuccess for workout: " + workout.getWorkout_id());
                        }
                        missingWorkoutDates(startDate);
                        sortArrayList();
                    }
            }
            );
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
        Log.d(TAG, "missingWorkoutDates: " + workoutsArray.size());
        for (int i = 0;i<workoutsArray.size();i++){
            Date longdate = workoutsArray.get(i).getWorkout_dateFor();
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
        Date date = c.getTime();
        for (int i = 0;i<dates.size();i++){
            String shortDate = dates.get(i).toString();
            try {
                date = format.parse(shortDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Workout workout = WorkoutFactory.newWorkout(usernameString,date);
            insertWorkouts(workout);
        }
    }

    private void insertWorkouts(Workout mWorkout){
        workoutsArray.add(mWorkout);
    }

    private void sortArrayList() {
        Collections.sort(workoutsArray, new Comparator<Workout>() {
            @Override
            public int compare(Workout o1, Workout o2) {
                return o1.getWorkout_dateFor().compareTo(o2.getWorkout_dateFor());
            }
        });
        workoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.workoutsFragment_button_back:{
                NavController navController = Navigation.findNavController(getActivity(), R.id.mainDetailsFragment);
                navController.navigate(R.id.action_workout_Activity_Fragment_to_individual_workout_activity);
            }
        }
    }

    @Override
    public void onNoteClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("message", "From Activity");
        bundle.putParcelable("selected_workout",workoutsArray.get(position));
        NavController navController = Navigation.findNavController(getActivity(), R.id.mainDetailsFragment);
        navController.navigate(R.id.action_workout_Activity_Fragment_to_individual_workout_activity,bundle);
    }
}