package com.tossapon.stadiumfinder.Model.Advance;

import com.tossapon.stadiumfinder.Model.Basic.Reserve;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by benvo_000 on 14/2/2559.
 */
@Parcel
public class TimeTable extends Reserve {

    String name;

    public TimeTable(int id, int match_id, String date, String time_from, String time_to, int isConfirm) {
        super(id, match_id, date, time_from, time_to, isConfirm);
    }

    @ParcelConstructor
    public TimeTable(int id, int match_id, String date, String time_from, String time_to, int isConfirm, String name) {
        super(id, match_id, date, time_from, time_to, isConfirm);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
