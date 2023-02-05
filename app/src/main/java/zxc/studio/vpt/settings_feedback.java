package zxc.studio.vpt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import zxc.studio.vpt.API.FirebaseAPI;
import zxc.studio.vpt.models.Suggestion;
import zxc.studio.vpt.utilities.DateFunctions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settings_feedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settings_feedback extends Fragment implements View.OnClickListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private Button buttonSuggestionSubmit;
    private EditText suggestionEditText;

    public settings_feedback() {
    }

    public static settings_feedback newInstance() {
        settings_feedback fragment = new settings_feedback();
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
        View view = inflater.inflate(R.layout.fragment_settings_feedback, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view){
        setIDS(view);
        setListeners();
    }

    private void setIDS(View view){
        buttonSuggestionSubmit=view.findViewById(R.id.feedbackFragment_button_submit);
        suggestionEditText=view.findViewById(R.id.feedbackFragment_editText_suggestionText);
    }

    private void setListeners(){
        buttonSuggestionSubmit.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.feedbackFragment_button_submit: {
                suggestion();
                break;
            }
        }
    }
}