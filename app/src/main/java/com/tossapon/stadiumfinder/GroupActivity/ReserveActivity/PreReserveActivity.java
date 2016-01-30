package com.tossapon.stadiumfinder.GroupActivity.ReserveActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Api.ReserveInterface;
import com.tossapon.stadiumfinder.Model.Basic.Reservation;
import com.tossapon.stadiumfinder.Model.Basic.Stadium;
import com.tossapon.stadiumfinder.Model.Response.PreReserveResponse;
import com.tossapon.stadiumfinder.Network.Server;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PreReserveActivity extends AppCompatActivity {

    private static final String TAG = "PreReserveActivity";
    @Bind(R.id.activity_reserve_image) ImageView image;
    @Bind(R.id.activity_reserve_date) LinearLayout dateClicker;
    @Bind(R.id.activity_reserve_timefrom) LinearLayout timeFromClicker;
    @Bind(R.id.activity_reserve_timeto) LinearLayout timeToClicker;
    @Bind(R.id.activity_reserve_date_tv) TextView date;
    @Bind(R.id.activity_reserve_timefrom_tv) TextView timeFrom;
    @Bind(R.id.activity_reserve_timeto_tv) TextView timeTo;
    @Bind(R.id.activity_reserve_reserve_button) Button button;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timeFromDialog;
    private TimePickerDialog timeToDialog;
    private Calendar calendar;
    private String userId;
    private int fieldId;

    private Calendar selectedDate;
    private String stringTimeFrom = null;
    private String stringTimeTo = null;

    private Stadium stadium;
    private String type;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat dateFormatterForServer = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        ButterKnife.bind(this);

        type = getIntent().getExtras().getString("type");
        stadium = Parcels.unwrap(getIntent().getExtras().getParcelable("stadium"));

//        Picasso.with(this).load(stadium.image).into(image);

        calendar = Calendar.getInstance();
        selectedDate = Calendar.getInstance();

        date.setText(dateFormatter.format(selectedDate.getTime()));

        datePickerDialog = new DatePickerDialog(PreReserveActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(selectedDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        timeFromDialog = new TimePickerDialog(PreReserveActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                stringTimeFrom = String.format("%02d:%02d:00", hourOfDay, minute);
                timeFrom.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        timeToDialog = new TimePickerDialog(PreReserveActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                stringTimeTo = String.format("%02d:%02d:00", hourOfDay, minute);
                timeTo.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        setOnClickAndShowEachDialog();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stringTimeFrom == null|| stringTimeTo == null){
                    Toast.makeText(PreReserveActivity.this, "คุณยังไม่ได้เลือกเวลา", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Date from = parser.parse(stringTimeFrom);
                    Date to = parser.parse(stringTimeTo);
                    if(from.after(to) || from.getTime() == to.getTime()){
                        Toast.makeText(PreReserveActivity.this, "คุณยังไม่ได้เลือกเวลา", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Server.BASEURL)
                        .build();

                ReserveInterface service = retrofit.create(ReserveInterface.class);
                Call<PreReserveResponse> call = service.getPreReserveData(
                        stadium.id,
                        dateFormatterForServer.format(selectedDate.getTime()),
                        stringTimeTo,
                        stringTimeFrom,
                        type);

                call.enqueue(new Callback<PreReserveResponse>() {
                    @Override
                    public void onResponse(Response<PreReserveResponse> response, Retrofit retrofit) {
                        if(response.body() != null){
                            Log.d(TAG, "onResponse: " + response.body().getData().size());
                            Intent intent = new Intent(getApplicationContext(), SelectStadiumActivity.class);
                            Parcelable[] p = new Parcelable[response.body().getData().size()];
                            for(int i = 0; i < response.body().getData().size();i++){
                                p[i] = Parcels.wrap(response.body().getData().get(i));
                            }
                            intent.putExtra("fields", p);
                            Reservation re = new Reservation(
                                    dateFormatterForServer.format(selectedDate.getTime()),
                                    stringTimeTo,
                                    stringTimeFrom,
                                    -1,
                                    stadium.id
                            );
                            intent.putExtra("reserve", Parcels.wrap(re));
                            intent.putExtra("stadium", Parcels.wrap(stadium));
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void setOnClickAndShowEachDialog() {
        dateClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        timeFromClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeFromDialog.show();
            }
        });
        timeToClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeToDialog.show();
            }
        });
    }
}
