package com.tossapon.stadiumfinder.Model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon on 17/12/2558.
 */
@Parcel
public class Reserve {
    int id;
    int match_id;
    String date;
    String from;
    String to;

    @ParcelConstructor
    public Reserve(int id, int match_id, String date, String from, String to) {
        this.id = id;
        this.match_id = match_id;
        this.date = date;
        this.from = from;
        this.to = to;
    }
}
