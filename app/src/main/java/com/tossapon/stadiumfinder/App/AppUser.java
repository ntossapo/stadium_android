package com.tossapon.stadiumfinder.App;

import com.facebook.AccessToken;
import com.tossapon.stadiumfinder.Model.Basic.User;

/**
 * Created by Tossapon on 20/12/2558.
 */
public class AppUser {
    private static User account;
    private static String facebookId;
    private static String token;
    private static String name;
    private static String picurl;
    public static void setInstance(User user){
        account = user;
        facebookId = new String(account.getFacebook_id());
        token = new String(account.getFacebook_token());
        name = new String(account.getName());
        picurl = new String(account.getPicurl());
    }

    public static User getInstance(){
        if(account == null){
            account = new User(facebookId, token, name, picurl);
        }
        return account;
    }
}
