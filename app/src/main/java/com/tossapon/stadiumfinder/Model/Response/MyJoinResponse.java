package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Advance.MyJoin;

import java.util.List;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public class MyJoinResponse extends Response{
    List<MyJoin> data;

    public List<MyJoin> getData() {
        return data;
    }
}
