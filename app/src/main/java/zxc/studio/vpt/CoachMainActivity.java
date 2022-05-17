package zxc.studio.vpt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import zxc.studio.vpt.R;

import zxc.studio.vpt.adapters.newWorkoutRecyclerAdapter;
import zxc.studio.vpt.models.Athlete;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Template;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.ui.login.LoginActivity;
import zxc.studio.vpt.utilities.ItemDeco;
import zxc.studio.vpt.utilities.MyItemTouchHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class CoachMainActivity extends AppCompatActivity implements newWorkoutRecyclerAdapter.OnNoteListener, View.OnClickListener, DatePickerDialog.OnDateSetListener, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "CoachMainActivity";

    private Button insertExerciseButton;
    private Button commitExerciseButton;
    private Button saveTemplateButton;
    private TextView dateTextView;
    private TextView killTextView;
    private Spinner athleteIDSpinner;
    private Button backButton;

    private static RecyclerView mRecyclerView;
    private newWorkoutRecyclerAdapter mExerciseRecyclerAdapter;

    public static ArrayList<Exercise> mExercises = new ArrayList<>();
    public static HashMap<Integer,Integer> mExersercisesSets = new HashMap<>();
    private ArrayList<Athlete> mAthletes = new ArrayList<>();
    private ArrayList<String> mAthleteNames = new ArrayList<>();
    private ArrayList<Template> mTemplates = new ArrayList<>();
    private ArrayList<String> mTemplateTitles = new ArrayList<>();
    public static String [] exerciseNameSuggestions;

    private String username = "b0rvlmVXcmgMCLG4RHbCWEZT0aO2";
    private String workoutREF = "YCDxUdFcw9ONST2jG21q";
    private String currentTemplateName;
    public static Date date = Calendar.getInstance().getTime();
    public static Date finalDate = Calendar.getInstance().getTime();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void removeSet(int id) {
        Log.d(TAG, "removeSet: " + id);
        mExersercisesSets.remove(id);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        dateTextView.setText(currentDateString);
        finalDate = c.getTime();
        Log.d(TAG, "onDateSet: " + finalDate);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coachmain);
        setIds();
        findAthletes();
        getTemplateSpinnerInfo();
        setListeners();
        initRecyclerView();
        addNameSuggestions();
    }

    private void addNameSuggestions(){
        exerciseNameSuggestions = new String[]{
                "Front Squat","Bench","Deadlift","Bent Over Rows","Pull Ups"
        };
    }

    public void setIds(){
        insertExerciseButton=findViewById(R.id.exerciseAddButton);
        commitExerciseButton=findViewById(R.id.commitExerciseButton);
        saveTemplateButton=findViewById(R.id.saveTemplateButton);
        mRecyclerView=findViewById(R.id.recyclerViewCoachMain);
        athleteIDSpinner=findViewById(R.id.spinnerAthlete);
        dateTextView=findViewById(R.id.dateTextView);
        backButton=findViewById(R.id.backbutton);
        killTextView=findViewById(R.id.editText_kill);
    }

    public void getIncomingIntent(){
        if(getIntent().hasExtra("selected_athlete")){
            Athlete a = getIntent().getParcelableExtra("selected_athlete");
            athleteIDSpinner.setPrompt(a.getFirst_name());

        }
        if(getIntent().hasExtra("templateID")){
            Intent i = getIntent();
            String templateID = i.getStringExtra("templateID");
            currentTemplateName = i.getStringExtra("templateName");
            templateExercises(templateID);
        }
    }

    private void sortArrayList() {
        Collections.sort(mExercises, new Comparator<Exercise>(){
            public int compare(Exercise e1, Exercise e2) {
                if (e1.getExercise_sequence() == e2.getExercise_sequence()) {
                    return 0;
                } else if (e1.getExercise_sequence() > e2.getExercise_sequence()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }


    public void athleteSpinnerAdapters(){
        ArrayAdapter<String> athleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mAthleteNames);
        athleteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        athleteIDSpinner.setAdapter(athleteAdapter);
        athleteIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG, "onNothingSelected: ");
            }
        });
    }

    private void getTemplateSpinnerInfo(){
        mTemplateTitles.add(" ");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getUid();
        //Gets templates
        CollectionReference templates = db.collection("users").document(username).collection("coachResourcesTemplates");
        templates.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Template t = documentSnapshot.toObject(Template.class);
                    mTemplates.add(t);
                    mTemplateTitles.add(t.getTemplateName());
                }
            }
        });
    }

    private void findAthletes(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getUid();
        CollectionReference athletes = db.collection("users").document(username).collection("currentAthletes");
        athletes.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: findAthlete ");
            }
        }).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Athlete athlete = documentSnapshot.toObject(Athlete.class);
                    mAthletes.add(athlete);
                    String athleteName = athlete.getFirst_name() + " " + athlete.getLast_name();
                    mAthleteNames.add(athleteName);
                }
                athleteSpinnerAdapters();
                getIncomingIntent();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void insertExerciseButtonAction(){
//        String dtStart = "2000-01-01";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date completedDate = finalDate;
//        try {
//            completedDate = format.parse(dtStart);
//            System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        int newId;
//        final Exercise exercise = new Exercise();
//        if (mExercises.size() == 0) {
//            newId = 0;
//            mExersercisesSets.clear();
//        } else {
//            ArrayList<Exercise> mExercise = mExercises;
//            Collections.sort(mExercise, new Comparator<Exercise>() {
//                @Override
//                public int compare(Exercise o1, Exercise o2) {
//                    return Integer.compare(o1.getSequence(), o2.getSequence());
//                }
//            });
//            Exercise maxValue = mExercise.get(mExercise.size()-1);
//            newId = maxValue.getId()+1;
//            Log.d(TAG, "insertExerciseButtonAction: " + newId);
//        }
//        exercise.setId(newId);
//        exercise.setWorkout_ID("");
//        exercise.setExerciseName("New Exercise Name");
//        exercise.setExerciseNote("");
//        exercise.setWeight(0);
//        exercise.setCompletedDate(completedDate);
//        exercise.setDifficulty(0);
//        exercise.setReps(0);
//        exercise.setCompleteBinary(false);
//        exercise.setSequence(newId+1);
//        exercise.setDistance(0);
//        exercise.setClassification("Strength");
//        exercise.setDuration(0);
//        exercise.setRPE(0);
//        exercise.setUom("kg");
//        mExercises.add(exercise);
//        mExersercisesSets.put(exercise.getId(),0);
//        mExerciseRecyclerAdapter.notifyDataSetChanged();
    }


    private void sequenceUpdate(){
        if (date == finalDate){
            ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
            ViewCompat.setBackgroundTintList(dateTextView,colorStateListRed);
        } else {
            Log.d(TAG, "sequenceUpdate: " + (dateTextView.getText().toString()));
            ArrayList<Exercise> fExercises = new ArrayList<>();
            HashMap<Integer,Integer> finalSets;
            finalSets = finalsets();
            for (int i = 0; i < mExercises.size(); i++){
                for (int n = 0; n < finalSets.get(i); n++){
                    fExercises.add(mExercises.get(i));
                }
            }
            commitExerciseAction(fExercises);
        }
    }

    private HashMap<Integer, Integer> finalsets() {
        HashMap<Integer,Integer> variables = new HashMap<>();
        List<Integer> keys = new ArrayList<>(mExersercisesSets.keySet());
        HashMap<Integer,Integer> finalSets = new HashMap<>();
        for (int i = 0; i < keys.size(); i++){
            variables.put(i,keys.get(i));
        }
        for (int i = 0; i < mExersercisesSets.size(); i++){
            int x = variables.get(i);
            finalSets.put(i,mExersercisesSets.get(x));
        }
        return finalSets;
    }

    private void clearExercisesAndToast(){
        mExercises.clear();
        mExerciseRecyclerAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();
    }


    private void commitExerciseAction(ArrayList<Exercise> fExercises){
//        String usernameName = athleteIDSpinner.getSelectedItem().toString();
//        String dtStart = "2000-01-01";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date completedDate = finalDate;
//        try {
//            completedDate = format.parse(dtStart);
//            System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(date);
//        int pos = mAthleteNames.indexOf(usernameName);
//        String usernameAthlete = mAthletes.get(pos).getAthleteUID();
//        DocumentReference workoutFBRef = db.collection("users").document(usernameAthlete).collection("workouts").document();
//        workoutREF = workoutFBRef.getId();
//        Workout workout = new Workout();
//        workout.setAthlete_ID(username);
//        workout.setCompletedDate(completedDate);
//        workout.setDate(finalDate);
//        workout.setFood(0);
//        workout.setId(0);
//        workout.setMood(0);
//        workout.setSleep(0);
//        workout.setWorkout_ID(workoutREF);
//        workout.setCoachID(username);
//        workoutFBRef.set(workout);
//        Log.d(TAG, "commitExerciseAction: " + fExercises);
//        for (int i = 0; i < fExercises.size(); i++){
//            fExercises.get(i).setId(i);
//            fExercises.get(i).setSequence(i+1);
//            fExercises.get(i).setWorkout_ID(workoutFBRef.getId());
//            DocumentReference exerciseRef = db.collection("users").document(usernameAthlete).collection("workouts").document(workoutREF).collection("exercises").document();
//            Log.d(TAG, "commitExerciseAction: " + exerciseRef.getPath());
//            fExercises.get(i).setExerciseID(exerciseRef.getId());
//            exerciseRef.set(fExercises.get(i));
//        }
//        clearExercisesAndToast();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)



    public static void updateExercise(String field, String text, int id){
//        int position = 0;
//        for (Exercise exercise: mExercises){
//            if (exercise.getId() == id){
//                position = mExercises.indexOf(exercise);
//                break;
//            }
//        }
//        if (field.equals("Name")){
//            mExercises.get(position).setExerciseName(text);
//        }
//        else if(field.equals("Note")){
//            mExercises.get(position).setExerciseNote(text);
//        }
//        else if (field.equals("Reps")){
//            mExercises.get(position).setReps(parseInt(text));
//        }
//        else if (field.equals("Weight")){
//            TextView tx = mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.idExerciseWeightTextView);
//            if (tx.getText() == "RPE"){
//                mExercises.get(position).setRPE(parseInt(text));
//            } else {
//                mExercises.get(position).setWeight(parseInt(text));
//            }
//        }
//        else if (field.equals("Sets")){
//            mExersercisesSets.replace(id,mExersercisesSets.get(id), parseInt(text));
//        }
//        else {
//            Log.d(TAG, "updateExercise: Something went wrong in updateExercise");
//        }
    }

    public static void rpeToWeightChangeText(int position, int value){
//        TextView tx = mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.idExerciseWeightTextView);
//        Log.d(TAG, "rpeToWeightChangeText: position" + position);
//        if (tx.getText() == "RPE"){
//            mExercises.get(position).setWeight(value);
//            mExercises.get(position).setRPE(0);
//            tx.setText("Weight");
//            Log.d(TAG, "rpeToWeightChangeText: rpe is "+ mExercises.get(position).getRPE());
//            Log.d(TAG, "rpeToWeightChangeText: rpe is "+ mExercises.get(position).getWeight());
//        } else {
//            mExercises.get(position).setRPE(value);
//            mExercises.get(position).setWeight(0);
//            tx.setText("RPE");
//            Log.d(TAG, "rpeToWeightChangeText: rpe is "+ mExercises.get(position).getRPE());
//            Log.d(TAG, "rpeToWeightChangeText: rpe is "+ mExercises.get(position).getWeight());
//        }
    }


    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ItemDeco itemDeco=new ItemDeco(20);
        mRecyclerView.addItemDecoration(itemDeco);
        mExerciseRecyclerAdapter = new newWorkoutRecyclerAdapter(mExercises,this,this);
        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mExerciseRecyclerAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mExerciseRecyclerAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mExerciseRecyclerAdapter);
    }

    private void setListeners(){
        insertExerciseButton.setOnClickListener(this);
        commitExerciseButton.setOnClickListener(this);
        saveTemplateButton.setOnClickListener(this);
        dateTextView.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.exerciseAddButton:{
                dateTextView.requestFocus();
                insertExerciseButtonAction();
                break;
            }
            case R.id.commitExerciseButton:{
                killTextView.requestFocus();
                killTextView.clearFocus();
                sequenceUpdate();
                break;
            }
            case R.id.saveTemplateButton:{
                dateTextView.requestFocus();
                Intent intent = new Intent(this,templateActivity.class);
                intent.putParcelableArrayListExtra("exercises",mExercises);
                intent.putParcelableArrayListExtra("templates",mTemplates);
                intent.putStringArrayListExtra("templateTitles",mTemplateTitles);
                intent.putExtra("exersercisesSets",mExersercisesSets);
                intent.putExtra("templateName",currentTemplateName);
                startActivity(intent);
                break;
            } case R.id.dateTextView:{
                DialogFragment datePicker = new DatePickerFragmat();
                datePicker.show(getSupportFragmentManager(),"date picker");
                break;
            }
            case R.id.backbutton:{
                Intent intent = new Intent(this,CoachFrontPage.class);
                startActivity(intent);
                break;
            }
        }
    }
    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: " + position);
    }

    public void templateExercises(String templateID){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        username = user.getUid();
//        mExercises.clear();
//        //Gets templates
//        CollectionReference templateExercises = db.collection("users").document(username).collection("coachResourcesTemplates").document(templateID).collection("exercises");
//        templateExercises.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                    Exercise e = documentSnapshot.toObject(Exercise.class);
//                    mExercises.add(e);
//                    int set = Integer.valueOf(documentSnapshot.getDouble("sets").intValue());
//                    mExersercisesSets.put(e.getId(),set);
//                }
//                sortArrayList();
//                mExerciseRecyclerAdapter.notifyDataSetChanged();
//            }
//        });
    }


    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.coach_popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.settings:{
                Intent intent = new Intent(this,settingsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.athlete:{
                Intent intent = new Intent(this,workout_activity.class);
                startActivity(intent);
                break;
            }
            case R.id.logout:{
                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "onClick: firebase user signed out");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
        return false;
    }
}
