package com.tossapon.stadiumfinder.Model.FacebookResponse;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public class FacebookNotificationResponse {
    boolean success;
    Error error;

    public FacebookNotificationResponse(boolean success, Error error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public Error getError() {
        return error;
    }
}
