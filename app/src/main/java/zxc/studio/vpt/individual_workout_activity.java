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

import zxc.studio.vpt.adapters.ExerciseRecyclerAdapter;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Workout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link individual_workout_activity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class individual_workout_activity extends Fragment implements ExerciseRecyclerAdapter.OnNoteListener,View.OnClickListener{

    private static final String TAG = "MainActivity";

    //UI Components
    private RecyclerView mRecyclerView;

    //Variables
    private static ArrayList<Exercise> mExercise = new ArrayList<>();
    private static ExerciseRecyclerAdapter mExerciseAdapter;
    private Button mFinishWorkout;
    private Button mBack;
    private Button addNew;
    private TextView mCoach_Notes;
    private static Workout mWorkout;
    private ImageView vptMenu;
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
        // Required empty public constructor
    }

    public static individual_workout_activity newInstance(String param1, String param2) {
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_individual_workout_activity, container, false);
        mBack=view.findViewById(R.id.button_workout_activity_fragment);
        mRecyclerView=view.findViewById(R.id.recyclerView_exercises);
        mFinishWorkout=view.findViewById(R.id.button_finish_workout);
        mCoach_Notes=view.findViewById(R.id.coaches_notes);
        addNew=view.findViewById(R.id.button_add_new_exercise_fragment);
        noexercises=view.findViewById(R.id.noexercises_text);
        mMood=view.findViewById(R.id.textview_Mood);
        mWeight=view.findViewById(R.id.textview_Weight);
        mSleep=view.findViewById(R.id.textview_Sleep);
        mCalories=view.findViewById(R.id.textview_Calories);
        mWorkoutDetails=view.findViewById(R.id.layout_details);
//        mBack.setOnClickListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getUid();
        initRecyclerView();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (getIncomingIntent()){
                setNewWorkoutProperties();
            }
            else {
                setWorkoutProperties();
                setListeners();
                loadWorkout();
            }
        }        else{
            if (getIncomingIntent() == false){
                Log.d(TAG, "onCreate: fire user doesn't exist");
                setWorkoutProperties();
                setListeners();
                loadWorkout();
            } else{
                setNewWorkoutProperties();
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.applyPattern("E MMM dd yyyy");
        String a = sdf.format(mWorkout.getWorkout_dateFor());
        mCoach_Notes.setText(a);





        return view;
    }

    public void loadWorkout(){
        mExercise.clear();
        workoutRef.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: loadworkouts failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Exercise exercise = documentSnapshot.toObject(Exercise.class);
                    insertExercise(exercise);
                }
                sortArrayList();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e);
            }
        });
    }

    private void sortArrayList() {
        Log.d(TAG, "sortArrayList: ");
        Collections.sort(mExercise, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise o1, Exercise o2) {
                return Integer.compare(o1.getExercise_sequence(), o2.getExercise_sequence());
            }
        });
        mExerciseAdapter.notifyDataSetChanged();
    }

    private void insertExercise(Exercise exercise){
        mExercise.add(exercise);
    }

    private boolean getIncomingIntent(){
        Workout argWorkout = getArguments().getParcelable("selected_workout");
        if (argWorkout != null){
            mWorkout = argWorkout;
            return false;
        }
        return true;
    }

    private void setWorkoutProperties(){
        workoutVariable = mWorkout.getWorkout_id();
        mMood.setText(String.valueOf(mWorkout.getWorkout_mood()));
        mSleep.setText(String.valueOf(mWorkout.getWorkout_sleep()));
        mWeight.setText(String.valueOf(mWorkout.getWorkout_weight()));
        mCalories.setText(String.valueOf(mWorkout.getWorkout_food()));
        workoutRef = db.collection("users").document(username).collection("workouts").document(workoutVariable).collection("exercises");
        Log.d(TAG, "setWorkoutProperties: " + workoutRef.getPath());
        if (!workoutVariable.equals("Blank")){
            noexercises.setAlpha(0);
        }
    }

    private void setNewWorkoutProperties(){
        mCoach_Notes.setText("mWorkout.getCoaches_Notes()");
    }

    private void setListeners(){
        mWorkoutDetails.setOnClickListener(this);
        mFinishWorkout.setOnClickListener(this);
        mBack.setOnClickListener(this);
        addNew.setOnClickListener(this);
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mExerciseAdapter = new ExerciseRecyclerAdapter(mExercise,this);
        mRecyclerView.setAdapter(mExerciseAdapter);
    }

    @Override
    public void onNoteClick(int position) {
//        Bundle bundle = new Bundle();
//        bundle.putString("message", "From Activity");
//        bundle.putParcelable("selected_exercise",mExercise.get(position));
//        NavController navController = Navigation.findNavController(getActivity(), R.id.frag);
//        navController.navigate(R.id.action_individual_workout_activity_to_single_exercise_fragment,bundle);
        Log.d(TAG, "onNoteClick: " + position);
        Intent intent = new Intent(getContext(),NotesActivity.class);
        intent.putExtra("selected_exercise",mExercise.get(position));
        startActivity(intent);
    }

    public void onChangedCheckBox(int position, boolean isChecked) {
        mExercise.get(position).setExercise_completedBoolean(isChecked);
        if (isChecked){
            mExercise.get(position).setExercise_completedDate(Calendar.getInstance().getTime());
        }
        else {
            Date completedDate = getCompletedDate();
            mExercise.get(position).setExercise_completedDate(completedDate);
        }
        Log.d(TAG, "onChangedCheckBox: " + mExercise.get(position).getExercise_completedDate());
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

    private void updateFirebase(){
        Log.d(TAG, "updateFirebase: " + mWorkout.getWorkout_weight());
        if (mWorkout.getWorkout_id().equals("Blank")){
            DocumentReference newworkoutRef = db.collection("users").document(username).collection("workouts").document();
            mWorkout.setWorkout_id(newworkoutRef.getId());
            mWorkout.setWorkout_completedDate(Calendar.getInstance().getTime());
            mWorkout.setWorkout_athlete_id(username);
            mWorkout.setWorkout_coach_id(username);
            newworkoutRef.set(mWorkout);
            for (int i = 0; i < mExercise.size(); i++){
                Exercise exercise = mExercise.get(i);
                DocumentReference docRef = newworkoutRef.collection("exercises").document();
                exercise.setExercise_workout_id(newworkoutRef.getId());
                exercise.setExercise_id(docRef.getId());
                docRef.set(exercise);
//                statsUpdate(exercise);
            }
        } else {
            for (int i = 0; i < mExercise.size(); i++){
                Exercise exercise = mExercise.get(i);
                Log.d(TAG, "updateFirebase: not blank " + exercise);
                if (exercise.getExercise_id().equals("Blank")){
                    String workoutID = mWorkout.getWorkout_id();
                    DocumentReference docRef = workoutRef.document();
                    exercise.setExercise_workout_id(workoutID);
                    exercise.setExercise_id(docRef.getId());
                    docRef.set(exercise);
                } else {
                    DocumentReference docRef = workoutRef.document(exercise.getExercise_id());
                    docRef.update("exercise_completedBoolean",exercise.getExercise_completedBoolean(),
                            "exercise_completedDate",exercise.getExercise_completedDate(),"exercise_difficulty",exercise.getExercise_difficulty(),
                            "exercise_distance",exercise.getExercise_distance(),"exercise_duration",exercise.getExercise_duration(),
                            "exercise_name",exercise.getExercise_name(),"exercise_note",exercise.getExercise_note(),"exercise_reps",exercise.getExercise_reps(),
                            "exercise_rpe",exercise.getExercise_rpe(),"exercise_sequence",exercise.getExercise_sequence(),"exercise_uom",exercise.getExercise_uom(),
                            "exercise_weight",exercise.getExercise_weight());
                }
//                statsUpdate(exercise);
            }
            DocumentReference workoutRef = db.collection("users").document(username).collection("workouts").document(mWorkout.getWorkout_id());
            Date currentTime = Calendar.getInstance().getTime();
            Map<String, Object> completedData = new HashMap<>();
            completedData.put("workout_completedDate",currentTime);
            completedData.put("workout_food",mWorkout.getWorkout_food());
            completedData.put("workout_mood",mWorkout.getWorkout_mood());
            completedData.put("workout_sleep",mWorkout.getWorkout_sleep());
            completedData.put("workout_weight",mWorkout.getWorkout_weight());
            workoutRef.set(completedData, SetOptions.merge());
        }
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

    private void addNewExercise(){
        int sequence = 1;
        if (mExercise.size() > 0){
            Exercise lastExercise = mExercise.get(mExercise.size()-1);
            int lastSeq = lastExercise.getExercise_sequence();
            sequence = lastSeq+1;
        }
        Log.d(TAG, "addNewExercise: " + sequence);
        Exercise exercise = new Exercise();
        exercise.setExercise_athlete_id(username);
        exercise.setExercise_coach_id(username);
        exercise.setExercise_classification("Strength");
        exercise.setExercise_completedBoolean(false);
        exercise.setExercise_completedDate(getCompletedDate());
        exercise.setExercise_difficulty(0);
        exercise.setExercise_distance(0);
        exercise.setExercise_duration(0);
        exercise.setExercise_id("Blank");
        exercise.setExercise_name("New Exercise");
        exercise.setExercise_note("New Note");
        exercise.setExercise_reps(0);
        exercise.setExercise_rpe(0);
        exercise.setExercise_sequence(sequence);
        exercise.setExercise_uom("kg");
        exercise.setExercise_weight(0);
        exercise.setExercise_workout_id(mWorkout.getWorkout_id());
        exercise.setExercise_programme_id(mWorkout.getWorkout_programme_id());
        insertExercise(exercise);
        mExerciseAdapter.notifyDataSetChanged();
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
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_workout_activity_fragment:{
                NavController navController = Navigation.findNavController(getActivity(), R.id.frag);
                navController.navigate(R.id.action_individual_workout_activity_to_workout_Activity_Fragment);
            }
            case R.id.button_add_new_exercise_fragment:{
                addNewExercise();
                noexercises.setAlpha(0);
                break;
            }
            case R.id.button_finish_workout:{
                Log.d(TAG, "onClick: ");
                updateFirebase();
                NavController navController = Navigation.findNavController(getActivity(), R.id.frag);
                navController.navigate(R.id.action_individual_workout_activity_to_workout_Activity_Fragment);
                break;
            }
            case R.id.layout_details:{
                Intent intent = new Intent(getContext(),workoutoutDetails.class);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.applyPattern("E MMM dd yyyy");
                String a = sdf.format(mWorkout.getWorkout_dateFor());
                intent.putExtra("workout_date",a);
                intent.putExtra("weight",mWorkout.getWorkout_weight());
                intent.putExtra("workout_sleep",mWorkout.getWorkout_sleep());
                intent.putExtra("workout_food",mWorkout.getWorkout_food());
                intent.putExtra("workout_mood",mWorkout.getWorkout_mood());
                startActivity(intent);
                break;
            }
        }
    }
}