package zxc.studio.vpt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import zxc.studio.vpt.ui.login.LoginActivity;

public class userstats extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private static final String TAG = "userstats";
    private LineChart lineChart;
    List<Entry> entries = new ArrayList<Entry>();
    private ArrayList<String> xAxes = new ArrayList<>();
    private ArrayList<Entry> yAxesS = new ArrayList<>();
    private ArrayList<Entry> yAxesC = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser userFB = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<String> exerciseList = new ArrayList<>();
    private HashMap<String,HashMap> masterStrengthArray = new HashMap();
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userstats);
        SetIDs();
        GetExercises();
        setListeners();
    }

    private void SetIDs(){
        lineChart=findViewById(R.id.LineChart);
        mButton1=findViewById(R.id.pushWeight);
        mButton2=findViewById(R.id.buttonReps);
        mButton3=findViewById(R.id.buttonDifficulty);
        mButton4=findViewById(R.id.buttonRPE);
    }

    private void setListeners(){
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }

    private void GetExercises(){
        db.collection("users").document(userFB.getUid()).collection("stats").document("stats").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HashMap<String,Object> map = new HashMap<>();
                map = (HashMap<String, Object>) documentSnapshot.getData();
                MasterArrayExercises(map);
            }
        });
    }

    private void MasterArrayExercises(HashMap<String, Object> map){
        ArrayList<String> masterKeys = new ArrayList<>();
        for ( String key : map.keySet() ) {
            masterKeys.add(key);
        }
        for ( String key : masterKeys ) {
            String name = key;
            String classification = (String) map.get(key);
            db.collection("users").document(userFB.getUid()).collection("stats").document(classification).collection(name).document("21-07").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (classification.equals("Strength")) {
                        HashMap<String, ArrayList<String>> exerciseData = new HashMap<>();
                        ArrayList<String> dates = new ArrayList<>();
                        ArrayList<String> difficulty = new ArrayList<>();
                        ArrayList<String> reps = new ArrayList<>();
                        ArrayList<String> weight = new ArrayList<>();
                        ArrayList<String> rpe = new ArrayList<>();
                        ArrayList<String> uom = new ArrayList<>();
                        HashMap<String,Object> dict = (HashMap<String, Object>) documentSnapshot.getData();
                        LinkedHashMap<String, Object> orderedMap = OrderDictionaries(dict);
                        Log.d(TAG, "onSuccess: " + orderedMap);
                        for ( String key : orderedMap.keySet() ) {
                            Log.d(TAG, "onSuccess: " + key);
                            ArrayList<String> cleanData = new ArrayList<>();
                            cleanData = CleanData((String) orderedMap.get(key));
                            dates.add(cleanData.get(0));
                            difficulty.add(cleanData.get(1));
                            reps.add(cleanData.get(2));
                            weight.add(cleanData.get(3));
                            rpe.add(cleanData.get(4));
                            uom.add(cleanData.get(5));
                        }
                        exerciseData.put("dateData", dates);
                        exerciseData.put("dataWeights", weight);
                        exerciseData.put("dataReps", reps);
                        exerciseData.put("dateUOM", uom);
                        exerciseData.put("dataDifficulties", difficulty);
                        exerciseData.put("dataRPEs", rpe);
                        masterStrengthArray.put(name, exerciseData);
                    }
                }
            });
        }
    }

    private LinkedHashMap<String, Object> OrderDictionaries(HashMap<String, Object> dict){
        TreeMap<Date,String> sorted = new TreeMap<>();
        LinkedHashMap<String, Object> orderedMap = new LinkedHashMap<>();
        for ( String key : dict.keySet() ) {
            String fullData = (String) dict.get(key);
            String time = fullData.substring(0,24);
            Date date = stringToDate(time, "dd/MM/yyyy HH:mm:ss.SSS");
            sorted.put(date,key);
        }
        for ( Date key: sorted.keySet()){
            orderedMap.put(sorted.get(key),dict.get(sorted.get(key)));
        }
        return orderedMap;
    }

    private Date stringToDate(String aDate,String aFormat) {
        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;
    }

    private ArrayList<String> CleanData(String data){
        String date = data.substring(0,10);
        String remainder = data.substring(25,data.length());
        int firstIndex = remainder.indexOf(".");
        String difficulty = remainder.substring(0,firstIndex);;
        remainder = remainder.substring(firstIndex+1,remainder.length());
        int secondIndex = remainder.indexOf(".");
        String reps = remainder.substring(0,secondIndex);
        remainder = remainder.substring(secondIndex+1,remainder.length());
        int thirdIndex = remainder.indexOf(".");
        String weight = remainder.substring(0,thirdIndex);
        remainder = remainder.substring(thirdIndex+1,remainder.length());
        int fourthIndex = remainder.indexOf(".");
        String rpe = remainder.substring(0,fourthIndex);
        remainder = remainder.substring(fourthIndex+1,remainder.length());
        String uom = remainder;
        ArrayList<String> returnArray = new ArrayList<>();
        returnArray.add(date);
        returnArray.add(difficulty);
        returnArray.add(reps);
        returnArray.add(weight);
        returnArray.add(rpe);
        returnArray.add(uom);
        return returnArray;
    }

    //private void SetData(Map<Integer, Map<String,Integer>> setData,Map<Integer, String> dataDates) {
    private void SetData(String exerciseName, String type) {
        HashMap<String, ArrayList<String>> exerciseData = masterStrengthArray.get(exerciseName);
        ArrayList<String> dataDifficulties = exerciseData.get("dataDifficulties");
        ArrayList<String> dateData = exerciseData.get("dateData");
        ArrayList<String> dataWeights = exerciseData.get("dataWeights");
        ArrayList<String> xAxesDates = new ArrayList<>();
        ArrayList<Entry> yAxesWeights = new ArrayList<>();
        ArrayList<Entry> yAxesDifficulties = new ArrayList<>();
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        if (type.equals("Difficulty")){
            for (int i = 0;i<dateData.size();i++){
                int difficulty = Integer.parseInt(dataDifficulties.get(i));
                int weight = Integer.parseInt(dataWeights.get(i));
                String date = dateData.get(i);
                xAxesDates.add(date);
                yAxesDifficulties.add(new Entry(i,difficulty));
            }
            LineDataSet lineDataSet1 = new LineDataSet(yAxesDifficulties,type);
            lineDataSet1.setDrawCircles(false);
            lineDataSet1.setColor(Color.BLUE);
            lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSets.add(lineDataSet1);
            lineChart.setData(new LineData(lineDataSets));
        }
        lineChart.setVisibleXRangeMaximum(65f);
        final String[] quarters = DatesDictionary(xAxesDates);

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return quarters[(int) value];
            }
        };

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        lineChart.invalidate();
    }

    private String[] DatesDictionary(ArrayList<String> Dates){
        String[] returnDates2 = new String[Dates.size()];
        Dates.toArray(returnDates2);
        return returnDates2;
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.coachPage:{
                Intent intent = new Intent(this,CoachFrontPage.class);
                startActivity(intent);
                break;
            }
            case R.id.athlete:{
                Intent intent = new Intent(this,workout_activity.class);
                startActivity(intent);
                break;
                //return false;
            }
            case R.id.logout:{
                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, "onClick: firebase user signed out");
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pushWeight: {
                SetData("New Exercise Name","Weight");
                break;
            }
            case R.id.buttonReps: {
                SetData("New Exercise Name","Reps");
                break;
            }
            case R.id.buttonDifficulty: {
                SetData("New Exercise Name","Difficulty");
                break;
            }
            case R.id.buttonRPE: {
                SetData("New Exercise Name","RPE");
                break;
            }
        }
    }
}