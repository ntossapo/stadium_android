package com.tossapon.stadiumfinder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Model.Advance.TimeTable;
import com.tossapon.stadiumfinder.Model.Basic.Reserve;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benvo_000 on 14/2/2559.
 */
public class TimeTableAdapter  extends RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder>{

    private List<TimeTable> dataSet;
    private Context context;

    public TimeTableAdapter(List<TimeTable> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public TimeTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_timetable, parent, false);
        TimeTableViewHolder vh = new TimeTableViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TimeTableViewHolder holder, int position) {
        holder.time.setText(dataSet.get(position).getDate() + "\n" + dataSet.get(position).getTime_from()  + " - " + dataSet.get(position).getTime_to());
        holder.field.setText(dataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class TimeTableViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.cardview_timetable_field)
        TextView field;
        @Bind(R.id.cardview_timetable_time)
        TextView time;
        public TimeTableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
