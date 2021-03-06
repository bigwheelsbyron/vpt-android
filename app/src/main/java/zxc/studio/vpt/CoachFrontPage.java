package zxc.studio.vpt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import zxc.studio.vpt.R;

import zxc.studio.vpt.adapters.athleteListAdapter;
import zxc.studio.vpt.adapters.incomingRequestAdapter;
import zxc.studio.vpt.adapters.newWorkoutRecyclerAdapter;
import zxc.studio.vpt.adapters.outgoingRequestsAdapter;
import zxc.studio.vpt.models.Athlete;
import zxc.studio.vpt.models.coachRequest;
import zxc.studio.vpt.ui.login.LoginActivity;
import zxc.studio.vpt.utilities.ItemDeco;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class CoachFrontPage extends AppCompatActivity implements View.OnClickListener, newWorkoutRecyclerAdapter.OnNoteListener, athleteListAdapter.OnNoteListenerAthlete, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "CoachFrontPage";

    //UI Elements
    private static RecyclerView mRecyclerViewRequestsIncoming;
    private static RecyclerView mRecyclerViewRequestsOutgoing;
    private static RecyclerView mRecyclerViewAthletes;
    private static outgoingRequestsAdapter mOutgoingRequestsAdapter;
    private static incomingRequestAdapter mIncomingRequestAdapter;
    private static athleteListAdapter mAthleteListAdapter;

    private Button requestOutgoingButton;
    private TextView outgoingLabel;
    private TextView incomingLabel;
    private TextView athletesLabel;

    public static ArrayList<coachRequest> mOutgoingRequests = new ArrayList<>();
    public static ArrayList<coachRequest> mIncomingRequests = new ArrayList<>();
    public static ArrayList<Athlete> mAthletes = new ArrayList<>();

    private CollectionReference incomingRequestsCollection;
    private CollectionReference outgoingRequestsCollection;
    private CollectionReference currentAthletesCollection;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coachfrontpage);
        clearArrays();
        setIDs();
        setListeners();
        initRecyclerViewOutgoing();
        initRecyclerViewIncoming();
        initRecyclerViewAthlete();
        getRequests();
        getAthletes();
    }

    private void clearArrays() {
        mOutgoingRequests.clear();
        mIncomingRequests.clear();
        mAthletes.clear();
    }

    private void setListeners() {
        requestOutgoingButton.setOnClickListener(this);
    }
    private void setIDs() {
        mRecyclerViewRequestsOutgoing=findViewById(R.id.recyclerViewOutgoingRequests);
        mRecyclerViewRequestsIncoming=findViewById(R.id.recyclerViewRequestsIncoming);
        mRecyclerViewAthletes=findViewById(R.id.recyclerViewAthlete);
        requestOutgoingButton=findViewById(R.id.buttonSendRequest);
        athletesLabel=findViewById(R.id.textViewNoAthlete);
        outgoingLabel=findViewById(R.id.textViewNoOut);
        incomingLabel=findViewById(R.id.textViewNoIn);
    }
    private void getRequests(){
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        outgoingRequestsCollection = db.collection("users").document(user).collection("outgoingRequests");
        incomingRequestsCollection = db.collection("users").document(user).collection("incomingRequests");
        incomingRequestsCollection.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: incomingRequests" + e);
            }}).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    coachRequest CoachRequest = documentSnapshot.toObject(coachRequest.class);
                    insertRequests(CoachRequest,"incoming");
                }
            }
        });
        outgoingRequestsCollection.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: outgoingRequests" + e);
            }}).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    coachRequest CoachRequest = documentSnapshot.toObject(coachRequest.class);
                    insertRequests(CoachRequest,"outgoing");
                }
            }
        });
    }
    private void insertRequests(coachRequest CoachRequest, String type){
        final SharedPreferences userDetails = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        Date date= new Date();
        if (type.equals("outgoing")){
            coachRequest mCoachRequest = new coachRequest();
            mCoachRequest.setAthleteID(CoachRequest.getAthleteID());
            mCoachRequest.setCoachID(CoachRequest.getCoachID());
            mCoachRequest.setReceiverFBRequestID(CoachRequest.getReceiverFBRequestID());
            mCoachRequest.setRequesterFBRequestID(CoachRequest.getRequesterFBRequestID());
            mCoachRequest.setDateSent(CoachRequest.getDateSent());
            mCoachRequest.setAthlete_Name_Last(CoachRequest.getAthlete_Name_Last());
            mCoachRequest.setAthlete_Name_First(CoachRequest.getAthlete_Name_First());
            mCoachRequest.setCoach_Name_Last(CoachRequest.getCoach_Name_Last());
            mCoachRequest.setCoach_Name_First(CoachRequest.getCoach_Name_First());
            mCoachRequest.setPreviousCoach(CoachRequest.getPreviousCoach());
            mOutgoingRequests.add(mCoachRequest);
            mOutgoingRequestsAdapter.notifyDataSetChanged();
            outgoingLabel.setAlpha(0);
        }
        else if (type.equals("incoming")){
            coachRequest mCoachRequest = new coachRequest();
            mCoachRequest.setAthleteID(CoachRequest.getAthleteID());
            mCoachRequest.setCoachID(CoachRequest.getCoachID());
            mCoachRequest.setReceiverFBRequestID(CoachRequest.getReceiverFBRequestID());
            mCoachRequest.setRequesterFBRequestID(CoachRequest.getRequesterFBRequestID());
            mCoachRequest.setDateSent(CoachRequest.getDateSent());
            mCoachRequest.setAthlete_Name_Last(CoachRequest.getAthlete_Name_Last());
            mCoachRequest.setAthlete_Name_First(CoachRequest.getAthlete_Name_First());
            mCoachRequest.setCoach_Name_Last(CoachRequest.getCoach_Name_Last());
            mCoachRequest.setCoach_Name_First(CoachRequest.getCoach_Name_First());
            incomingLabel.setAlpha(0);
            if (mCoachRequest.getAthleteID().equals(userDetails.getString("uid",""))){
                Log.d(TAG, "insertRequests: yes");
                mCoachRequest.setPreviousCoach(userDetails.getString("coach",""));
            } else {
                mCoachRequest.setPreviousCoach(CoachRequest.getPreviousCoach());
                Log.d(TAG, "insertRequests: no");
            }
            mIncomingRequests.add(mCoachRequest);
            mIncomingRequestAdapter.notifyDataSetChanged();
        }
        else{
            Log.d(TAG, "insertRequests: error in insertReqeusts");
        }
        for (int i = 0; i < mIncomingRequests.size(); i++){
            Log.d(TAG, "onCreate: " + mIncomingRequests.get(i).getPreviousCoach());
        }
    }
    private void getAthletes(){
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        currentAthletesCollection = db.collection("users").document(user).collection("currentAthletes");
        currentAthletesCollection.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: currentAthletes" + e);
            }}).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Athlete athlete = documentSnapshot.toObject(Athlete.class);
                    insertAthletes(athlete);
                }
            }
        });
    }
    
    private void insertAthletes(Athlete athlete){
        mAthletes.add(athlete);
        mAthleteListAdapter.notifyDataSetChanged();
        athletesLabel.setAlpha(0);
    }
    private void initRecyclerViewOutgoing(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewRequestsOutgoing.setLayoutManager(linearLayoutManager);
        ItemDeco itemDeco=new ItemDeco(20);
        mRecyclerViewRequestsOutgoing.addItemDecoration(itemDeco);
        mOutgoingRequestsAdapter = new outgoingRequestsAdapter(mOutgoingRequests);
        mRecyclerViewRequestsOutgoing.setAdapter(mOutgoingRequestsAdapter);
    }
    private void initRecyclerViewIncoming(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewRequestsIncoming.setLayoutManager(linearLayoutManager);
        ItemDeco itemDeco=new ItemDeco(20);
        mRecyclerViewRequestsIncoming.addItemDecoration(itemDeco);
        mIncomingRequestAdapter = new incomingRequestAdapter(mIncomingRequests);
        mRecyclerViewRequestsIncoming.setAdapter(mIncomingRequestAdapter);
    }
    private void initRecyclerViewAthlete(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewAthletes.setLayoutManager(linearLayoutManager);
        ItemDeco itemDeco=new ItemDeco(20);
        mRecyclerViewAthletes.addItemDecoration(itemDeco);
        mAthleteListAdapter = new athleteListAdapter(mAthletes,this);
        mRecyclerViewAthletes.setAdapter(mAthleteListAdapter);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSendRequest: {
                Intent intent = new Intent(this, requestForm.class);
                startActivity(intent);
                break;
            }
            case R.id.backbutton:{
                Intent intent = new Intent(this,workout_activity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this,CoachMainActivity.class);
        intent.putExtra("selected_athlete",mAthletes.get(position));
        startActivity(intent);
    }


    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this,view);
        Log.d(TAG, "showPopup: clicked");
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.coach_popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.settings:{
                Intent intent = new Intent(this,settingsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.athlete:{
                Intent intent = new Intent(this,workout_activity.class);
                startActivity(intent);
                break;
            }
            case R.id.logout:{
                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "onClick: firebase user signed out");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
        return false;
    }
}