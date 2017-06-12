package shivam.developer.featuredrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public class FeatureLinearLayoutManager extends android.support.v7.widget.LinearLayoutManager{

    public FeatureLinearLayoutManager(Context context) {
        super(context);
    }

    public FeatureLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        return 1000;
    }
}
