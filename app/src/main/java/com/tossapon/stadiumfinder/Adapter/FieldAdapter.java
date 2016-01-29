package com.tossapon.stadiumfinder.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.GroupActivity.ReserveActivity.ConfirmActivity;
import com.tossapon.stadiumfinder.Model.Basic.Field;
import com.tossapon.stadiumfinder.Model.Basic.Reservation;
import com.tossapon.stadiumfinder.Model.Basic.Stadium;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benvo_000 on 27/1/2559.
 */
public class FieldAdapter  extends RecyclerView.Adapter<FieldAdapter.ViewHolder>{

    ArrayList<Field> dataSet = new ArrayList<>();
    Reservation reservation = null;
    Stadium stadium = null;
    Context context;
    public FieldAdapter(ArrayList dataSet, Reservation reservation, Stadium stadium) {
        this.dataSet = dataSet;
        this.reservation = reservation;
        this.stadium = stadium;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.listview_field, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText(dataSet.get(position).getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ConfirmActivity.class);
                i.putExtra("stadium", Parcels.wrap(stadium));
                i.putExtra("field", Parcels.wrap(dataSet.get(position)));
                i.putExtra("reserve", Parcels.wrap(reservation));
                context.startActivity(i);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.listview_field_text)
        TextView tv;
        @Bind(R.id.listview_self)
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
