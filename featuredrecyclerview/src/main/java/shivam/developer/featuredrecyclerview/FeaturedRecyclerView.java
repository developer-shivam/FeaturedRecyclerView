package shivam.developer.featuredrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class FeaturedRecyclerView extends RecyclerView {

    private int defaultItemHeight;
    private int featuredItemHeight;
    private int maxDistance;
    private int diffHeight;

    private int totalItemsInView = 0;
    private int itemToResize;
    private int dyAbs;

    private FeatureRecyclerViewAdapter adapter;

    public FeaturedRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public FeaturedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FeaturedRecyclerView);
        initAttributes(array);
        array.recycle();

        addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
                    if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                        totalItemsInView = layoutManager.getItemCount();
                        dyAbs = Math.abs(dy);
                        itemToResize = dy > 0 ? 1 : 0;
                        changeHeightAccordingToScrolling(recyclerView);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
    }

    private void initAttributes(TypedArray array) {

        defaultItemHeight = (int) array.getDimension(R.styleable.FeaturedRecyclerView_defaultItemHeight,
                getResources().getDimension(R.dimen.defaultItemHeight));
        featuredItemHeight = (int) array.getDimension(R.styleable.FeaturedRecyclerView_featuredItemHeight,
                getResources().getDimension(R.dimen.featuredItemHeight));

        System.out.println("Featured Height: " + featuredItemHeight);
        System.out.println("Default Height: " + defaultItemHeight);

        diffHeight = featuredItemHeight - defaultItemHeight;
        System.out.println("Difference: " + diffHeight);

        maxDistance = featuredItemHeight;
        System.out.println("Maximum Distance: " + maxDistance);
    }

    private float height(float distance) {
        return featuredItemHeight - ((distance * (diffHeight)) / maxDistance);
    }

    private void changeHeightAccordingToScrolling(RecyclerView recyclerView) {
        for (int i = 0; i < totalItemsInView; i++) {
            View viewToBeResized = recyclerView.getChildAt(i);
            if (viewToBeResized != null) {
                float distance = getTopOfView(viewToBeResized);
                if (distance > maxDistance) {
                    viewToBeResized.getLayoutParams().height = defaultItemHeight;
                    viewToBeResized.requestLayout();
                } else if (distance <= maxDistance) {
                    viewToBeResized.getLayoutParams().height = (int) height(distance);
                    viewToBeResized.requestLayout();
                }
                if (i == itemToResize) {
                    onItemBigResize(viewToBeResized, recyclerView);
                } else {
                    onItemSmallResize(viewToBeResized, recyclerView);
                }
            }

        }
    }

    private void onItemSmallResize(View view, RecyclerView recyclerView) {
        adapter.onSmallItemResize(recyclerView.getChildViewHolder(view), itemToResize, dyAbs);
    }

    private void onItemBigResize(View view, RecyclerView recyclerView) {
        adapter.onBigItemResize(recyclerView.getChildViewHolder(view), itemToResize, dyAbs);
    }

    private float getTopOfView(View view) {
        return Math.abs(view.getTop());
    }

    public void setAdapter(FeatureRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
    }
}
