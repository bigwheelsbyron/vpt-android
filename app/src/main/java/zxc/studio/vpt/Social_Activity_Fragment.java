package zxc.studio.vpt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Social_Activity_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Social_Activity_Fragment extends Fragment implements View.OnClickListener {

    private Button mSocialToCoachMainButton;

    public Social_Activity_Fragment() {
        // Required empty public constructor
    }
    public static Social_Activity_Fragment newInstance() {
        Social_Activity_Fragment fragment = new Social_Activity_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavController navController = Navigation.findNavController(getActivity(), R.id.mainDetailsFragment);
        navController.navigate(R.id.action_social_Activity_Fragment_to_coach_Main_Activity_Fragmenet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social__activity_, container, false);
        mSocialToCoachMainButton = view.findViewById(R.id.button_socialmainview);
        mSocialToCoachMainButton.setOnClickListener(this);
        setupUI(view);
        return view;
    }

    private void setupUI(View view){
        hideLogout();
    }

    private void hideLogout(){
        workout_activity activity = (workout_activity) getActivity();
        activity.hideLogout();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_socialmainview:{
                NavController navController = Navigation.findNavController(getActivity(), R.id.mainDetailsFragment);
                navController.navigate(R.id.action_social_Activity_Fragment_to_coach_Main_Activity_Fragmenet);
            }
        }
    }
}