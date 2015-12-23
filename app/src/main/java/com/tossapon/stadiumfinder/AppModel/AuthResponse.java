package com.tossapon.stadiumfinder.AppModel;

import com.tossapon.stadiumfinder.Model.User;

/**
 * Created by Tossapon on 23/12/2558.
 */
public class AuthResponse extends Response{
    public String status;
    public User data;

    public AuthResponse(String status, User data) {
        this.status = status;
        this.data = data;
    }
}
