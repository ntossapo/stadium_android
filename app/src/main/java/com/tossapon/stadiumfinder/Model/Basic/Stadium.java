package com.tossapon.stadiumfinder.Model.Basic;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon on 17/12/2558.
 */

@Parcel
public class Stadium {
    public int id;
    public String name;
    public String image;
    public double rating;
    public int nowPlayer;
    public String tel;
    public String link;
    public String time_open;
    public String time_close;
    public String describe;
    public double latitude;
    public double logitude;


    @ParcelConstructor
    public Stadium(int id, String name, String image, double rating, int nowPlayer, String tel, String link, String time_open, String time_close, String describe, double latitude, double logitude) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.nowPlayer = nowPlayer;
        this.tel = tel;
        this.link = link;
        this.time_open = time_open;
        this.time_close = time_close;
        this.describe = describe;
        this.latitude = latitude;
        this.logitude = logitude;
    }
}
