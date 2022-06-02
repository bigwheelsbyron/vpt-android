package zxc.studio.vpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import zxc.studio.vpt.API.FirebaseAPI;
import zxc.studio.vpt.adapters.WorkoutRecyclerAdapter;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.User;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.ui.elements.colourManager;
import zxc.studio.vpt.ui.login.LoginActivity;
import zxc.studio.vpt.utilities.ItemDeco;
import zxc.studio.vpt.utilities.LocalStorageService;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class workout_activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "workout_activity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String username = "Byron";
    private Button logoutButton;
    private ImageView mSubstractBottom;
    private ImageView mSubstractTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userTest();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_activity);
        setUpUI();
        setTitle("VPT");
    }

    private void userTest() {
        if (firebaseSignedIn()){
            applyUserColorSchemeBackgroundObjects();
            getUserDetailsFirebase();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private boolean firebaseSignedIn(){
        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    public void setUpUI(){
        setIDS();
        setListeners();
        applyUserColorSchemeForegroundObjects();
        resizeSubtract();
        setUpNavBar();
    }

    public void setIDS(){
        logoutButton = findViewById(R.id.logout_button_main);
    }

    public void setListeners(){
        logoutButton.setOnClickListener(this);
    }

    private void applyUserColorSchemeBackgroundObjects(){
        colourManager.applyColourSchemeSubtractCustom(this);
    }

    private void applyUserColorSchemeForegroundObjects(){
        colourManager.applyColourSchemeCustom(this);
    }

    private void resizeSubtract(){
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        Log.d(TAG, "onCreate: " + dpHeight);
        Log.d(TAG, "onCreate: " + dpWidth);
        mSubstractBottom = findViewById(R.id.mainScreenSubtractBottom);
        mSubstractBottom.getLayoutParams().width = (int) outMetrics.widthPixels;
        mSubstractBottom.getLayoutParams().height = (int) ((int) outMetrics.heightPixels*.2);
        mSubstractTop = findViewById(R.id.mainScreenSubtractTop);
        mSubstractTop.getLayoutParams().width = (int) outMetrics.widthPixels;
        mSubstractTop.getLayoutParams().height = (int) ((int) outMetrics.heightPixels*.2);
    }

    private void setUpNavBar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this,R.id.frag);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    private void getUserDetailsFirebase(){
        DocumentReference userDocument = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userDocument.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: firebaseUserDetails" + e);
            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                final User user = documentSnapshot.toObject(User.class);
                LocalStorageService storageService = new LocalStorageService(getBaseContext());
                storageService.setUser(user);
            }
        });

    }

    public void hideLogout(){
        logoutButton.setAlpha(0);
    }

    public void showLogout(){
        logoutButton.setAlpha(1);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.logout_button_main: {
                colourManager.applyColourSchemeSubtractStandard(this);
                Log.d(TAG, "onClick: loggout button ");
                FirebaseAuth.getInstance().signOut();
                userTest();
                break;
            }
        }
    }

}
