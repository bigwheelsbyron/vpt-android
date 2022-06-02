package zxc.studio.vpt.ui.elements;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    static private void customHeader(Context context,int colour){
        View header = (View) ((Activity) context).findViewById(R.id.header);
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




}
