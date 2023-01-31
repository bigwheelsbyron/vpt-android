package zxc.studio.vpt.models;

import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import zxc.studio.vpt.Factories.LineChartDataFactory;
import zxc.studio.vpt.utilities.DateFunctions;

public class ChartData {

    private static final String TAG = "ChartData";
    private CombinedData data = new CombinedData();
    private ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
    private ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
    private ArrayList<Date> dates = new ArrayList<>();

    public void setDates(ArrayList<Date> mDates){
        dates = new ArrayList<>();
        addDatesToArray(mDates);
        addDatesToHiddenChartData();
    }

    private void addDatesToArray(ArrayList<Date> mDates){
        for (int i = 0; i < mDates.size(); i++){
            Date date = mDates.get(i);
            date = DateFunctions.roundDateToDay(date);
            dates.add(date);
        }
        Collections.reverse(dates);
    }

    private void addDatesToHiddenChartData(){
        ArrayList<Entry> entriesValues = new ArrayList<>();
        ArrayList<Date> entriesDates = new ArrayList<>();
        for (int i = 0; i < dates.size();i++){
            Entry e = new Entry(i,0);
            entriesValues.add(e);
            entriesDates.add(dates.get(i));
        }
        LineDataSet lineDataSet = new LineDataSet(entriesValues,"");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setVisible(false);
        lineDataSets.add(lineDataSet);
        LineData lineData = new LineData(lineDataSets);
        data.setData(lineData);
    }

    public void lineDataSetInsert(LineDataSet lineDataSet){
        lineDataSets.add(lineDataSet);
        Log.d(TAG, "lineDataSetInsert: " + lineDataSets);
    }

    public void barDataSetInsert(BarDataSet barDataSet){
        barDataSets.add(barDataSet);
        Log.d(TAG, "barDataSets: " + barDataSets);
    }

    public void setData(){
        LineData lineData = new LineData(lineDataSets);
        BarData barData = new BarData(barDataSets);
        data.setData(lineData);
        data.setData(barData);
    }

    public int returnCount(){
        return dates.size();
    }

    public CombinedData returnDataSet(){
        return data;
    }

    public void clearData(){
        Log.d(TAG, "clearData: called");
        data = new CombinedData();
        dates = new ArrayList<>();
        lineDataSets = new ArrayList<>();
        barDataSets = new ArrayList<>();
    }

    public ArrayList<Date> returnDates(){
        return dates;
    }







//    public void adjustDataDatesForChart(){
//        ArrayList<String> dateArray = dates.get(0);
//        final String[] quarters = DateFunctions.DatesDictionary(dateArray);
//        format = new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                String returnObject = quarters[(int) value];
//                return returnObject;
//            }
//        };
//    }

}
