package zxc.studio.vpt.ui.elements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

import zxc.studio.vpt.R;
import zxc.studio.vpt.utilities.LocalStorageService;

public class colourManager {

    private static final String TAG = "colourManager";

    static public void applyColourSchemeSubtractStandard(Context context){
        Log.d(TAG, "colourScheme: colour scheme called");
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.subtractshape);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, context.getResources().getColor(R.color.baby_blue));
        unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.backgroundsubtract);
        wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, context.getResources().getColor(R.color.baby_blue));
    }
    static public void applyColourSchemeSubtractCustom(Context context){
        LocalStorageService localStorageService = new LocalStorageService(context);
        int colour = localStorageService.getSubtractColour();
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.subtractshape);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, colour);
        unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.backgroundsubtract);
        wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, colour);
    }
    static public void applyColourSchemeCustom(Context context){
        LocalStorageService localStorageService = new LocalStorageService(context);
        int colour = localStorageService.getHeaderColour();
        customHeader(context,colour);
        customText(context,colour);
    }
    @SuppressLint("NewApi")
    static public void applyColourSchemeHeaderTemporary(Context context, Color colour){
        View header = ((Activity) context).findViewById(R.id.header);
        header.setBackgroundColor(Color.argb(100,colour.red(),colour.green(),colour.blue()));
        Window window = ((Activity) context).getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.argb(100,colour.red(),colour.green(),colour.blue()));
        }
    }
    @SuppressLint("NewApi")
    static public void applyColourSchemeTitleTemporary(Context context, Color colour){
        TextView v = ((Activity) context).findViewById(R.id.textViewVPTSignMainV);
        TextView p = ((Activity) context).findViewById(R.id.textViewVPTSignMainP);
        TextView t = ((Activity) context).findViewById(R.id.textViewVPTSignMainT);
        Log.d(TAG, "applyColourSchemeTitleTemporary: " + colour.alpha());
        v.setTextColor(Color.argb(100,colour.red(),colour.green(),colour.blue()));
        v.setAlpha(1);
        p.setTextColor(Color.argb(100,colour.red(),colour.green(),colour.blue()));
        p.setAlpha(1);
        t.setTextColor(Color.argb(100,colour.red(),colour.green(),colour.blue()));
        t.setAlpha(1);
    }
    @SuppressLint("NewApi")
    static public void applyColourSchemeBorderTemporary(Context context, Color colour){
//        ImageView subtractBottom = ((Activity) context).findViewById(R.id.mainScreenSubtractBottom);
//        ImageView subtractTop = ((Activity) context).findViewById(R.id.mainScreenSubtractTop);
//        subtractBottom.setColorFilter(colour);
//        subtractTop.setColorFilter(colour);
    }
    @SuppressLint("NewApi")
    static public void applyColourSchemeSubtractTemporary(Context context, Color colour){
        ImageView subtractBottom = ((Activity) context).findViewById(R.id.mainScreenSubtractBottom);
        ImageView subtractTop = ((Activity) context).findViewById(R.id.mainScreenSubtractTop);
        subtractBottom.setColorFilter(Color.argb(100,colour.red(),colour.green(),colour.blue()));
        subtractTop.setColorFilter(Color.argb(100,colour.red(),colour.green(),colour.blue()));
    }
    @SuppressLint("NewApi")
    static public void applyColourSchemeBackgroundTemporary(Context context, Color colour){
        ConstraintLayout background = ((Activity) context).findViewById(R.id.container);
        background.setBackgroundColor(Color.argb(100,colour.red(),colour.green(),colour.blue()));
    }

    static private void customHeader(Context context,int colour){
        View header = ((Activity) context).findViewById(R.id.header);
        header.setBackgroundColor(colour);
        Window window = ((Activity) context).getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(colour);
        }
    }
    static private void customText(Context context,int colour){
        TextView v = (TextView) ((Activity) context).findViewById(R.id.textViewVPTSignMainV);
        TextView p = (TextView) ((Activity) context).findViewById(R.id.textViewVPTSignMainP);
        TextView t = (TextView) ((Activity) context).findViewById(R.id.textViewVPTSignMainT);
        v.setTextColor(colour);
        p.setTextColor(colour);
        t.setTextColor(colour);
        BottomNavigationView bottomNavigationView = ((Activity) context).findViewById(R.id.bottom_nav);
        bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(colour));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    static public Color rgbToColour(int r, int g, int b){
        return Color.valueOf(Color.argb(100,r,g,b));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static public ArrayList<Float> colourToRBG(Color color){
        ArrayList<Float> returnArray = new ArrayList<>();
        float red = color.red()*255;
        float green = color.green()*255;
        float blue = color.blue()*255;
        returnArray.add(red);
        returnArray.add(green);
        returnArray.add(blue);
        Log.d(TAG, "colourToRBG: " + color);
        Log.d(TAG, "colourToRBG: " + color.alpha());
        return returnArray;
    }

//    static public HashMap<String, HashMap<String,Integer>> presetColours(int position){
//        HashMap<String,Integer> header = new HashMap<>();
//        header.put("Header",)
//        HashMap<String,Integer> title = new HashMap<>();
//        HashMap<String,Integer> border = new HashMap<>();
//        HashMap<String,Integer> subtract = new HashMap<>();
//        HashMap<String,Integer> background = new HashMap<>();
//        HashMap<String, HashMap<String,Integer>> classic = new HashMap<>();
//        classic.
//    }



}
