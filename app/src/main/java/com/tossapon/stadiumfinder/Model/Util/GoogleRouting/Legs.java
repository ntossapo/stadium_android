package com.tossapon.stadiumfinder.Model.Util.GoogleRouting;

import java.util.List;

/**
 * Created by Tossapon on 30/12/2558.
 */
public class Legs {
    public Distance distance;
    public Duration duration;
    public String end_address;
    public LtLng end_location;
    public String start_address;
    public LtLng start_location;
    public List<Steps> steps;
}
