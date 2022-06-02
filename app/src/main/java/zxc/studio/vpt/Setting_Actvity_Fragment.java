package zxc.studio.vpt;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import zxc.studio.vpt.API.FirebaseAPI;
import zxc.studio.vpt.models.Suggestion;
import zxc.studio.vpt.models.User;
import zxc.studio.vpt.utilities.DateFunctions;
import zxc.studio.vpt.utilities.LocalStorageService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting_Actvity_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting_Actvity_Fragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "settings_activity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    private Button buttonUserDetailsView;
    private Button buttonHelpView;
    private Button buttonFeedbackView;
    private Button buttonAboutView;
    private Button buttonTOCView;
    private Button buttonPrivacyView;
    private Button buttonAccountView;
    private Button buttonSuggestionSubmit;
    private Button buttonChangeName;
    private Button removeCoach;
    private Button changeEmail;
    private Button buttonChangeEmail;
    private Button buttonChangePassword;
    private Button buttonDeleteAccount;
    private EditText suggestionEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailVerification;
    private EditText passwordVerification;
    private EditText newEmail;
    private EditText newPassword;
    private TextView incorrectDetails;
    private TextView currentEmail;
    private TextView currentCoach;
    private CheckBox deleteAccountCheckbox;
    private LinearLayout userDetailsView;
    private LinearLayout aboutView;
    private LinearLayout helpView;
    private LinearLayout feedbackView;
    private LinearLayout tocsView;
    private LinearLayout privacyView;
    private LinearLayout accountView;

    public Setting_Actvity_Fragment() {
        // Required empty public constructor
    }
    public static Setting_Actvity_Fragment newInstance(String param1, String param2) {
        Setting_Actvity_Fragment fragment = new Setting_Actvity_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting__actvity_, container, false);
        setupUI(view);
        setData();
        return view;
    }

    private void setupUI(View view){
        showLogoutButton();
        setIDS(view);
        setListeners();
    }

    private void showLogoutButton(){
        workout_activity activity = (workout_activity) getActivity();
        activity.showLogout();
    }

    private void setIDS(View view){
        buttonUserDetailsView=view.findViewById(R.id.button_UserDetails_View);
        buttonHelpView=view.findViewById(R.id.button_Help_View);
        buttonFeedbackView=view.findViewById(R.id.button_Feedback_View);
        buttonAboutView=view.findViewById(R.id.button_About_View);
        buttonTOCView=view.findViewById(R.id.button_TOC_View);
        buttonPrivacyView=view.findViewById(R.id.button_Privacy_View);
        buttonAccountView=view.findViewById(R.id.button_Account_View);
        buttonSuggestionSubmit=view.findViewById(R.id.submitButton);
        buttonChangeName=view.findViewById(R.id.user_savechangesbutton);
        changeEmail=view.findViewById(R.id.accountSettings_changeemail);
        removeCoach=view.findViewById(R.id.accountSettings_removeCoach);
        buttonChangeEmail=view.findViewById(R.id.changeEmailButton_accountsettings);
        buttonChangePassword=view.findViewById(R.id.changePasswordButton_accountsettings);
        buttonDeleteAccount=view.findViewById(R.id.deleteAccountButton_accountsettings);
        userDetailsView=view.findViewById(R.id.view_userDetails);
        aboutView=view.findViewById(R.id.view_aboutus);
        helpView=view.findViewById(R.id.view_Help);
        feedbackView=view.findViewById(R.id.view_feedback);
        tocsView=view.findViewById(R.id.view_tocs);
        privacyView=view.findViewById(R.id.view_privacy);
        accountView=view.findViewById(R.id.view_accountsecurity);
        suggestionEditText=view.findViewById(R.id.suggestionText);
        firstNameEditText=view.findViewById(R.id.user_editText_FirstName);
        lastNameEditText=view.findViewById(R.id.user_editText_LastName);
        userDetailsView.bringToFront();
        emailVerification=view.findViewById(R.id.accountSettings_emailEditText);
        passwordVerification=view.findViewById(R.id.accountSettings_passwordEditText);
        newEmail=view.findViewById(R.id.editText_accountsettings_newemail);
        newPassword=view.findViewById(R.id.editText_accountsettings_newpassword);
        deleteAccountCheckbox=view.findViewById(R.id.deleteAccount_checkbox);
        incorrectDetails=view.findViewById(R.id.incorrectDetailsAccountSettings);
        currentCoach=view.findViewById(R.id.textViewCoach);
        currentEmail=view.findViewById(R.id.textViewEmail);
    }

    private void setListeners(){
        buttonUserDetailsView.setOnClickListener(this);
        buttonHelpView.setOnClickListener(this);
        buttonFeedbackView.setOnClickListener(this);
        buttonAboutView.setOnClickListener(this);
        buttonTOCView.setOnClickListener(this);
        buttonPrivacyView.setOnClickListener(this);
        buttonAccountView.setOnClickListener(this);
        buttonSuggestionSubmit.setOnClickListener(this);
        removeCoach.setOnClickListener(this);
        changeEmail.setOnClickListener(this);
        buttonChangeName.setOnClickListener(this);
        buttonChangeEmail.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
        buttonDeleteAccount.setOnClickListener(this);
        deleteAccountCheckbox.setOnClickListener(this);
    }

    private void setData(){
        LocalStorageService storageService = new LocalStorageService(this.getActivity());
        User user = storageService.getUser();
        firstNameEditText.setText(user.getUser_firstName());
        lastNameEditText.setText(user.getUser_lastName());
        currentEmail.setText(user.getUser_email());
        currentCoach.setText(user.getUser_coach());
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_UserDetails_View: {
                showUserDetailsView();
                break;
            }
            case R.id.button_Help_View: {
                showHelpView();
                break;
            }
            case R.id.button_Feedback_View: {
                showFeedbackView();
                break;
            }
            case R.id.button_About_View: {
                showAboutView();
                break;
            }
            case R.id.button_TOC_View: {
                showTOCView();
                break;
            }
            case R.id.button_Privacy_View: {
                showPrivacyView();
                break;
            }
            case R.id.button_Account_View: {
                showAccountView();
                break;
            }
            case R.id.submitButton:{
                suggestion();
                break;
            }
            case R.id.accountSettings_removeCoach:{
                removeCoach();
                break;
            }
            case R.id.accountSettings_changeemail:{
                changeEmailUserDetailsButton();
                break;
            }
            case R.id.changeEmailButton_accountsettings:{
                reauthenticateUser("email");
                emailVerification.setText("");
                passwordVerification.setText("");
                break;
            }
            case R.id.user_savechangesbutton:{
                String newfirst = firstNameEditText.getText().toString();
                String newlast = lastNameEditText.getText().toString();
                final SharedPreferences userDetails = this.getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = userDetails.edit();
                String storedFirst = userDetails.getString("Name_First","");
                String storedLast = userDetails.getString("Name_Last","");
                if (!newfirst.equals(storedFirst)){
                    db.collection("users").document(userDetails.getString("uid","")).update("Name_First",newfirst);
                    editor.putString("Name_First",newfirst);
                } else {
                }
                if (!newlast.equals(storedLast)){
                    db.collection("users").document(userDetails.getString("uid","")).update("Name_Last",newlast);
                    editor.putString("Name_Last",newlast);
                } else {
                }
                break;
            }
            case R.id.deleteAccountButton_accountsettings:{
                reauthenticateUser("delete");
                emailVerification.setText("");
                passwordVerification.setText("");
                break;
            }
            case R.id.changePasswordButton_accountsettings:{
                reauthenticateUser("password");
                emailVerification.setText("");
                passwordVerification.setText("");
                break;
            }
            case R.id.deleteAccount_checkbox:{
                Log.d(TAG, "onClick: checkbox");
                CheckBoxClicked();
                break;
            }
        }
    }

    private void showUserDetailsView(){
        accountView.setAlpha(0);
        privacyView.setAlpha(0);
        tocsView.setAlpha(0);
        feedbackView.setAlpha(0);
        helpView.setAlpha(0);
        aboutView.setAlpha(0);
        userDetailsView.setAlpha(1);
        userDetailsView.bringToFront();
    }

    private void showHelpView(){
        accountView.setAlpha(0);
        privacyView.setAlpha(0);
        tocsView.setAlpha(0);
        feedbackView.setAlpha(0);
        helpView.setAlpha(1);
        helpView.bringToFront();
        aboutView.setAlpha(0);
        userDetailsView.setAlpha(0);
    }

    private void showFeedbackView(){
        accountView.setAlpha(0);
        privacyView.setAlpha(0);
        tocsView.setAlpha(0);
        feedbackView.setAlpha(1);
        feedbackView.bringToFront();
        helpView.setAlpha(0);
        aboutView.setAlpha(0);
        userDetailsView.setAlpha(0);
    }

    private void showAboutView(){
        accountView.setAlpha(0);
        privacyView.setAlpha(0);
        tocsView.setAlpha(0);
        feedbackView.setAlpha(0);
        helpView.setAlpha(0);
        aboutView.setAlpha(1);
        aboutView.bringToFront();
        userDetailsView.setAlpha(0);
    }

    private void showTOCView(){
        accountView.setAlpha(0);
        privacyView.setAlpha(0);
        tocsView.setAlpha(1);
        tocsView.bringToFront();
        feedbackView.setAlpha(0);
        helpView.setAlpha(0);
        aboutView.setAlpha(0);
        userDetailsView.setAlpha(0);
    }

    private void showPrivacyView(){
        accountView.setAlpha(0);
        privacyView.setAlpha(1);
        privacyView.bringToFront();
        tocsView.setAlpha(0);
        feedbackView.setAlpha(0);
        helpView.setAlpha(0);
        aboutView.setAlpha(0);
        userDetailsView.setAlpha(0);
    }

    private void showAccountView(){
        accountView.setAlpha(1);
        accountView.bringToFront();
        privacyView.setAlpha(0);
        tocsView.setAlpha(0);
        feedbackView.setAlpha(0);
        helpView.setAlpha(0);
        aboutView.setAlpha(0);
        userDetailsView.setAlpha(0);
    }

    private void suggestion(){
        String text = suggestionEditText.getText().toString();
        suggestionEditText.setText(" ");
        submitSuggestion(text);
    }

    private void submitSuggestion(String suggestionText){
        Suggestion suggestion = new Suggestion(user.getUid(),suggestionText, DateFunctions.getCurrentDateAndTime());
        FirebaseAPI.newSuggestion(suggestion);
    }

    private void removeCoach(){
        db.collection("users").document(user.getUid()).update("coach","");
        LocalStorageService storageService = new LocalStorageService(this.getActivity());
        User user = storageService.getUser();
        db.collection("users").document(user.getUser_coach()).collection("currentAthletes").document(user.getUser_firebase_id()).delete();
    }

    private void changeEmailUserDetailsButton(){
        accountView.setAlpha(1);
        accountView.bringToFront();
        privacyView.setAlpha(0);
        tocsView.setAlpha(0);
        feedbackView.setAlpha(0);
        helpView.setAlpha(0);
        aboutView.setAlpha(0);
        userDetailsView.setAlpha(0);
        newEmail.requestFocus();
    }

    private void reauthenticateUser(String changeType){
        resetColours();
        String email = emailVerification.getText().toString();
        String password = passwordVerification.getText().toString();
        if (emailAndPasswordArentEmpty(email,password)){
            AuthCredential credential = EmailAuthProvider.getCredential(email,password);
            user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    if (changeType.equals("delete")){
                        deleteAccount();
                    }
                    if (changeType.equals("email")){
                        changeEmail();
                    }
                    if (changeType.equals("password")){
                        changePassword();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    failedAuthentication();
                }
            });
        } else {
            missingEntry();
        }
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

    private boolean emailAndPasswordArentEmpty(String username, String password){
        return (username.length() != 0 && password.length() !=0);
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

    private void failedAuthentication(){
        ViewGroup.LayoutParams params = incorrectDetails.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        incorrectDetails.setLayoutParams(params);
        incorrectDetails.setText("Incorrect Details");
        incorrectDetails.setAlpha(1);
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ViewCompat.setBackgroundTintList(emailVerification,colorStateListRed);
        ViewCompat.setBackgroundTintList(passwordVerification,colorStateListRed);
        emailVerification.requestFocus();
    }


    public void deleteAccount(){
        Log.d(TAG, "DeleteAccount:");
    }

    public void changeEmail(){
        resetColours();
        String newEmailString = newEmail.getText().toString();
        if (validEmail(newEmailString)){
            user.updateEmail(newEmailString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(), "Email Changed", Toast.LENGTH_SHORT).show();
                    db.collection("users").document(user.getUid()).update("email",newEmailString);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + e);
                }
            });
        } else {
            missingNewEmail();
        }
    }

    public Boolean validEmail(String newEmailString){
        return (newEmailString.length() > 0);
    }

    private void missingNewEmail(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ViewCompat.setBackgroundTintList(newEmail,colorStateListRed);
    }

    public void changePassword(){
        resetColours();
        String newPasswordString = newPassword.getText().toString();
        if (validPassword(newPasswordString)){
            user.updatePassword(newPasswordString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + e);
                }
            });
        } else {
            missingNewPassword();
        }
    }

    public Boolean validPassword(String newPasswordString){
        return (newPasswordString.length() > 0);
    }

    private void missingNewPassword(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ViewCompat.setBackgroundTintList(newPassword,colorStateListRed);
    }

    private void CheckBoxClicked(){
        if (deleteAccountCheckbox.isChecked()){
            buttonDeleteAccount.setEnabled(true);
            buttonDeleteAccount.setTextColor(Color.parseColor("#080708"));
        } if (!deleteAccountCheckbox.isChecked()) {
            buttonDeleteAccount.setEnabled(false);
            buttonDeleteAccount.setTextColor(Color.parseColor("#a6a6a6"));
        }
    }

}