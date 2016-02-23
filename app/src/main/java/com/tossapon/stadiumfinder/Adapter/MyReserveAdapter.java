package com.tossapon.stadiumfinder.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Api.FacebookServiceInterface;
import com.tossapon.stadiumfinder.Api.JoinInterface;
import com.tossapon.stadiumfinder.Api.ReserveInterface;
import com.tossapon.stadiumfinder.GroupActivity.FriendInviteActivity.FriendInviteActivity;
import com.tossapon.stadiumfinder.Model.Advance.MyReserve;
import com.tossapon.stadiumfinder.Model.FacebookResponse.FacebookNotificationResponse;
import com.tossapon.stadiumfinder.Model.Response.Response;
import com.tossapon.stadiumfinder.Network.Server;

import org.json.JSONArray;
import org.parceler.Parcels;

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
    private static final int invite = 0;
    private static final int cancel = 1;

    String[] itemServices = {"ชวนเพื่อน", "ยกเลิกการจอง"};

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Date timeFrom;
        Date date;
        Calendar reserveDate = Calendar.getInstance();
        try {
            if(dataSet.get(position).getIsConfirm() == 1){
                Picasso.with(context).load(R.drawable.check52).into(holder.image);
            }else{
                Picasso.with(context).load(R.drawable.cancel10).into(holder.image);
            }
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

            holder.self.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder b = new AlertDialog.Builder(context);
                    b.setItems(itemServices, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case invite :
                                    Intent i = new Intent(context, FriendInviteActivity.class);
                                    i.putExtra("reserve", Parcels.wrap(dataSet.get(position)));
                                    context.startActivity(i);
                                    break;
                                case cancel :
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(Server.BASEURL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    Log.d(TAG, "onClick: " + dataSet.get(position).getId());
                                    ReserveInterface service = retrofit.create(ReserveInterface.class);
                                    Call<Response> call = service.deleteMyReserve(dataSet.get(position).getId());
                                    call.enqueue(new Callback<Response>() {
                                        @Override
                                        public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                                            if(response.body().getStatus().equals("ok")) {
                                                Toast.makeText(context, "ลบการจองสำเร็จ", Toast.LENGTH_LONG).show();
                                                dataSet.remove(position);
                                                MyReserveAdapter.this.notifyItemRemoved(position);
                                                Log.d(TAG, "onResponse: " + MyReserveAdapter.this.getItemCount());
                                                Log.d(TAG, "onResponse: " + response.body().getStatus());
                                            }else{
                                                Toast.makeText(context, response.body().getErr(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {

                                        }
                                    });
                                    break;
                            }
                        }
                    });
                    b.show();
                }
            });
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
        @Bind(R.id.cardview_myreserve_self)
        RelativeLayout self;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
