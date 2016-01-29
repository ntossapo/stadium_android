package com.tossapon.stadiumfinder.Model.Basic;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon on 17/12/2558.
 */
@Parcel
public class Field {
    int id;
    String name;
    int stadium_id;
    String type;

    @ParcelConstructor
    public Field(int id, String name, int stadium_id, String type) {
        this.id = id;
        this.name = name;
        this.stadium_id = stadium_id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStadium_id() {
        return stadium_id;
    }

    public String getType() {
        return type;
    }
}
