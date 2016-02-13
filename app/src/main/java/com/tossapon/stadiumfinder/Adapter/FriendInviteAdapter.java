package com.tossapon.stadiumfinder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Model.Facebook.Friend;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public class FriendInviteAdapter extends RecyclerView.Adapter<FriendInviteAdapter.ViewHolder>{

    private static final String TAG = "FriendInviteAdapter";
    ArrayList<Friend> dataSet;
    Context context;
    List<String> friendsId;
    public FriendInviteAdapter(ArrayList<Friend> f, List<String> fid) {
        friendsId = fid;
        dataSet = f;
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
        Picasso.with(context).load(dataSet.get(position).getPicture()).into(holder.picture);
        holder.self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkbox.isChecked())
                    holder.checkbox.setChecked(false);
                else
                    holder.checkbox.setChecked(true);
            }
        });
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    friendsId.add(dataSet.get(position).getId());
                }else{
                    int index = friendsId.indexOf(dataSet.get(position).getId());
                    friendsId.remove(index);
                }
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
