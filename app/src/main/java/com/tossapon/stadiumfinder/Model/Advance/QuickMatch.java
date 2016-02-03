package com.tossapon.stadiumfinder.Model.Advance;

import com.tossapon.stadiumfinder.Model.Basic.Reserve;
import com.tossapon.stadiumfinder.Model.Basic.User;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

/**
 * Created by Tossapon Nuanchuay on 3/2/2559.
 */
@Parcel
public class QuickMatch extends Reserve {

    String stadiumName;
    double latitude;
    double longitude;
    String fieldName;
    int isjoin;
    String image;
    String username;
    List<User> user;

    public QuickMatch(int id, int match_id, String date, String time_from, String time_to, int isConfirm) {
        super(id, match_id, date, time_from, time_to, isConfirm);
    }
    @ParcelConstructor
    public QuickMatch(int id, int match_id, String date, String time_from, String time_to, int isConfirm, String stadiumName, double latitude, double longitude, String fieldName, int isjoin, String image, String username, List<User> user) {
        super(id, match_id, date, time_from, time_to, isConfirm);
        this.stadiumName = stadiumName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fieldName = fieldName;
        this.isjoin = isjoin;
        this.image = image;
        this.username = username;
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int getIsjoin() {
        return isjoin;
    }

    public List<User> getUser() {
        return user;
    }
}
