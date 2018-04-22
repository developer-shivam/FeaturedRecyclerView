package shivam.developer.focusrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import shivam.developer.featuredrecyclerview.FeatureRecyclerViewAdapter;

public class CustomRecyclerViewAdapter extends FeatureRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_FEATURED = 0;
    private static final int ITEM_TYPE_DUMMY = 1;

    private List<String> data;
    private int[] images = new int[5];

    CustomRecyclerViewAdapter() {
        images[0] = R.drawable.image_one;
        images[1] = R.drawable.image_three;
        images[2] = R.drawable.image_two;
        images[3] = R.drawable.image_four;
        images[4] = R.drawable.image_five;
    }

    public void swapData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateFeaturedViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_FEATURED:
                return new FeaturedViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_featured_item, parent, false));
            case ITEM_TYPE_DUMMY:
            default:
                return new DummyViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_dummy_item, parent, false));
        }
    }

    @Override
    public void onBindFeaturedViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeaturedViewHolder) {
            FeaturedViewHolder featuredViewHolder = (FeaturedViewHolder) holder;
            Picasso.with(holder.itemView.getContext()).load(images[position % 4]).into(featuredViewHolder.ivBackground);
            featuredViewHolder.tvHeading.setText(data.get(position));
        } else if (holder instanceof DummyViewHolder) {
            //Do nothing
        }
    }

    @Override
    public int getFeaturedItemsCount() {
        return data.size() + 2; /* Return 2 extra dummy items */
    }

    @Override
    public int getItemViewType(int position) {
        return position >= 0 && position < data.size() ? ITEM_TYPE_FEATURED : ITEM_TYPE_DUMMY;
    }

    @Override
    public void onSmallItemResize(RecyclerView.ViewHolder holder, int position, float offset) {
        if (holder instanceof FeaturedViewHolder) {
            FeaturedViewHolder featuredViewHolder = (FeaturedViewHolder) holder;
            featuredViewHolder.tvHeading.setAlpha(offset / 100f);
        }
    }

    @Override
    public void onBigItemResize(RecyclerView.ViewHolder holder, int position, float offset) {
        if (holder instanceof FeaturedViewHolder) {
            FeaturedViewHolder featuredViewHolder = (FeaturedViewHolder) holder;
            featuredViewHolder.tvHeading.setAlpha(offset / 100f);
        }
    }


    static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBackground;
        TextView tvHeading;

        FeaturedViewHolder(View itemView) {
            super(itemView);

            ivBackground = (ImageView) itemView.findViewById(R.id.iv_background);
            tvHeading = (TextView) itemView.findViewById(R.id.tv_heading);
        }
    }

    static class DummyViewHolder extends RecyclerView.ViewHolder {

        DummyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
