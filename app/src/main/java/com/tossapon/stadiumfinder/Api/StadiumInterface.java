package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.StadiumDetailResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Tossapon on 29/12/2558.
 */
public interface StadiumInterface {
    @GET("/stadium/{id}/{type}")
    Call<StadiumDetailResponse> getStadiumDetail(@Path("id") String id, @Path("type") String type);
}
