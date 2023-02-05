package zxc.studio.vpt;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import zxc.studio.vpt.Factories.ChartDataFactory;
import zxc.studio.vpt.adapters.historyExerciseListRecyclerAdpater;
import zxc.studio.vpt.models.ChartData;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.models.Workout;
import zxc.studio.vpt.utilities.DateFunctions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link history#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  history extends Fragment implements historyExerciseListRecyclerAdpater.OnNoteListenerHistoryExercise, View.OnClickListener {

    private CombinedChart combinedChart;
    private TextView exerciseDateRangeExerciseTextView;
    private FirebaseFirestore firebaseDB = FirebaseFirestore.getInstance();
    private ArrayList<Workout> workoutsArray = new ArrayList<>();
    private TreeMap<String, ArrayList<Exercise>> exercises = new TreeMap<>();
    private TreeMap<String, ArrayList<String>> selectedExercises = new TreeMap<>();
    private ChartData chartData = new ChartData();
    private ArrayList<Date> dates = new ArrayList<>();
    private HashMap<String,Date> dateRange = new HashMap<>();
    private String frequency = "Daily";
    private String period = "Last Month";

    public history() {
        // Required empty public constructor
    }

    public static history newInstance() {
        history fragment = new history();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_history, container, false);
        setUpUI(view);
        getHistory();
        clearData();
        dates = DateFunctions.returnLastMonth();
        dateRange.put("Start",dates.get(0));
        dateRange.put("End",dates.get(dates.size()-1));
        return view;
    }

    private void setUpUI(View view){
        setIDS(view);
        setListeners();
        hideUIDetails();
        lineChartSetColors();
        hideLogout();
    }

    private void setIDS(View view){
        combinedChart=view.findViewById(R.id.historyFragment_LineChart_exerciseGraph);
        exerciseDateRangeExerciseTextView = view.findViewById(R.id.historyFragment_textView_manage);
    }

    private void setListeners(){
        exerciseDateRangeExerciseTextView.setOnClickListener(this);
    }

    private void hideUIDetails(){
        exerciseDateRangeExerciseTextView.setAlpha(1);
    }

    private void clearData(){
        Log.d(TAG, "clearData: called");
        chartData.clearData();
        combinedChart.invalidate();
        combinedChart.clear();
    }

    private void lineChartSetColors(){
        combinedChart.setNoDataText("Please select an exercise");
        Paint paint = combinedChart.getPaint(Chart.PAINT_INFO);
        paint.setColor(Color.parseColor("#1a1b29"));
    }

    private void getHistory(){
        CollectionReference workoutsFirebaseRef = firebaseDB.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("workouts");
        workoutsFirebaseRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Workout workout = documentSnapshot.toObject(Workout.class);
                    workoutsArray.add(workout);
                }
                getWorkoutExercises();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: no workouts");
            }
        });
    }

    private void getWorkoutExercises(){
        for (int i = 0;i<workoutsArray.size();i++){
            getExercises(workoutsArray.get(i).getWorkout_id());
        }
    }

    private void getExercises(String workoutID){
        CollectionReference exercisesFirebaseRef = firebaseDB.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("workouts").document(workoutID).collection("Exercises");
        exercisesFirebaseRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Exercise exercise = documentSnapshot.toObject(Exercise.class);
                    addExercise(exercise);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: no exercises");
            }
        });
    }

    private void addExercise(Exercise exercise){
        if (hashMapIncludesExercise(exercise.getExercise_name())){
            addExerciseToHashMapExists(exercise);
        } else {
            addExerciseToHashMapNew(exercise);
        }
    }

    private boolean hashMapIncludesExercise(String exerciseName){
        return exercises.containsKey(exerciseName);
    }

    private void addExerciseToHashMapExists(Exercise exercise){
        exercises.get(exercise.getExercise_name()).add(exercise);
    }

    private void addExerciseToHashMapNew(Exercise exercise){
        exercises.put(exercise.getExercise_name(),new ArrayList<>());
        exercises.get(exercise.getExercise_name()).add(exercise);
    }

    private void hideLogout(){
        workout_activity activity = (workout_activity) getActivity();
        activity.hideLogout();
    }

    @Override
    public void onNoteClick(int position) {
    }

    @Override
    public void onChangedCheckBox(int adapterPosition, boolean isChecked) {}

    @Override
    public void onChangedCheckBox(int adapterPosition, boolean isChecked, CharSequence id) {}

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.historyFragment_textView_manage: {
                showDateRangeActivity();
                break;
            }
        }
    }

    private void showDateRangeActivity(){
        selectedExercises.clear();
        chartData.clearData();
        combinedChart.clear();
        combinedChart.invalidate();
        Intent intent = new Intent(getContext(),activity_historyDates.class);
        Bundle extras = new Bundle();
        extras.putSerializable("exercises",exercises);
        extras.putSerializable("dates",dates);
        extras.putString("period",period);
        extras.putString("frequency",frequency);
        intent.putExtras(extras);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (resultCode == RESULT_OK ) {
                HashMap passedExercises = (HashMap) data.getSerializableExtra("selectedExercises");
                selectedExercises = new TreeMap<String, ArrayList<String>>(passedExercises);
                ArrayList<Date> passedDateRange = (ArrayList<Date>) data.getSerializableExtra("dates");
                dates = new ArrayList<>(passedDateRange);
                String passedFrequency = data.getStringExtra("frequency");
                frequency = passedFrequency;
                String passedPeriod = data.getStringExtra("period");
                period = passedPeriod;
            } else {
                Log.d(TAG, "onActivityResult: no intent");
            }
        }
        Log.d(TAG, "onActivityResult: " + selectedExercises);
        clearData();
        chartData = ChartDataFactory.newChartData(selectedExercises,exercises,dates,frequency);
        updateChartData();
    }

    private void chartDataRelevantRange(){
        combinedChart.setVisibleXRangeMaximum(65f);
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        ArrayList<Date> dates = chartData.returnDates();
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                Date d = (dates.get((int) value));
                return DateFunctions.dateAndTimeToSimpleDateStringShort(d);
            }
        };
        xAxis.setValueFormatter(formatter);
        YAxis yAxisLeft = combinedChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(1);
        yAxisLeft.setGranularity(1f);
        yAxisLeft.setGranularityEnabled(true);
        YAxis yAxisRight = combinedChart.getAxisRight();
        yAxisRight.setAxisMinimum(1);
        yAxisRight.setGranularity(1f);
        yAxisRight.setGranularityEnabled(true);
    }

    private void updateChartData(){
        Log.d(TAG, "updateChartData: " + chartData.returnDates());
        chartData.setData();
        CombinedData dataSet = chartData.returnDataSet();
        combinedChart.setData(dataSet);
        combinedChart.getAxisLeft().setAxisMinimum(0);
        combinedChart.getAxisRight().setAxisMinimum(0);
        combinedChart.getAxisLeft().setDrawGridLines(false);
        combinedChart.getAxisRight().setDrawGridLines(false);
        chartDataRelevantRange();
        combinedChart.invalidate();
    }

}