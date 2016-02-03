package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Basic.Stadium;

import java.util.List;

/**
 * Created by Tossapon on 23/12/2558.
 */
public class AllStadiumResponse extends Response{
    public List<Stadium> data;


    public AllStadiumResponse(String status, List<Stadium> data) {
        this.status = status;
        this.data = data;
    }
}
