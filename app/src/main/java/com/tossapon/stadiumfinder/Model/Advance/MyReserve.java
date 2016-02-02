package com.tossapon.stadiumfinder.Model.Advance;

import com.tossapon.stadiumfinder.Model.Basic.Reserve;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon Nuanchuay on 31/1/2559.
 */
@Parcel
public class MyReserve extends Reserve {
    double latitude;
    double longitude;
    String stadium_name;
    String field_name;
    String image;

    public MyReserve(int id, int match_id, String date, String from, String to, int isConfirm) {
        super(id, match_id, date, from, to, isConfirm);
    }

    @ParcelConstructor
    public MyReserve(int id, int match_id, String date, String time_from, String time_to, int isConfirm, double latitude, double longitude, String stadium_name, String field_name, String image) {
        super(id, match_id, date, time_from, time_to, isConfirm);
        this.latitude = latitude;
        this.longitude = longitude;
        this.stadium_name = stadium_name;
        this.field_name = field_name;
        this.image = image;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStadium_name() {
        return stadium_name;
    }

    public String getField_name() {
        return field_name;
    }

    public String getImage() {
        return image;
    }
}
