package com.tossapon.stadiumfinder.GroupActivity.ReserveActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Api.ReserveInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.Model.Basic.Field;
import com.tossapon.stadiumfinder.Model.Basic.Reservation;
import com.tossapon.stadiumfinder.Model.Basic.Stadium;
import com.tossapon.stadiumfinder.Model.Basic.User;
import com.tossapon.stadiumfinder.Model.Response.ReserveResponse;
import com.tossapon.stadiumfinder.Network.Server;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by benvo_000 on 28/1/2559.
 */
public class ConfirmActivity extends AppCompatActivity{

    private static final String TAG = "ConfirmActivity";
    @Bind(R.id.activity_confirm_date_tv)
    TextView dateTextView;
    @Bind(R.id.activity_confirm_stadium_tv)
    TextView stadiumTextView;
    @Bind(R.id.activity_confirm_timefrom_tv)
    TextView timeFromTextView;
    @Bind(R.id.activity_confirm_timeto_tv)
    TextView timeToTextView;
    @Bind(R.id.activity_confirm_reserve_button)
    Button confirmButton;

    private Stadium stadium;
    private Reservation reservation;
    private Field field;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);

        stadium = Parcels.unwrap(getIntent().getExtras().getParcelable("stadium"));
        field = Parcels.unwrap(getIntent().getExtras().getParcelable("field"));
        reservation = Parcels.unwrap(getIntent().getExtras().getParcelable("reserve"));

        dateTextView.setText("วันที่ " + reservation.getDate());
        timeFromTextView.setText("เวลา " + reservation.getTimeFrom());
        timeToTextView.setText("ถึง " + reservation.getTimeTo());
        stadiumTextView.setText("ที่ " + stadium.name + " สนาม " + field.getName());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Server.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ReserveInterface service = retrofit.create(ReserveInterface.class);
                Call<ReserveResponse> call = service.Reserve(
                        AppUser.getInstance().facebook_id,
                        reservation.getTimeTo(),
                        reservation.getTimeFrom(),
                        reservation.getDate(),
                        field.getId()
                );
                call.enqueue(new Callback<ReserveResponse>() {
                    @Override
                    public void onResponse(Response<ReserveResponse> response, Retrofit retrofit) {
                        if(response.body() != null){
                            if (response.body().getStatus().equals("ok")) {
                                Toast.makeText(ConfirmActivity.this, "เรียบร้อย รอการตอบกลับจากเจ้าของสนาม", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                        Log.d(TAG, "onResponse: " + response.message());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(ConfirmActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
}
