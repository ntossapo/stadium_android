package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.MyJoinResponse;
import com.tossapon.stadiumfinder.Model.Response.Response;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public interface JoinInterface {
    @FormUrlEncoded
    @POST("/myjoin")
    Call<MyJoinResponse> getMyJoin(@Field("facebook_id") String facebookId);

    @FormUrlEncoded
    @POST("/myjoin/delete")
    Call<Response> deleteJoin(@Field("id") int id);
}
