package shivam.developer.featuredrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
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
                        itemToResize = dy > 0 ? 1 : 0;
                        changeHeightAccordingToScroll(recyclerView);
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

    /**
     * If the distance of view is 0 then its height would be equal to featuredItemHeight
     * else height according to distance.
     * In every case maxDistance = (featuredItemHeight + defaultItemHeight) / 2
     *
     * @param distance is distance between 0 Y-Coordinate and view.getTop()
     * @return height = = featuredItemHeight - ((distance * (featuredItemHeight - defaultItemHeight) / maxDistance)
     */
    private float height(float distance) {
        return featuredItemHeight - ((distance * (diffHeight)) / maxDistance);
    }

    /**
     * This method will invoked every time onScroll is invoked.
     *
     * @param recyclerView is the recyclerview on whom scrolling is performed.
     */
    private void changeHeightAccordingToScroll(RecyclerView recyclerView) {
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

    /**
     * Ranges from 0 to 100. If height is featuredItemHeight then offset would be
     * 100 and if height is defaultItemHeight then it will be equal to 0.
     *
     * @param height will be the height of view
     * @return the offset according to which view's child's property can be
     * changed.
     */
    private float getOffsetAccordingToHeight(int height) {
        return ((height - defaultItemHeight) * 100) / diffHeight;
    }

    private void onItemSmallResize(View view, RecyclerView recyclerView) {
        adapter.onSmallItemResize(recyclerView.getChildViewHolder(view), itemToResize, getOffsetAccordingToHeight(view.getHeight()));
    }

    private void onItemBigResize(View view, RecyclerView recyclerView) {
        adapter.onBigItemResize(recyclerView.getChildViewHolder(view), itemToResize, getOffsetAccordingToHeight(view.getHeight()));
    }

    private float getTopOfView(View view) {
        return Math.abs(view.getTop());
    }

    public void setAdapter(FeatureRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
    }
}
