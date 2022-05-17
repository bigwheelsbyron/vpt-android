package zxc.studio.vpt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import io.perfmark.Link;
import zxc.studio.vpt.adapters.ExerciseRecyclerAdapter;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.ui.login.LoginActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.protobuf.StringValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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

public class MainActivity extends AppCompatActivity implements ExerciseRecyclerAdapter.OnNoteListener,View.OnClickListener {
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
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
    private TextView mWeight;
    private LinearLayout mWorkoutDetails;
    private CollectionReference workoutRef = db.collection("Notebook").document(username).collection("workouts").document(workoutVariable).collection("exercises");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setIDS();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        username = user.getUid();
//        initRecyclerView();
//        if (Build.VERSION.SDK_INT >= 21)
//        {
//            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
//        }
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            if (getIncomingIntent()){
//                setNewWorkoutProperties();
//            }
//            else {
//                setWorkoutProperties();
//                setListeners();
//                loadWorkout();
//            }
//        }
//        else{
//            if (getIncomingIntent() == false){
//                Log.d(TAG, "onCreate: fire user doesn't exist");
//                setWorkoutProperties();
//                setListeners();
//                loadWorkout();
//            } else{
//                setNewWorkoutProperties();
//            }
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        sdf.applyPattern("E MMM dd yyyy");
//        String a = sdf.format(mWorkout.getWorkout_dateFor());
//        mCoach_Notes.setText(a);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setIDS(){
        mRecyclerView=findViewById(R.id.recyclerView_exercises);
        mFinishWorkout=findViewById(R.id.button_finish_workout);
        mCoach_Notes=findViewById(R.id.coaches_notes);
        mBack=findViewById(R.id.backbutton);
        vptMenu=findViewById(R.id.vptMenuButton);
        addNew=findViewById(R.id.button_add_new_exercise);
        noexercises=findViewById(R.id.noexercises_text);
        mMood=findViewById(R.id.textview_Mood);
        mWeight=findViewById(R.id.textview_Weight);
        mSleep=findViewById(R.id.textview_Sleep);
        mCalories=findViewById(R.id.textview_Calories);
        mWorkoutDetails=findViewById(R.id.layout_details);
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
        if(getIntent().hasExtra("selected_workout")){
            mWorkout = getIntent().getParcelableExtra("selected_workout");
            return false;
        }
        return true;
    }

    private void setWorkoutProperties(){
        workoutVariable = mWorkout.getWorkout_id();
        mMood.setText(String.valueOf(mWorkout.getWorkout_mood()));
        mSleep.setText(String.valueOf(mWorkout.getWorkout_sleep()));
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
        vptMenu.setOnClickListener(this);
        addNew.setOnClickListener(this);
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mExerciseAdapter = new ExerciseRecyclerAdapter(mExercise,this);
        mRecyclerView.setAdapter(mExerciseAdapter);
    }

    @Override
    public void onNoteClick(int position) {
//        Log.d(TAG, "onNoteClick: " + position);
//        Intent intent = new Intent(this,NotesActivity.class);
//        intent.putExtra("selected_exercise",mExercise.get(position));
//        startActivity(intent);
    }

    @Override
    public void onChangedCheckBox(int position, boolean isChecked) {
//        mExercise.get(position).setExercise_completedBoolean(isChecked);
//        if (isChecked){
//            mExercise.get(position).setExercise_completedDate(Calendar.getInstance().getTime());
//        }
//        else {
//            Date completedDate = getCompletedDate();
//            mExercise.get(position).setExercise_completedDate(completedDate);
//        }
//        Log.d(TAG, "onChangedCheckBox: " + mExercise.get(position).getExercise_completedDate());
    }

    public static void updateMainActivitymExercise(Exercise exercise){
//        mExercise.get(exercise.getId()).setReps(exercise.getReps());
//        mExercise.get(exercise.getId()).setWeight(exercise.getWeight());
//        mExercise.get(exercise.getId()).setExerciseNote(exercise.getExerciseNote());
//        mExercise.get(exercise.getId()).setRPE(exercise.getRPE());
//        mExercise.get(exercise.getId()).setExerciseName(exercise.getExerciseName());
//        mExerciseAdapter.notifyDataSetChanged();
    }

    public static void updateMainActivityWorkout(String sleep, String weight, String mood, String calories){
//        mWorkout.setSleep(Float.parseFloat(sleep));
//        mWorkout.setMood(Integer.parseInt(mood));
//        mWorkout.setFood(Integer.parseInt(calories));
//        mSleep.setText(sleep);
//        mMood.setText(mood);
//        mCalories.setText(calories);
    }

    private void updateFirebase(){
//        if (mWorkout.getWorkout_ID().equals("Blank")){
//            DocumentReference newworkoutRef = db.collection("users").document(username).collection("workouts").document();
//            mWorkout.setWorkout_ID(newworkoutRef.getId());
//            mWorkout.setCompletedDate(Calendar.getInstance().getTime());
//            newworkoutRef.set(mWorkout);
//            for (int i = 0; i < mExercise.size(); i++){
//                Exercise exercise = mExercise.get(i);
//                Log.d(TAG, "updateFirebase: blank " + exercise.getUom());
//                DocumentReference docRef = newworkoutRef.collection("exercises").document();
//                exercise.setWorkout_ID(newworkoutRef.getId());
//                exercise.setExerciseID(docRef.getId());
//                docRef.set(exercise);
//                statsUpdate(exercise);
//            }
//        } else {
//            for (int i = 0; i < mExercise.size(); i++){
//                Exercise exercise = mExercise.get(i);
//                Log.d(TAG, "updateFirebase: not blank " + exercise);
//                if (exercise.getExerciseID().equals("Blank")){
//                    String workoutID = mWorkout.getWorkout_ID();
//                    DocumentReference docRef = workoutRef.document();
//                    exercise.setWorkout_ID(workoutID);
//                    exercise.setExerciseID(docRef.getId());
//                    docRef.set(exercise);
//                } else {
//                    DocumentReference docRef = workoutRef.document(exercise.getExerciseID());
//                    docRef.update("exerciseNote",exercise.getExerciseNote(),"weight",exercise.getWeight(),"reps",exercise.getReps(),"rpe",exercise.getRPE(),"completeBinary",exercise.getCompleteBinary(),"completedDate",exercise.getCompletedDate(),"uom",exercise.getUom());
//                }
//                statsUpdate(exercise);
//            }
//            DocumentReference workoutRef = db.collection("users").document(username).collection("workouts").document(mWorkout.getWorkout_ID());
//            Date currentTime = Calendar.getInstance().getTime();
//            Map<String, Object> completedData = new HashMap<>();
//            completedData.put("completedDate",currentTime);
//            completedData.put("food",mWorkout.getFood());
//            completedData.put("mood",mWorkout.getMood());
//            completedData.put("sleep",mWorkout.getSleep());
//            workoutRef.set(completedData, SetOptions.merge());
//        }
    }
    
    private void statsUpdate(Exercise exercise){
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat format = new SimpleDateFormat("yy-MM");
//        String yearMonth = format.format(c);
//        Log.d(TAG, "statsUpdate: " + yearMonth);
//        if (exercise.getCompleteBinary()){
//            if (exercise.getClassification().equals("Strength")){
//                Map<String, Object> setData = new HashMap<>();
//                SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSSS");
//                String date = dateFormater.format(exercise.getCompletedDate());
//                String difficult = String.valueOf(exercise.getDifficulty());
//                String reps = String.valueOf(exercise.getReps());
//                String weight = String.valueOf(exercise.getWeight());
//                String rpe = String.valueOf(exercise.getRPE());
//                String uom = exercise.getUom();
//                String data = date.concat(".").concat(difficult).concat(".").concat(reps).concat(".").concat(weight).concat(".").concat(rpe).concat(".").concat(uom);
//                Log.d(TAG, "updateFirebase: " + difficult);
//                setData.put(exercise.getExerciseID(),data);
//                DocumentReference fbref = db.collection("users").document(username).collection("stats").document(exercise.getClassification()).collection(exercise.getExerciseName()).document(yearMonth);
//                fbref.set(setData, SetOptions.merge());
//                RecordsCheckAndUpdate(exercise);
//            }
//        }
    }

    private void RecordsCheckAndUpdate(Exercise exercise){
//        DocumentReference fbref = db.collection("users").document(username).collection("stats").document(exercise.getClassification()).collection(exercise.getExerciseName()).document("Records");
//        fbref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.getData() != null){
//                    HashMap historicalData = (HashMap) documentSnapshot.getData().get("Historical");
//                    LinkedHashMap historical = OrderDictionariesDates(historicalData);
//                    Integer reps = exercise.getReps();
//                    LinkedHashMap prevHistData = (LinkedHashMap) historical.get(String.valueOf(reps));
//                    Log.d(TAG, "onSuccess: " + historical);
//                    Log.d(TAG, "onSuccess: " + prevHistData);
//                    int weight = exercise.getWeight();
//                    int index = prevHistData.values().size()-1;
//                    long prevWeight = (long) prevHistData.values().toArray()[index];
//                    Log.d(TAG, "onSuccess: " + historical);
//                    if (weight>prevWeight){
//                        Log.d(TAG, "onSuccess: bigger");
//                        Date c = Calendar.getInstance().getTime();
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                        String date = format.format(c);
//                        HashMap newData = new HashMap<>();
//                        newData.put(date,weight);
//                        prevHistData.put(date,weight);
//                        historical.put(String.valueOf(reps),prevHistData);
//                        Log.d(TAG, "onSuccess: " + historical);
//                        Log.d(TAG, "onSuccess: " + prevHistData);
//                        fbref.update("Historical",historical);
//                    }
//                } else {
//                    Log.d(TAG, "onFailure: no records");
//                    Date c = Calendar.getInstance().getTime();
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                    String date = format.format(c);
//                    LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Integer>>> orderedMap = new LinkedHashMap<>();
//                    LinkedHashMap<String, LinkedHashMap<String, Integer>> historical = new LinkedHashMap<>();
//                    LinkedHashMap<String, LinkedHashMap<String, Integer>> calculated = new LinkedHashMap<>();
//                    LinkedHashMap<String, Integer> dateValue = new LinkedHashMap<>();
//                    dateValue.put(date,0);
//                    for (int i = 1;i<16;i++){
//                        historical.put(String.valueOf(i),dateValue);
//                        calculated.put(String.valueOf(i),dateValue);
//                    }
//                    orderedMap.put("Historical",historical);
//                    orderedMap.put("Calculated",calculated);
//                    fbref.set(orderedMap);
//                }
//            }
//        });
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

    private void addNewExercise(){
//        int sequence;
//        int id;
//        if (mExercise.size() > 0){
//
//            ArrayList<Exercise> mExercises = mExercise;
//            Collections.sort(mExercises, new Comparator<Exercise>() {
//                @Override
//                public int compare(Exercise o1, Exercise o2) {
//                    return Integer.compare(o1.getSequence(), o2.getSequence());
//                }
//            });
//            Exercise maxValue = mExercises.get(mExercises.size()-1);
//            id = maxValue.getId()+1;
//            Log.d(TAG, "insertExerciseButtonAction: " + id);
//            sequence = id+1;
//
//        } else {
//            sequence = 1;
//            id = 0;
//        }
//        Exercise exercise = new Exercise(id, "New Exercise Name", "Blank", "New Exercise Note", 0, 0, false, "Blank",sequence, 0, 0, 0, "Strength", 0, getCompletedDate(),"kg");
//        insertExercise(exercise);
//        mExerciseAdapter.notifyDataSetChanged();
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_finish_workout:{
                Log.d(TAG, "onClick: ");
                updateFirebase();
                finish();
                break;
            }
            case R.id.backbutton:{
                Intent intent = new Intent(this,workout_activity.class);
                startActivity(intent);
                break;
            }
            case R.id.button_add_new_exercise:{
                addNewExercise();
                noexercises.setAlpha(0);
                break;
            }
//            case R.id.layout_details:{
//                Intent intent = new Intent(this,workoutoutDetails.class);
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                sdf.applyPattern("E MMM dd yyyy");
//                String a = sdf.format(mWorkout.getDate());
//                intent.putExtra("date",a);
//                //intent.putExtra("weight",mWeight);
//                intent.putExtra("sleep",mWorkout.getSleep());
//                intent.putExtra("food",mWorkout.getFood());
//                intent.putExtra("mood",mWorkout.getMood());
//                startActivity(intent);
//                break;
//            }
        }
    }
}