package zxc.studio.vpt.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import zxc.studio.vpt.R;
import zxc.studio.vpt.models.Athlete;

import java.util.ArrayList;

public class athleteListAdapter extends RecyclerView.Adapter<athleteListAdapter.ViewHolder> {
    private ArrayList<Athlete> mAthletes;
    private OnNoteListenerAthlete mOnNoteListener;


    public athleteListAdapter(ArrayList<Athlete> mAthletes, OnNoteListenerAthlete onNoteListener) {
        this.mAthletes=mAthletes;
        this.mOnNoteListener = onNoteListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_coachathleteview,parent,false);
        return new ViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = mAthletes.get(position).getFirst_name() + " " + mAthletes.get(position).getLast_name();
        holder.first_name.setText(name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView first_name, nickname;
        OnNoteListenerAthlete onNoteListenerAthlete;

        public ViewHolder(@NonNull View itemView, OnNoteListenerAthlete onNoteListenerAthlete) {
            super(itemView);
            first_name = itemView.findViewById(R.id.textview_coachview_athlete_firstname);
            this.onNoteListenerAthlete = onNoteListenerAthlete;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListenerAthlete.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListenerAthlete{
        void onNoteClick(int position);
    }

    @Override
    public int getItemCount() {
        return mAthletes.size();
    }
}
