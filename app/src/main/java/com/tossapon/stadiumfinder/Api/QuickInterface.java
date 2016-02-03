package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.Response;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Tossapon Nuanchuay on 4/2/2559.
 */
public interface QuickInterface {
    @FormUrlEncoded
    @POST("/quickmatch/join")
    Call<Response> join(@Field("facebook_id")String facebookId, @Field("reserve_id") int reserveId);
}
