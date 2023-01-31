package zxc.studio.vpt.Factories;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import zxc.studio.vpt.models.ChartData;
import zxc.studio.vpt.models.Exercise;
import zxc.studio.vpt.utilities.DateFunctions;
import zxc.studio.vpt.utilities.OneRMCalculator;

public class ChartDataFactory {

    static private ChartData chartData = new ChartData();
    static private ArrayList<Date>  mDates = new ArrayList<>();


    static public ChartData newChartData(TreeMap<String, ArrayList<String>> selectedExercises, TreeMap<String, ArrayList<Exercise>> exerciseData,ArrayList<Date> dates,String frequency){
        Set<String> keys = selectedExercises.keySet();
        ArrayList<String> selectedExercisesNames = new ArrayList<>(keys);
        mDates = dates;
        chartData.setDates(mDates);
        for (int i = 0; i < selectedExercisesNames.size(); i++){
            calculateExerciseData(selectedExercises.get(selectedExercisesNames.get(i)),exerciseData.get(selectedExercisesNames.get(i)));
        }
        return chartData;
    }

    static private void calculateExerciseData(ArrayList<String> selected, ArrayList<Exercise> exercises){
        for (int i = 0; i < selected.size(); i++){
            splitOnSelected(selected.get(i),exercises);
        }
    }

    static private void splitOnSelected(String type,ArrayList<Exercise> exercises){

        switch(type){
            case "Reps":{
                repsCalculate(exercises);
                break;
            }

            case "Weight":{
                weightCalculate(exercises);
                break;
            }

            case "1 RM":{
                oneRMCalculate(exercises);
                break;
            }

            case "Volume":{
                volumeCalculate(exercises);
                break;
            }

            case "RPE":{
                rpeCalculate(exercises);
            }
        }
    }

    private static TreeMap<Date, Integer> intFillInMap(TreeMap<Date, Integer> map){
        TreeMap<Date, Integer> lMap = map;
        for (int i = 0; i < mDates.size(); i++){
            Date roundedDate = DateFunctions.roundDateToDay(mDates.get(i));
            lMap.put(roundedDate,0);
        }
        return lMap;
    }

    private static TreeMap<Date, Float> floatFillInMap(TreeMap<Date, Float> map){
        TreeMap<Date, Float> lMap = map;
        for (int i = 0; i < mDates.size(); i++){
            Date roundedDate = DateFunctions.roundDateToDay(mDates.get(i));
            lMap.put(roundedDate, Float.valueOf(0));
        }
        return lMap;
    }

    private static void repsCalculate(ArrayList<Exercise> exercises){
        String exerciseName = exercises.get(0).getExercise_name();
        exerciseName = exerciseName + " Reps";
        TreeMap<Date, Integer> repsMap = new TreeMap<>();
        repsMap = intFillInMap(repsMap);
        for (int i = 0; i < exercises.size(); i++){
            repsMap = repsReduceToMap(exercises.get(i),repsMap);
        }
        repsTreeMapToChartData(repsMap, exerciseName);
    }

    private static TreeMap<Date, Integer> repsReduceToMap(Exercise exercise, TreeMap<Date, Integer> repsMap){
        Date fullDateCompleted = exercise.getExercise_completedDate();
        Date dateCompleted = DateFunctions.dateAndTimeToDate(fullDateCompleted);
        dateCompleted = DateFunctions.roundDateToDay(dateCompleted);
        int reps = exercise.getExercise_reps();
        Set<Date> set = repsMap.keySet();
        List<Date> list = new ArrayList<>();
        list.addAll(set);
        if (repsMap.containsKey(dateCompleted)){
            int existingValue = repsMap.get(dateCompleted);
            int newValue = existingValue + reps;
            repsMap.put(dateCompleted,newValue);
        } else {
        }
        return repsMap;
    }

    private static void repsTreeMapToChartData(TreeMap<Date, Integer> map,String exerciseName){
        ArrayList<BarEntry> entriesReps = new ArrayList<>();
        ArrayList<String> entriesDates = new ArrayList<>();
        Set<Date> keySet = map.keySet();
        ArrayList<Date> keys = new ArrayList<>(keySet);
        Log.d(TAG, "treeMapToChartData:     " + map);
        for (int i = 0; i < keys.size(); i++){
            if (map.get(keys.get(i)) > 0){
                BarEntry e = new BarEntry(i,map.get(keys.get(i)));
                entriesReps.add(e);
                entriesDates.add(keys.get(i).toString());
            }
        }
        BarDataSet lineDataSet = new BarDataSet(entriesReps,exerciseName);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        chartData.barDataSetInsert(lineDataSet);
    }

    private static void weightCalculate(ArrayList<Exercise> exercises){
        String exerciseName = exercises.get(0).getExercise_name();
        exerciseName = exerciseName + " Weight";
        TreeMap<Date, Float> weightMap = new TreeMap<>();
        weightMap = floatFillInMap(weightMap);
        for (int i = 0; i < exercises.size(); i++){
            weightMap = weightReduceToMap(exercises.get(i),weightMap);
        }
        weightTreeMapToChartData(weightMap, exerciseName);
    }

    private static TreeMap<Date, Float> weightReduceToMap(Exercise exercise, TreeMap<Date, Float> weightMap){
        Date fullDateCompleted = exercise.getExercise_completedDate();
        Date dateCompleted = DateFunctions.dateAndTimeToDate(fullDateCompleted);
        dateCompleted = DateFunctions.roundDateToDay(dateCompleted);
        float weight = exercise.getExercise_weight();
        Set<Date> set = weightMap.keySet();
        List<Date> list = new ArrayList<>();
        list.addAll(set);
        if (weightMap.containsKey(dateCompleted)){
            float existingValue = weightMap.get(dateCompleted);
            float newValue = existingValue + weight;
            weightMap.put(dateCompleted,newValue);
        } else {
        }
        return weightMap;
    }

    private static void weightTreeMapToChartData(TreeMap<Date, Float> map,String exerciseName){
        ArrayList<Entry> entriesWeights = new ArrayList<>();
        ArrayList<String> entriesDates = new ArrayList<>();
        Set<Date> keySet = map.keySet();
        ArrayList<Date> keys = new ArrayList<>(keySet);
        Log.d(TAG, "treeMapToChartData:     " + map);
        for (int i = 0; i < keys.size(); i++){
            if (map.get(keys.get(i)) > 0){
                Entry e = new Entry(i,map.get(keys.get(i)));
                entriesWeights.add(e);
                entriesDates.add(keys.get(i).toString());
            }
        }
        LineDataSet lineDataSet = new LineDataSet(entriesWeights,exerciseName);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        chartData.lineDataSetInsert(lineDataSet);
    }

    private static void oneRMCalculate(ArrayList<Exercise> exercises){
        String exerciseName = exercises.get(0).getExercise_name();
        exerciseName = exerciseName + " 1RM";
        TreeMap<Date, Integer> oneRMMap = new TreeMap<>();
        oneRMMap = intFillInMap(oneRMMap);
        for (int i = 0; i < exercises.size(); i++){
            oneRMMap = oneRMReduceToMap(exercises.get(i),oneRMMap);
        }
        oneRMTreeMapToChartData(oneRMMap, exerciseName);
    }

    private static TreeMap<Date, Integer> oneRMReduceToMap(Exercise exercise, TreeMap<Date, Integer> oneRMMap){
        Date fullDateCompleted = exercise.getExercise_completedDate();
        Date dateCompleted = DateFunctions.dateAndTimeToDate(fullDateCompleted);
        dateCompleted = DateFunctions.roundDateToDay(dateCompleted);
        int reps = exercise.getExercise_reps();
        float weight = exercise.getExercise_weight();
        int oneRM = OneRMCalculator.oneRMCalc(weight,reps);
        Log.d(TAG, "oneRMReduceToMap: one RM calc" + oneRM);
        Set<Date> set = oneRMMap.keySet();
        List<Date> list = new ArrayList<>();
        list.addAll(set);
        if (oneRMMap.containsKey(dateCompleted)){
            int existingValue = oneRMMap.get(dateCompleted);
            if (oneRM > existingValue){
                oneRMMap.put(dateCompleted,oneRM);
            } else {}
        } else {
        }
        return oneRMMap;
    }

    private static void oneRMTreeMapToChartData(TreeMap<Date, Integer> map,String exerciseName){
        ArrayList<Entry> entriesWeights = new ArrayList<>();
        ArrayList<String> entriesDates = new ArrayList<>();
        Set<Date> keySet = map.keySet();
        ArrayList<Date> keys = new ArrayList<>(keySet);
        Log.d(TAG, "treeMapToChartData:     " + map);
        for (int i = 0; i < keys.size(); i++){
            if (map.get(keys.get(i)) > 0){
                Entry e = new Entry(i,map.get(keys.get(i)));
                entriesWeights.add(e);
                entriesDates.add(keys.get(i).toString());
            }
        }
        LineDataSet lineDataSet = new LineDataSet(entriesWeights,exerciseName);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        chartData.lineDataSetInsert(lineDataSet);
    }

    private static void volumeCalculate(ArrayList<Exercise> exercises){
        String exerciseName = exercises.get(0).getExercise_name();
        exerciseName = exerciseName + " Volume";
        TreeMap<Date, Integer> volumeMap = new TreeMap<>();
        volumeMap = intFillInMap(volumeMap);
        for (int i = 0; i < exercises.size(); i++){
            volumeMap = volumeMapReduceToMap(exercises.get(i),volumeMap);
        }
        volumeTreeMapToChartData(volumeMap, exerciseName);
    }

    private static TreeMap<Date, Integer> volumeMapReduceToMap(Exercise exercise, TreeMap<Date, Integer> volumeMap){
        Date fullDateCompleted = exercise.getExercise_completedDate();
        Date dateCompleted = DateFunctions.dateAndTimeToDate(fullDateCompleted);
        dateCompleted = DateFunctions.roundDateToDay(dateCompleted);
        int reps = exercise.getExercise_reps();
        float weight = exercise.getExercise_weight();
        int volume = Math.round(weight * reps);
        Set<Date> set = volumeMap.keySet();
        List<Date> list = new ArrayList<>();
        list.addAll(set);
        if (volumeMap.containsKey(dateCompleted)){
            int existingValue = volumeMap.get(dateCompleted);
            int newValue = existingValue + volume;
            volumeMap.put(dateCompleted,newValue);
        } else {
        }
        return volumeMap;
    }

    private static void volumeTreeMapToChartData(TreeMap<Date, Integer> map,String exerciseName){
        ArrayList<Entry> entriesVolume = new ArrayList<>();
        ArrayList<String> entriesDates = new ArrayList<>();
        Set<Date> keySet = map.keySet();
        ArrayList<Date> keys = new ArrayList<>(keySet);
        Log.d(TAG, "treeMapToChartData:     " + map);
        for (int i = 0; i < keys.size(); i++){
            if (map.get(keys.get(i)) > 0){
                Entry e = new Entry(i,map.get(keys.get(i)));
                entriesVolume.add(e);
                entriesDates.add(keys.get(i).toString());
            }
        }
        LineDataSet lineDataSet = new LineDataSet(entriesVolume,exerciseName);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.YELLOW);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        chartData.lineDataSetInsert(lineDataSet);
    }

    private static void rpeCalculate(ArrayList<Exercise> exercises){
        String exerciseName = exercises.get(0).getExercise_name();
        exerciseName = exerciseName + " RPE";
        TreeMap<Date, Integer> rpeMap = new TreeMap<>();
        rpeMap = intFillInMap(rpeMap);
        Log.d(TAG, "rpeCalculate: " + rpeMap);
        for (int i = 0; i < exercises.size(); i++){
            rpeMap = rpeMapReduceToMap(exercises.get(i),rpeMap);
        }
        rpeTreeMapToChartData(rpeMap, exerciseName);
    }

    private static TreeMap<Date, Integer> rpeMapReduceToMap(Exercise exercise, TreeMap<Date, Integer> rpeMap){
        Date fullDateCompleted = exercise.getExercise_completedDate();
        Date dateCompleted = DateFunctions.dateAndTimeToDate(fullDateCompleted);
        dateCompleted = DateFunctions.roundDateToDay(dateCompleted);
        int rpe = exercise.getExercise_rpe();
        Set<Date> set = rpeMap.keySet();
        List<Date> list = new ArrayList<>();
        list.addAll(set);
        if (rpeMap.containsKey(dateCompleted)){
            int existingValue = rpeMap.get(dateCompleted);
            int newValue = existingValue + rpe;
            rpeMap.put(dateCompleted,newValue);
        } else {
        }
        return rpeMap;
    }

    private static void rpeTreeMapToChartData(TreeMap<Date, Integer> map,String exerciseName){
        ArrayList<Entry> entriesRPE = new ArrayList<>();
        ArrayList<String> entriesDates = new ArrayList<>();
        Set<Date> keySet = map.keySet();
        ArrayList<Date> keys = new ArrayList<>(keySet);
        Log.d(TAG, "treeMapToChartData:     " + map);
        for (int i = 0; i < keys.size(); i++){
            if (map.get(keys.get(i)) > 0){
                Entry e = new Entry(i,map.get(keys.get(i)));
                entriesRPE.add(e);
                entriesDates.add(keys.get(i).toString());
            }
        }
        LineDataSet lineDataSet = new LineDataSet(entriesRPE,exerciseName);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        chartData.lineDataSetInsert(lineDataSet);
    }

}
