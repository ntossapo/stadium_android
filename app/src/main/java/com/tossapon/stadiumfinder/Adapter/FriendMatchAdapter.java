package com.tossapon.stadiumfinder.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Api.QuickInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.Model.Advance.FriendMatch;
import com.tossapon.stadiumfinder.Model.Advance.QuickMatch;
import com.tossapon.stadiumfinder.Model.Basic.User;
import com.tossapon.stadiumfinder.Model.Response.Response;
import com.tossapon.stadiumfinder.Network.Server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Tossapon Nuanchuay on 7/2/2559.
 */
public class FriendMatchAdapter extends RecyclerView.Adapter<FriendMatchAdapter.ViewHolder>{

    private List<FriendMatch> dataSet;
    private Context context;

    private SimpleDateFormat timeHHmm = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

    public FriendMatchAdapter(List<FriendMatch> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_quickmatch, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            Date date = dateYYYYMMDD.parse(dataSet.get(position).getDate());
            long diff = (date.getTime() - Calendar.getInstance().getTime().getTime())/ (24 * 60 * 60 * 1000);

            String timeString = (diff == 0 ? "วันนี้" : "อีก " + diff + " วัน") + "\nเวลา " + dataSet.get(position).getTime_from();
            timeString += "\n" + dataSet.get(position).getUsername() + " ได้จองไว้";

            Picasso.with(context).load(dataSet.get(position).getImage()).into(holder.image);
            holder.time.setText(timeString);

            List<User> users = dataSet.get(position).getUser();
            String userString = "";
            if(users.size() > 0) {
                for (int i = 0; i < (users.size() >= 3 ? 3 : users.size()); i++)
                    userString += users.get(i).name + ", ";
                userString += " เข้าร่วม";
            }else{
                userString = "ยังไม่มีใครเข้าร่วม";
            }
            holder.friend.setText(userString);
            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog builder = new AlertDialog.Builder(context).setMessage("คุณต้องการเข้าร่วมหรือไม่")
                            .setPositiveButton("เข้าร่วม", new DatePickerDialog.OnClickListener() {
                                @Override
                                public void onClick(final DialogInterface dialog, int which) {
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(Server.BASEURL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    QuickInterface service = retrofit.create(QuickInterface.class);
                                    Call<Response> call = service.join(AppUser.getInstance().facebook_id, dataSet.get(position).getId());
                                    call.enqueue(new Callback<Response>() {
                                        @Override
                                        public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                                            if(response.body().getStatus().equals("ok")) {
                                                Toast.makeText(context, "คุณได้เข้าร่วมการเล่นแล้ว", Toast.LENGTH_SHORT).show();
                                                dataSet.remove(position);
                                                FriendMatchAdapter.this.notifyItemRemoved(position);
                                            }else
                                                Toast.makeText(context, response.body().getErr(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }).setNegativeButton("ไม่ ขอบคุณ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    builder.show();
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
        @Bind(R.id.cardview_quickmatch_image)
        ImageView image;
        @Bind(R.id.cardview_quickmatch_time)
        TextView time;
        @Bind(R.id.cardview_quickmatch_friend)
        TextView friend;
        @Bind(R.id.cardview_quickmatch_self)
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
