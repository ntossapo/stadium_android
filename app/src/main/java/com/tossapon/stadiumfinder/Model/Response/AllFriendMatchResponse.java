package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Advance.FriendMatch;

import java.util.List;

/**
 * Created by Tossapon Nuanchuay on 7/2/2559.
 */
public class AllFriendMatchResponse extends Response {
    List<FriendMatch> data;

    public List<FriendMatch> getData() {
        return data;
    }
}
