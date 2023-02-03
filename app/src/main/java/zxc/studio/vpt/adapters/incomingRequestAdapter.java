package zxc.studio.vpt.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import zxc.studio.vpt.R;
import zxc.studio.vpt.models.Athlete;
import zxc.studio.vpt.models.coachRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;

public class incomingRequestAdapter extends RecyclerView.Adapter<incomingRequestAdapter.ViewHolder> {

    private Context context;
    private ArrayList<coachRequest> mCoachRequests;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public incomingRequestAdapter(ArrayList<coachRequest> mCoachRequests) {
        this.mCoachRequests = mCoachRequests;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_incomingrequest,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String request;
        String name;
        String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(mCoachRequests.get(position).getDateSent());
        if (user.getUid().equals(mCoachRequests.get(position).getCoachID())){
            request = "Athlete Req";
            name = mCoachRequests.get(position).getAthlete_Name_First() + " " +mCoachRequests.get(position).getAthlete_Name_Last();
        } else {
            request = "Coach Req";
            name = mCoachRequests.get(position).getCoach_Name_First() + " " +mCoachRequests.get(position).getCoach_Name_Last();
        }
        holder.name.setText(name);
        holder.date.setText(date);
        holder.type.setText(request);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, date, type;
        Button approve,reject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textview_incoming_name);
            date = itemView.findViewById(R.id.textView_incoming_date);
            type = itemView.findViewById(R.id.textView_incomingrequest_type);
            reject = itemView.findViewById(R.id.button_incoming_reject);
            approve = itemView.findViewById(R.id.button_incoming_approval);
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rejectRequest(getAdapterPosition());
                }
            });
            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    approveRequest(getAdapterPosition());
                }
            });
        }
    }
    private void rejectRequest(int position) {
        String sender = "";
        String receiver = "";
        String senderref = "";
        String receiverref = "";
        if (mCoachRequests.get(position).getCoachID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            receiver = mCoachRequests.get(position).getCoachID();
            sender = mCoachRequests.get(position).getAthleteID();
            senderref = mCoachRequests.get(position).getRequesterFBRequestID();
            receiverref = mCoachRequests.get(position).getReceiverFBRequestID();
        } else if (mCoachRequests.get(position).getAthleteID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            sender = mCoachRequests.get(position).getCoachID();
            receiver = mCoachRequests.get(position).getAthleteID();
            senderref = mCoachRequests.get(position).getRequesterFBRequestID();
            receiverref = mCoachRequests.get(position).getReceiverFBRequestID();
        } else {

        }
        DocumentReference cancelRequestSender = db.collection("users").document(sender).collection("outgoingRequests").document(senderref);
        DocumentReference cancelRequestReceiver = db.collection("users").document(receiver).collection("incomingRequests").document(receiverref);
        cancelRequestSender.delete();
        cancelRequestReceiver.delete();
        mCoachRequests.remove(position);
        notifyDataSetChanged();
    }
    private void approveRequest(int position) {
        String sender = "";
        String receiver = "";
        String senderref = "";
        String receiverref = "";
        String coach = "";
        String athlete = "";
        String previousCoach = "";
        String coach_NameFirst = "";
        String coach_NameLast = "";
        String athlete_NameFirst = "";
        String athlete_NameLast = "";
        if (mCoachRequests.get(position).getCoachID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            receiver = mCoachRequests.get(position).getCoachID();
            sender = mCoachRequests.get(position).getAthleteID();
            senderref = mCoachRequests.get(position).getRequesterFBRequestID();
            receiverref = mCoachRequests.get(position).getReceiverFBRequestID();
            coach = mCoachRequests.get(position).getCoachID();
            athlete = mCoachRequests.get(position).getAthleteID();
            athlete_NameFirst = mCoachRequests.get(position).getAthlete_Name_First();
            athlete_NameLast = mCoachRequests.get(position).getAthlete_Name_Last();
            previousCoach = mCoachRequests.get(position).getPreviousCoach();
            coach_NameFirst = mCoachRequests.get(position).getCoach_Name_First();
            coach_NameLast = mCoachRequests.get(position).getCoach_Name_Last();
        } else if (mCoachRequests.get(position).getAthleteID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            sender = mCoachRequests.get(position).getCoachID();
            receiver = mCoachRequests.get(position).getAthleteID();
            senderref = mCoachRequests.get(position).getRequesterFBRequestID();
            receiverref = mCoachRequests.get(position).getReceiverFBRequestID();
            coach = mCoachRequests.get(position).getCoachID();
            athlete = mCoachRequests.get(position).getAthleteID();
            athlete_NameFirst = mCoachRequests.get(position).getAthlete_Name_First();
            athlete_NameLast = mCoachRequests.get(position).getAthlete_Name_Last();
            previousCoach = mCoachRequests.get(position).getPreviousCoach();
            coach_NameFirst = mCoachRequests.get(position).getCoach_Name_First();
            coach_NameLast = mCoachRequests.get(position).getCoach_Name_Last();
        } else {

        }
        if (previousCoach.equals("")){
            previousCoach = " ";
        }
        DocumentReference cancelRequestSender = db.collection("users").document(sender).collection("outgoingRequests").document(senderref);
        DocumentReference cancelRequestReceiver = db.collection("users").document(receiver).collection("incomingRequests").document(receiverref);
        DocumentReference newAthleteInsert = db.collection("users").document(coach).collection("currentAthletes").document(athlete);
        DocumentReference updateCurrentCoach = db.collection("users").document(athlete);
        DocumentReference removeCurrentCoachDocument = db.collection("users").document(previousCoach).collection("currentAthletes").document(athlete);
        cancelRequestSender.delete();
        final SharedPreferences userDetails = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        if (athlete.equals(userDetails.getString("uid",""))){
            SharedPreferences.Editor editor = userDetails.edit();
            editor.putString("coach", coach);
            editor.commit();
        }
        cancelRequestReceiver.delete();
        removeCurrentCoachDocument.delete();
        newAthleteInsert.set(new Athlete(athlete,athlete_NameFirst,athlete_NameLast));
        updateCurrentCoach.update("coach",coach,"coach_Name_First",coach_NameFirst,"coach_Name_Last",coach_NameLast);
        mCoachRequests.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {return mCoachRequests.size();}
}
