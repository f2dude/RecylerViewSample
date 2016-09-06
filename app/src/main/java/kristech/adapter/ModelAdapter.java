package kristech.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kristech.dto.ModelDTO;
import kristech.readresponses.RecyclerClickListener;
import kristech.recyclerviewandroid.R;
import kristech.requests.SingletonInstance;

/**
 * Created by saikrishna.pawar on 04/09/16.
 */

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.DataObjectHolder> {

    private static String LOG_TAG = "RecyclerViewAdapter";
    private static RecyclerClickListener clickListener;
    public List<ModelDTO> list = new ArrayList<>();
    private Context ctx;

    public ModelAdapter(List<ModelDTO> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.id.setText(Integer.toString(list.get(position).getId()));
        holder.title.setText(list.get(position).getTitle());
//        Picasso.with(ctx).load(list.get(position).getThumbnailURL()).into(holder.imageView);

//        Picasso.with(ctx).load("http://www.blindfiveyearold.com/wp-content/uploads/2011/01/homer-simpson-150x150.jpg").into(holder.imageView);
        ImageLoader imageLoader = SingletonInstance.getInstance(ctx).getImageLoader();
        holder.imageView.setImageUrl("http://www.blindfiveyearold.com/wp-content/uploads/2011/01/homer-simpson-150x150.jpg", imageLoader);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(RecyclerClickListener clickListener) {
        ModelAdapter.clickListener = clickListener;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.thumbnail_view)
        NetworkImageView imageView;
        @BindView(R.id.view_id)
        TextView id;
        @BindView(R.id.view_title)
        TextView title;

        public DataObjectHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
