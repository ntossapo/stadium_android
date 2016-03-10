package com.tossapon.stadiumfinder.GroupActivity.FriendStatActivity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Api.PlayerStatInterface;
import com.tossapon.stadiumfinder.Model.Advance.PlayerStat;
import com.tossapon.stadiumfinder.Model.Facebook.Friend;
import com.tossapon.stadiumfinder.Model.Response.PlayerStatResponse;
import com.tossapon.stadiumfinder.Network.Server;
import com.tossapon.stadiumfinder.Util.DataLoader;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class FriendStatActivity extends AppCompatActivity {
    private static final String TAG = "FriendStatActivity";
    Friend friend;
    @Bind(R.id.activity_friend_stat_toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_friend_stat_image)
    CircleImageView image;

    @Bind(R.id.activity_friend_stat_all_join)
    CircularProgressBar cpbAllJoin;
    @Bind(R.id.activity_friend_stat_create_match)
    CircularProgressBar cpbCreateMatch;
    @Bind(R.id.activity_friend_stat_dismiss_match)
    CircularProgressBar cpbDismissMatch;
    @Bind(R.id.activity_friend_stat_level)
    CircularProgressBar cpbLevel;

    @Bind(R.id.activity_friend_stat_cm_text)
    TextView createMatchTextView;
    @Bind(R.id.activity_friend_stat_dm_text)
    TextView dismissMatchTextView;
    @Bind(R.id.activity_friend_stat_aj_text)
    TextView allJoinTextView;
    @Bind(R.id.activity_friend_stat_level_text)
    TextView levelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_stat);
        ButterKnife.bind(this);

        friend = Parcels.unwrap(getIntent().getExtras().getParcelable("friend"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(friend.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(this).load("http://graph.facebook.com/" + friend.getId() + "/picture?type=large&redirect=true&width=400&height=400").into(image);

        final ProgressDialog dialog;
        dialog = ProgressDialog.show(FriendStatActivity.this, "", "กำลังโหลดข้อมูล", true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Server.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlayerStatInterface service = retrofit.create(PlayerStatInterface.class);
        Call<PlayerStatResponse> call = service.getPlayerStat(friend.getId());
        call.enqueue(new Callback<PlayerStatResponse>() {
            @Override
            public void onResponse(Response<PlayerStatResponse> response, Retrofit retrofit) {
                if(response.body() != null){
                    Log.d(TAG, "onResponse: " + response.body().getStatus());
                    if(response.body().getStatus().equals("ok")){
                        PlayerStat ps = response.body().getData();
                        cpbCreateMatch.setProgressWithAnimation( ps.getAllReserves() != 0 ? ((float) ps.getReservesAndPlay() / (float) ps.getAllReserves() * 100.0f) : 0, 500);
                        createMatchTextView.setText(ps.getReservesAndPlay() + "");

                        cpbDismissMatch.setProgressWithAnimation(ps.getAllReserves() != 0 ?((float) ps.getReservesAndMiss() / (float)ps.getAllReserves() * 100.0f) : 0, 750);
                        dismissMatchTextView.setText(ps.getReservesAndMiss() + "");

                        cpbAllJoin.setProgressWithAnimation( ps.getAllJoin() != 0 ? ((float)ps.getAllReserves() / (float)ps.getAllJoin() * 100.0f) : 0, 1000);
                        allJoinTextView.setText(ps.getAllJoin()+"");

                        int ex = ((ps.getReservesAndPlay()*100) + (ps.getAllJoin()*10)) - (ps.getReservesAndMiss()*105);
                        int level = ex / 150;
                        level += 1;
                        cpbLevel.setProgressWithAnimation(level, 1250);
                        levelTextView.setText(level + "");
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
