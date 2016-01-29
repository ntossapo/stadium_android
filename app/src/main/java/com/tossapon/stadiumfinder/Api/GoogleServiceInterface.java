package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.GoogleRoutingResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Tossapon on 30/12/2558.
 */
public interface GoogleServiceInterface {
    @GET("maps/api/directions/json")
    Call<GoogleRoutingResponse> getRoute(@Query("origin") String org, @Query("destination") String des, @Query("mode") String mode);
}
