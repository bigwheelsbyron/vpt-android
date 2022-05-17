package zxc.studio.vpt.utilities;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemDeco extends RecyclerView.ItemDecoration {

    private int itemDecoSpaceHeight;

    public ItemDeco(int vertSpaceHeight){
        this.itemDecoSpaceHeight=vertSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom=itemDecoSpaceHeight;
    }
}
