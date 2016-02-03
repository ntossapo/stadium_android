package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Advance.QuickMatch;

import java.util.List;

/**
 * Created by Tossapon Nuanchuay on 3/2/2559.
 */
public class AllQuickMatchResponse extends Response{
    List<QuickMatch> data;

    public String getStatus() {
        return status;
    }

    public List<QuickMatch> getData() {
        return data;
    }
}
