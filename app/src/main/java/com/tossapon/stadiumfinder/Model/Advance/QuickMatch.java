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

    String stadiumname;
    double latitude;
    double longitude;
    String fieldname;
    int isjoin;
    String image;
    String username;
    List<User> user;

    public QuickMatch(int id, int match_id, String date, String time_from, String time_to, int isConfirm, String stadiumname, double latitude, double longitude, String fieldname, int isjoin, String image, String username, List<User> user) {
        super(id, match_id, date, time_from, time_to, isConfirm);
        this.stadiumname = stadiumname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fieldname = fieldname;
        this.isjoin = isjoin;
        this.image = image;
        this.username = username;
        this.user = user;
    }

    @ParcelConstructor


    public QuickMatch(int id, int match_id, String date, String time_from, String time_to, int isConfirm) {
        super(id, match_id, date, time_from, time_to, isConfirm);
    }

    public String getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public String getStadiumname() {
        return stadiumname;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFieldname() {
        return fieldname;
    }

    public int getIsjoin() {
        return isjoin;
    }

    public List<User> getUser() {
        return user;
    }
}
