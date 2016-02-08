package com.tossapon.stadiumfinder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Model.Advance.MyReserve;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Tossapon Nuanchuay on 30/1/2559.
 */
public class MyReserveAdapter extends RecyclerView.Adapter<MyReserveAdapter.ViewHolder>{

    private static final String TAG = "MyReserveAdapter";
    private List<MyReserve> dataSet;
    private Context context;

    private SimpleDateFormat timeHHmmss = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat timeHHmm = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar = Calendar.getInstance();

    public MyReserveAdapter(List<MyReserve> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_myreserve, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Date timeFrom;
        Date date;
        Calendar reserveDate = Calendar.getInstance();
        Picasso.with(context).load(dataSet.get(position).getImage()).into(holder.image);
        try {
            timeFrom = timeHHmmss.parse(dataSet.get(position).getTime_from());
            date = dateYYYYMMDD.parse(dataSet.get(position).getDate());
            reserveDate.setTime(date);
            Log.d(TAG, "onBindViewHolder: " + reserveDate.get(Calendar.YEAR) + " " + calendar.get(Calendar.YEAR));
            long diff = (reserveDate.getTime().getTime() - calendar.getTime().getTime())/ (24 * 60 * 60 * 1000);


            holder.time.setText(timeHHmm.format(timeFrom) + "\n"  + (diff==0?"วันนี้" : "อีก " + diff  + " วัน"));
            holder.header.setText("คุณได้จองสนามที่ " + dataSet.get(position).getStadium_name() +
                            " \nสนาม " + dataSet.get(position).getField_name() +
                            "\nวันที่ " + dataSet.get(position).getDate()
            );
        } catch (ParseException e) {
            Log.d(TAG, "onBindViewHolder: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.cardview_myreserve_header)
        TextView header;
        @Bind(R.id.cardview_myreserve_time)
        TextView time;
        @Bind(R.id.cardview_myreserve_image)
        CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
