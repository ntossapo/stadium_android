package com.tossapon.stadiumfinder.Model;

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
    public String describe;
    public double latitude;
    public double logitude;

    @ParcelConstructor
    public Stadium(int id, String name, double rating, int nowPlayer, String describe, double latitude, double logitude) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.nowPlayer = nowPlayer;
        this.describe = describe;
        this.latitude = latitude;
        this.logitude = logitude;
    }
}
