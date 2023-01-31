package zxc.studio.vpt.utilities;

import static com.android.volley.VolleyLog.TAG;

import android.util.Log;

import java.util.TreeMap;

import zxc.studio.vpt.data.Constants;

public class OneRMCalculator {

    public static Integer oneRMCalc(float weight, int reps){
        TreeMap<Integer,Double> loadChart  = Constants.returnLoadChart();
        Log.d(TAG, "oneRMCalc: " + weight);
        Log.d(TAG, "oneRMCalc: " + reps);
        Double percentage = loadChart.get(reps);
        Log.d(TAG, "oneRMCalc: " + percentage);
        return (int) Math.round(weight * percentage);
    }

}
