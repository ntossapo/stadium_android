package com.tossapon.stadiumfinder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Model.Basic.User;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by benvo_000 on 10/3/2559.
 */
public class FriendMiniAdapter extends RecyclerView.Adapter<FriendMiniAdapter.ViewHolder>{
    private Context context;
    private List<User> dataSet;

    public FriendMiniAdapter(List<User> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_friend_mini, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(dataSet.get(position).getPicurl()).into(holder.circleImage);
        holder.textViewName.setText(dataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.cardview_friend_mini_image)
        CircleImageView circleImage;
        @Bind(R.id.cardview_friend_mini_text)
        TextView textViewName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
