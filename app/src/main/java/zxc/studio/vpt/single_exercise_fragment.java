package zxc.studio.vpt;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Workout;

public class single_exercise_fragment extends Fragment implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener {

    private static final String TAG = "NotesActivity";
    private EditText mEditText;
    private Exercise mInitialExercise;
    private GestureDetector mGestureDectector;
    private ImageButton mBackArrow;
    private Button mCommitNotes;
    private EditText mRepsEditText;
    private AutoCompleteTextView mExerciseNameEditText;
    private EditText mWeightEditText;
    private TextView mWeightOrRpe;
    private String username = "Byron";
    private String workoutVariable = "WxnB4sEqJVrHDLZF0S9x";
    private String exerciseID = "IE78anCsFfCRsLpAljyC";
    private String [] exerciseNameSuggestions;
    private int id = 99;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference exerciseRef = db.collection("users").document(username).collection("workouts").document(workoutVariable).collection("exercises").document(exerciseID);


    public single_exercise_fragment() {
        // Required empty public constructor
    }

    public static single_exercise_fragment newInstance(String param1, String param2) {
        single_exercise_fragment fragment = new single_exercise_fragment();
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
        View view = inflater.inflate(R.layout.fragment_single_exercise_fragment, container, false);
        //view.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mEditText=view.findViewById(R.id.ExerciseNote);
        mBackArrow=view.findViewById(R.id.toolbar_back_arrow);
        mCommitNotes=view.findViewById(R.id.ButtonCommitNotes);
        mRepsEditText=view.findViewById(R.id.repsEditText);
        mWeightEditText=view.findViewById(R.id.weightEditText);
        mWeightOrRpe=view.findViewById(R.id.kgorrpetextView);
        mExerciseNameEditText=view.findViewById(R.id.editTextNewExerciseName);
        getIncomingIntent();
        updateVariables();
        mEditText.setOnTouchListener(this);
        mGestureDectector = new GestureDetector(getContext(),this);
        mBackArrow.setOnClickListener(this);
        mCommitNotes.setOnClickListener(this);
        setExerciseProperties(mInitialExercise);
        addNameSuggestions();
        return view;
    }

    @Override
    public void onClick(View view) {

    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }







    private void addNameSuggestions(){
        exerciseNameSuggestions = new String[]{
                "Front Squat","Bench","Deadlift","Bent Over Rows","Pull Ups"
        };
        ArrayAdapter<String> adapater = new ArrayAdapter<String>(getContext(), R.layout.spinner_item,exerciseNameSuggestions);
        mExerciseNameEditText.setAdapter(adapater);
    }

    private void updateVariables() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getUid();
        workoutVariable = mInitialExercise.getExercise_workout_id();
        exerciseID = mInitialExercise.getExercise_id();
        exerciseRef = db.collection("users").document(username).collection("workouts").document(workoutVariable).collection("exercises").document(exerciseID);
    }

    private void setExerciseProperties(Exercise exercise){
        mEditText.setText(exercise.getExercise_note());
        mRepsEditText.setText(""+exercise.getExercise_reps());
        mExerciseNameEditText.setText(exercise.getExercise_name());
        if (exercise.getExercise_weight() == 0 && exercise.getExercise_rpe() == 0){
            mWeightEditText.setText(exercise.getExercise_weight()+"");
        } else if  (exercise.getExercise_weight() >= 0 && exercise.getExercise_rpe() == 0) {
            mWeightEditText.setText(exercise.getExercise_weight()+"");
        } else if (exercise.getExercise_weight() == 0 && exercise.getExercise_rpe() >= 0){
            mWeightEditText.setText(exercise.getExercise_rpe()+"");
            mWeightOrRpe.setText("RPE");
        }
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: ");
        mInitialExercise = getArguments().getParcelable("selected_exercise");
        Log.d(TAG, "getIncomingIntent: ");
    }


}