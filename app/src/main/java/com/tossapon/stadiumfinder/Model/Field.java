package com.tossapon.stadiumfinder.Model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon on 17/12/2558.
 */
@Parcel
public class Field {
    int id;
    int name;
    int stadium_id;
    int type;

    @ParcelConstructor
    public Field(int id, int name, int stadium_id, int type) {
        this.id = id;
        this.name = name;
        this.stadium_id = stadium_id;
        this.type = type;
    }
}
