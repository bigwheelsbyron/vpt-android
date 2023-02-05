package zxc.studio.vpt;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import zxc.studio.vpt.API.FirebaseAPI;
import zxc.studio.vpt.Factories.ExerciseFactory;
import zxc.studio.vpt.adapters.ExerciseRecyclerAdapter;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.utilities.DateFunctions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link individual_workout_activity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class individual_workout_activity extends Fragment implements ExerciseRecyclerAdapter.OnNoteListener,View.OnClickListener{

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private static ArrayList<Exercise> mExercise = new ArrayList<>();
    private static ExerciseRecyclerAdapter mExerciseAdapter;
    private Button finishWorkoutButton;
    private Button backButton;
    private Button addNewExerciseButton;
    private TextView mCoach_Notes;
    private static Workout mWorkout;
    private TextView noexercises;
    private String username = "Byron";
    private String workoutVariable = " ";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static TextView mSleep;
    private static TextView mMood;
    private static TextView mCalories;
    private static TextView mWeight;
    private LinearLayout mWorkoutDetails;
    private CollectionReference workoutRef = db.collection("Notebook").document(username).collection("workouts").document(workoutVariable).collection("exercises");

    public individual_workout_activity() {
    }

    public static individual_workout_activity newInstance() {
        individual_workout_activity fragment = new individual_workout_activity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_individual_workout_activity, container, false);
        setIDS(view);
        setProperties();
        setListeners();
        initRecyclerView();
        return view;
    }

    private void setIDS(View view){
        backButton=view.findViewById(R.id.individualWorkoutActivity_button_workoutsReturn);
        recyclerView=view.findViewById(R.id.individualWorkoutActivity_recyclerView_exercises);
        finishWorkoutButton=view.findViewById(R.id.individualWorkoutActivity_button_finishWorkout);
        mCoach_Notes=view.findViewById(R.id.individualWorkoutActivity_textView_dateplaceholder);
        addNewExerciseButton=view.findViewById(R.id.individualWorkoutActivity_button_addNewExercise);
        noexercises=view.findViewById(R.id.individualWorkoutActivity_textView_noExercises);
        mMood=view.findViewById(R.id.individualWorkoutActivity_textView_moodValue);
        mWeight=view.findViewById(R.id.individualWorkoutActivity_textView_weightValue);
        mSleep=view.findViewById(R.id.individualWorkoutActivity_textView_sleepValue);
        mCalories=view.findViewById(R.id.individualWorkoutActivity_textView_caloriesValue);
        mWorkoutDetails=view.findViewById(R.id.individualWorkoutActivity_linearLayout_workoutDetails);
    }

    private void setProperties(){
        if (firebaseSignedIn()){
            setUserDetails();
            setWorkoutDetails();
        } else {
        }
    }

    private boolean firebaseSignedIn(){
        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    private void setUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getUid();
    }

    private void setWorkoutDetails(){
        setWorkout();
        setDate();
        setWorkoutProperties();
        loadWorkout();
    }

    private void setDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.applyPattern("E MMM dd yyyy");
        String a = sdf.format(mWorkout.getWorkout_dateFor());
        mCoach_Notes.setText(a);
    }

    private void setWorkout(){
        Workout argWorkout = getArguments().getParcelable("selected_workout");
        mWorkout = argWorkout;
        workoutVariable = mWorkout.getWorkout_id();
        workoutRef = db.collection("users").document(username).collection("workouts").document(workoutVariable).collection("Exercises");
    }

    private void setWorkoutProperties(){
        mMood.setText(String.valueOf(mWorkout.getWorkout_mood()));
        mSleep.setText(String.valueOf(mWorkout.getWorkout_sleep()));
        mWeight.setText(String.valueOf(mWorkout.getWorkout_weight()));
        mCalories.setText(String.valueOf(mWorkout.getWorkout_food()));
        if (!workoutVariable.equals("Blank")){
            noexercises.setAlpha(0);
        }
    }

    public void loadWorkout(){
        mExercise.clear();
        Log.d(TAG, "loadWorkout: " + workoutRef.getPath());
        workoutRef.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: loadworkouts failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                addExercisesFromFirebase(queryDocumentSnapshots);
            }
        });
    }

    private void addExercisesFromFirebase(QuerySnapshot queryDocumentSnapshots){
        Log.d(TAG, "addExercisesFromFirebase: ");
        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
            Exercise exercise = documentSnapshot.toObject(Exercise.class);
            Log.d(TAG, "addExercisesFromFirebase: " + exercise);
            insertExercise(exercise);
        }
        sortArrayList();
    }

    private void sortArrayList() {
        Collections.sort(mExercise, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise o1, Exercise o2) {
                return Integer.compare(o1.getExercise_sequence(), o2.getExercise_sequence());
            }
        });
        Log.d(TAG, "sortArrayList: " + mExercise);
        mExerciseAdapter.notifyDataSetChanged();
    }

    private void insertExercise(Exercise exercise){
        mExercise.add(exercise);
    }

    private void setListeners(){
        mWorkoutDetails.setOnClickListener(this);
        finishWorkoutButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        addNewExerciseButton.setOnClickListener(this);
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        mExerciseAdapter = new ExerciseRecyclerAdapter(mExercise,this);
        recyclerView.setAdapter(mExerciseAdapter);
    }

    public void onChangedCheckBox(int position, boolean isChecked) {
        mExercise.get(position).setExercise_completedBoolean(isChecked);
        if (isChecked){
            Date completedDate = DateFunctions.getCurrentDateAndTime();
            mExercise.get(position).setExercise_completedDate(completedDate);
        }
        else {
            Date baselineCompletedDate = DateFunctions.getBaselineCompletedDate();
            mExercise.get(position).setExercise_completedDate(baselineCompletedDate);
        }
    }

    public static void updateMainActivityFragmentExercise(Exercise exercise){
        mExercise.get(exercise.getExercise_sequence()-1).setExercise_reps(exercise.getExercise_reps());
        mExercise.get(exercise.getExercise_sequence()-1).setExercise_weight(exercise.getExercise_weight());
        mExercise.get(exercise.getExercise_sequence()-1).setExercise_note(exercise.getExercise_note());
        mExercise.get(exercise.getExercise_sequence()-1).setExercise_rpe(exercise.getExercise_rpe());
        mExercise.get(exercise.getExercise_sequence()-1).setExercise_name(exercise.getExercise_name());
        mExerciseAdapter.notifyDataSetChanged();
    }

    public static void updateMainActivityWorkout(String sleep, String weight, String mood, String calories){
        mWorkout.setWorkout_sleep(Float.parseFloat(sleep));
        mWorkout.setWorkout_mood(Integer.parseInt(mood));
        mWorkout.setWorkout_food(Integer.parseInt(calories));
        mWorkout.setWorkout_weight(Float.parseFloat(weight));
        mSleep.setText(sleep);
        mMood.setText(mood);
        mWeight.setText(weight);
        mCalories.setText(calories);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.individualWorkoutActivity_button_addNewExercise:{
                addNewExercise();
                break;
            }
            case R.id.individualWorkoutActivity_button_finishWorkout:{
                finishWorkout();
                break;
            }
            case R.id.individualWorkoutActivity_button_workoutsReturn:{
                moveToWorkoutFragment();
                break;
            }
            case R.id.individualWorkoutActivity_linearLayout_workoutDetailsAndDate:{
                Intent intent = new Intent(getContext(),workoutoutDetails.class);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.applyPattern("E MMM dd yyyy");
                String a = sdf.format(mWorkout.getWorkout_dateFor());
                intent.putExtra("workout_date",a);
                intent.putExtra("workout_weight",mWorkout.getWorkout_weight());
                intent.putExtra("workout_sleep",mWorkout.getWorkout_sleep());
                intent.putExtra("workout_food",mWorkout.getWorkout_food());
                intent.putExtra("workout_mood",mWorkout.getWorkout_mood());
                startActivity(intent);
                break;
            }
        }
    }

    private void addNewExercise(){
        newExerciseToArray();
        noexercises.setAlpha(0);
    }

    private void newExerciseToArray(){
        int sequence = getSequence();
        Exercise exercise = ExerciseFactory.newExercise(username,sequence,mWorkout.getWorkout_id(),mWorkout.getWorkout_programme_id());
        insertExercise(exercise);
        mExerciseAdapter.notifyDataSetChanged();
    }

    private int getSequence(){
        int sequence = 1;
        if (mExercise.size() > 0){
            Exercise lastExercise = mExercise.get(mExercise.size()-1);
            int lastSeq = lastExercise.getExercise_sequence();
            sequence = lastSeq+1;
        }
        return sequence;
    }

    private void finishWorkout(){
        mWorkout.setWorkout_completedDate(DateFunctions.getCurrentDateAndTime());
        updateFirebase();
        moveToWorkoutFragment();
    }

    private void updateFirebase(){
        if (hasAWorkoutID()){
            FirebaseAPI.updateExistingWorkout(mExercise,mWorkout);
        } else {
            FirebaseAPI.createNewWorkout(mExercise,mWorkout);
        }
    }

    private void moveToWorkoutFragment(){
        NavController navController = Navigation.findNavController(getActivity(), R.id.mainDetailsFragment);
        navController.navigate(R.id.action_individual_workout_activity_to_workout_Activity_Fragment);
    }

    private boolean hasAWorkoutID(){
        return (!mWorkout.getWorkout_id().equals("Blank"));
    }

    private void statsUpdate(Exercise exercise){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yy-MM");
        String yearMonth = format.format(c);
        Log.d(TAG, "statsUpdate: " + yearMonth);
        if (exercise.getExercise_completedBoolean()){
            if (exercise.getExercise_classification().equals("Strength")){
                Map<String, Object> setData = new HashMap<>();
                SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSSS");
                String date = dateFormater.format(exercise.getExercise_completedDate());
                String difficult = String.valueOf(exercise.getExercise_difficulty());
                String reps = String.valueOf(exercise.getExercise_reps());
                String weight = String.valueOf(exercise.getExercise_weight());
                String rpe = String.valueOf(exercise.getExercise_rpe());
                String uom = exercise.getExercise_uom();
                String data = date.concat(".").concat(difficult).concat(".").concat(reps).concat(".").concat(weight).concat(".").concat(rpe).concat(".").concat(uom);
                Log.d(TAG, "updateFirebase: " + difficult);
                setData.put(exercise.getExercise_id(),data);
                DocumentReference fbref = db.collection("users").document(username).collection("stats").document(exercise.getExercise_classification()).collection(exercise.getExercise_name()).document(yearMonth);
                fbref.set(setData, SetOptions.merge());
//                RecordsCheckAndUpdate(exercise);
            }
        }
    }

    private void RecordsCheckAndUpdate(Exercise exercise){
        DocumentReference fbref = db.collection("users").document(username).collection("stats").document(exercise.getExercise_classification()).collection(exercise.getExercise_name()).document("Records");
        fbref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getData() != null){
                    HashMap historicalData = (HashMap) documentSnapshot.getData().get("Historical");
                    LinkedHashMap historical = OrderDictionariesDates(historicalData);
                    Integer reps = exercise.getExercise_reps();
                    LinkedHashMap prevHistData = (LinkedHashMap) historical.get(String.valueOf(reps));
                    Log.d(TAG, "onSuccess: " + historical);
                    Log.d(TAG, "onSuccess: " + prevHistData);
                    float weight = exercise.getExercise_weight();
                    int index = prevHistData.values().size()-1;
                    long prevWeight = (long) prevHistData.values().toArray()[index];
                    Log.d(TAG, "onSuccess: " + historical);
                    if (weight>prevWeight){
                        Log.d(TAG, "onSuccess: bigger");
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String date = format.format(c);
                        HashMap newData = new HashMap<>();
                        newData.put(date,weight);
                        prevHistData.put(date,weight);
                        historical.put(String.valueOf(reps),prevHistData);
                        Log.d(TAG, "onSuccess: " + historical);
                        Log.d(TAG, "onSuccess: " + prevHistData);
                        fbref.update("Historical",historical);
                    }
                } else {
                    Log.d(TAG, "onFailure: no records");
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String date = format.format(c);
                    LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>> orderedMap = new LinkedHashMap<>();
                    LinkedHashMap<String, LinkedHashMap<String, Integer>> historical = new LinkedHashMap<>();
                    LinkedHashMap<String, LinkedHashMap<String, Integer>> calculated = new LinkedHashMap<>();
                    LinkedHashMap<String, Integer> dateValue = new LinkedHashMap<>();
                    dateValue.put(date,0);
                    for (int i = 1;i<16;i++){
                        historical.put(String.valueOf(i),dateValue);
                        calculated.put(String.valueOf(i),dateValue);
                    }
                    orderedMap.put("Historical",historical);
                    orderedMap.put("Calculated",calculated);
                    fbref.set(orderedMap);
                }
            }
        });
    }

    private LinkedHashMap<String,LinkedHashMap<String,Integer>> OrderDictionariesDates(HashMap<String, Object> dict){
        TreeMap numbersOrdered = OrderDictionariesNumbers(dict);
        LinkedHashMap finalMap = new LinkedHashMap<String,LinkedHashMap<String,Integer>>();
        for (int i = 1;i<16;i++){
            TreeMap<Date,String> sorted = new TreeMap<>();
            LinkedHashMap<String, Long> orderedMap = new LinkedHashMap<>();
            HashMap dataSet = (HashMap) numbersOrdered.get(i);
            Log.d(TAG, "OrderDictionariesDates: " + dataSet);
            for ( Object key : dataSet.keySet() ) {
                Date date = stringToDate(String.valueOf(key), "yyyy/MM/dd");
                sorted.put(date, String.valueOf(key));
            }
            for ( Date key: sorted.keySet()){
                String date = sorted.get(key);
                Long value = (Long) dataSet.get(sorted.get(key));
                orderedMap.put(date,value);

            }
            finalMap.put(String.valueOf(i),orderedMap);
        }
        return finalMap;
    }

    private TreeMap<Integer,Object> OrderDictionariesNumbers(HashMap<String, Object> dict){
        TreeMap<Integer,Object> sorted = new TreeMap<>();
        for ( String key : dict.keySet() ) {
            sorted.put(Integer.valueOf(key),dict.get(key));
        }
        return sorted;
    }

    private Date stringToDate(String aDate,String aFormat) {
        if(aDate==null) {
            return null;
        } else {
            StringBuilder str = new StringBuilder(aDate);
            str.setCharAt(4,'/');
            str.setCharAt(7,'/');
            ParsePosition pos = new ParsePosition(0);
            SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
            Date stringDate = simpledateformat.parse(str.toString(), pos);
            return stringDate;
        }
    }

    private void AddRecords(){
        HashMap historical = new HashMap();
        HashMap calculated = new HashMap();
        HashMap data = new HashMap();
        data.put("2021/07/07",100);
        for (int i = 1; i<16;i++){
            historical.put(String.valueOf(i),data);
            calculated.put(String.valueOf(i),data);
        }
        HashMap Records = new HashMap();
        Records.put("Historical",historical);
        Records.put("Calculated",calculated);
        Log.d(TAG, "AddRecords: " +historical);
        Log.d(TAG, "AddRecords: " +calculated);
        DocumentReference fbref = db.collection("users").document(username).collection("stats").document("Strength").collection("New Exercise Name").document("Records");
        Log.d(TAG, "AddRecords: " + fbref.getPath());
        fbref.set(Records);
    }

    private Date getCompletedDate(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2000);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date completedDate = c.getTime();
        return completedDate;
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getContext(),NotesActivity.class);
        intent.putExtra("selected_exercise",mExercise.get(position));
        startActivity(intent);
    }

}