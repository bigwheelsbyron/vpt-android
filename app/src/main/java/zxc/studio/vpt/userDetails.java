package zxc.studio.vpt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link userDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class userDetails extends Fragment {

    private Button buttonChangeName;
    private Button removeCoach;

    private EditText firstNameEditText;
    private EditText lastNameEditText;

    private TextView currentEmail;
    private TextView currentCoach;


    public userDetails() {
    }

    public static userDetails newInstance() {
        userDetails fragment = new userDetails();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);
        return view;
    }
}