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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    Fragment userDetailsFragment = new userDetails();
    Fragment paletteFragment = new pallette();
    Fragment helpFragment = new settingsHelp();
    Fragment feedbackFragment = new settings_feedback();
    Fragment aboutUsFragment = new settings_aboutus();
    Fragment termsFragment = new settings_tocs();
    Fragment privacyFragment = new settings_privacy();
    Fragment securityFragment = new settings_security();

    Fragment currentFragment;

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
        addFragments();
    }

    private void addFragments() {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        transaction.add(R.id.settingsFragment_fragment_settingsFragment, securityFragment);
        transaction.add(R.id.settingsFragment_fragment_settingsFragment, privacyFragment);
        transaction.add(R.id.settingsFragment_fragment_settingsFragment, termsFragment);
        transaction.add(R.id.settingsFragment_fragment_settingsFragment, aboutUsFragment);
        transaction.add(R.id.settingsFragment_fragment_settingsFragment, feedbackFragment);
        transaction.add(R.id.settingsFragment_fragment_settingsFragment, helpFragment);
        transaction.add(R.id.settingsFragment_fragment_settingsFragment, paletteFragment);
        transaction.add(R.id.settingsFragment_fragment_settingsFragment, userDetailsFragment);
        transaction.commit();
        currentFragment = userDetailsFragment;
    }

    private void showLogoutButton(){
        workout_activity activity = (workout_activity) getActivity();
        activity.showLogout();
    }
    private void setIDS(View view){
        buttonUserDetailsView=view.findViewById(R.id.settingsFragment_button_UserDetails_View);
        buttonPaletteView=view.findViewById(R.id.settingsFragment_button_Palette_View);
        buttonHelpView=view.findViewById(R.id.settingsFragment_button_Help_View);
        buttonFeedbackView=view.findViewById(R.id.settingsFragment_button_Feedback_View);
        buttonAboutView=view.findViewById(R.id.settingsFragment_button_About_View);
        buttonTOCView=view.findViewById(R.id.settingsFragment_button_TOC_View);
        buttonPrivacyView=view.findViewById(R.id.settingsFragment_button_Privacy_View);
        buttonAccountView=view.findViewById(R.id.settingsFragment_button_Account_View);
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
            case R.id.settingsFragment_button_UserDetails_View:{
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = childFragmentManager.beginTransaction();
                transaction.show(userDetailsFragment);
                transaction.hide(currentFragment);
                transaction.commit();
                currentFragment = userDetailsFragment;
                break;
            }
            case R.id.settingsFragment_button_Palette_View:{
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = childFragmentManager.beginTransaction();
                transaction.hide(currentFragment);
                transaction.show(paletteFragment);
                transaction.commit();
                currentFragment = paletteFragment;
                break;
            }
            case R.id.settingsFragment_button_Help_View:{
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = childFragmentManager.beginTransaction();
                transaction.hide(currentFragment);
                transaction.show(helpFragment);
                transaction.commit();
                currentFragment = helpFragment;
                break;
            }
            case R.id.settingsFragment_button_Feedback_View:{
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = childFragmentManager.beginTransaction();
                transaction.hide(currentFragment);
                transaction.show(feedbackFragment);
                transaction.commit();
                currentFragment = feedbackFragment;
                break;
            }
            case R.id.settingsFragment_button_About_View:{
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = childFragmentManager.beginTransaction();
                transaction.hide(currentFragment);
                transaction.show(aboutUsFragment);
                transaction.commit();
                currentFragment = aboutUsFragment;
                break;
            }
            case R.id.settingsFragment_button_TOC_View:{
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = childFragmentManager.beginTransaction();
                transaction.hide(currentFragment);
                transaction.show(termsFragment);
                transaction.commit();
                currentFragment = termsFragment;
                break;
            }
            case R.id.settingsFragment_button_Privacy_View:{
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = childFragmentManager.beginTransaction();
                transaction.hide(currentFragment);
                transaction.show(privacyFragment);
                transaction.commit();
                currentFragment = privacyFragment;
                break;
            }
            case R.id.settingsFragment_button_Account_View:{
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = childFragmentManager.beginTransaction();
                transaction.hide(currentFragment);
                transaction.show(securityFragment);
                transaction.commit();
                currentFragment = securityFragment;
                break;
            }
        }

    }

}