package zxc.studio.vpt;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import zxc.studio.vpt.R;

import zxc.studio.vpt.models.coachRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class requestForm extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "requestForm";
    private Spinner mSpinnerAthleteOrCoach;
    private EditText mEditTextAthleteOrCoach;
    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private TextView mErrorMessageTextView;
    private Button requestButton;

    private String firstName;
    private String lastName;

    private ArrayList<String> spinnerOptions = new ArrayList<>();

    private coachRequest mRequest = new coachRequest();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private DocumentReference receiverDocument;
    private DocumentReference senderDocument;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.activity_requestform);
        setIDs();
        spinnerAdapter();
        senderNames();
        setListeners();
    }

    private void setIDs() {
        mSpinnerAthleteOrCoach=findViewById(R.id.spinnerCoachAthleteRequest);
        mEditTextAthleteOrCoach=findViewById(R.id.editTextCoachNameRequestForm);
        mEditTextFirstName=findViewById(R.id.editviewCoachFirstname);
        mEditTextLastName=findViewById(R.id.editTextCoachLastName);
        requestButton=findViewById(R.id.buttonRequestFormRequest);
        mErrorMessageTextView=findViewById(R.id.errorMessageTextView);
    }

    private void setListeners() {
        requestButton.setOnClickListener(this);
    }

    private void spinnerAdapter() {
        spinnerOptions.add("Request Coach");
        spinnerOptions.add("Request Athlete");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,spinnerOptions);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAthleteOrCoach.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRequestFormRequest: {
                requestProcess();
                break;
            }
        }
    }

    private void requestProcess(){
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ViewCompat.setBackgroundTintList(mEditTextAthleteOrCoach,colorStateList);
        ViewCompat.setBackgroundTintList(mEditTextFirstName,colorStateList);
        ViewCompat.setBackgroundTintList(mEditTextLastName,colorStateList);
        String email = mEditTextAthleteOrCoach.getText().toString();
        String name_first = mEditTextFirstName.getText().toString();
        String name_last = mEditTextLastName.getText().toString();
        if (email.length() != 0 && name_first.length() != 0 && name_last.length() != 0){
            findReceiver(email,name_first,name_last);
        } else {
            ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
            if (name_last.length() == 0) {
                ViewCompat.setBackgroundTintList(mEditTextLastName,colorStateListRed);
                mEditTextLastName.requestFocus();
            }
            if (name_first.length() == 0) {
                ViewCompat.setBackgroundTintList(mEditTextFirstName,colorStateListRed);
                mEditTextFirstName.requestFocus();
            }
            if (email.length() == 0) {
                ViewCompat.setBackgroundTintList(mEditTextAthleteOrCoach,colorStateListRed);
                mEditTextAthleteOrCoach.requestFocus();
            }
        }

    }

    private void senderNames(){
        String sender = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference doc = db.collection("users").document(sender);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                firstName = (documentSnapshot.getString("Name_First"));
                lastName = (documentSnapshot.getString("Name_Last"));
            }
        });
    }

    private void updateRequestDetails(String receiver) {
        final SharedPreferences userDetails = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        Date date;
        date = Calendar.getInstance().getTime();
        String sender = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (mSpinnerAthleteOrCoach.getSelectedItem().toString().equals("Request Athlete")) {
            senderDocument = db.collection("users").document(sender).collection("outgoingRequests").document();
            receiverDocument = db.collection("users").document(receiver).collection("incomingRequests").document();
            mRequest.setAthleteID(receiver);
            mRequest.setCoachID(sender);
            mRequest.setDateSent(date);
            mRequest.setReceiverFBRequestID(receiverDocument.getId());
            mRequest.setRequesterFBRequestID(senderDocument.getId());
            mRequest.setAthlete_Name_First(mEditTextFirstName.getText().toString());
            mRequest.setAthlete_Name_Last(mEditTextLastName.getText().toString());
            mRequest.setCoach_Name_First(firstName);
            mRequest.setCoach_Name_Last(lastName);
            mRequest.setPreviousCoach("");
        }
        else if (mSpinnerAthleteOrCoach.getSelectedItem().toString().equals("Request Coach")){
            senderDocument  = db.collection("users").document(sender).collection("outgoingRequests").document();
            receiverDocument = db.collection("users").document(receiver).collection("incomingRequests").document();
            mRequest.setAthleteID(sender);
            mRequest.setCoachID(receiver);
            mRequest.setDateSent(date);
            mRequest.setReceiverFBRequestID(receiverDocument.getId());
            mRequest.setRequesterFBRequestID(senderDocument.getId());
            mRequest.setCoach_Name_First(mEditTextFirstName.getText().toString());
            mRequest.setCoach_Name_Last(mEditTextLastName.getText().toString());
            mRequest.setAthlete_Name_First(firstName);
            mRequest.setAthlete_Name_Last(lastName);
            mRequest.setPreviousCoach(userDetails.getString("coach",""));
        }
        else{return;}
        senderDocument.set(mRequest);
        receiverDocument.set(mRequest);
    }



    private void findReceiver(String email, String name_first, String name_last) {
        CollectionReference receiveRef = db.collection("users");
        receiveRef.whereEqualTo("Name_First",name_first).whereEqualTo("Name_Last",name_last).whereEqualTo("email",email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            private static final String TAG = "requestForm";
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                documentSorter(documents);
            };
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: findreiver");
            }
        });
    }

    private void documentSorter(List<DocumentSnapshot> documents) {
        if (documents.size() > 1){
            mErrorMessageTextView.setText("Error");
            mErrorMessageTextView.setAlpha(1);
        } else if (documents.size() == 1){
            updateRequestDetails(documents.get(0).getId());
            Toast.makeText(this, "Request Sent", Toast.LENGTH_SHORT).show();
            mErrorMessageTextView.setText("Successful");
            mErrorMessageTextView.setTextColor(Color.parseColor("#006775"));
            mErrorMessageTextView.setAlpha(1);
            finish();
        } else {
            mErrorMessageTextView.setAlpha(1);
        }
    }
}
