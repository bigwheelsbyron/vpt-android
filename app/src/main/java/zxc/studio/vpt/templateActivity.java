package zxc.studio.vpt;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import zxc.studio.vpt.R;

import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class templateActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner mTemplatesSpinner;
    private Button mOverrideButton;
    private Button mSaveNewButton;
    private Button mOpenTemplateButton;
    private Button cancelButton;
    private String currentTemplateName;
    private EditText templateNameEditText;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private static final String TAG = "templateActivity";
    public static ArrayList<Exercise> mExercises = new ArrayList<>();
    public static HashMap<Integer,Integer> mExersercisesSets = new HashMap<>();
    private ArrayList<Template> mTemplates = new ArrayList<>();
    private ArrayList<String> mTemplateTitles = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_template);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setIds();
        setListeners();
        getIncomingIntent();
        templateSpinnerAdapters();
        Log.d(TAG, "onCreate: " + currentTemplateName);
    }

    private void setListeners() {
        mOverrideButton.setOnClickListener(this);
        mSaveNewButton.setOnClickListener(this);
        mOpenTemplateButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    private void navigateToCoachMainWithTemplate(String templateID,String templateName){
        Coach_Workout_Activity_fragment.templateSelected(templateID,templateName);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.overrideButton: {
                overrideFunction();
                break;
            }
            case R.id.saveAsNewButton: {
                saveFunction();
                break;
            }
            case R.id.openTemplateButton: {
                ColorStateList colorStateListB = ColorStateList.valueOf(Color.BLACK);
                ViewCompat.setBackgroundTintList(mTemplatesSpinner,colorStateListB);
                if (mTemplatesSpinner.getSelectedItemPosition() == 0) {
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
                    ViewCompat.setBackgroundTintList(mTemplatesSpinner,colorStateList);
                    Toast.makeText(this, "Pick a template", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "load: none selected");
                } else {
                    int pos = mTemplateTitles.indexOf(mTemplatesSpinner.getSelectedItem().toString())-1;
                    Template template = mTemplates.get(pos);
                    navigateToCoachMainWithTemplate(template.getTemplate_ID(),template.getTemplateName());
                }
                break;
            }
            case R.id.dismissButton: {
                finish();
                break;
            }
        }
    }

    private void overrideFunction() {
        int pos = mTemplateTitles.indexOf(mTemplatesSpinner.getSelectedItem().toString())-1;
        Template template = mTemplates.get(pos);
        DocumentReference oldExerciseDocRef = db.collection("users").document(user.getUid()).collection("coachResourcesTemplates").document(template.getTemplate_ID());
        oldExerciseDocRef.delete();
        saveFunction();
    }

    private void saveFunction() {
        ColorStateList colorStateList = ColorStateList.valueOf(Color.LTGRAY);
        ViewCompat.setBackgroundTintList(templateNameEditText,colorStateList);
        String templateName = templateNameEditText.getText().toString();
        if (templateName.length() == 0){
            ColorStateList colorStateListRed = ColorStateList.valueOf(Color.RED);
            ViewCompat.setBackgroundTintList(templateNameEditText,colorStateListRed);
            templateNameEditText.requestFocus();
        } else {
            DocumentReference newTemplateDocRef = db.collection("users").document(user.getUid()).collection("coachResourcesTemplates").document();
            Template newTemplate = new Template(user.getUid(),templateName,newTemplateDocRef.getId());
            newTemplateDocRef.set(newTemplate);
            insertExercises(newTemplateDocRef.getId());
        }
    }

    private void insertExercises(String id) {
        for (int i = 0; i < mExercises.size(); i++){
            DocumentReference newExerciseDocRef = db.collection("users").document(user.getUid()).collection("coachResourcesTemplates").document(id).collection("exercises").document();
            Exercise e = mExercises.get(i);
            int setNo = mExersercisesSets.get(mExercises.get(i).getExercise_sequence());
            e.setExercise_id(newExerciseDocRef.getId());
            e.setExercise_workout_id(id);
            newExerciseDocRef.set(e);
            newExerciseDocRef.update("sets",setNo);
        }
    }

    public void templateSpinnerAdapters(){
        ArrayAdapter<String> templateAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,mTemplateTitles);
        templateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTemplatesSpinner.setAdapter(templateAdapter);
    }

    private void setIds() {
        mTemplatesSpinner=findViewById(R.id.templateSpinnerChooser);
        mOverrideButton=findViewById(R.id.overrideButton);
        mSaveNewButton=findViewById(R.id.saveAsNewButton);
        mOpenTemplateButton=findViewById(R.id.openTemplateButton);
        cancelButton=findViewById(R.id.dismissButton);
        templateNameEditText=findViewById(R.id.editTextTextPersonName);
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("exercises")){
            Intent i = getIntent();
            mExercises = i.getParcelableArrayListExtra("exercises");
            mTemplates = i.getParcelableArrayListExtra("templates");
            mTemplateTitles = i.getStringArrayListExtra("templateTitles");
            mExersercisesSets = (HashMap<Integer, Integer>) i.getSerializableExtra("exersercisesSets");
            currentTemplateName = i.getStringExtra("templateName");
            templateNameEditText.setText(currentTemplateName);
        } else {
            Log.d(TAG, "getIncomingIntent: no incoming intent");
        }
    }
}