package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.AllStadiumResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Tossapon on 23/12/2558.
 */
public interface MainInterface {
    @GET("/stadium/all/{id}/{type}")
    Call<AllStadiumResponse> getStadium(@Path("id") String id, @Path("type") String type);
}
