package com.tossapon.stadiumfinder.Model.Basic;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon on 17/12/2558.
 */

@Parcel
public class Match {
    int id;
    int field_id;
    int user_id;
    int price_id;
    int from;
    int to;

    @ParcelConstructor
    public Match(int id, int field_id, int user_id, int price_id, int from, int to) {
        this.id = id;
        this.field_id = field_id;
        this.user_id = user_id;
        this.price_id = price_id;
        this.from = from;
        this.to = to;
    }
}
