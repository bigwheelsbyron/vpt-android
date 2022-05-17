package zxc.studio.vpt.utilities;

import android.content.res.Resources;

public class functions {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
