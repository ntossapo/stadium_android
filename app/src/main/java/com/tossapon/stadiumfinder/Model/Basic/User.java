package com.tossapon.stadiumfinder.Model.Basic;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by Tossapon on 17/12/2558.
 */

@Parcel
public class User {
    public String facebook_id;
    public String facebook_token;
    public String name;
    public String picurl;

    @ParcelConstructor
    public User(String facebook_id, String facebook_token, String name, String picurl) {
        this.facebook_id = facebook_id;
        this.facebook_token = facebook_token;
        this.name = name;
        this.picurl = picurl;
    }
}
