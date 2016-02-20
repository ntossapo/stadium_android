package com.tossapon.stadiumfinder.App;

import com.tossapon.stadiumfinder.Model.Basic.User;

/**
 * Created by Tossapon on 20/12/2558.
 */
public class AppUser {
    private static User account;
    public static void setInstance(User user){
        account = user;
    }

    public static User getInstance(){
        return account;
    }
    public static User emergencyUser;
}
