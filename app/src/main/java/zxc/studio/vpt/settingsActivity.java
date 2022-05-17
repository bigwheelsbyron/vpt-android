package zxc.studio.vpt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import zxc.studio.vpt.ui.login.LoginActivity;

public class settingsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener,View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setIds();
        setListeners();
        setData();
    }

    public void setIds(){
        buttonUserDetailsView=findViewById(R.id.button_UserDetails_View);
        buttonHelpView=findViewById(R.id.button_Help_View);
        buttonFeedbackView=findViewById(R.id.button_Feedback_View);
        buttonAboutView=findViewById(R.id.button_About_View);
        buttonTOCView=findViewById(R.id.button_TOC_View);
        buttonPrivacyView=findViewById(R.id.button_Privacy_View);
        buttonAccountView=findViewById(R.id.button_Account_View);
        buttonSuggestionSubmit=findViewById(R.id.submitButton);
        buttonChangeName=findViewById(R.id.user_savechangesbutton);
        changeEmail=findViewById(R.id.accountSettings_changeemail);
        removeCoach=findViewById(R.id.accountSettings_removeCoach);
        buttonChangeEmail=findViewById(R.id.changeEmailButton_accountsettings);
        buttonChangePassword=findViewById(R.id.changePasswordButton_accountsettings);
        buttonDeleteAccount=findViewById(R.id.deleteAccountButton_accountsettings);
        userDetailsView=findViewById(R.id.view_userDetails);
        aboutView=findViewById(R.id.view_aboutus);
        helpView=findViewById(R.id.view_Help);
        feedbackView=findViewById(R.id.view_feedback);
        tocsView=findViewById(R.id.view_tocs);
        privacyView=findViewById(R.id.view_privacy);
        accountView=findViewById(R.id.view_accountsecurity);
        suggestionEditText=findViewById(R.id.suggestionText);
        firstNameEditText=findViewById(R.id.user_editText_FirstName);
        lastNameEditText=findViewById(R.id.user_editText_LastName);
        userDetailsView.bringToFront();
        //accountView.bringToFront();
        emailVerification=findViewById(R.id.accountSettings_emailEditText);
        passwordVerification=findViewById(R.id.accountSettings_passwordEditText);
        newEmail=findViewById(R.id.editText_accountsettings_newemail);
        newPassword=findViewById(R.id.editText_accountsettings_newpassword);
        deleteAccountCheckbox=findViewById(R.id.deleteAccount_checkbox);
        incorrectDetails=findViewById(R.id.incorrectDetailsAccountSettings);
        currentCoach=findViewById(R.id.textViewCoach);
        currentEmail=findViewById(R.id.textViewEmail);
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
        final SharedPreferences userDetails = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        firstNameEditText.setText(userDetails.getString("Name_First",""));
        lastNameEditText.setText(userDetails.getString("Name_Last",""));
        currentEmail.setText(userDetails.getString("email",""));
        currentCoach.setText(userDetails.getString("coach_First","") + " " + userDetails.getString("coach_Last",""));
    }

    private void submitSuggestion(String suggestion){
        final SharedPreferences userDetails = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        DocumentReference newSuggestion =  db.collection("community").document("suggestion").collection("suggestions").document();
        Map<String, Object> setData = new HashMap<>();
        setData.put("id",userDetails.getString("uid",""));
        setData.put("body",suggestion);
        setData.put("date",Calendar.getInstance().getTime());
        setData.put("title","");
        newSuggestion.set(setData);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_UserDetails_View: {
                accountView.setAlpha(0);
                privacyView.setAlpha(0);
                tocsView.setAlpha(0);
                feedbackView.setAlpha(0);
                helpView.setAlpha(0);
                aboutView.setAlpha(0);
                userDetailsView.setAlpha(1);
                userDetailsView.bringToFront();
                break;
            }
            case R.id.button_Help_View: {
                accountView.setAlpha(0);
                privacyView.setAlpha(0);
                tocsView.setAlpha(0);
                feedbackView.setAlpha(0);
                helpView.setAlpha(1);
                helpView.bringToFront();
                aboutView.setAlpha(0);
                userDetailsView.setAlpha(0);
                break;
            }
            case R.id.button_Feedback_View: {
                accountView.setAlpha(0);
                privacyView.setAlpha(0);
                tocsView.setAlpha(0);
                feedbackView.setAlpha(1);
                feedbackView.bringToFront();
                helpView.setAlpha(0);
                aboutView.setAlpha(0);
                userDetailsView.setAlpha(0);
                break;
            }
            case R.id.button_About_View: {
                accountView.setAlpha(0);
                privacyView.setAlpha(0);
                tocsView.setAlpha(0);
                feedbackView.setAlpha(0);
                helpView.setAlpha(0);
                aboutView.setAlpha(1);
                aboutView.bringToFront();
                userDetailsView.setAlpha(0);
                break;
            }
            case R.id.button_TOC_View: {
                accountView.setAlpha(0);
                privacyView.setAlpha(0);
                tocsView.setAlpha(1);
                tocsView.bringToFront();
                feedbackView.setAlpha(0);
                helpView.setAlpha(0);
                aboutView.setAlpha(0);
                userDetailsView.setAlpha(0);
                break;
            }
            case R.id.button_Privacy_View: {
                accountView.setAlpha(0);
                privacyView.setAlpha(1);
                privacyView.bringToFront();
                tocsView.setAlpha(0);
                feedbackView.setAlpha(0);
                helpView.setAlpha(0);
                aboutView.setAlpha(0);
                userDetailsView.setAlpha(0);
                break;
            }
            case R.id.button_Account_View: {
                accountView.setAlpha(1);
                accountView.bringToFront();
                privacyView.setAlpha(0);
                tocsView.setAlpha(0);
                feedbackView.setAlpha(0);
                helpView.setAlpha(0);
                aboutView.setAlpha(0);
                userDetailsView.setAlpha(0);
                break;
            }
            case R.id.submitButton:{
                String text = suggestionEditText.getText().toString();
                suggestionEditText.setText(" ");
                submitSuggestion(text);
                break;
            }
            case R.id.accountSettings_removeCoach:{
                RemoveCoach();
                break;
            }
            case R.id.accountSettings_changeemail:{
                Log.d(TAG, "onClick: change email");
                accountView.setAlpha(1);
                accountView.bringToFront();
                privacyView.setAlpha(0);
                tocsView.setAlpha(0);
                feedbackView.setAlpha(0);
                helpView.setAlpha(0);
                aboutView.setAlpha(0);
                userDetailsView.setAlpha(0);
                break;
            }
            case R.id.user_savechangesbutton:{
                String newfirst = firstNameEditText.getText().toString();
                String newlast = lastNameEditText.getText().toString();
                final SharedPreferences userDetails = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
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
                Reauthenticateuser("delete");
                emailVerification.setText("");
                passwordVerification.setText("");
                break;
            }
            case R.id.changeEmailButton_accountsettings:{
                Reauthenticateuser("changeEmail");
                emailVerification.setText("");
                passwordVerification.setText("");
                break;
            }
            case R.id.changePasswordButton_accountsettings:{
                Reauthenticateuser("changePassword");
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

    private void RemoveCoach(){
        final SharedPreferences userDetails = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        db.collection("users").document(user.getUid()).update("coach","");
        db.collection("users").document(userDetails.getString("coach","")).collection("currentAthletes").document(user.getUid()).delete();
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

    private void Reauthenticateuser(String followup){
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ViewCompat.setBackgroundTintList(emailVerification,colorStateList);
        ViewCompat.setBackgroundTintList(passwordVerification,colorStateList);
        ViewGroup.LayoutParams params = incorrectDetails.getLayoutParams();
        params.height = 0;
        incorrectDetails.setLayoutParams(params);
        incorrectDetails.setAlpha(0);
        String email = emailVerification.getText().toString();
        String password = passwordVerification.getText().toString();
        if (email.length() > 0 && password.length()>0){
            AuthCredential credential = EmailAuthProvider.getCredential(email,password);
            user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "onSuccess: success");
                    if (followup.equals("delete")){
                        DeleteAccount();
                    } if (followup.equals("changeEmail")){
                        ChangeEmail();
                    } if (followup.equals("changePassword")){
                        ChangePassword();
                    } else {

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: failure");
                    Log.d(TAG, "onFailure: " + e);
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
            });
        } else {
            ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
            ViewGroup.LayoutParams params1 = incorrectDetails.getLayoutParams();
            params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            incorrectDetails.setLayoutParams(params1);
            incorrectDetails.setText("Please provide your details.");
            incorrectDetails.setAlpha(1);
            if (email.length() == 0){
                ViewCompat.setBackgroundTintList(emailVerification,colorStateListRed);
                emailVerification.requestFocus();
            } if (password.length() == 0){
                ViewCompat.setBackgroundTintList(passwordVerification,colorStateListRed);
                passwordVerification.requestFocus();
            }
            if (email.length() == 0 && password.length() == 0){
                ViewCompat.setBackgroundTintList(emailVerification,colorStateListRed);
                ViewCompat.setBackgroundTintList(passwordVerification,colorStateListRed);
                emailVerification.requestFocus();
            }
        }
    }

    public void DeleteAccount(){
        Log.d(TAG, "DeleteAccount:");
    }

    public void ChangeEmail(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ViewCompat.setBackgroundTintList(newEmail,colorStateList);
        String newEmailString = newEmail.getText().toString();
        if (newEmailString.length() > 0){
            user.updateEmail(newEmailString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(settingsActivity.this, "Email Changed", Toast.LENGTH_SHORT).show();
                    db.collection("users").document(user.getUid()).update("email",newEmailString);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(settingsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + e);
                }
            });
        } else {
            newEmail.requestFocus();
            ViewCompat.setBackgroundTintList(newEmail,colorStateListRed);
        }
    }

    public void ChangePassword(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ViewCompat.setBackgroundTintList(newPassword,colorStateList);
        String newPasswordString = newPassword.getText().toString();
        if (newPasswordString.length() > 0){
            user.updatePassword(newPasswordString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(settingsActivity.this, "Password Changed", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(settingsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + e);
                }
            });
        } else {
            newPassword.requestFocus();
            ViewCompat.setBackgroundTintList(newPassword,colorStateListRed);
        }
    }


    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.settings_pop_up);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.coachPage:{
                Intent intent = new Intent(this,CoachFrontPage.class);
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