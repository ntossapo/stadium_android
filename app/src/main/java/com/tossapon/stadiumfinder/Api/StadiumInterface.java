package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.ReserveResponse;
import com.tossapon.stadiumfinder.Model.Response.StadiumDetailResponse;
import com.tossapon.stadiumfinder.Model.Response.TimeTableResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Tossapon on 29/12/2558.
 */
public interface StadiumInterface {
    @GET("/stadium/{id}/{type}")
    Call<StadiumDetailResponse> getStadiumDetail(@Path("id") String id, @Path("type") String type);

    @FormUrlEncoded
    @POST("/stadium/reserve")
    Call<TimeTableResponse> getStadiumReserve(@Field("id") int stadiumId, @Field("type")String type);
}
