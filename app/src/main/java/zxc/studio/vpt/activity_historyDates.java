package zxc.studio.vpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import zxc.studio.vpt.adapters.historyExerciseListRecyclerAdpater;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.utilities.DateFunctions;
import zxc.studio.vpt.utilities.ItemDeco;

public class activity_historyDates extends AppCompatActivity implements View.OnClickListener, historyExerciseListRecyclerAdpater.OnNoteListenerHistoryExercise {

    private static final String TAG = "activityHistory";
    private TreeMap<String, ArrayList<Exercise>> mExercise;
    private TreeMap<String, ArrayList<String>> selectedExercises = new TreeMap<>();
    private String mFrequency;
    private ArrayList<Date> mDates;
    private String mPeriod = "Last Month";
    private TextView startDateTextView;
    private TextView endDateTextView;
    private TextView periodOptionsTextView;
    private LinearLayout periodOptionsLinearLayout;
    private TextView periodOptionLastMonth;
    private TextView periodoptionLastTwoMonths;
    private TextView periodOptionLastQuarter;
    private TextView periodOptionLastHalfYear;
    private TextView periodOptionLastYear;
    private TextView periodOptionAll;
    private TextView frequencyOptionsTextView;
    private LinearLayout frequencyOptionsLinearLayout;
    private TextView frequencyOptionDaily;
    private TextView frequencyOptionWeekly;
    private TextView frequencyOptionFortnightly;
    private TextView frequencyOptionMonthly;
    private TextView frequencyOptionQuarterly;
    private RecyclerView exerciseOptionsRecyclerView;
    private historyExerciseListRecyclerAdpater exerciseListAdapter;

    public activity_historyDates() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_dates);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getExtras();
        setUpUI();
    }

    private void getExtras(){
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null) {
            HashMap exercises = (HashMap) bundle.getSerializable("exercises");
            ArrayList<Date> dates = (ArrayList<Date>) bundle.getSerializable("dates");
            String frequency = bundle.getString("frequency");
            String period = bundle.getString("period");
            mExercise = new TreeMap<String, ArrayList<Exercise>>(exercises);
            mDates = dates;
            mFrequency = frequency;
            mPeriod = period;
            Log.d(TAG, "getExtras: " + mExercise);
        } else {
            Log.d(TAG, "getExtras: no extras????");
        }
    }

    private void setUpUI(){
        setIDS();
        setListeners();
        setTexts();
        setRecyclerView();
    }

    private void setListeners(){
        startDateTextView.setOnClickListener(this);
        endDateTextView.setOnClickListener(this);
        periodOptionsTextView.setOnClickListener(this);
        periodOptionLastMonth.setOnClickListener(this);
        periodoptionLastTwoMonths.setOnClickListener(this);
        periodOptionLastQuarter.setOnClickListener(this);
        periodOptionLastHalfYear.setOnClickListener(this);
        periodOptionLastYear.setOnClickListener(this);
        periodOptionAll.setOnClickListener(this);
        frequencyOptionsTextView.setOnClickListener(this);
        frequencyOptionDaily.setOnClickListener(this);
        frequencyOptionWeekly.setOnClickListener(this);
        frequencyOptionFortnightly.setOnClickListener(this);
        frequencyOptionMonthly.setOnClickListener(this);
        frequencyOptionQuarterly.setOnClickListener(this);
    }

    private void setTexts(){
        Date currentDate = mDates.get(0);
        String currentDateString = DateFunctions.dateAndTimeToSimpleDateString(currentDate);
        Date decrementedDate = mDates.get(mDates.size()-1);
        String decrementedDateString = DateFunctions.dateAndTimeToSimpleDateString(decrementedDate);
        endDateTextView.setText(currentDateString);
        startDateTextView.setText(decrementedDateString);
        frequencyOptionsTextView.setText(mFrequency);
        periodOptionsTextView.setText(mPeriod);
    }

    private void setIDS(){
        startDateTextView = findViewById(R.id.historyDatesActivity_TextView_pickStart);
        endDateTextView = findViewById(R.id.historyDatesActivity_TextView_pickEnd);
        periodOptionsTextView = findViewById(R.id.historyDatesActivity_TextView_periodOptions);
        periodOptionsLinearLayout = findViewById(R.id.historyDatesActivity_LinearLayout_periodOptionsExpandable);
        periodOptionLastMonth = findViewById(R.id.historyDatesActivity_TextView_periodOptionsLastMonth);
        periodoptionLastTwoMonths = findViewById(R.id.historyDatesActivity_TextView_periodOptionsLastTwoMonth);
        periodOptionLastQuarter = findViewById(R.id.historyDatesActivity_TextView_periodOptionsLastQuarter);
        periodOptionLastHalfYear = findViewById(R.id.historyDatesActivity_TextView_periodOptionsLastHalfYear);
        periodOptionLastYear = findViewById(R.id.historyDatesActivity_TextView_periodOptionsLastYear);
        periodOptionAll = findViewById(R.id.historyDatesActivity_TextView_periodOptionsAll);
        frequencyOptionsTextView = findViewById(R.id.historyDatesActivity_TextView_frequencyOptions);
        frequencyOptionsLinearLayout = findViewById(R.id.historyDatesActivity_LinearLayout_frequencyOptionsExpandable);
        frequencyOptionDaily = findViewById(R.id.historyDatesActivity_TextView_frequencyOptionsDaily);
        frequencyOptionWeekly = findViewById(R.id.historyDatesActivity_TextView_frequencyOptionsWeekly);
        frequencyOptionFortnightly = findViewById(R.id.historyDatesActivity_TextView_frequencyOptionsFortnightly);
        frequencyOptionMonthly = findViewById(R.id.historyDatesActivity_TextView_frequencyOptionsMonthly);
        frequencyOptionQuarterly = findViewById(R.id.historyDatesActivity_TextView_frequencyOptionsQuarterly);
        exerciseOptionsRecyclerView = findViewById(R.id.historyDatesActivity_recyclerView_exercises);
    }

    private void setRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        exerciseOptionsRecyclerView.setLayoutManager(linearLayoutManager);
        ItemDeco itemDeco=new ItemDeco(20);
        exerciseOptionsRecyclerView.addItemDecoration(itemDeco);
        exerciseListAdapter = new historyExerciseListRecyclerAdpater(mExercise,this);
        exerciseOptionsRecyclerView.setAdapter(exerciseListAdapter);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.historyDatesActivity_TextView_pickStart: {
                pickANewStartDate();
                break;
            }
            case R.id.historyDatesActivity_TextView_pickEnd: {
                pickANewEndDate();
                break;
            }
            case R.id.historyDatesActivity_TextView_periodOptions: {
                showPeriodOptions();
                break;
            }
            case R.id.historyDatesActivity_TextView_periodOptionsLastMonth: {
                periodOptionsLastMonth();
                break;
            }
            case R.id.historyDatesActivity_TextView_periodOptionsLastTwoMonth: {
                periodOptionsLastTwoMonth();
                break;
            }
            case R.id.historyDatesActivity_TextView_periodOptionsLastQuarter: {
                periodOptionsLastQuarter();
                break;
            }
            case R.id.historyDatesActivity_TextView_periodOptionsLastHalfYear: {
                periodOptionsLastHalfYear();
                break;
            }
            case R.id.historyDatesActivity_TextView_periodOptionsLastYear: {
                periodOptionsLastYear();
                break;
            }
            case R.id.historyDatesActivity_TextView_periodOptionsAll: {
                periodOptionsAll();
                break;
            }
            case R.id.historyDatesActivity_TextView_frequencyOptions: {
                showFrequencyOptions();
                break;
            }
            case R.id.historyDatesActivity_TextView_frequencyOptionsDaily: {
                frequencyOptionsDaily();
                break;
            }
            case R.id.historyDatesActivity_TextView_frequencyOptionsWeekly: {
                frequencyOptionsWeekly();
                break;
            }
            case R.id.historyDatesActivity_TextView_frequencyOptionsFortnightly: {
                frequencyOptionsFortnightly();
                break;
            }
            case R.id.historyDatesActivity_TextView_frequencyOptionsMonthly: {
                frequencyOptionsMonthly();
                break;
            }
            case R.id.historyDatesActivity_TextView_frequencyOptionsQuarterly: {
                frequencyOptionsQuarterly();
                break;
            }
        }
    }

    private void pickANewStartDate(){
        Log.d(TAG, "pickANewStartDate: new start date");
    } //TODO: add functionality

    private void pickANewEndDate(){
        Log.d(TAG, "pickANewEndDate: new end date");
    } //TODO: add functionality

    private void showPeriodOptions(){
        ViewGroup.LayoutParams params = periodOptionsLinearLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        periodOptionsLinearLayout.setLayoutParams(params);
    }

    private void periodOptionsLastMonth(){
        hidePeriodOptions();
        changeDateRange(31);
        changePeriodText("Last Month");
    }

    private void periodOptionsLastTwoMonth(){
        hidePeriodOptions();
        changeDateRange(62);
        changePeriodText("Last Two Months");
    }

    private void periodOptionsLastQuarter(){
        hidePeriodOptions();
        changeDateRange(121);
        changePeriodText("Last Quarter");
    }

    private void periodOptionsLastHalfYear(){
        hidePeriodOptions();
        changeDateRange(183);
        changePeriodText("Last Half Year");
    }

    private void periodOptionsLastYear(){
        hidePeriodOptions();
        changeDateRange(365);
        changePeriodText("Last Year");
    }

    private void periodOptionsAll(){
        hidePeriodOptions();
        changePeriodText("All");
    }

    private void showFrequencyOptions(){
        ViewGroup.LayoutParams params = frequencyOptionsLinearLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        frequencyOptionsLinearLayout.setLayoutParams(params);
    }

    private void frequencyOptionsDaily(){
        hideFrequencyOptions();
        changeFrequencytext("Daily");
    }

    private void frequencyOptionsWeekly(){
        hideFrequencyOptions();
        changeFrequencytext("Weekly");
    }

    private void frequencyOptionsFortnightly(){
        hideFrequencyOptions();
        changeFrequencytext("Fortnightly");
    }

    private void frequencyOptionsMonthly(){
        hideFrequencyOptions();
        changeFrequencytext("Monthly");
    }

    private void frequencyOptionsQuarterly(){
        hideFrequencyOptions();
        changeFrequencytext("Quarterly");
    }

    private void hidePeriodOptions(){
        ViewGroup.LayoutParams params = periodOptionsLinearLayout.getLayoutParams();
        params.height = 0;
        periodOptionsLinearLayout.setLayoutParams(params);
    }

    private void changePeriodText(String text){
        periodOptionsTextView.setText(text);
        mPeriod = text;
    }

    private void hideFrequencyOptions(){
        ViewGroup.LayoutParams params = frequencyOptionsLinearLayout.getLayoutParams();
        params.height = 0;
        frequencyOptionsLinearLayout.setLayoutParams(params);
    }

    private void changeDateRange(int days){
        Log.d(TAG, "changeDateRange: ");
        Date now = DateFunctions.getCurrentDateAndTime();
        Date decrementedDate = DateFunctions.decrementDate(now,days);
        now = DateFunctions.dateAndTimeToDate(now);
        String nowDateString = DateFunctions.dateAndTimeToSimpleDateString(now);
        String decrementedDateString = DateFunctions.dateAndTimeToSimpleDateString(decrementedDate);
        startDateTextView.setText(decrementedDateString);
        endDateTextView.setText(nowDateString);
        mDates = DateFunctions.returnRangeArrayFromTwoDates(now,decrementedDate);
        Log.d(TAG, "changeDateRange: " + mDates);
    }


    private void changeFrequencytext(String text){
        frequencyOptionsTextView.setText(text);
        mFrequency = text;
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: position " + position);
    }
    @Override
    public void onChangedCheckBox(int adapterPosition, boolean isChecked) {

    }

    @Override
    public void onChangedCheckBox(int position, boolean isChecked, CharSequence id) {
        List<String> keys = new ArrayList<>(mExercise.keySet());
        String exerciseName = keys.get(position);
        if (isChecked){
            addToSelectedExercises(exerciseName,(String) id);
        } else {
            removeFromSelectedExercises(exerciseName,(String) id);
        }
    }

    private void addToSelectedExercises(String name, String type){
        if (hashMapIncludesExercise(name)){
            selectedExercises.get(name).add(type);
        } else {
            selectedExercises.put(name,new ArrayList<>());
            selectedExercises.get(name).add(type);
        }
    }

    private boolean hashMapIncludesExercise(String exerciseName){
        if (selectedExercises.isEmpty()){
            return false;
        } else {
            Set<String> keys = selectedExercises.keySet();
            return keys.contains(exerciseName);
        }
    }

    private void removeFromSelectedExercises(String name, String type){
        selectedExercises.get(name).remove(type);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("selectedExercises",selectedExercises);
        intent.putExtra("dates",mDates);
        intent.putExtra("frequency",mFrequency);
        intent.putExtra("period",mPeriod);
        setResult(RESULT_OK, intent);
        super.finish();
    }
}