package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Basic.Field;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by benvo_000 on 26/1/2559.
 */
public class PreReserveResponse {
    String status;
    List<Field> data;

    public String getStatus() {
        return status;
    }
    public List<Field> getData() {
        return data;
    }
}
