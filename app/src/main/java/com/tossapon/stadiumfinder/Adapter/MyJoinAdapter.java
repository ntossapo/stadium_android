package com.tossapon.stadiumfinder.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Api.JoinInterface;
import com.tossapon.stadiumfinder.Model.Advance.MyJoin;
import com.tossapon.stadiumfinder.Model.Advance.MyReserve;
import com.tossapon.stadiumfinder.Model.Response.Response;
import com.tossapon.stadiumfinder.Network.Server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public class MyJoinAdapter extends RecyclerView.Adapter<MyJoinAdapter.ViewHolder> {

    private static final String TAG = "MyJoinAdapter";
    private List<MyJoin> dataSet;
    private Context context;

    private SimpleDateFormat timeHHmmss = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat timeHHmm = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar = Calendar.getInstance();

    public MyJoinAdapter(List<MyJoin> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_myreserve, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Date timeForm;
        Date date;
        final Calendar joinDate = Calendar.getInstance();
        Picasso.with(context).load(dataSet.get(position).getReserverImage()).into(holder.image);

        try {
            timeForm = timeHHmmss.parse(dataSet.get(position).getTime_from());
            date = dateYYYYMMDD.parse(dataSet.get(position).getDate());
            joinDate.setTime(date);
            long diff = (joinDate.getTime().getTime() - calendar.getTime().getTime())/ (24 * 60 * 60 * 1000);

            holder.time.setText(timeHHmm.format(timeForm) + "\n"  + (diff==0?"วันนี้" : "อีก " + diff  + " วัน"));
            holder.header.setText(dataSet.get(position).getOwnername() + " ได้จองสนามที่ " + dataSet.get(position).getStadium_name() +
                            " \nสนาม " + dataSet.get(position).getField_name() +
                            "\nวันที่ " + dataSet.get(position).getDate()
            );
            holder.self.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog joinDialog = new AlertDialog.Builder(context).setMessage("คุณต้องการยกเลิกหรือไม่")
                            .setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(Server.BASEURL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    JoinInterface service = retrofit.create(JoinInterface.class);
                                    Call<Response> call = service.deleteJoin(dataSet.get(position).getJoinId());
                                    call.enqueue(new Callback<Response>() {
                                        @Override
                                        public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                                            Toast.makeText(context, "ลบการเข้าร่วมสำเร็จ", Toast.LENGTH_LONG).show();
                                            dataSet.remove(position);
                                            MyJoinAdapter.this.notifyItemRemoved(position);
                                            Log.d(TAG, "onResponse: " + MyJoinAdapter.this.getItemCount());
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {

                                        }
                                    });
                                }
                            })
                            .setNegativeButton("ไม่ ขอบคุณ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    joinDialog.show();
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
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
        @Bind(R.id.cardview_myreserve_self)
        RelativeLayout self;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
