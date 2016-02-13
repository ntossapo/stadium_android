package com.tossapon.stadiumfinder.Model.Advance;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by benvo_000 on 10/2/2559.
 */
@Parcel
public class MyJoin extends MyReserve {
    String ownername;
    String reserverImage;
    int joinId;
    public MyJoin(int id, int match_id, String date, String time_from, String time_to, int isConfirm, double latitude, double longitude, String stadium_name, String field_name, String image) {
        super(id, match_id, date, time_from, time_to, isConfirm, latitude, longitude, stadium_name, field_name, image);
    }

    @ParcelConstructor
    public MyJoin(int id, int match_id, String date, String time_from, String time_to, int isConfirm, double latitude, double longitude, String stadium_name, String field_name, String image, String ownername, String reserverImage, int joinId) {
        super(id, match_id, date, time_from, time_to, isConfirm, latitude, longitude, stadium_name, field_name, image);
        this.ownername = ownername;
        this.reserverImage = reserverImage;
        this.joinId = joinId;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getReserverImage() {
        return reserverImage;
    }

    public int getJoinId() {
        return joinId;
    }
}
