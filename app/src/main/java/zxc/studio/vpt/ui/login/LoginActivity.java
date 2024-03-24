package zxc.studio.vpt.ui.login;

import static zxc.studio.vpt.utilities.functions.dpToPx;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import zxc.studio.vpt.controllers.Login_Controller;
import zxc.studio.vpt.R;

import zxc.studio.vpt.sign_up;
import zxc.studio.vpt.ui.elements.colourManager;
import zxc.studio.vpt.workout_activity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Login_activity";
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;
    private TextView forgotPassword;
    private ProgressBar loadingProgressBar;
    private LinearLayout signupLayout;
    private ImageView subtractBottom;
    private Login_Controller login_controller = new Login_Controller(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        colourManager.applyColourSchemeSubtractStandard(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setIDS();
        setListeners();
    }

    private void setIDS(){
        usernameEditText = findViewById(R.id.loginActivity_editText_emailInput);
        passwordEditText = findViewById(R.id.loginActivity_editText_passwordInput);
        loginButton = findViewById(R.id.loginActivity_button_emailLogin);
        signupButton = findViewById(R.id.loginActivity_button_emailSignUp);
        loadingProgressBar = findViewById(R.id.loginActivity_progressBar_loginProcessing);
        signupLayout = findViewById(R.id.loginActivity_linearLayout_loginDetails);
        forgotPassword = findViewById(R.id.loginActivity_button_forgotPassword);
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
            case R.id.loginActivity_button_emailLogin: {
                login();
                break;
            }
            case R.id.loginActivity_button_emailSignUp: {
                signup();
                break;
            }
            case R.id.loginActivity_button_forgotPassword: {
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
        login_controller.login(username,password);
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

    public void successfulLogin(){
        Log.d(TAG, "signInWithEmail:success");
        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
        navigateToWorkouts();
    }

    public void unsuccessfulLogin(){
        loadingProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT).show();
    }

    private void forgotPasswordPressed(){
        String email = usernameEditText.getText().toString();
        if (isValidEmail(email)){
            login_controller.forgot_password(email);
        } else {
            invalidEmail();
        }
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    private void invalidEmail(){
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ViewCompat.setBackgroundTintList(usernameEditText,colorStateListRed);
        Toast.makeText(LoginActivity.this, "Please provide your email", Toast.LENGTH_LONG).show();
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

    private void navigateToWorkouts(){
        Intent intent = new Intent(this, workout_activity.class);
        startActivity(intent);
    }

}
