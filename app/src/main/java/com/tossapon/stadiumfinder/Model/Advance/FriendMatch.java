package com.tossapon.stadiumfinder.Model.Advance;

import com.tossapon.stadiumfinder.Model.Basic.Reserve;
import com.tossapon.stadiumfinder.Model.Basic.User;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

/**
 * Created by Tossapon Nuanchuay on 7/2/2559.
 */
@Parcel
public class FriendMatch extends Reserve {

    String stadiumname;
    double latitude;
    double longitude;
    String fieldname;
    String image;
    String username;
    List<User> user;

    public FriendMatch(int id, int match_id, String date, String time_from, String time_to, int isConfirm) {
        super(id, match_id, date, time_from, time_to, isConfirm);
    }

    @ParcelConstructor
    public FriendMatch(int id, int match_id, String date, String time_from, String time_to, int isConfirm, String stadiumname, double latitude, double longitude, String fieldname, String image, String username, List<User> user) {
        super(id, match_id, date, time_from, time_to, isConfirm);
        this.stadiumname = stadiumname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fieldname = fieldname;
        this.image = image;
        this.username = username;
        this.user = user;
    }

    public String getUsername() {
        return username;
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

    public String getImage() {
        return image;
    }

    public List<User> getUser() {
        return user;
    }
}
