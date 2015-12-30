package com.tossapon.stadiumfinder.Model.Basic;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon on 17/12/2558.
 */
@Parcel
public class Join {
    int id;
    int match_id;
    int user_id;

    @ParcelConstructor
    public Join(int id, int match_id, int user_id) {
        this.id = id;
        this.match_id = match_id;
        this.user_id = user_id;
    }
}
