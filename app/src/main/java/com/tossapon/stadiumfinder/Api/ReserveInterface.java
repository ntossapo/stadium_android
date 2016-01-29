package com.tossapon.stadiumfinder.Api;

import com.tossapon.stadiumfinder.Model.Response.PreReserveResponse;
import com.tossapon.stadiumfinder.Model.Response.ReserveResponse;
import com.tossapon.stadiumfinder.Model.Response.Response;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by benvo_000 on 25/1/2559.
 */
public interface ReserveInterface {

    @FormUrlEncoded
    @POST("/reserve")
    Call<ReserveResponse> getStadiumDetail(
            @Field("user") String user,
            @Field("time_to") String timeTo,
            @Field("time_from") String timeFrom,
            @Field("date") String date,
            @Field("field") int fieldId);

    @FormUrlEncoded
    @POST("/prereserve")
    Call<PreReserveResponse> getPreReserveData(
      @Field("stadium") int stadiumId,
      @Field("date") String date,
      @Field("time_to") String timeTo,
      @Field("time_from") String timeFrom,
      @Field("type") String type
    );

    @FormUrlEncoded
    @POST("/reserve")
    Call<ReserveResponse> Reserve(
            @Field("user")  String userid,
            @Field("time_to") String timeTo,
            @Field("time_from") String timeFrom,
            @Field("date") String date,
            @Field("field") int field
    );
}
