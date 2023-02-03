package zxc.studio.vpt.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import zxc.studio.vpt.Coach_Workout_Activity_fragment;
import zxc.studio.vpt.R;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.utilities.ItemTouchHelperAdapter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class newWorkoutRecyclerAdapter extends RecyclerView.Adapter<newWorkoutRecyclerAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Exercise> mExercises;
    private OnNoteListener mOnNoteListener;
    private ItemTouchHelper mTouchHelper;
    private Context mContext;

    public newWorkoutRecyclerAdapter(ArrayList<Exercise> mExercises, OnNoteListener onNoteListener, Context context) {
        this.mExercises = mExercises;
        this.mOnNoteListener = onNoteListener;
        this.mContext = context;
    }
    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.mTouchHelper = touchHelper;
    }

    public static class focusWatcher implements View.OnFocusChangeListener{
        private EditText mEditText;
        private TextView mTextView;
        public focusWatcher(EditText editText, TextView textView){
            mEditText = editText;
            mTextView = textView;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                try {
                    Log.d(TAG, "onFocusChange: current text is" + mEditText.getText().toString());
                    Log.d(TAG, "onFocusChange: current position id is" + mTextView.getText().toString());
                    Log.d(TAG, "onFocusChange: current tag is" + mEditText.getTag().toString());
                } finally {
                    return;
                }
            }
            else{
                try {
                    Log.d(TAG, "onFocusChange: old text is" + mEditText.getText().toString());
                    Log.d(TAG, "onFocusChange: old position id is" + Integer.parseInt(mTextView.getText().toString()));
                    Log.d(TAG, "onFocusChange: old tag is" + mEditText.getTag().toString());
                    Coach_Workout_Activity_fragment.updateExercise(mEditText.getTag().toString(), mEditText.getText().toString(), Integer.parseInt(mTextView.getText().toString()));
                } finally {
                    return;
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_newexercise_item,parent,false);
        return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.exerciseName.setText(mExercises.get(position).getExercise_name());
        holder.exerciseNote.setText(mExercises.get(position).getExercise_note());
        holder.exerciseReps.setText(mExercises.get(position).getExercise_reps()+"");
        holder.exerciseWeight.setText(mExercises.get(position).getExercise_weight()+"");
        holder.exerciseIDView.setText(mExercises.get(position).getExercise_id()+"");
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {
        AutoCompleteTextView exerciseName;
        EditText exerciseNote, exerciseReps, exerciseWeight, exerciseSets;
        TextView exerciseRepsTextView, exerciseWeightTextview, exerciseSetsTextview,exerciseIDView;
        OnNoteListener onNoteListener;
        GestureDetector mGestureDetector;


        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.idExerciseName);
            exerciseName.setTag("Name");
            exerciseNote = itemView.findViewById(R.id.idExerciseNote);
            exerciseNote.setTag("Note");
            exerciseReps = itemView.findViewById(R.id.idExerciseReps);
            exerciseReps.setTag("Reps");
            exerciseWeight = itemView.findViewById(R.id.idExerciseWeight);
            exerciseWeight.setTag("Weight");
            exerciseSets = itemView.findViewById(R.id.idExerciseSets);
            exerciseSets.setTag("Sets");
            exerciseRepsTextView = itemView.findViewById(R.id.idExerciseRepsTextView);
            exerciseWeightTextview = itemView.findViewById(R.id.idExerciseWeightTextView);
            exerciseSetsTextview = itemView.findViewById(R.id.idExerciseSets);
            exerciseIDView = itemView.findViewById(R.id.idIDview);
            mOnNoteListener = onNoteListener;
            mGestureDetector = new GestureDetector(itemView.getContext(),this);
            itemView.setOnTouchListener(this);
            exerciseName.setOnFocusChangeListener(new focusWatcher(exerciseName,exerciseIDView));
            exerciseNote.setOnFocusChangeListener(new focusWatcher(exerciseNote,exerciseIDView));
            exerciseReps.setOnFocusChangeListener(new focusWatcher(exerciseReps,exerciseIDView));
            exerciseWeight.setOnFocusChangeListener(new focusWatcher(exerciseWeight,exerciseIDView));
            exerciseSets.setOnFocusChangeListener(new focusWatcher(exerciseSets,exerciseIDView));
//            ArrayAdapter<String> adapater = new ArrayAdapter<String>(mContext, R.layout.spinner_item, CoachMainActivity.exerciseNameSuggestions);
//            exerciseName.setAdapter(adapater);
            exerciseWeightTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeText(getAdapterPosition());
                }
            });

        }

        public int onSelected(ViewHolder view){
            return view.getLayoutPosition();
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override

        public void onShowPress(MotionEvent motionEvent) {

        }

        public void changeText(int position){
            int value = Integer.parseInt(exerciseWeight.getText().toString());
            Coach_Workout_Activity_fragment.rpeToWeightChangeText(position, value);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            mOnNoteListener.onNoteClick(getAdapterPosition());
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            mTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGestureDetector.onTouchEvent(motionEvent);
            return true;
        }
    }



    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Exercise fromExercise = mExercises.get(fromPosition);
        mExercises.remove(fromExercise);
        mExercises.add(toPosition, fromExercise);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemSwiped(int position) {
//        int id = mExercises.get(position).getExercise_sequence();
//        mExercises.remove(position);
//        CoachMainActivity.removeSet(id);
//        notifyItemRemoved(position);
        //TODO: fix removal of items in coach new workout
    }

}
