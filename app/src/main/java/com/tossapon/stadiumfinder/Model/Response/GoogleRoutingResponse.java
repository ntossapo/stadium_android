package com.tossapon.stadiumfinder.Model.Response;

import com.tossapon.stadiumfinder.Model.Util.GoogleRouting.GeocodedWaypoints;
import com.tossapon.stadiumfinder.Model.Util.GoogleRouting.Routes;

import java.util.List;

/**
 * Created by Tossapon on 30/12/2558.
 */
public class GoogleRoutingResponse {
    public List<GeocodedWaypoints> geocoded_waypoints;
    public List<Routes> routes;
    public String status;

}
