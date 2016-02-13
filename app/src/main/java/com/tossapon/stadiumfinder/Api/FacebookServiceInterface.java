package com.tossapon.stadiumfinder.Api;

import com.facebook.FacebookSdkNotInitializedException;
import com.tossapon.stadiumfinder.Model.FacebookResponse.FacebookNotificationResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public interface FacebookServiceInterface {
    @FormUrlEncoded
    @POST("{user}/notifications")
    Call<FacebookNotificationResponse> notificate(
            @Path("user")String user,
            @Field("access_token") String accessToken,
            @Field("href") String href,
            @Field("template")String template);

}
