package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Advance.MyReserve;
import com.tossapon.stadiumfinder.Model.Basic.Reserve;

import java.util.List;

/**
 * Created by Tossapon Nuanchuay on 30/1/2559.
 */
public class MyReserveResponse {
    String status;
    List<MyReserve> data;

    public String getStatus() {
        return status;
    }

    public List<MyReserve> getData() {
        return data;
    }
}
