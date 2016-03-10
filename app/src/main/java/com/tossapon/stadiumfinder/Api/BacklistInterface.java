package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.Response;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by benvo_000 on 9/3/2559.
 */
public interface BacklistInterface {
    @FormUrlEncoded
    @POST("/atr")
    Call<Response> CheckAvailableToReserve(@Field("id")String facebookId);
}
