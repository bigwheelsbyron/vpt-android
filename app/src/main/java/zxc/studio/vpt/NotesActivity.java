package zxc.studio.vpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import zxc.studio.vpt.R;

import zxc.studio.vpt.models.Exercise;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotesActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnClickListener{

    private static final String TAG = "NotesActivity";

    private EditText mEditText;
    private EditText mRepsEditText;
    private EditText mWeightEditText;
    private EditText mRPEEditText;
    private EditText mDifficultyEditText;

    private Exercise mInitialExercise;

    private GestureDetector mGestureDectector;

    private ImageButton mBackArrow;
    private Button mCommitNotes;
    private Button mCameraButton;
    private Button mNotesShowHideButton;

    private AutoCompleteTextView mExerciseNameEditText;


    private TextView mWeightOrRpe;
    private String username = "Byron";
    private String workoutVariable = "WxnB4sEqJVrHDLZF0S9x";
    private String exerciseID = "IE78anCsFfCRsLpAljyC";
    private String [] exerciseNameSuggestions;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference exerciseRef = db.collection("users").document(username).collection("workouts").document(workoutVariable).collection("exercises").document(exerciseID);


    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.activity_notes);
        setIds();
        getIncomingIntent();
        updateVariables();
        setListeners();
        setExerciseProperties(mInitialExercise);
        addNameSuggestions();
    }

    private void addNameSuggestions(){
        exerciseNameSuggestions = new String[]{
                "Front Squat","Bench","Deadlift","Bent Over Rows","Pull Ups"
        };
        ArrayAdapter<String> adapater = new ArrayAdapter<String>(this, R.layout.spinner_item,exerciseNameSuggestions);
        mExerciseNameEditText.setAdapter(adapater);
    }

    private void updateVariables() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getUid();
        workoutVariable = mInitialExercise.getExercise_id();
        exerciseID = mInitialExercise.getExercise_id();
        exerciseRef = db.collection("users").document(username).collection("workouts").document(workoutVariable).collection("exercises").document(exerciseID);
    }

    private void setIds(){
        mEditText=findViewById(R.id.ExerciseNote);
        mBackArrow=findViewById(R.id.toolbar_back_arrow);
        mCommitNotes=findViewById(R.id.ButtonCommitNotes);
        mRepsEditText=findViewById(R.id.repsEditText);
        mWeightEditText=findViewById(R.id.weightEditText);
        mWeightOrRpe=findViewById(R.id.kgorrpetextView);
        mExerciseNameEditText=findViewById(R.id.editTextNewExerciseName);
        mCameraButton=findViewById(R.id.activity_notes_button_camera);
        mNotesShowHideButton=findViewById(R.id.activity_notes_button_noteshow);
        mDifficultyEditText=findViewById(R.id.difficultyEditText);
        mRPEEditText=findViewById(R.id.rpeEditText);
    }
    
    private void setExerciseProperties(Exercise exercise){
        if (exercise.getExercise_note()==null){

        } else {
            mEditText.setText(exercise.getExercise_note());
        }
        mRepsEditText.setText(""+exercise.getExercise_reps());
        mExerciseNameEditText.setText(exercise.getExercise_name());
        mWeightEditText.setText(exercise.getExercise_weight()+"");
        mDifficultyEditText.setText(exercise.getExercise_difficulty()+"");
        mRPEEditText.setText(exercise.getExercise_rpe()+"");
    }

    private void getIncomingIntent(){
        mInitialExercise = getIntent().getParcelableExtra("selected_exercise");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDectector.onTouchEvent(event);
    }
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {

    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {

    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }


    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        }
    };

    private void setListeners(){
        mEditText.setOnTouchListener(this);
        mGestureDectector = new GestureDetector(this,this);
        mBackArrow.setOnClickListener(this);
        mCommitNotes.setOnClickListener(this);
        mCameraButton.setOnClickListener(this);
        mNotesShowHideButton.setOnClickListener(this);
    }
    private void updateExercise(){
        mInitialExercise.setExercise_reps(Integer.parseInt(String.valueOf(mRepsEditText.getText())));
        mInitialExercise.setExercise_note(String.valueOf(mEditText.getText()));
        mInitialExercise.setExercise_weight(Float.parseFloat(String.valueOf(mWeightEditText.getText())));
        mInitialExercise.setExercise_name(String.valueOf(mExerciseNameEditText.getText()));
        individual_workout_activity.updateMainActivityFragmentExercise(mInitialExercise);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.toolbar_back_arrow:{
                finish();
                break;
            }
            case R.id.ButtonCommitNotes:{
                updateExercise();
                finish();
                break;
            }
            case R.id.activity_notes_button_camera:{
                Log.d(TAG, "onClick: camera button");
                break;
            }
            case R.id.activity_notes_button_noteshow:{
                Log.d(TAG, "onClick: notes show or hide button");
                ViewGroup.LayoutParams params = mEditText.getLayoutParams();
                if (params.height == 0){
                    ViewGroup.LayoutParams params1 = mEditText.getLayoutParams();
                    params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mEditText.setLayoutParams(params1);
                } else {
                    params.height=0;
                    mEditText.setLayoutParams(params);
                }
                break;
            }
        }
    }
}
