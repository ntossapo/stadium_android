package com.tossapon.stadiumfinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.GroupActivity.FriendStatActivity.FriendStatActivity;
import com.tossapon.stadiumfinder.Model.Facebook.Friend;

import org.json.JSONArray;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by benvo_000 on 11/2/2559.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{

    private List<Friend> dataSet;
    private Context context;
    private ArrayList<String> arrayListBlockedFriend;
    public FriendAdapter(List<Friend> dataSet, ArrayList<String> arrayList) {
        this.dataSet = dataSet;
        arrayListBlockedFriend = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_friend, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(dataSet.get(position).getName());
        Picasso.with(context).load("http://graph.facebook.com/" + dataSet.get(position).getId() + "/picture?type=large&redirect=true&width=400&height=400").into(holder.picture);

        if(arrayListBlockedFriend.indexOf(dataSet.get(position).getId()) > -1)
            holder.checkbox.setChecked(false);
        else
            holder.checkbox.setChecked(true);

        holder.self.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(holder.checkbox.isChecked()) {
                    holder.checkbox.setChecked(false);
                    arrayListBlockedFriend.add(dataSet.get(position).getId());
                }else {
                    holder.checkbox.setChecked(true);
                    arrayListBlockedFriend.remove(dataSet.get(position).getId());
                }
                return true;
            }
        });

        holder.self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FriendStatActivity.class);
                i.putExtra("friend", Parcels.wrap(dataSet.get(position)));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.cardview_friend_name)
        TextView name;

        @Bind(R.id.cardview_friend_picture)
        CircleImageView picture;

        @Bind(R.id.cardview_friend_checker)
        CheckBox checkbox;

        @Bind(R.id.cardview_friend_self)
        RelativeLayout self;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
