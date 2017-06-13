package shivam.developer.focusrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohitarya.picasso.facedetection.transformation.FaceCenterCrop;
import com.rohitarya.picasso.facedetection.transformation.core.PicassoFaceDetector;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomRecyclerViewHolder> {

    private List<String> dataList;
    private Context context;
    private int[] images = new int[5];

    public CustomRecyclerViewAdapter(Context context, List<String> list) {
        this.dataList = list;
        this.context = context;
        PicassoFaceDetector.initialize(context);

        images[0] = R.drawable.image_one;
        images[1] = R.drawable.image_two;
        images[3] = R.drawable.image_three;
        images[2] = R.drawable.image_four;
        images[4] = R.drawable.image_five;

    }

    @Override
    public CustomRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomRecyclerViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.simple_reycler_view_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewHolder holder, int position) {
        Picasso.with(context)
                .load(images[position % 5]).into(holder.ivBackground);
        holder.tvHeading.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBackground;
        TextView tvHeading;

        public CustomRecyclerViewHolder(View itemView) {
            super(itemView);

            ivBackground = (ImageView) itemView.findViewById(R.id.iv_background);
            tvHeading = (TextView) itemView.findViewById(R.id.tv_heading);
        }
    }
}
