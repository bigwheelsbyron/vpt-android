package zxc.studio.vpt;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class workoutoutDetails extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private TextView moodValue;
    private TextView sleepValue;
    private TextView weightValue;
    private TextView caloriesValue;
    private TextView dateValue;
    private Float mSleep;
    private Float mWeight;
    private int mCalories;
    private int mMood;
    private String mDate;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workoutout_details);
        setIDs();
        setData();
        setListeners();
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    private void setIDs(){
        moodValue=findViewById(R.id.moodValue);
        sleepValue=findViewById(R.id.sleepValue);
        weightValue=findViewById(R.id.weightValue);
        caloriesValue=findViewById(R.id.caloriesValue);
        dateValue=findViewById(R.id.dateValue);
        updateButton=findViewById(R.id.updateButton);
    }

    private void setData(){
        mDate = getIntent().getStringExtra("workout_date");
        mMood =  getIntent().getIntExtra("workout_mood",0);
        mSleep = getIntent().getFloatExtra("workout_sleep",0);
        mWeight = getIntent().getFloatExtra("workout_weight",0);
        mCalories = getIntent().getIntExtra("workout_food",0);
        Log.d(TAG, "setData: " + mCalories);
        Log.d(TAG, "setData: " + mDate);
        Log.d(TAG, "setData: " + mSleep);
        Log.d(TAG, "setData: " + mMood);
        Log.d(TAG, "setData: " + mWeight);
        moodValue.setText(String.valueOf(mMood));
        dateValue.setText(String.valueOf(mDate));
        sleepValue.setText(String.valueOf(mSleep));
        weightValue.setText(String.valueOf(mWeight));
        caloriesValue.setText(String.valueOf(mCalories));
    }

    private void setListeners(){
        updateButton.setOnClickListener(this);
    }

    private void updateValues(){
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
        ViewCompat.setBackgroundTintList(moodValue,colorStateList);
        ViewCompat.setBackgroundTintList(sleepValue,colorStateList);
        ViewCompat.setBackgroundTintList(weightValue,colorStateList);
        ViewCompat.setBackgroundTintList(caloriesValue,colorStateList);
        String mV = String.valueOf(moodValue.getText());
        String sV = String.valueOf(sleepValue.getText());
        String wV = String.valueOf(weightValue.getText());
        String fV = String.valueOf(caloriesValue.getText());
        boolean mB = isInteger(mV);
        boolean sB = isFloat(sV);
        boolean wB = isFloat(wV);
        boolean fB = isInteger(fV);
        if (mB && sB && wB && fB){
            individual_workout_activity.updateMainActivityWorkout(sV,wV,mV,fV);
            finish();
        } else {
            if (!mB){
                Log.d(TAG, "updateValues: not mv");
                ViewCompat.setBackgroundTintList(moodValue,colorStateListRed);
            }
            if (!sB){
                Log.d(TAG, "updateValues: not sv");
                ViewCompat.setBackgroundTintList(sleepValue,colorStateListRed);
            }
            if (!wB){
                Log.d(TAG, "updateValues: not wv");
                ViewCompat.setBackgroundTintList(weightValue,colorStateListRed);
            }
            if (!fB){
                Log.d(TAG, "updateValues: not fv");
                ViewCompat.setBackgroundTintList(caloriesValue,colorStateListRed);
            }
        }
    }

    public static boolean isFloat(String st) {
        Float s;
        try {
            s = Float.parseFloat(st);
            return true;
        } catch (NumberFormatException ex) {
            // Not a float
            return false;
        }
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.updateButton: {
                updateValues();
                break;
            }
        }
    }
}