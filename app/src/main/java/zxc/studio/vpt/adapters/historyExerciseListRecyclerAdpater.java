package zxc.studio.vpt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import zxc.studio.vpt.R;
import zxc.studio.vpt.models.Exercise;

public class historyExerciseListRecyclerAdpater  extends RecyclerView.Adapter<historyExerciseListRecyclerAdpater.ViewHolder> {

    private TreeMap<String, ArrayList<Exercise>> exerciseList;
    private OnNoteListenerHistoryExercise mOnNoteListener;


    public historyExerciseListRecyclerAdpater(TreeMap<String, ArrayList<Exercise>> exercises, OnNoteListenerHistoryExercise onNoteListener) {
        this.exerciseList=exercises;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_exercise_history_list_item,parent,false);
        return new historyExerciseListRecyclerAdpater.ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(exerciseList.keySet());
        String name = keys.get(position);
        holder.first_name.setText(name);
    }

    public interface OnNoteListenerHistoryExercise {
        void onNoteClick(int position);

        void onChangedCheckBox(int adapterPosition, boolean isChecked);

        void onChangedCheckBox(int adapterPosition, boolean isChecked, CharSequence id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener  {
        TextView first_name;
        CheckBox reps_checkBox;
        CheckBox weight_checkBox;
        CheckBox rpe_checkBox;
        CheckBox volume_checkBox;
        CheckBox Onerm_checkBox;

        historyExerciseListRecyclerAdpater.OnNoteListenerHistoryExercise onNoteListenerExerciseHistory;

        public ViewHolder(@NonNull View itemView, historyExerciseListRecyclerAdpater.OnNoteListenerHistoryExercise onNoteListenerExerciseHistory) {
            super(itemView);
            first_name = itemView.findViewById(R.id.layoutExerciseHistoryListItem_textView_exerciseName);
            reps_checkBox = itemView.findViewById(R.id.reps_checkBox);
            weight_checkBox = itemView.findViewById(R.id.weight_checkBox);
            rpe_checkBox = itemView.findViewById(R.id.rpe_checkBox);
            volume_checkBox = itemView.findViewById(R.id.volume_checkBox);
            Onerm_checkBox = itemView.findViewById(R.id.calc_1rm);
            this.onNoteListenerExerciseHistory = onNoteListenerExerciseHistory;
            itemView.setOnClickListener(this);
            reps_checkBox.setOnCheckedChangeListener(this);
            weight_checkBox.setOnCheckedChangeListener(this);
            rpe_checkBox.setOnCheckedChangeListener(this);
            volume_checkBox.setOnCheckedChangeListener(this);
            Onerm_checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListenerExerciseHistory.onNoteClick(getAdapterPosition());
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            onNoteListenerExerciseHistory.onChangedCheckBox(getAdapterPosition(),isChecked,compoundButton.getText());
        }
    }

    @Override
    public int getItemCount() {
        List<String> keys = new ArrayList<>(exerciseList.keySet());
        return keys.size();
    }
}