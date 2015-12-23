package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.AppModel.AuthResponse;
import com.tossapon.stadiumfinder.AppModel.Response;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Tossapon on 20/12/2558.
 */
public interface AuthInterface {

    @FormUrlEncoded
    @POST("/auth")
    Call<AuthResponse> Auth(
            @Field("facebook_id") String facebook_id,
            @Field("facebook_token") String facebook_token,
            @Field("name") String name,
            @Field("picurl") String picurl);
}
