package zxc.studio.vpt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import zxc.studio.vpt.R;
import zxc.studio.vpt.models.Workout;

import java.text.DateFormat;
import java.util.ArrayList;

public class WorkoutRecyclerAdapter extends RecyclerView.Adapter<WorkoutRecyclerAdapter.ViewHolder> {
    private ArrayList<Workout> mWorkouts = new ArrayList<>();
    private OnNoteListenerWorkout mOnNoteListener;

    public WorkoutRecyclerAdapter(ArrayList<Workout> workouts, OnNoteListenerWorkout onNoteListener) {
        this.mWorkouts = workouts;
        this.mOnNoteListener= onNoteListener;
    }
    @NonNull
    @Override
    public WorkoutRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item_workouts,parent,false);
        return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutRecyclerAdapter.ViewHolder holder, int position) {
        holder.date.setText(DateFormat.getDateInstance().format(mWorkouts.get(position).getWorkout_dateFor()));
    }

    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView workout_id, date;
        OnNoteListenerWorkout onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListenerWorkout onNoteListener) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            this.onNoteListener=onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListenerWorkout{
        void onNoteClick(int position);
    }
}
