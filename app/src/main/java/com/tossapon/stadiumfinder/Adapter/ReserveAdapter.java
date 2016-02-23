package com.tossapon.stadiumfinder.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.GroupActivity.MainActivity.MainActivity;
import com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity.StadiumInformationActivity;
import com.tossapon.stadiumfinder.Model.Basic.Reserve;
import com.tossapon.stadiumfinder.Model.Basic.Stadium;
import com.tossapon.stadiumfinder.Network.RealTimeStadiumPeopleSocketIO;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tossapon on 24/12/2558.
 */
public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ViewHolder>{

    private static final String TAG = "ReserveAdapter";
    private List<Stadium> dataSet;
    private Context context;
    private String type;
    private Activity activity;
    private boolean[] socketInit;
    public ReserveAdapter(List<Stadium> dataSet, String t, Activity mainActivity) {
        this.dataSet = dataSet;
        type = t;
        activity = mainActivity;
        socketInit = new boolean[dataSet.size()];
        for(int i = 0; i < socketInit.length; i ++)
            socketInit[i] = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_reserve, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Stadium stadium = dataSet.get(position);
        Picasso.with(context).load(stadium.image).into(holder.image);
        holder.textViewName.setText(stadium.name.length() > 16 ? stadium.name.substring(0, 14) + ".." : stadium.name);
        holder.count.setText(stadium.count + "");
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, StadiumInformationActivity.class);
                i.putExtra("stadium", Parcels.wrap(dataSet.get(position)));
                i.putExtra("type", type);
                context.startActivity(i);
            }
        });
        if(!socketInit[position])
        RealTimeStadiumPeopleSocketIO.getInstance().on("stadium"+stadium.id, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String s = (String) args[0];
                Log.d(TAG, "call: " + s);
                if(s.equals("-")){
                    stadium.count = stadium.count - 1;
                }else if(s.equals("+")){
                    stadium.count = stadium.count + 1;
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ReserveAdapter.this.notifyItemChanged(position);
                    }
                });
            }
        });
        socketInit[position] = true;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.cardview_reserve_image)
        ImageView image;

        @Bind(R.id.cardview_reserve_name)
        TextView textViewName;

        @Bind(R.id.cardview_reserve_card)
        CardView cardview;

        @Bind(R.id.cardview_reserve_count)
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
