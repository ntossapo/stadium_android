package com.tossapon.stadiumfinder.Model.Basic;

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
    String time_from;
    String time_to;
    int isConfirm;

    @ParcelConstructor
    public Reserve(int id, int match_id, String date, String time_from, String time_to, int isConfirm) {
        this.id = id;
        this.match_id = match_id;
        this.date = date;
        this.time_from = time_from;
        this.time_to = time_to;
        this.isConfirm = isConfirm;
    }

    public int getId() {
        return id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public String getDate() {
        return date;
    }

    public String getTime_from() {
        return time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public int getIsConfirm() {
        return isConfirm;
    }
}
