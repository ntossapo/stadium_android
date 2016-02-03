package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Basic.User;

/**
 * Created by Tossapon on 23/12/2558.
 */
public class AuthResponse extends Response{
    User data;

    public AuthResponse(User data) {
        this.data = data;
    }

    public User getData() {
        return data;
    }
}
