package com.tossapon.stadiumfinder.Model.Basic;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by benvo_000 on 27/1/2559.
 */
@Parcel
public class Reservation {
    String date;
    String timeTo;
    String timeFrom;
    int fieldId;
    int stadiumId;

    @ParcelConstructor
    public Reservation(String date, String timeTo, String timeFrom, int fieldId, int stadiumId) {
        this.date = date;
        this.timeTo = timeTo;
        this.timeFrom = timeFrom;
        this.fieldId = fieldId;
        this.stadiumId = stadiumId;
    }

    public String getDate() {
        return date;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public int getFieldId() {
        return fieldId;
    }

    public int getStadiumId() {
        return stadiumId;
    }
}
