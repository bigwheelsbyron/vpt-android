package zxc.studio.vpt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import zxc.studio.vpt.API.FirebaseAPI;
import zxc.studio.vpt.models.Suggestion;
import zxc.studio.vpt.models.User;
import zxc.studio.vpt.ui.elements.colourManager;
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
    private Button buttonPaletteView;
    private Button buttonHelpView;
    private Button buttonFeedbackView;
    private Button buttonAboutView;
    private Button buttonTOCView;
    private Button buttonPrivacyView;
    private Button buttonAccountView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting__actvity_, container, false);
        setupUI(view);
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
//        buttonUserDetailsView=view.findViewById(R.id.button_UserDetails_View);
//        buttonPaletteView=view.findViewById(R.id.button_Palette_View);
//        buttonHelpView=view.findViewById(R.id.button_Help_View);
//        buttonFeedbackView=view.findViewById(R.id.button_Feedback_View);
//        buttonAboutView=view.findViewById(R.id.button_About_View);
//        buttonTOCView=view.findViewById(R.id.button_TOC_View);
//        buttonPrivacyView=view.findViewById(R.id.button_Privacy_View);
//        buttonAccountView=view.findViewById(R.id.button_Account_View);
    }

    private void setListeners(){
        buttonUserDetailsView.setOnClickListener(this);
        buttonPaletteView.setOnClickListener(this);
        buttonHelpView.setOnClickListener(this);
        buttonFeedbackView.setOnClickListener(this);
        buttonAboutView.setOnClickListener(this);
        buttonTOCView.setOnClickListener(this);
        buttonPrivacyView.setOnClickListener(this);
        buttonAccountView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

        }

    }


}