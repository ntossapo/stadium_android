package com.tossapon.stadiumfinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity.StadiumInformationActivity;
import com.tossapon.stadiumfinder.Model.Stadium;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tossapon on 24/12/2558.
 */
public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ViewHolder>{

    private List<Stadium> dataSet;
    private Context context;

    public ReserveAdapter(List<Stadium> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_reserve, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stadium stadium = dataSet.get(position);
        Picasso.with(context).load(stadium.image).into(holder.image);
        holder.textview.setText(stadium.describe.length() > 200 ? stadium.describe.substring(0, 199) + "  อ่านต่อ..." : stadium.describe);
        holder.textViewName.setText(stadium.name.length() > 16 ? stadium.name.substring(0, 14) + ".." : stadium.name);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, StadiumInformationActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.cardview_reserve_image)
        ImageView image;

        @Bind(R.id.cardview_reserve_text)
        TextView textview;

        @Bind(R.id.cardview_reserve_name)
        TextView textViewName;

        @Bind(R.id.cardview_reserve_card)
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
