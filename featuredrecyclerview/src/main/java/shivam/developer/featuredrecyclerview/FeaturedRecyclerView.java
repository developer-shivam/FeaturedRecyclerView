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

    private int itemToResizePositionWhileMovingUp = 0;
    private int itemToResizePositionWhileMovingDown = 1;
    private int itemToResize = 0;
    private boolean isFirstItemHeightSetToFeaturedItemHeight = false;
    private int totalItemsInView = 0;

    private int displacement = 0;

    private int snapGravity = SnapGravity.START;

    public class SnapGravity {
        public static final int START = 0;
        public static final int CENTER = 1;
        public static final int END = 2;
    }

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

                        System.out.println("Vertical movement: " + dy);

                        if (!isFirstItemHeightSetToFeaturedItemHeight) {
                            if (getLayoutManager().getChildAt(0) != null) {
                                getLayoutManager().getChildAt(0).getLayoutParams().height = featuredItemHeight;
                                isFirstItemHeightSetToFeaturedItemHeight = true;
                            }
                        }

                        //displacement(irrespective of direction)
                        displacement = Math.abs(dy);

                        //Check the total items in view.
                        totalItemsInView = layoutManager.getItemCount();
                        System.out.println("Total items in view are " + totalItemsInView);

                        //if vertical displacement is positive then it means scrolling is
                        // done from bottom to top and view second view (ID: 1) should be resized.
                        // else the scrolling is in opposite direction so first view (ID: 0)
                        // will be resized.
                        itemToResize = dy > 0 ? itemToResizePositionWhileMovingUp : itemToResizePositionWhileMovingDown;

                        changeHeightAccordingToScroll(recyclerView);

                    } else System.out.println("Horizontal movement: " + dx);
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

        snapGravity = array.getInt(R.styleable.FeaturedRecyclerView_snapGravity, SnapGravity.START);

        calculateItemToBeResizePosition();
    }

    private void calculateItemToBeResizePosition() {
        switch (snapGravity) {
            case SnapGravity.START: itemToResizePositionWhileMovingUp = 1;
                itemToResizePositionWhileMovingDown = itemToResizePositionWhileMovingUp - 1;
                break;
            case SnapGravity.CENTER: itemToResizePositionWhileMovingUp = 2;
                itemToResizePositionWhileMovingDown = itemToResizePositionWhileMovingUp - 1;
                break;
            case SnapGravity.END: itemToResizePositionWhileMovingUp = 3;
                itemToResizePositionWhileMovingDown = itemToResizePositionWhileMovingUp - 1;
                break;
        }
    }

    private void changeHeightAccordingToScroll(RecyclerView recyclerView) {
        for (int i = 0; i < totalItemsInView; i++) {
            View viewToBeResized = recyclerView.getChildAt(i);
            if (viewToBeResized != null) {
                if (i == itemToResize) {
                    //If view that is to be resized has height greater than featuredItemHeight
                    // set its height equal to featuredItemHeight else increase its height according
                    // to displacement
                    if (viewToBeResized.getLayoutParams().height + displacement >= featuredItemHeight) {
                        viewToBeResized.getLayoutParams().height = featuredItemHeight;
                    } else {
                        viewToBeResized.getLayoutParams().height += displacement;
                    }
                } else {
                    //If view that is to be resized to defaultItemHeight and has height less than
                    // default item height then set its height to defaultItemHeight else decrease its
                    // height accordingly
                    if (viewToBeResized.getLayoutParams().height - displacement <= defaultItemHeight) {
                        viewToBeResized.getLayoutParams().height = defaultItemHeight;
                    } else {
                        viewToBeResized.getLayoutParams().height -= displacement;
                    }
                }
                viewToBeResized.requestLayout();
            }
        }
    }
}
