package zxc.studio.vpt.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import zxc.studio.vpt.R;
import zxc.studio.vpt.models.coachRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class outgoingRequestsAdapter extends RecyclerView.Adapter<outgoingRequestsAdapter.ViewHolder> {

    private ArrayList<coachRequest> mCoachRequests;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public outgoingRequestsAdapter(ArrayList<coachRequest> mCoachRequests) {
        this.mCoachRequests = mCoachRequests;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_outgoingrequest,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder parent, int position) {
        String request;
        String name;
        String date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(mCoachRequests.get(position).getDateSent());
        if (user.getUid().equals(mCoachRequests.get(position).getCoachID())){
            request = "Coach Req";
            name = mCoachRequests.get(position).getAthlete_Name_First() + " " +mCoachRequests.get(position).getAthlete_Name_Last();
        } else {
            request = "Athlete Req";
            name = mCoachRequests.get(position).getCoach_Name_First() + " " +mCoachRequests.get(position).getCoach_Name_Last();
        }
        parent.name.setText(name);
        parent.date.setText(date);
        parent.type.setText(request);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, date, type;
        Button cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textview_outgoing_name);
            date = itemView.findViewById(R.id.textView_outgoing_date);
            type = itemView.findViewById(R.id.textView_outgoingrequest_type);
            cancel = itemView.findViewById(R.id.button_outgoingrequest_cancelbutton);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelrequestButton(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
    private void cancelrequestButton(int position){
        String sender = "";
        String receiver = "";
        String senderref = "";
        String receiverref = "";
        if (mCoachRequests.get(position).getCoachID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            sender = mCoachRequests.get(position).getCoachID();
            receiver = mCoachRequests.get(position).getAthleteID();
            senderref = mCoachRequests.get(position).getRequesterFBRequestID();
            receiverref = mCoachRequests.get(position).getReceiverFBRequestID();
        } else if (mCoachRequests.get(position).getAthleteID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            receiver = mCoachRequests.get(position).getCoachID();
            sender = mCoachRequests.get(position).getAthleteID();
            senderref = mCoachRequests.get(position).getRequesterFBRequestID();
            receiverref = mCoachRequests.get(position).getReceiverFBRequestID();
        } else {
            Log.d(TAG, "cancelrequestButton: something went wrong");
        }
        DocumentReference cancelRequestSender = db.collection("users").document(sender).collection("outgoingRequests").document(senderref);
        DocumentReference cancelRequestReceiver = db.collection("users").document(receiver).collection("incomingRequests").document(receiverref);
        cancelRequestSender.delete();
        cancelRequestReceiver.delete();
        mCoachRequests.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount()  {
        return mCoachRequests.size();
    }
}
