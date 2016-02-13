package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Advance.QuickMatch;
import com.tossapon.stadiumfinder.Model.Response.AllFriendMatchResponse;
import com.tossapon.stadiumfinder.Model.Response.AllQuickMatchResponse;
import com.tossapon.stadiumfinder.Model.Response.AllStadiumResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Tossapon on 23/12/2558.
 */
public interface MainInterface {
    @GET("/stadium/all/{id}/{type}/{lat}/{long}")
    Call<AllStadiumResponse> getStadium(@Path("id") String id, @Path("type") String type, @Path("lat") double lat, @Path("long") double lng);

    @FormUrlEncoded
    @POST("/quickmatch")
    Call<AllQuickMatchResponse> getQuickMatch(@Field("lat") double lat, @Field("long") double lng, @Field("type") String type, @Field("facebookId") String facebookId);

    @FormUrlEncoded
    @POST("/friendplay")
    Call<AllFriendMatchResponse> getFriendMatch(@Field("friends") String fiends, @Field("facebook_id") String facebookId, @Field("type") String type, @Field("lat") double lat, @Field("lng") double lng);
}
