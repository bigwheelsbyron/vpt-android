package zxc.studio.vpt;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import zxc.studio.vpt.controllers.SignUpController;
import zxc.studio.vpt.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.HashMap;
import java.util.Map;

public class sign_up extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "signup";
    private EditText mEdittext_Email;
    private EditText mEdittext_Password;
    private EditText mEdittext_FirstName;
    private EditText mEdittext_LastName;
    private TextView mViewText_toc;
    private TextView mViewText_opening;
    private TextView mViewText_changes;
    private TextView mViewText_thirdparty;
    private TextView mViewText_entire;
    private TextView mViewText_offer;
    private TextView mViewText_termination;
    private TextView mViewText_banning;
    private TextView mViewText_data;
    private TextView mViewText_cost;
    private TextView mViewText_commercial;
    private TextView mViewText_warranty;
    private TextView mViewText_acl;
    private TextView mViewText_ip;
    private TextView mViewText_userguide;
    private Button mOkayButton;
    private Button mSignUpButton;
    private Button mCancelButton;
    private CheckBox mCheckBox;
    private LinearLayout mLayoutTOC;
    private LinearLayout mLayoutSignUp;
    private ImageView vptSign;
    private SignUpController signUpController = new SignUpController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setIds();
        setListeners();
        setupTOCs();
    }

    private void setListeners() {
        mViewText_toc.setOnClickListener(this);
        mOkayButton.setOnClickListener(this);
        mCheckBox.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        keyboardEventListener();
    }

    private void keyboardEventListener(){
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean b) {
                if (b){
                    moveUp();
                } if (!b){
                    moveDown();
                }
            }
        });
    }

    private void moveUp(){
        Log.d(TAG, "moveUp: ");
        vptSign.setImageAlpha(0);
    }

    private void moveDown(){
        Log.d(TAG, "moveDown: ");
        vptSign.setImageAlpha(255);
    }

    private void setIds() {
        mViewText_toc=findViewById(R.id.signUpActivity_textView_toc);
        mOkayButton=findViewById(R.id.signUpActivity_button_okayTOC);
        mLayoutSignUp=findViewById(R.id.signUpActivity_linearLayout_signUpDetails);
        mLayoutTOC=findViewById(R.id.signUpActivity_linearlayout_toc);
        mCheckBox=findViewById(R.id.signUpActivity_checkBox_toc);
        mSignUpButton=findViewById(R.id.signUpActivity_button_agree);
        mEdittext_Email=findViewById(R.id.signUpActivity_editText_email);
        mEdittext_Password=findViewById(R.id.signUpActivity_editText_password);
        mEdittext_FirstName=findViewById(R.id.signUpActivity_editText_firstname);
        mEdittext_LastName=findViewById(R.id.signUpActivity_editText_lastname);
        mCancelButton=findViewById(R.id.signUpActivity_button_cancel);
        mViewText_opening=findViewById(R.id.signUpActivity_textView_openingText);
        mViewText_changes=findViewById(R.id.signUpActivity_textView_changesText);
        mViewText_thirdparty=findViewById(R.id.signUpActivity_textView_thirdPartyText);
        mViewText_entire=findViewById(R.id.signUpActivity_textView_entireAgreementText);
        mViewText_offer=findViewById(R.id.signUpActivity_textView_offerText);
        mViewText_termination=findViewById(R.id.signUpActivity_textView_terminationText);
        mViewText_banning=findViewById(R.id.signUpActivity_textView_banningText);
        mViewText_data=findViewById(R.id.signUpActivity_textView_dataText);
        mViewText_cost=findViewById(R.id.signUpActivity_textView_costText);
        mViewText_commercial=findViewById(R.id.signUpActivity_textView_commercialText);
        mViewText_warranty=findViewById(R.id.signUpActivity_textView_warrantyText);
        mViewText_acl=findViewById(R.id.signUpActivity_textView_aclText);
        mViewText_ip=findViewById(R.id.signUpActivity_textView_ipText);
        mViewText_userguide=findViewById(R.id.signUpActivity_textView_uglText);
        mLayoutSignUp.bringToFront();
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.signUpActivity_textView_toc:{
                displayTOCs();
                break;
            }
            case R.id.signUpActivity_button_okayTOC:{
                displaySignUp();
                break;
            }
            case R.id.signUpActivity_checkBox_toc:{
                checkboxClicked();
                break;
            }
            case R.id.signUpActivity_button_agree:{
                signUpProcess();
                break;
            }
            case R.id.signUpActivity_button_cancel:{
                moveToLoginScreen();
            }
        }
    }

    private void displayTOCs(){
        mLayoutTOC.setAlpha(1);
        mLayoutSignUp.setAlpha(0);
        mLayoutTOC.bringToFront();
    }

    private void displaySignUp(){
        mLayoutTOC.setAlpha(0);
        mLayoutSignUp.setAlpha(1);
        mLayoutSignUp.bringToFront();
    }

    private void resetBorderColours(){
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ViewCompat.setBackgroundTintList(mEdittext_Email,colorStateList);
        ViewCompat.setBackgroundTintList(mEdittext_Password,colorStateList);
        ViewCompat.setBackgroundTintList(mEdittext_FirstName,colorStateList);
        ViewCompat.setBackgroundTintList(mEdittext_LastName,colorStateList);
    }

    private boolean validEntries(String email, String password, String firstName, String lastName){
        return email.length() != 0 && password.length() != 0 && firstName.length() != 0 && lastName.length() != 0;
    }

    private void missingEntries(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        if (mEdittext_FirstName.getText().toString().length() == 0) {
            ViewCompat.setBackgroundTintList(mEdittext_FirstName,colorStateListRed);
            mEdittext_FirstName.requestFocus();
        }
        if (mEdittext_LastName.getText().toString().length() == 0) {
            ViewCompat.setBackgroundTintList(mEdittext_LastName,colorStateListRed);
            mEdittext_LastName.requestFocus();
        }
        if (mEdittext_Password.getText().toString().length() == 0) {
            ViewCompat.setBackgroundTintList(mEdittext_Password,colorStateListRed);
            mEdittext_Password.requestFocus();
        }
        if (mEdittext_Email.getText().toString().length() == 0) {
            ViewCompat.setBackgroundTintList(mEdittext_Email,colorStateListRed);
            mEdittext_Email.requestFocus();
        }
    }


    private void navigate(){
        Intent intent = new Intent(this, workout_activity.class);
        startActivity(intent);
    }



    private void checkboxClicked() {
        if (mCheckBox.isChecked()){
            allowSignUp();
        }
        if (!mCheckBox.isChecked()) {
            disallowSignUp();
        }
    }

    private void allowSignUp(){
        mSignUpButton.setEnabled(true);
        mSignUpButton.setTextColor(Color.parseColor("#080708"));
        Log.d(TAG, "checkboxClicked: clicked true");
    }

    private void disallowSignUp(){
        mSignUpButton.setEnabled(false);
        mSignUpButton.setTextColor(Color.parseColor("#a6a6a6"));
        Log.d(TAG, "checkboxClicked: clicked flase");
    }

    private void moveToLoginScreen(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void setupTOCs() {
        signUpController.fetchTOCs().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot != null) {
                mViewText_opening.setText(documentSnapshot.getString("opening"));
                mViewText_changes.setText(documentSnapshot.getString("changes"));
                mViewText_thirdparty.setText(documentSnapshot.getString("thirdparty"));
                mViewText_entire.setText(documentSnapshot.getString("entireagreement"));
                mViewText_offer.setText(documentSnapshot.getString("offerandacceptance"));
                mViewText_termination.setText(documentSnapshot.getString("termination"));
                mViewText_banning.setText(documentSnapshot.getString("banning"));
                mViewText_data.setText(documentSnapshot.getString("data"));
                mViewText_cost.setText(documentSnapshot.getString("cost"));
                mViewText_commercial.setText(documentSnapshot.getString("commercialuse"));
                mViewText_warranty.setText(documentSnapshot.getString("warranty"));
                mViewText_acl.setText(documentSnapshot.getString("acl"));
                mViewText_ip.setText(documentSnapshot.getString("ip"));
                mViewText_userguide.setText(documentSnapshot.getString("userguideline"));
            } else {
                // Handle the case where the document does not exist or is null
            }
        }).addOnFailureListener(e -> {
            // Handle any errors here
        });
    }


    private void signUpProcess() {
        resetBorderColours();
        String email = mEdittext_Email.getText().toString();
        String password = mEdittext_Password.getText().toString();
        String first = mEdittext_FirstName.getText().toString();
        String last = mEdittext_LastName.getText().toString();
        if (validEntries(email,password,first,last)) {
            boolean success = signUpController.signup(email, password, first, last);

        }
        else {
            missingEntries();
        }
    }


}