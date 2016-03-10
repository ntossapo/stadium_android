package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.PlayerStatResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by benvo_000 on 9/3/2559.
 */
public interface PlayerStatInterface {
    @FormUrlEncoded
    @POST("/friend")
    Call<PlayerStatResponse> getPlayerStat(@Field("id") String userId);
}
