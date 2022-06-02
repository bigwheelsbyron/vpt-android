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
    private Exercise mExercise;
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
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference exerciseRef = db.collection("users").document(username).collection("workouts").document(workoutVariable).collection("exercises").document(exerciseID);


    public single_exercise_fragment() {
        // Required empty public constructor
    }

    public static single_exercise_fragment newInstance() {
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
        setIDS(view);
        setProperties();
        setListeners();
        return view;
    }

    private void setIDS(View view){
        mEditText=view.findViewById(R.id.ExerciseNote);
        mBackArrow=view.findViewById(R.id.toolbar_back_arrow);
        mCommitNotes=view.findViewById(R.id.ButtonCommitNotes);
        mRepsEditText=view.findViewById(R.id.repsEditText);
        mWeightEditText=view.findViewById(R.id.weightEditText);
        mWeightOrRpe=view.findViewById(R.id.kgorrpetextView);
        mExerciseNameEditText=view.findViewById(R.id.editTextNewExerciseName);
    }

    private void setListeners(){
        mEditText.setOnTouchListener(this);
        mGestureDectector = new GestureDetector(getContext(),this);
        mBackArrow.setOnClickListener(this);
        mCommitNotes.setOnClickListener(this);
    }

    private void setProperties(){
        setUserDetails();
        setExerciseDetails();
        setExerciseProperties();
        addNameSuggestions();
    }

    private void setExerciseDetails(){
        updateVariables();
        addNameSuggestions();
    }

    private void updateVariables() {
        mExercise = getArguments().getParcelable("selected_exercise");
        workoutVariable = mExercise.getExercise_workout_id();
        exerciseID = mExercise.getExercise_id();
        exerciseRef = db.collection("users").document(username).collection("workouts").document(workoutVariable).collection("exercises").document(exerciseID);
    }

    private void setUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getUid();
    }

    private void addNameSuggestions(){
        exerciseNameSuggestions = new String[]{"Front Squat","Bench","Deadlift","Bent Over Rows","Pull Ups"};
        ArrayAdapter<String> adapater = new ArrayAdapter<String>(getContext(), R.layout.spinner_item,exerciseNameSuggestions);
        mExerciseNameEditText.setAdapter(adapater);
    }

    private void setExerciseProperties(){
        mEditText.setText(mExercise.getExercise_note());
        mRepsEditText.setText(""+mExercise.getExercise_reps());
        mExerciseNameEditText.setText(mExercise.getExercise_name());
        if (mExercise.getExercise_weight() == 0 && mExercise.getExercise_rpe() == 0){
            mWeightEditText.setText(mExercise.getExercise_weight()+"");
        } else if  (mExercise.getExercise_weight() >= 0 && mExercise.getExercise_rpe() == 0) {
            mWeightEditText.setText(mExercise.getExercise_weight()+"");
        } else if (mExercise.getExercise_weight() == 0 && mExercise.getExercise_rpe() >= 0){
            mWeightEditText.setText(mExercise.getExercise_rpe()+"");
            mWeightOrRpe.setText("RPE");
        }
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


}