package zxc.studio.vpt;

import static java.lang.Integer.parseInt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import zxc.studio.vpt.adapters.newWorkoutRecyclerAdapter;
import zxc.studio.vpt.models.Athlete;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Template;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.utilities.ItemDeco;
import zxc.studio.vpt.utilities.MyItemTouchHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Coach_Workout_Activity_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Coach_Workout_Activity_fragment extends Fragment implements newWorkoutRecyclerAdapter.OnNoteListener, View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private static final String TAG = "CoachMainActivity";

    private Button insertExerciseButton;
    private Button commitExerciseButton;
    private Button saveTemplateButton;
    private TextView dateTextView;
    private TextView killTextView;
    private Spinner athleteIDSpinner;
    private Button backButton;

    private static RecyclerView mRecyclerView;
    private static newWorkoutRecyclerAdapter mExerciseRecyclerAdapter;

    public static ArrayList<Exercise> mExercises = new ArrayList<>();
    public static HashMap<Integer,Integer> mExersercisesSets = new HashMap<>();
    private ArrayList<Athlete> mAthletes = new ArrayList<>();
    private ArrayList<String> mAthleteNames = new ArrayList<>();
    private ArrayList<Template> mTemplates = new ArrayList<>();
    private ArrayList<String> mTemplateTitles = new ArrayList<>();
    public static String [] exerciseNameSuggestions;

    private String username = "b0rvlmVXcmgMCLG4RHbCWEZT0aO2";
    private String workoutREF = "YCDxUdFcw9ONST2jG21q";
    private static HashMap currentTemplate = new HashMap<>();
    public static Date date = Calendar.getInstance().getTime();
    public static Date finalDate = Calendar.getInstance().getTime();


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Coach_Workout_Activity_fragment() {
        // Required empty public constructor
    }
    public static Coach_Workout_Activity_fragment newInstance(String param1, String param2) {
        Coach_Workout_Activity_fragment fragment = new Coach_Workout_Activity_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coach__workout__activity_fragment, container, false);
        insertExerciseButton=view.findViewById(R.id.exerciseAddButton);
        commitExerciseButton=view.findViewById(R.id.commitExerciseButton);
        saveTemplateButton=view.findViewById(R.id.saveTemplateButton);
        mRecyclerView=view.findViewById(R.id.recyclerViewCoachMain);
        athleteIDSpinner=view.findViewById(R.id.spinnerAthlete);
        dateTextView=view.findViewById(R.id.dateTextView);
        backButton=view.findViewById(R.id.button_coach_workout_fragment);
        killTextView=view.findViewById(R.id.editText_kill);
        findAthletes();
        getTemplateSpinnerInfo();
        setListeners();
        initRecyclerView();
        addNameSuggestions();
        return view;
    }

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

    private void addNameSuggestions(){
        exerciseNameSuggestions = new String[]{
                "Front Squat","Bench","Deadlift","Bent Over Rows","Pull Ups"
        };
    }

    public void getIncomingIntent(){
        Athlete a = getArguments().getParcelable("selected_athlete");
        athleteIDSpinner.setPrompt(a.getFirst_name());
    }

    private static void sortArrayList() {
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
        ArrayAdapter<String> athleteAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,mAthleteNames);
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
        String dtStart = "2000-01-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date completedDate = finalDate;
        try {
            completedDate = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int newId;
        final Exercise exercise = new Exercise();
        if (mExercises.size() == 0) {
            newId = 0;
            mExersercisesSets.clear();
        } else {
            ArrayList<Exercise> mExercise = mExercises;
            Collections.sort(mExercise, new Comparator<Exercise>() {
                @Override
                public int compare(Exercise o1, Exercise o2) {
                    return Integer.compare(o1.getExercise_sequence(), o2.getExercise_sequence());
                }
            });
            Exercise maxValue = mExercise.get(mExercise.size()-1);
            newId = maxValue.getExercise_sequence()+1;
            Log.d(TAG, "insertExerciseButtonAction: " + newId);
        }
        exercise.setExercise_id(String.valueOf(newId));
        exercise.setExercise_workout_id("");
        exercise.setExercise_name("New Exercise Name");
        exercise.setExercise_note("");
        exercise.setExercise_weight(0);
        exercise.setExercise_completedDate(completedDate);
        exercise.setExercise_difficulty(0);
        exercise.setExercise_reps(0);
        exercise.setExercise_completedBoolean(false);
        exercise.setExercise_sequence(newId);
        exercise.setExercise_distance(0);
        exercise.setExercise_classification("Strength");
        exercise.setExercise_duration(0);
        exercise.setExercise_rpe(0);
        exercise.setExercise_uom("kg");
        mExercises.add(exercise);
        mExersercisesSets.put(exercise.getExercise_sequence(),0);
        mExerciseRecyclerAdapter.notifyDataSetChanged();
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
            Log.d(TAG, "sequenceUpdate: "+mExercises);
            Log.d(TAG, "sequenceUpdate: " + finalSets);
            for (int i = 0; i < mExercises.size(); i++){
                for (int n = 0; n < finalSets.get(i); n++){
                    fExercises.add(mExercises.get(i));
                }
            }
            commitExerciseAction(fExercises);
        }
    }

    private HashMap<Integer, Integer> finalsets() {
        Log.d(TAG, "finalsets: " + mExersercisesSets);
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
        Toast.makeText(getContext(), "Sent", Toast.LENGTH_SHORT).show();
    }

    private void commitExerciseAction(ArrayList<Exercise> fExercises){
        String usernameName = athleteIDSpinner.getSelectedItem().toString();
        String dtStart = "2000-01-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date completedDate = finalDate;
        try {
            completedDate = format.parse(dtStart);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        int pos = mAthleteNames.indexOf(usernameName);
        String usernameAthlete = mAthletes.get(pos).getAthleteUID();
        DocumentReference workoutFBRef = db.collection("users").document(usernameAthlete).collection("workouts").document();
        workoutREF = workoutFBRef.getId();
        Workout workout = new Workout();
        workout.setWorkout_athlete_id(username);
        workout.setWorkout_completedDate(completedDate);
        workout.setWorkout_dateFor(finalDate);
        workout.setWorkout_food(0);
        workout.setWorkout_mood(0);
        workout.setWorkout_sleep(0);
        workout.setWorkout_id(workoutREF);
        workout.setWorkout_coach_id(username);
        workoutFBRef.set(workout);
        Log.d(TAG, "commitExerciseAction: " + fExercises);
        for (int i = 0; i < fExercises.size(); i++){
            fExercises.get(i).setExercise_sequence(i+1);
            fExercises.get(i).setExercise_workout_id(workoutFBRef.getId());
            DocumentReference exerciseRef = db.collection("users").document(usernameAthlete).collection("workouts").document(workoutREF).collection("exercises").document();
            fExercises.get(i).setExercise_id(exerciseRef.getId());
            exerciseRef.set(fExercises.get(i));
        }
        clearExercisesAndToast();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void updateExercise(String field, String text, int sequence){
        int position = 0;
        for (Exercise exercise: mExercises){
            if (exercise.getExercise_sequence() == sequence){
                position = mExercises.indexOf(exercise);
                break;
            }
        }
        if (field.equals("Name")){
            mExercises.get(position).setExercise_name(text);
        }
        else if(field.equals("Note")){
            mExercises.get(position).setExercise_note(text);
        }
        else if (field.equals("Reps")){
            mExercises.get(position).setExercise_reps(parseInt(text));
        }
        else if (field.equals("Weight")){
            TextView tx = mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.idExerciseWeightTextView);
            if (tx.getText() == "RPE"){
                mExercises.get(position).setExercise_rpe(parseInt(text));
            } else {
                mExercises.get(position).setExercise_weight(parseInt(text));
            }
        }
        else if (field.equals("Sets")){
            mExersercisesSets.replace(sequence,mExersercisesSets.get(sequence), parseInt(text));
        }
        else {
            Log.d(TAG, "updateExercise: Something went wrong in updateExercise");
        }
    }

    public static void rpeToWeightChangeText(int position, int value){
        TextView tx = mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.idExerciseWeightTextView);
        Log.d(TAG, "rpeToWeightChangeText: position" + position);
        if (tx.getText() == "RPE"){
            mExercises.get(position).setExercise_weight(value);
            mExercises.get(position).setExercise_rpe(0);
            tx.setText("Weight");
            Log.d(TAG, "rpeToWeightChangeText: rpe is "+ mExercises.get(position).getExercise_rpe());
            Log.d(TAG, "rpeToWeightChangeText: rpe is "+ mExercises.get(position).getExercise_weight());
        } else {
            mExercises.get(position).setExercise_rpe(value);
            mExercises.get(position).setExercise_weight(0);
            tx.setText("RPE");
            Log.d(TAG, "rpeToWeightChangeText: rpe is "+ mExercises.get(position).getExercise_rpe());
            Log.d(TAG, "rpeToWeightChangeText: rpe is "+ mExercises.get(position).getExercise_weight());
        }
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ItemDeco itemDeco=new ItemDeco(20);
        mRecyclerView.addItemDecoration(itemDeco);
        mExerciseRecyclerAdapter = new newWorkoutRecyclerAdapter(mExercises,this,getContext());
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
                dateTextView.requestFocus();
                sequenceUpdate();
                break;
            }
            case R.id.saveTemplateButton:{
                dateTextView.requestFocus();
                Intent intent = new Intent(getContext(),templateActivity.class);
                intent.putParcelableArrayListExtra("exercises",mExercises);
                intent.putParcelableArrayListExtra("templates",mTemplates);
                intent.putStringArrayListExtra("templateTitles",mTemplateTitles);
                intent.putExtra("exersercisesSets",mExersercisesSets);
                intent.putExtra("templateName", (String) currentTemplate.get("templateName"));
                startActivity(intent);
                break;
            } case R.id.dateTextView:{
                DialogFragment datePicker = new DatePickerFragmat();
                datePicker.setTargetFragment(Coach_Workout_Activity_fragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
                break;
            } case R.id.button_coach_workout_fragment:{
                NavController navController = Navigation.findNavController(getActivity(), R.id.mainDetailsFragment);
                navController.navigate(R.id.action_coach_Workout_Activity_fragment_to_coach_Main_Activity_Fragmenet);
            }
        }
    }

    public static void templateSelected(String templateID, String templateName){
        currentTemplate.put("templateID",templateID);
        currentTemplate.put("templateName",templateID);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore dbx = FirebaseFirestore.getInstance();
        String x = user.getUid();
        mExercises.clear();
        //Gets templates
        CollectionReference templateExercises = dbx.collection("users").document(x).collection("coachResourcesTemplates").document(templateID).collection("exercises");
        templateExercises.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Exercise e = documentSnapshot.toObject(Exercise.class);
                    mExercises.add(e);
                    int set = Integer.valueOf(documentSnapshot.getDouble("sets").intValue());
                    mExersercisesSets.put(e.getExercise_sequence(),set);
                }
                sortArrayList();
                mExerciseRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: " + position);
    }

    public void templateExercises(String templateID){

    }

}

