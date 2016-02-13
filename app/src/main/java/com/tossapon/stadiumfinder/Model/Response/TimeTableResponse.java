package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Advance.TimeTable;

import java.util.List;

/**
 * Created by benvo_000 on 14/2/2559.
 */
public class TimeTableResponse extends Response{
    List<TimeTable> data;

    public List<TimeTable> getData() {
        return data;
    }
}
