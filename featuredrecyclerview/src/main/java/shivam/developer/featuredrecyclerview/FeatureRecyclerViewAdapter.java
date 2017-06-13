package shivam.developer.featuredrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class FeatureRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public abstract T onCreateFeaturedViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindFeaturedViewHolder(T holder, int position);

    public abstract int getFeaturedItemsCount();

    public abstract void onSmallItemResize(T holder, int position, int offset);

    public abstract void onBigItemResize(T holder, int position, int offset);

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateFeaturedViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        onBindFeaturedViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return getFeaturedItemsCount();
    }
}
