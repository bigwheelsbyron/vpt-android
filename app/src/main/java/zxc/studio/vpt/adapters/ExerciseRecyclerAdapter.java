package zxc.studio.vpt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import zxc.studio.vpt.R;
import zxc.studio.vpt.models.Exercise;

import java.util.ArrayList;

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ViewHolder>{
    private static final String TAG = "ExerciseRecyclerAdapter";
    private ArrayList<Exercise> mExercise = new ArrayList<>();
    private OnNoteListener mOnNoteListener;
    private boolean mCheckedState;

    public ExerciseRecyclerAdapter(ArrayList<Exercise> exercise, OnNoteListener onNoteListener) {
        this.mExercise = exercise;
        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mExercise.get(position).getExercise_completedBoolean()==false){
            mCheckedState=false;
        }
        else mCheckedState=true;
        holder.exercise.setText(mExercise.get(position).getExercise_name());
        holder.reps.setText(mExercise.get(position).getExercise_reps()+"");
        if (mExercise.get(position).getExercise_weight() == 0 && mExercise.get(position).getExercise_rpe() == 0){
            holder.weight.setText(mExercise.get(position).getExercise_weight()+"");
        } else if  (mExercise.get(position).getExercise_weight() >= 0 && mExercise.get(position).getExercise_rpe() == 0) {
            holder.weight.setText(mExercise.get(position).getExercise_weight()+"");
        } else if (mExercise.get(position).getExercise_weight() == 0 && mExercise.get(position).getExercise_rpe() >= 0){
            holder.weight.setText(mExercise.get(position).getExercise_rpe()+"");
            holder.weightorRpe.setText(" RPE");
        }

        holder.complete_checkbox.setChecked(mCheckedState);
    }

    @Override
        public int getItemCount() {
            return mExercise.size();
        }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        TextView exercise, weight, reps, weightorRpe;
        OnNoteListener onNoteListener;
        CheckBox complete_checkbox;

        public ViewHolder(@NonNull View itemView, final OnNoteListener onNoteListener) {
            super(itemView);
            exercise=itemView.findViewById(R.id.ExerciseName);
            weight=itemView.findViewById(R.id.Weight);
            reps=itemView.findViewById(R.id.Reps);
            weightorRpe=itemView.findViewById(R.id.WeightKGText);
            complete_checkbox=itemView.findViewById(R.id.checkBox);
            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);
            complete_checkbox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            onNoteListener.onChangedCheckBox(getAdapterPosition(),isChecked);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
        void onChangedCheckBox(int position,boolean isChecked);
    }
}
