package zxc.studio.vpt.ui.login;

import static zxc.studio.vpt.utilities.functions.dpToPx;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import zxc.studio.vpt.R;

import zxc.studio.vpt.sign_up;
import zxc.studio.vpt.workout_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login_activity";
    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;
    private TextView forgotPassword;
    private ProgressBar loadingProgressBar;
    private LinearLayout signupLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpFirebase();
        setIDS();
        viewModels();
        setListeners();
    }

    private void setUpFirebase(){
        mAuth = FirebaseAuth.getInstance();
    }

    private void setIDS(){
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        signupButton = findViewById(R.id.signupButton);
        loadingProgressBar = findViewById(R.id.loading);
        signupLayout = findViewById(R.id.linearlayout_signup);
        forgotPassword = findViewById(R.id.forgotPassword);
    }

    private void viewModels(){
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });
    }

    private void setListeners(){
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signupButton.setOnClickListener(this);
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
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) signupLayout.getLayoutParams();
        params.topMargin = 508;
    }

    private void moveDown(){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) signupLayout.getLayoutParams();
        params.topMargin = dpToPx(268);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.login: {
                login();
                break;
            }
            case R.id.signupButton: {
                signup();
                break;
            }
            case R.id.forgotPassword: {
                forgotPasswordPressed();
                break;
            }
        }
    }

    private void login(){
        resetBorderColours();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (usernameAndPasswordArentEmpty(username,password)){
            firebaseLogin(username,password);
        } else {
            missingEntry();
        }

    }

    private boolean usernameAndPasswordArentEmpty(String username, String password){
        return (username.length() != 0 && password.length() !=0);
    }

    private void firebaseLogin(String username, String password){
        signInUIReact();
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    successfulLogin();
                } else {
                    unsuccessfulLogin(task);
                }
            }
        });
    }

    private void signInUIReact(){
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    private void missingEntry(){
        if (usernameEditText.getText().toString().length() == 0){
            ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
            ViewCompat.setBackgroundTintList(usernameEditText,colorStateListRed);
        }
        if (passwordEditText.getText().toString().length() == 0){
            ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
            ViewCompat.setBackgroundTintList(passwordEditText,colorStateListRed);
        }
    }

    private void successfulLogin(){
        Log.d(TAG, "signInWithEmail:success");
        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
        navigate();
    }

    private void unsuccessfulLogin(Task<AuthResult> task){
        Log.w(TAG, "signInWithEmail:failure", task.getException());
        //TODO: Feedback based on failure type
        loadingProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
    }

    private void forgotPasswordPressed(){
        String email = usernameEditText.getText().toString();
        if (validEmail(email)){
            firebasePasswordReset(email);
        } else {
            invalidEmail();
        }
    }

    private void firebasePasswordReset(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validEmail(String email){
        return email.length() > 0;
    }

    private void invalidEmail(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ViewCompat.setBackgroundTintList(usernameEditText,colorStateListRed);
        Toast.makeText(LoginActivity.this, "Please provide your email", Toast.LENGTH_SHORT).show();
    }

    private void resetBorderColours(){
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ViewCompat.setBackgroundTintList(usernameEditText,colorStateList);
        ViewCompat.setBackgroundTintList(passwordEditText,colorStateList);
    }

    private void signup(){
        Intent intent = new Intent(this, sign_up.class);
        startActivity(intent);
    }

    private void navigate(){
        Intent intent = new Intent(this, workout_activity.class);
        startActivity(intent);
    }

}
