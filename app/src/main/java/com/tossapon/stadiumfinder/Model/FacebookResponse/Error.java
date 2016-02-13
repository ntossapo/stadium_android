package com.tossapon.stadiumfinder.Model.FacebookResponse;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public class Error {
    String message;
    String type;
    int code;

    public Error(String message, String type, int code) {
        this.message = message;
        this.type = type;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }
}
