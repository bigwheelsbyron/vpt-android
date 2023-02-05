package zxc.studio.vpt;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import zxc.studio.vpt.API.FirebaseAPI;
import zxc.studio.vpt.utilities.LocalStorageService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings_security#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings_security extends Fragment implements View.OnClickListener {

    private static final String TAG = "settings_securityfragme" ;
    private Button buttonChangeEmail;
    private Button buttonChangePassword;
    private Button buttonDeleteAccount;

    private EditText emailVerification;
    private EditText passwordVerification;
    private EditText newEmail;
    private EditText newPassword;

    private CheckBox deleteAccountCheckbox;

    private TextView incorrectDetails;

    public settings_security() {
    }
    public static settings_security newInstance() {
        settings_security fragment = new settings_security();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_security, container, false);
        setupUI(view);
        return view;
    }
    private void setupUI(View view){
        setIDS(view);
        setListeners();
    }

    private void setIDS(View view){
        buttonChangeEmail=view.findViewById(R.id.accountSettingsFragment_button_changeEmail);
        buttonChangePassword=view.findViewById(R.id.accountSettingsFragment_button_changePassword);
        buttonDeleteAccount=view.findViewById(R.id.accountSettingsFragment_button_deleteAccount);
        emailVerification=view.findViewById(R.id.accountSettingsFragment_editText_email);
        passwordVerification=view.findViewById(R.id.accountSettingsFragment_editText_password);
        newEmail=view.findViewById(R.id.accountSettingsFragment_editText_newEmail);
        newPassword=view.findViewById(R.id.accountSettingsFragment_editText_newPassword);
        deleteAccountCheckbox=view.findViewById(R.id.accountSettingsFragment_checkbox_deleteAccount);
        incorrectDetails=view.findViewById(R.id.accountSettingsFragment_textView_incorrectDetails);
    }

    private void setListeners(){
        buttonChangeEmail.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
        buttonDeleteAccount.setOnClickListener(this);
    }

    private void changeEmail(){
        resetColours();
        if (authenticateUser()){

        }
        Log.d(TAG, "changeEmail: ");
    }

    private void changePassword(){
        resetColours();
        if (authenticateUser()){

        }
        Log.d(TAG, "changePassword: ");
    }

    private void deleteAccount(){
        resetColours();
        if (authenticateUser()){

        }
        Log.d(TAG, "deleteAccount: ");

    }

    private void resetColours(){
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ViewCompat.setBackgroundTintList(emailVerification,colorStateList);
        ViewCompat.setBackgroundTintList(passwordVerification,colorStateList);
        ViewGroup.LayoutParams params = incorrectDetails.getLayoutParams();
        params.height = 0;
        incorrectDetails.setLayoutParams(params);
        incorrectDetails.setAlpha(0);
    }

    private boolean authenticateUser(){
        String email = emailVerification.getText().toString();
        String password = passwordVerification.getText().toString();
        if (email.length() != 0 && (password.length() != 0)){
            return FirebaseAPI.authenticateUser(email,password);
        } else {
            missingEntry();
        }
        return false;
    }

    private void missingEntry(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ViewGroup.LayoutParams params1 = incorrectDetails.getLayoutParams();
        params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        incorrectDetails.setLayoutParams(params1);
        incorrectDetails.setText("Please provide your details.");
        incorrectDetails.setAlpha(1);
        if (passwordVerification.getText().toString().length() == 0){
            ViewCompat.setBackgroundTintList(passwordVerification,colorStateListRed);
            passwordVerification.requestFocus();
        }
        if (emailVerification.getText().toString().length() == 0){
            ViewCompat.setBackgroundTintList(emailVerification,colorStateListRed);
            emailVerification.requestFocus();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.accountSettingsFragment_button_changeEmail: {
                changeEmail();
                break;
            }
            case R.id.accountSettingsFragment_button_changePassword: {
                changePassword();
                break;
            }
            case R.id.accountSettingsFragment_button_deleteAccount: {
                deleteAccount();
                break;
            }
        }
    }
}